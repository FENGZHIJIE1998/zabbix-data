package com.dazhi.zabbixdata.modules.data.controller;


import com.dazhi.zabbixdata.modules.data.entity.Excel;
import com.dazhi.zabbixdata.common.uitls.Result;
import com.dazhi.zabbixdata.modules.data.response.DataResponse;
import com.dazhi.zabbixdata.modules.data.service.DataService;
import com.dazhi.zabbixdata.modules.data.service.ExcelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class DataController {


    private final DataService dataService;
    private final ExcelService excelService;

    public DataController(DataService dataService, ExcelService excelService) {
        this.dataService = dataService;
        this.excelService = excelService;
    }

    @ApiOperation(value = "下载Excel", notes = "参数: 无", produces = "application/octet-stream")
    @GetMapping("/getExcel")
    public Result getExcel(Integer time_from, Integer time_till, HttpServletResponse response) {
        long l = System.currentTimeMillis();
        List<DataResponse> dataResponse = dataService.getDataEntity(time_from, time_till);
        Excel excel = excelService.createExcel(dataResponse, time_from, time_till, response);
        long l1 = System.currentTimeMillis();
        System.out.println(((l1 - l) / 1000) + "s");
        return Result.ok(excel);
    }


    @ApiOperation(value = "列出excel记录")
    @GetMapping("/listExcel")
    public Result listExcel(@RequestParam(defaultValue = "0") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Excel> excelPage = excelService.listExcel(pageable);
        return Result.ok(excelPage);
    }


}

