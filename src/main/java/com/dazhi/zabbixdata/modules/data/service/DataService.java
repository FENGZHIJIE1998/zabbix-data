package com.dazhi.zabbixdata.modules.data.service;

import com.dazhi.zabbixdata.modules.data.entity.ItemNameMapping;
import com.dazhi.zabbixdata.modules.data.response.DataResponse;

import java.util.List;

public interface DataService {

    List<DataResponse> getDataEntity(Integer time_from, Integer time_till);

    List<ItemNameMapping> getItemNameMapping();

}
