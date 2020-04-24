package com.dazhi.zabbixdata.modules.data.dto;

import lombok.Data;

import java.util.List;

@Data
public class HostDTO {
    private String[] output;
    private String[] SelectInterfaces;
}
