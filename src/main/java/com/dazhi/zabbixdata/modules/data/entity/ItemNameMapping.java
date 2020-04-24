package com.dazhi.zabbixdata.modules.data.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 名字映射表
 */
@Data
@Entity
@Table(name = "[item_name_mapping]")
public class ItemNameMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * zabbix里的监控项名
     */
    private String itemName;
    /**
     * 映射名
     */
    private String mappingName;

    /**
     * 权重
     */
    private Integer weight;
}
