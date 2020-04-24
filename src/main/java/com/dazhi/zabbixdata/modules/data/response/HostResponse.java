package com.dazhi.zabbixdata.modules.data.response;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 主机响应对象
 */
@Data
public class HostResponse {
    private String hostid;
    private String host;
    private String name;
    private Map<String,String> interfaces;
}
