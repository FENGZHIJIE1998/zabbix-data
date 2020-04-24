package com.dazhi.zabbixdata.modules.data.dto.child;

import lombok.Data;

@Data
public class Search {
    private String name;

    public Search(String name) {
        this.name = name;
    }
}
