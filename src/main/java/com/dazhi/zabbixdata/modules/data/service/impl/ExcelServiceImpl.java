package com.dazhi.zabbixdata.modules.data.service.impl;

import com.dazhi.zabbixdata.modules.data.dao.ExcelDao;
import com.dazhi.zabbixdata.modules.data.entity.Excel;
import com.dazhi.zabbixdata.modules.data.service.ExcelService;
import com.dazhi.zabbixdata.common.uitls.FileUtil;
import com.dazhi.zabbixdata.modules.data.response.DataResponse;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class ExcelServiceImpl  implements ExcelService {

    private final ExcelDao excelDao;

    public ExcelServiceImpl(ExcelDao excelDao) {
        this.excelDao = excelDao;
    }

    /**
     * 生成Excel
     *
     * @return
     */
    @Override
    public Excel createExcel(List<DataResponse> list, Integer time_from, Integer time_till, HttpServletResponse response) {

        // 把时间戳转为日期
        LocalDate startTime = LocalDateTime.ofEpochSecond(time_from, 0, ZoneOffset.ofHours(8)).toLocalDate();
        LocalDate endTime = LocalDateTime.ofEpochSecond(time_till, 0, ZoneOffset.ofHours(8)).toLocalDate();

        // 1.创建Excel文件
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 2.创建工作簿
        XSSFSheet sheet = workbook.createSheet("服务器数据");
        // 3.创建大标题
        createBigTitle(workbook, sheet, startTime, endTime);
        // 4.填充数据
        createRow(workbook, sheet, list);


        // 设置表格每一列自适应大小
        for (int i = 0; i < 6; i++) {
            sheet.autoSizeColumn(i);
        }
        // 将文件持久化到硬盘
        String url = FileUtil.saveExcel(workbook);
        String title = startTime + "~" + endTime + "数据.xlsx";
        // 将记录保存到数据库
        Excel excel = new Excel(title, url, LocalDateTime.now());
        excelDao.save(excel);
        return excel;
    }

    @Override
    public Page<Excel> listExcel(Pageable pageable) {
        return excelDao.findAll(pageable);
    }

    /**
     * 创建大标题
     */
    private void createBigTitle(XSSFWorkbook workbook, XSSFSheet sheet, LocalDate startTime, LocalDate endTime) {
        //合并单元格
        CellRangeAddress cellRange1 = new CellRangeAddress(0, 3, (short) 0, (short) 5);
        sheet.addMergedRegion(cellRange1);
        //格式
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.HAIR);
        cellStyle.setBorderLeft(BorderStyle.HAIR);
        cellStyle.setBorderRight(BorderStyle.HAIR);
        cellStyle.setBorderTop(BorderStyle.HAIR);
        //创建字体格式
        XSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 18);
        font.setBold(true);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        cellStyle.setFont(font);
        XSSFRow bigTitleRow = sheet.createRow(0);
        XSSFCell bigTitle = bigTitleRow.createCell(0);
        bigTitle.setCellValue(startTime + " ~ " + endTime + "报告");
        bigTitle.setCellStyle(cellStyle);
        sheet.createRow(3);
    }


    /**
     * 创建标题
     */
    private void createTile(XSSFWorkbook XSSfWorkbook, XSSFSheet sheet) {
        //格式
        XSSFCellStyle cellStyle = XSSfWorkbook.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.MEDIUM);
        cellStyle.setBorderLeft(BorderStyle.MEDIUM);
        cellStyle.setBorderRight(BorderStyle.MEDIUM);
        cellStyle.setBorderTop(BorderStyle.MEDIUM);

        cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中

        //创建字体格式
        XSSFFont font = XSSfWorkbook.createFont();
        font.setBold(true);
        int lastRowNum = sheet.getLastRowNum();
        XSSFRow titleRow = sheet.createRow(lastRowNum + 1);
        //设置行高
        titleRow.setHeightInPoints(20);

        XSSFCell hostTitle = titleRow.createCell(0);
        hostTitle.setCellValue("               ");
        //hostTitle.setCellStyle(cellStyle);

        XSSFCell itemTitle = titleRow.createCell(1);
        itemTitle.setCellValue("监控项");
        itemTitle.setCellStyle(cellStyle);

        XSSFCell maxTitle = titleRow.createCell(2);
        maxTitle.setCellValue("最大值");
        maxTitle.setCellStyle(cellStyle);

        XSSFCell minTitle = titleRow.createCell(3);
        minTitle.setCellValue("最小值");
        minTitle.setCellStyle(cellStyle);

        XSSFCell diffTitle = titleRow.createCell(4);
        diffTitle.setCellValue("差值");
        diffTitle.setCellStyle(cellStyle);

        XSSFCell avgTitle = titleRow.createCell(5);
        avgTitle.setCellValue("平均值");
        avgTitle.setCellStyle(cellStyle);


    }

    /**
     * 创建每一行数据
     */
    private void createRow(XSSFWorkbook XSSfWorkbook, XSSFSheet sheet, List<DataResponse> list) {
        //格式
        XSSFCellStyle cellStyle = XSSfWorkbook.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中

        //记录当前行的主机值
        String hostName = "";
        String ip = "";
        //遍历数据
        int lastRowNum = 0;
        for (DataResponse dataResponse : list) {
            //如果hostname不等于上一行hostname 证明是一个新的主机，就重新生成一行标题
            if (!hostName.equals(dataResponse.getHostName())) {
                //获取最后一行的行号
                lastRowNum = sheet.getLastRowNum();
                //合并单元格
                CellRangeAddress cellRange1 = new CellRangeAddress(lastRowNum + 1, lastRowNum + 1, (short) 0, (short) 5);
                sheet.addMergedRegion(cellRange1);
                //创建一行新行 填hostname
                XSSFRow dataRow = sheet.createRow(lastRowNum + 1);
                hostName = dataResponse.getHostName();
                ip = dataResponse.getIp();
                XSSFCell hostCell = dataRow.createCell(0);
                hostCell.setCellValue("主机名： " + hostName + " IP: " + ip);
                hostCell.setCellStyle(cellStyle);
                dataRow.setHeightInPoints(20);
                //创建标题栏
                createTile(XSSfWorkbook, sheet);
            }

            lastRowNum = sheet.getLastRowNum();
            XSSFRow dataRow = sheet.createRow(lastRowNum + 1);
            //设置行高
            dataRow.setHeightInPoints(20);
            //设置表格值
            XSSFCell itemCell = dataRow.createCell(1);
            itemCell.setCellValue(dataResponse.getItemName());
            itemCell.setCellStyle(cellStyle);
            XSSFCell maxCell = dataRow.createCell(2);
            maxCell.setCellValue(dataResponse.getMax());
            maxCell.setCellStyle(cellStyle);
            XSSFCell minCell = dataRow.createCell(3);
            minCell.setCellValue(dataResponse.getMin());
            minCell.setCellStyle(cellStyle);
            XSSFCell diffCell = dataRow.createCell(4);
            diffCell.setCellValue(dataResponse.getDiff());
            diffCell.setCellStyle(cellStyle);
            XSSFCell avgCell = dataRow.createCell(5);
            avgCell.setCellValue(dataResponse.getAvg());
            avgCell.setCellStyle(cellStyle);


        }
    }

}
