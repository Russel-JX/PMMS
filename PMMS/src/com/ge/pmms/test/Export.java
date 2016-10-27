/**
 * ============================================================
 * File : Export.java
 * Description : 
 * 
 * Package : com.ge.pmms.test
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Jan 21, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.pmms.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


public class Export {
	public static void main(String[] args) throws IOException {
		OutputStream ops = new FileOutputStream("C:\\Users\\817306\\Desktop\\测试导出Excel.xls");
		//创建Excel
		Workbook wb = new HSSFWorkbook();//工作薄
		Sheet sheet0 = wb.createSheet();//创建sheet页
		Row row0 = sheet0.createRow(0);//创建行
		
		
		//创建表头
		Cell cell0 = row0.createCell(0);
		Cell cell1 = row0.createCell(1);
		Cell cell2 = row0.createCell(2);
		Cell cell3 = row0.createCell(3);
		Cell cell4 = row0.createCell(4);
		Cell cell5 = row0.createCell(5);
		cell0.setCellValue("序号");
		cell1.setCellValue("内容");
		cell2.setCellValue("周期");
		cell3.setCellValue("方法cc");
		cell4.setCellValue("正常状态");
		cell5.setCellValue("备注");
		//创建数据行
		
		
		//写到客户端
		wb.write(ops);
		
	}
}
