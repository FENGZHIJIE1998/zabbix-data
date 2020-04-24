package com.dazhi.zabbixdata.modules.data.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 保存生成EXcel路径
 */
@Data
@Entity
@Table(name = "[excel]")
public class Excel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String url;

    private LocalDateTime createTime;

    public Excel() {
    }

    public Excel(String title, String url, LocalDateTime createTime) {
        this.title = title;
        this.url = url;
        this.createTime = createTime;
    }

}
