package com.dazhi.zabbixdata.common.uitls;


import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Random;


public class FileUtil {
    //所有文件保存在此目录下 绝对路径
    private static final String MAIN_PATH = "/zabbix_data";
    //excel文件保存的文件夹
    private static final String EXCEL_PATH = "/excel";

    public static String saveExcel(XSSFWorkbook workbook) {
        //5.文件名
        String fileName = getNewFileName("data.xlsx");
        //生成年月日
        String datePath = new SimpleDateFormat("/yyyy/MM/").format(System.currentTimeMillis());
        // 文件绝对路径
        String filepath = MAIN_PATH + EXCEL_PATH + datePath;
        File f = new File(filepath);
        // 如果不存在，生成该路径
        if (!f.exists()) {
            f.mkdirs();
        }
        //文件全路径
        String pathname = null;
        FileOutputStream out = null;
        try {
            //保存文件
            pathname = filepath + fileName;
            File file = new File(pathname);
            out = new FileOutputStream(file);
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EXCEL_PATH + datePath + fileName;
    }


    //获取新文件名,格式为原文件+5位随机数数
    private static String getNewFileName(String fileName) {
        int rannum = (int) (new Random().nextDouble() * (99999 - 10000 + 1)) + 10000; // 获取5位随机数
        String extName = fileName.substring(fileName.lastIndexOf("."));    //获取文件拓展名
        return fileName.substring(0, fileName.lastIndexOf(".")) + rannum + extName;    //新文件名，格式为原文件+5位随机数数
    }


}
