package com.dazhi.zabbixdata.modules.data.dao;

import com.dazhi.zabbixdata.modules.data.entity.ItemNameMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemNameMappingDao extends JpaRepository<ItemNameMapping, Integer> {
    List<ItemNameMapping> findAllByOrderByMappingName();
}
