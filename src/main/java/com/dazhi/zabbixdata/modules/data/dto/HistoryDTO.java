package com.dazhi.zabbixdata.modules.data.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistoryDTO {
    private String[] output;
    private Integer history;
    private String itemids;
    private String sortfield = "clock";
    private String sortorder = "DESC";

    private Integer time_from;
    private Integer time_till;
}
