package com.dazhi.zabbixdata.modules.data.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazhi.zabbixdata.modules.data.dao.ItemNameMappingDao;
import com.dazhi.zabbixdata.modules.data.dto.*;
import com.dazhi.zabbixdata.modules.data.dto.child.Filter;
import com.dazhi.zabbixdata.modules.data.entity.ItemNameMapping;
import com.dazhi.zabbixdata.common.uitls.HttpClientUtil;
import com.dazhi.zabbixdata.modules.data.dao.ExcelDao;

import com.dazhi.zabbixdata.modules.data.response.DataResponse;
import com.dazhi.zabbixdata.modules.data.response.HistoryResponse;
import com.dazhi.zabbixdata.modules.data.response.HostResponse;
import com.dazhi.zabbixdata.modules.data.response.ItemResponse;
import com.dazhi.zabbixdata.modules.data.service.DataService;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static java.math.BigDecimal.*;

@Service
public class DataServiceImpl implements DataService {

    @Value("${zabbix.url}")
    private String URL;
    @Value("${zabbix.username}")
    private String username;
    @Value("${zabbix.password}")
    private String password;

    //缓存登录凭证
    private static String auth = null;

    private Map<String, ItemNameMapping> map = null;

    // 声明线程池
    private ExecutorService cachedThreadPool = Executors.newFixedThreadPool(11);


    private final ItemNameMappingDao itemNameMappingDao;


    public DataServiceImpl(ItemNameMappingDao itemNameMappingDao, ExcelDao excelDao) {
        this.itemNameMappingDao = itemNameMappingDao;
    }

