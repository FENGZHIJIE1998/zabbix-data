package com.dazhi.zabbixdata.modules.data.service.impl;

import com.dazhi.zabbixdata.modules.data.dao.ItemNameMappingDao;
import com.dazhi.zabbixdata.modules.data.entity.ItemNameMapping;
import com.dazhi.zabbixdata.modules.data.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemNameMappingDao itemNameMappingDao;

    @Override
    public Page<ItemNameMapping> listItem(Pageable pageable) {
        return itemNameMappingDao.findAll(pageable);
    }

    @Override
    public void updateItem(ItemNameMapping itemNameMapping) {
        ItemNameMapping entity = itemNameMappingDao.findById(itemNameMapping.getId()).get();
        entity.setItemName(itemNameMapping.getItemName());
        entity.setMappingName(itemNameMapping.getMappingName());
        entity.setWeight(itemNameMapping.getWeight());
        itemNameMappingDao.saveAndFlush(entity);
    }

    @Override
    public void addItem(ItemNameMapping itemNameMapping) {
        itemNameMapping.setId(null);
        itemNameMappingDao.saveAndFlush(itemNameMapping);
    }

    @Override
    public void deleteItemById(Integer id) {
        itemNameMappingDao.deleteById(id);
    }
}
