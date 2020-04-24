package com.dazhi.zabbixdata.modules.data.dto;

import lombok.Data;

@Data
public class DTO {
    private String jsonrpc = "2.0";
    private String method = "";
    private Object params;
    private Integer id = 1;
    private String auth = null;

    public DTO(Object params, String method) {
        this.params = params;
        this.method = method;
    }

    public DTO(Object params, String method,String auth) {
        this.params = params;
        this.method = method;
        this.auth=auth;
    }
}
