package com.dazhi.zabbixdata.modules.data.dto;

import com.dazhi.zabbixdata.modules.data.dto.child.Filter;
import lombok.Data;

@Data
public class ItemDTO {
    private String hostids;
    private String[] output;
    //  private Search search;
    private Filter filter;
}
