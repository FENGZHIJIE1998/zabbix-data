package com.dazhi.zabbixdata.modules.data.service;

import com.dazhi.zabbixdata.modules.data.entity.ItemNameMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemService {

    Page<ItemNameMapping> listItem(Pageable pageable);

    void updateItem(ItemNameMapping itemNameMapping);

    void addItem(ItemNameMapping itemNameMapping);

    void deleteItemById(Integer id);
}
