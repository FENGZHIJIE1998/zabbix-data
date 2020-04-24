package com.dazhi.zabbixdata.modules.data.response;

import lombok.Data;

/**
 * 监控项响应对象
 */
@Data
public class ItemResponse  {
    private String itemid;
    private String hostid;
    private String name;
    private String key_;
    private String value_type;
    private String units;
}
