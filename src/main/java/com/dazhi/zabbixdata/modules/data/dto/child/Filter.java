package com.dazhi.zabbixdata.modules.data.dto.child;

import lombok.Data;

@Data
public class Filter {
    private String[] name;

    public Filter(String[] name) {
        this.name = name;
    }
}
