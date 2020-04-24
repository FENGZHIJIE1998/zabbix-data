package com.dazhi.zabbixdata.modules.data.service;

import com.dazhi.zabbixdata.modules.data.entity.Excel;
import com.dazhi.zabbixdata.modules.data.response.DataResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ExcelService {



    Excel createExcel(List<DataResponse> dataResponse, Integer time_from, Integer time_till, HttpServletResponse response);

    Page<Excel> listExcel(Pageable pageable);


}
