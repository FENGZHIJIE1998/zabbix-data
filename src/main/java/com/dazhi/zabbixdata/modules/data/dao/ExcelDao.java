package com.dazhi.zabbixdata.modules.data.dao;

import com.dazhi.zabbixdata.modules.data.entity.Excel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExcelDao extends JpaRepository<Excel, Integer> {

}
