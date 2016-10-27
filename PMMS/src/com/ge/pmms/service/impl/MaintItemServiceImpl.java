/**
 * ============================================================
 * File : MaintItemServiceImpl.java
 * Description : 
 * 
 * Package : com.ge.pmms.service.impl
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Jan 13, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.pmms.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ge.pmms.base.BaseService;
import com.ge.pmms.base.PmmsException;
import com.ge.pmms.base.ServiceReturns;
import com.ge.pmms.dao.MaintItemDao;
import com.ge.pmms.po.EquipNameInfo;
import com.ge.pmms.po.MaintItem;
import com.ge.pmms.service.CommonService;
import com.ge.pmms.service.MaintItemService;
import com.ge.pmms.utils.Constants;
import com.ge.pmms.utils.Tools;


/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Jan 13, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
@Service
@Transactional
public class MaintItemServiceImpl extends BaseService<MaintItem> implements MaintItemService {
	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Autowired
	private MaintItemDao maintItemDao;
	@Autowired
	private CommonService commonService;
	
	
	
	public ServiceReturns findEquipNamesByEquipType(String equipType)throws PmmsException{
		serviceReturns = new ServiceReturns();
		List<EquipNameInfo> list = commonService.getEquipNameList(equipType);
		try{
			serviceReturns.putData("list",list);
			serviceReturns.setsEcho(2);
			serviceReturns.setiTotalRecords(10);
			serviceReturns.setiTotalDisplayRecords(10);
			
		}catch(Exception e){
			LOGGER.error("findEquipNamesByEquipType() method occures exception......Exception:"+e.getMessage());
		}
		
		return serviceReturns;
	}
	
	public ServiceReturns findMaintItemsByName(String equipNameID)throws PmmsException{
		serviceReturns = new ServiceReturns();
		try{
			List<MaintItem> list = maintItemDao.findMaintItemsByName(equipNameID);
			serviceReturns.putData("list",list);
			serviceReturns.setsEcho(2);
			serviceReturns.setiTotalRecords(10);
			serviceReturns.setiTotalDisplayRecords(10);
			
		}catch(Exception e){
			LOGGER.error("findMaintItemsByName() method occures exception......Exception:"+e.getMessage());
		}
		
		return serviceReturns;
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = false)
	public ServiceReturns addMaintItem(MaintItem maintItem)throws PmmsException{
		serviceReturns = new ServiceReturns();
		try{
			maintItemDao.addMaintItemByName(maintItem);
			serviceReturns.putData("detail",maintItem);
		}catch(Exception e){
			LOGGER.error("addMaintItem() method occures exception......Exception:"+e.getMessage());
		}
		
		return serviceReturns;
	}
	
	public ServiceReturns modifyMaintItem(MaintItem maintItem)throws PmmsException{
		serviceReturns = new ServiceReturns();
		MaintItem originalMI = maintItemDao.getByIdNoSession(MaintItem.class,
				maintItem.getId());
		BeanUtils.copyProperties(originalMI, maintItem, new String[] {
				"maint_item", "cycle", "maint_way", "standard", "updater",
				"last_updated_date", "remark"});
		//relase object in session
		hibernateTemplate.evict(originalMI);
		try{
			maintItemDao.modifyMaintItem(maintItem);
			serviceReturns.putData("detail",maintItem);
		}catch(Exception e){
			LOGGER.error("modifyMaintItem() method occures exception......Exception:"+e.getMessage());
		}
		
		return serviceReturns;
	}
	
	public ServiceReturns removeMaintItems(String[] ids)throws PmmsException{
		serviceReturns = new ServiceReturns();
		Integer[] ids2 = new Integer[ids.length];
		for (int i = 0; i < ids.length; i++) {
			ids2[i] = Integer.parseInt(ids[i]);
		}
		int effectedRowNum = 0;
		try {
			effectedRowNum = maintItemDao.removeMaintItems(ids2);
		}
		catch (Exception e) {
			LOGGER.error("removeMaintItems() method occures exception......Exception:"+e.getMessage());
		}
		serviceReturns.putData("effectedRowNum",effectedRowNum);
		return serviceReturns;
	}
	
	@SuppressWarnings("deprecation")
	public void exportToExcel(String equipNameId,HttpServletResponse response)throws PmmsException{
		String sheetName = "";
		String fileName = "Equip_Maint_Item";
		String equipName = "";
		
		
		try {
			List<MaintItem> list = maintItemDao.findMaintItemsByName(equipNameId);
			List<EquipNameInfo> nameList = maintItemDao.findEquipNameInfoById(equipNameId);
			if(!CollectionUtils.isEmpty(list)){
				sheetName = list.get(0).getEquip_name_id()+"_Maint_Items";
				equipName = nameList.get(0).getEquipmentName();
			}else{
				sheetName = "Maint_Items";
			}
			HSSFWorkbook wb = new HSSFWorkbook();//工作薄
			HSSFSheet sheet0 = wb.createSheet(sheetName);
			HSSFRow rowFirst = sheet0.createRow(0);//创建第一行说明
			HSSFRow row1 = sheet0.createRow(1);//创建表头行
			//第一行表格说明
			HSSFFont font0 = (HSSFFont) wb.createFont();
			HSSFFont font1 = (HSSFFont) wb.createFont();
			font0.setFontHeightInPoints((short)12);
			font0.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font0.setFontName("宋体");
			font1.setFontHeightInPoints((short)8);
			font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font1.setFontName("宋体");
			font1.setItalic(true);
			HSSFCellStyle csHead0 = (HSSFCellStyle) wb.createCellStyle();
			HSSFCellStyle csHead1 = (HSSFCellStyle) wb.createCellStyle();
			csHead0.setFont(font0);
			csHead1.setFont(font1);
			csHead0.setAlignment(CellStyle.ALIGN_CENTER);
			rowFirst.createCell(0).setCellValue("设备维护保养计划  PMP --"+equipName);
			Cell cell = rowFirst.createCell(6);
			cell.setCellValue("Y：年度保养；Q：季度保养；M：月度保养");
			Tools.setCellStyleForEachCell(rowFirst, csHead0);
			cell.setCellStyle(csHead1);
			sheet0.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
			
			//创建表头
			HSSFFont headFont = (HSSFFont) wb.createFont();
			headFont.setFontHeightInPoints((short)11);
			headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			
			HSSFCellStyle csHead = (HSSFCellStyle) wb.createCellStyle();
			csHead.setFont(headFont);
			csHead.setAlignment(CellStyle.ALIGN_CENTER);
			row1.createCell(0).setCellValue("序号");
			row1.createCell(1).setCellValue("内容");
			row1.createCell(2).setCellValue("周期");
			row1.createCell(3).setCellValue("方法");
			row1.createCell(4).setCellValue("正常状态");
			row1.createCell(5).setCellValue("备注");
			Tools.setCellStyleForEachCell(row1, csHead);
			//创建数据行
			for(int i=0;i<list.size();i++){
				MaintItem item = list.get(i);
				Row row = sheet0.createRow(i+2);
				row.createCell(0).setCellValue(i+1);
				row.createCell(1).setCellValue(item.getMaint_item());
				row.createCell(2).setCellValue(item.getCycle());
				row.createCell(3).setCellValue(item.getMaint_way());
				row.createCell(4).setCellValue(item.getStandard());
				row.createCell(5).setCellValue(item.getRemark());
			}
			
			//创建表格下方说明
			int rowHelpIndex = list.size()+3;
			HSSFRow rowHelp = sheet0.createRow(rowHelpIndex);
			rowHelp.createCell(0).setCellValue(Constants.MAINTITEMHELP);
			Tools.setCellStyleForEachCell(rowHelp, csHead0);
			sheet0.addMergedRegion(new CellRangeAddress(rowHelpIndex, rowHelpIndex, 0, 10));
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			wb.write(bos);
			byte[] bytes = bos.toByteArray();
			
			Calendar cal = Calendar.getInstance();
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName+"_"
					+ cal.get(Calendar.MONTH) + ""
					+ cal.get(Calendar.DATE) +""
					+ cal.get(Calendar.YEAR) + ""
					+ cal.get(Calendar.HOUR_OF_DAY)
					+""+""
					+ cal.get(Calendar.MINUTE) +""
					+ cal.get(Calendar.SECOND) + ".xls");
			response.setContentType("application/ms-excel; charset=UTF-8");  
			response.setContentLength(bytes.length);
			response.setHeader("Expires", "0");
			
			OutputStream os = response.getOutputStream(); 
			os.write(bytes);
			os.flush();
			os.close();
			bos.close();
		}
		catch (IOException e) {
			LOGGER.error("exportToExcel() method occures exception......Exception:"+e.getMessage());
		}
		
	}
	
	public HibernateTemplate getHiberanteTemplate() {
		return hibernateTemplate;
	}

	public void setHiberanteTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
}