    /**
     * 获取数据Entity
     *
     * @param time_from
     * @param time_till
     */
    @Override
    public List<DataResponse> getDataEntity(Integer time_from, Integer time_till) {

        List<DataResponse> res = new ArrayList<>();

        //登录获取登录凭证
        if (auth == null) {
            synchronized (this) {
                if (auth == null) {
                    System.out.println("执行登录方法");
                    auth = login();
                }
            }
        }
        // 获取要统计的监控项name
        //初始化对应关系
        String[] name = null;
        map = new HashMap<>();
        List<ItemNameMapping> mapping = getItemNameMapping();
        name = new String[mapping.size()];
        for (int i = 0; i < mapping.size(); i++) {
            name[i] = mapping.get(i).getItemName();
            map.put(mapping.get(i).getItemName(), mapping.get(i));
        }
        //获取所有主机
        List<HostResponse> hostResponseList = getHost();

        List<Future<List<DataResponse>>> futures = new ArrayList<>();
        for (HostResponse hostResponse : hostResponseList) {
            // 多线程分别计算各自主机的数据
            Future<List<DataResponse>> future = cachedThreadPool.submit(new AsynClu(time_from, time_till, hostResponse, name));
            futures.add(future);
        }
        // future.get 会阻塞
        for (Future<List<DataResponse>> future : futures) {
            try {
                List<DataResponse> dataResponses = future.get();
                res.addAll(dataResponses);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }


        return res;
    }

    /**
     * 异步计算各个主机的数据
     */
    class AsynClu implements Callable<List<DataResponse>> {
        private String[] name;
        private Integer time_from;
        private Integer time_till;
        private HostResponse hostResponse;
        List<DataResponse> res = new ArrayList<>();

        public AsynClu(Integer time_from, Integer time_till, HostResponse hostResponse, String[] name) {
            this.name = name;
            this.time_from = time_from;
            this.time_till = time_till;
            this.hostResponse = hostResponse;
        }

        @Override
        public List<DataResponse> call() throws Exception {
            DataResponse dataResponse;
            List<ItemResponse> itemResponseList = getItem(hostResponse.getHostid(), name);
            for (ItemResponse item : itemResponseList) {
                // 封装数据
                dataResponse = new DataResponse();
                dataResponse.setHostName(hostResponse.getName());
                dataResponse.setIp(hostResponse.getInterfaces().get("ip"));
                // 这里设置是Item对应的映射名（别名）
                dataResponse.setItemName(map.get(item.getName()).getMappingName());
                dataResponse.setWeight(map.get(item.getName()).getWeight());
                //获取该监控项的指定范围的历史数据
                List<HistoryResponse> historyResponseList = getHistory(time_from, time_till, item);
                //计算最大最小平均值
                calculate(dataResponse, historyResponseList, item.getUnits());
                res.add(dataResponse);
            }
            //排序itemResponseList
            return res.stream().sorted(new Comparator<DataResponse>() {
                @Override
                public int compare(DataResponse o1, DataResponse o2) {
                    //根据权重排列
                    if (o1.getWeight().compareTo(o2.getWeight()) != 0) {
                        return o1.getWeight().compareTo(o2.getWeight());
                    } else {
                        //权重一样 根据映射名字排列
                        return o1.getItemName().compareTo(o2.getItemName());
                    }
                }
            }).collect(Collectors.toList());
        }
    }

    /**
     * 获取监控项及其映射信息
     *
     * @return
     */
    @Override
    public List<ItemNameMapping> getItemNameMapping() {
        Sort.Order order = Sort.Order.asc("mappingName");
        Sort.Order order2 = Sort.Order.desc("weight");
        Sort sort = Sort.by(order, order2);
        return itemNameMappingDao.findAll(sort);
    }


    /**
     * 登录方法
     */
    private String login() {
        // 指定方法
        String method = "user.login";
        // 设置参数
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setPassword(password);
        loginDTO.setUser(username);
        // 发起请求
        JSONObject jsonObject = sendPost(new DTO(loginDTO, method));
        return jsonObject.getString("result");
    }

    private List<HostResponse> getHost() {
        // 指定方法
        String method = "host.get";
        //设置参数
        HostDTO hostDTO = new HostDTO();
        //设置输出
        String[] output = {"hostid", "host", "name", "ip"};
        String[] selectInterfaces = {"ip"};
        hostDTO.setOutput(output);
        hostDTO.setSelectInterfaces(selectInterfaces);
        // 发起请求
        JSONObject jsonObject = sendPost(new DTO(hostDTO, method, auth));
        String result = jsonObject.getString("result");
        return JSON.parseArray(result, HostResponse.class);
    }

    private List<ItemResponse> getItem(String hostid, String[] name) {
        // 指定方法
        String method = "item.get";
        // 设置参数
        ItemDTO itemDTO = new ItemDTO();
        String[] output = {"itemid", "hostid", "name", "key_", "value_type", "units"};
        itemDTO.setHostids(hostid);
        itemDTO.setOutput(output);
        itemDTO.setFilter(new Filter(name));
        // 发起请求
        JSONObject jsonObject = sendPost(new DTO(itemDTO, method, auth));
        String result = jsonObject.getString("result");
        return JSON.parseArray(result, ItemResponse.class);
    }

    private List<HistoryResponse> getHistory(Integer time_from, Integer time_till, ItemResponse item) {
        String method = "history.get";
        HistoryDTO historyDTO = new HistoryDTO();
        String[] output = {"value"};
        historyDTO.setOutput(output);
        historyDTO.setHistory(Integer.valueOf(item.getValue_type()));
        historyDTO.setItemids(item.getItemid());
        historyDTO.setTime_from(time_from);
        historyDTO.setTime_till(time_till);
        // 发起请求
        JSONObject jsonObject = sendPost(new DTO(historyDTO, method, auth));
        String result = jsonObject.getString("result");
        return JSON.parseArray(result, HistoryResponse.class);

    }


    /**
     * 发起请求 并将结果 解析为JsonObject
     */
    private JSONObject sendPost(DTO dto) {
        HttpEntity httpEntity = HttpClientUtil.doPost(URL, dto);
        String s = "";
        try {
            s = EntityUtils.toString(httpEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //解析json字符串
        return JSON.parseObject(s);
    }

    /**
     * 单位缓存用
     */
    private final static BigDecimal gb = new BigDecimal("1073741824");
    private final static BigDecimal mb = new BigDecimal("1048576");
    private final static BigDecimal kb = new BigDecimal("1024");

    private DataResponse calculate(DataResponse dataResponse, List<HistoryResponse> historyResponseList, String units) {

        //如果size=0 直接返回0
        if (historyResponseList.size() == 0) {
            dataResponse.setAvg("0");
            dataResponse.setMax("0");
            dataResponse.setMin("0");
            return dataResponse;
        }

        if (dataResponse.getItemName().equals("Total memory")) {
            System.out.println();
        }
        HistoryResponse first = historyResponseList.get(0);
        // 如果 值是文本类型，直接返回文本
        if (!first.getValue().matches("(-?\\d+)(\\.\\d+)?$")) {
            dataResponse.setAvg(first.getValue());
            dataResponse.setMax(first.getValue());
            dataResponse.setMin(first.getValue());
            return dataResponse;
        }
        // 此处初始最大最小值不能使用Integer.MAX_VALUE/MIN_VALUE 因为历史记录比这个值要大
        BigDecimal min = new BigDecimal(first.getValue());
        BigDecimal max = new BigDecimal(first.getValue());
        BigDecimal sum = ZERO;
        BigDecimal length = new BigDecimal(historyResponseList.size());
        BigDecimal value = null;
        for (HistoryResponse historyResponse : historyResponseList) {
            value = new BigDecimal(historyResponse.getValue());
            if (value.compareTo(max) > 0) {
                max = value;
            }
            if (value.compareTo(min) < 0) {
                min = value;
            }
            sum = sum.add(value);
        }
        // 计算平均值
        BigDecimal avg = sum.divide(length, 2, ROUND_HALF_UP);
        // 计算最大与最小的差值，因为部分监控的是历史总量，差值才是业务需求
        BigDecimal diff = max.subtract(min);

        //单位换算并没有太好的想法 日后更正
        //单位设置
        String maxU = units;
        String minU = units;
        String avgU = units;
        String diffU = units;
        // 单位换算
        if ("B".equals(units)) {
            //  如果单位是B 转为GB
            // 如果值大于1GB 转换为GB
            if (max.compareTo(gb) > 0) {
                max = max.divide(gb, 2, ROUND_HALF_UP);
                maxU = "GB";
            } else {
                max = max.divide(mb, 2, ROUND_HALF_UP);
                maxU = "MB";
            }
            if (min.compareTo(gb) > 0) {
                min = min.divide(gb, 2, ROUND_HALF_UP);
                minU = "GB";
            } else {
                min = min.divide(mb, 2, ROUND_HALF_UP);
                minU = "MB";
            }
            if (avg.compareTo(gb) > 0) {
                avg = avg.divide(gb, 2, ROUND_HALF_UP);
                avgU = "GB";
            } else {
                avg = avg.divide(mb, 2, ROUND_HALF_UP);
                avgU = "MB";
            }
            if (diff.compareTo(gb) > 0) {
                diff = diff.divide(gb, 2, ROUND_HALF_UP);
                diffU = "GB";
            } else {
                diff = diff.divide(mb, 2, ROUND_HALF_UP);
                diffU = "MB";
            }

        } else if ("bps".equals(units)) {
            //当 最大值 小于 10Mbs 并且 平均值 小于 1Mbs 转换为 KBS
            if (max.compareTo(new BigDecimal("10485760")) < 0 && avg.compareTo(mb) < 0) {
                max = max.divide(kb, 2, ROUND_HALF_UP);
                min = min.divide(kb, 2, ROUND_HALF_UP);
                avg = avg.divide(kb, 2, ROUND_HALF_UP);
                diff = diff.divide(kb, 2, ROUND_HALF_UP);
                maxU = "Kbs";
                minU = "Kbs";
                avgU = "Kbs";
                diffU = "Kbs";
            } else {
                max = max.divide(mb, 2, ROUND_HALF_UP);
                min = min.divide(mb, 2, ROUND_HALF_UP);
                avg = avg.divide(mb, 2, ROUND_HALF_UP);
                diff = diff.divide(mb, 2, ROUND_HALF_UP);
                maxU = "Mbs";
                minU = "Mbs";
                avgU = "Mbs";
                diffU = "Mbs";
            }
        } else if ("".equals(units)) {
            //如果没有单位的就取整
            max = max.setScale(0, ROUND_HALF_UP);
            min = min.setScale(0, ROUND_HALF_UP);
            avg = avg.setScale(0, ROUND_HALF_UP);
            diff = diff.setScale(0, ROUND_HALF_UP);
        } else {
            // 其余单位 保留两位小数
            max = max.setScale(2, ROUND_HALF_UP);
            min = min.setScale(2, ROUND_HALF_UP);
            avg = avg.setScale(2, ROUND_HALF_UP);
            diff = diff.setScale(2, ROUND_HALF_UP);
        }
        dataResponse.setAvg(avg.toString() + " " + avgU);
        dataResponse.setMax(max.toString() + " " + maxU);
        dataResponse.setMin(min.toString() + " " + minU);
        dataResponse.setDiff(diff.toString() + " " + diffU);
        return dataResponse;

    }

}
