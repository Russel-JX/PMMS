/**
 * ============================================================
 * File : ExcelExportTest.java
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
import java.io.OutputStream;
import java.util.List;

import junit.framework.TestCase;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.Query;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ge.pmms.dao.MaintItemDao;
import com.ge.pmms.po.MaintItem;
import com.ge.pmms.service.CommonService;
import com.ge.pmms.service.MaintItemService;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = {"classpath:/springMVC.xml"})//指代项目在服务器中的路径
public class ExcelExportTest extends TestCase{
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Autowired
    private MaintItemDao maintItemDao;
	@Autowired
    private MaintItemService maintItemService;
	
	@Autowired
    private CommonService commonService;
	
	@org.junit.Test
	@Rollback(false)
	public void test() throws Exception {
		
		OutputStream ops = new FileOutputStream("C:\\Users\\817306\\Desktop\\测试导出Excel2.xls");
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
		cell3.setCellValue("方法");
		cell4.setCellValue("正常状态");
		cell5.setCellValue("备注");
		//创建数据行
		Query query = hibernateTemplate.getSessionFactory().openSession().createQuery("FROM maintItem item where item.equip_name_id=?");
		query.setParameter(0, "enid001");
		@SuppressWarnings("unchecked")
		List<MaintItem> list = query.list();
		for(int i=0;i<list.size();i++){
			MaintItem item = list.get(i);
			Row row = sheet0.createRow(i+1);
			Cell cell1_0 = row.createCell(0);
			Cell cell1_1 = row.createCell(1);
			Cell cell1_2 = row.createCell(2);
			Cell cell1_3 = row.createCell(3);
			Cell cell1_4 = row.createCell(4);
			Cell cell1_5 = row.createCell(5);
			cell1_0.setCellValue(i+1);
			cell1_1.setCellValue(item.getMaint_item());
			cell1_2.setCellValue(item.getCycle());
			cell1_3.setCellValue(item.getMaint_way());
			cell1_4.setCellValue(item.getStandard());
			cell1_5.setCellValue(item.getRemark());
		}
		//写到客户端
		wb.write(ops);
		
//		MaintItem item = new MaintItem();
//		item.setEquip_name_id("enid001");
//		item.setMaint_item("哈哈哈");
//		item.setCycle("Y");
//		item.setMaint_way("1,2,3");
//		item.setStandard("mmmm");
//		hibernateTemplate.save(item);//方法一
//		maintItemDao.save(item);//方法二
//		maintItemService.addMaintItem(item);
//		Query query = hibernateTemplate.getSessionFactory().openSession().createQuery("FROM maintItem item where item.equip_name_id=?");
//		query.setParameter(0, "enid001");
//		@SuppressWarnings("unchecked")
//		List<EquipNameInfo> list = query.list();
		
//		List<EquipNameInfo> list = hibernateTemplate.find("FROM equipNmInfo");//方法三
//		System.out.println("查询EQUIP_NAME_INFO表...");
//		System.out.println("记录数量是："+list.size());
	}
}
