package com.dazhi.zabbixdata.modules.data.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 最终封装的数据
 */
@Data
public class DataResponse {
    private String hostName;
    private String ip;
    private String itemName;
    private Integer weight;
    private String max;
    private String min;
    private String avg;
    private String diff;
}
