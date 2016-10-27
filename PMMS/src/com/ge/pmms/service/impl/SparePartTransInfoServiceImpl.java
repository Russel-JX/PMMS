/**
 * ============================================================
 * File : SparePartTransInfoServiceImpl.java
 * Description : 
 * 
 * Package : com.ge.pmms.service.impl
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Feb 5, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.pmms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ge.pmms.base.BaseService;
import com.ge.pmms.base.PmmsException;
import com.ge.pmms.base.ServiceReturns;
import com.ge.pmms.dao.SparePartTransInfoDao;
import com.ge.pmms.po.SafetyStockInfo;
import com.ge.pmms.po.SparePartTransInfo;
import com.ge.pmms.service.SparePartTransInfoService;
import com.ge.pmms.utils.Constants;
import com.ge.pmms.utils.Tools;

/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Feb 5, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
@Service
@Transactional
public class SparePartTransInfoServiceImpl extends BaseService<SparePartTransInfo> implements SparePartTransInfoService {
	
	@Autowired
	private SparePartTransInfoDao sparePartTransInfoDao;
	
	private List<SparePartTransInfo> excelSPTransList;
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public ServiceReturns getSPTrans(SparePartTransInfo sparePartTransInfo) throws PmmsException {
		try {
			serviceReturns = new ServiceReturns();
			List<SparePartTransInfo>  spTransList = sparePartTransInfoDao.getSPTrans(sparePartTransInfo);
			excelSPTransList = spTransList;
			serviceReturns.putData("spTransList", spTransList);
		}
		catch (Exception e) {
			Tools.getExceptionServiceReturns(e);
			LOGGER.info(e);
		}
		return serviceReturns;
	}
	@Override
	public ServiceReturns getSPTransByWOID(String workOrderId)
			throws PmmsException {
		try {
			serviceReturns = new ServiceReturns();
			serviceReturns.putData("spareParts", sparePartTransInfoDao.getSPTransByWOID(workOrderId));
		}catch(Exception e){
			Tools.getExceptionServiceReturns(e);
			LOGGER.info(e);
		}
		return serviceReturns;
	}
	@SuppressWarnings("deprecation")
	@Override
	public HSSFWorkbook spTransExportToExcel() throws PmmsException {
		//第一步，创建一个workbook,对应一个Excel文件
		HSSFWorkbook hwb = new HSSFWorkbook();
		//第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = hwb.createSheet("Spare Part Info");
		//第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		int rowNum = 1;
		
		//第一行表格说明
		HSSFRow firtRow = sheet.createRow(0);
		HSSFFont font0 = (HSSFFont) hwb.createFont();
		font0.setFontHeightInPoints((short)12);
		font0.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font0.setFontName("宋体");
		HSSFCellStyle csHead0 = (HSSFCellStyle) hwb.createCellStyle();
		csHead0.setFont(font0);
		csHead0.setAlignment(CellStyle.ALIGN_CENTER);
		firtRow.createCell(0).setCellValue("备件出入库记录");
		Tools.setCellStyleForEachCell(firtRow, csHead0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
		
				
		HSSFRow title = sheet.createRow(rowNum);
		//设置字体
		HSSFFont headFont = hwb.createFont();
		headFont.setFontHeightInPoints((short)11);        //字体大小
		headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //字体加粗
		//headFont.setColor(HSSFColor.BLUE.index);        //字体颜色
		//第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle csHead = hwb.createCellStyle();
		csHead.setFont(headFont);                         //设置的字体放里面
		csHead.setAlignment(CellStyle.ALIGN_CENTER);	  //左右居中
		
		title.createCell(0).setCellValue("流水号");
		title.createCell(1).setCellValue("备件名称");
		title.createCell(2).setCellValue("备件编号");
		title.createCell(3).setCellValue("规格型号");
		title.createCell(4).setCellValue("单位");
		title.createCell(5).setCellValue("单价(RMB)");
		title.createCell(6).setCellValue("数量");
		title.createCell(7).setCellValue("出库/入库");
		title.createCell(8).setCellValue("操作时间");
		title.createCell(9).setCellValue("操作人");
		title.createCell(10).setCellValue("备注");
		Tools.setCellStyleForEachCell(title, csHead);
		LOGGER.info("set head each cell style");
		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
		HSSFRow temp = null;
		for(SparePartTransInfo partTransInfo : excelSPTransList){
			temp = sheet.createRow((short) ++ rowNum);
			temp.createCell(0).setCellValue(partTransInfo.getTransId());
			temp.createCell(1).setCellValue(partTransInfo.getSparePartName());
			temp.createCell(2).setCellValue(partTransInfo.getSparePartId());
			temp.createCell(3).setCellValue(partTransInfo.getSparePartType());
			temp.createCell(4).setCellValue(partTransInfo.getMeasurement());
			temp.createCell(5).setCellValue(partTransInfo.getPrice());
			temp.createCell(6).setCellValue(partTransInfo.getAmount());
			String flag = partTransInfo.getTransType();
			if(Constants.TRANS_TYPE_IN.equals(flag)){
				temp.createCell(7).setCellValue(Constants.TRANS_TYPE_IN_DESC);
			}else{
				temp.createCell(7).setCellValue(Constants.TRANS_TYPE_OUT_DESC);
			}
			temp.createCell(8).setCellValue(partTransInfo.getStrCreatedDate());
			temp.createCell(9).setCellValue(partTransInfo.getUpdater());
			temp.createCell(10).setCellValue(partTransInfo.getRemark());
		}
		return hwb;
	}
	@Override
	public ServiceReturns updateSysSafetyStock() throws PmmsException {
		serviceReturns = new ServiceReturns();
		try{
		//今天
		Date currentDate = Tools.getToday();
		//昨天
		Date lastDate = Tools.getLastDate(currentDate);
		//从昨天往前推两年的日期
		Date lastTwoYrDate = Tools.getLastTwoYrDate(lastDate);
		//为计算STD准备数据
		List<SafetyStockInfo> stdList = sparePartTransInfoDao.getSTDPreparedData(lastTwoYrDate,lastDate);
		//STD的map
		Map<String, Double> stdMap = calculateSTD(stdList);
		//L的map
		Map<String, Double> lMap = sparePartTransInfoDao.getLeadTime(lastTwoYrDate, lastDate);
		//所有备件的备件编号
		List<String> spIdList = sparePartTransInfoDao.getSPIds();
		//计算安全库存
		List<SafetyStockInfo> ssList = calculateSafetyStock(spIdList,stdMap, lMap);
		sparePartTransInfoDao.updateSS(ssList);
		}catch(Exception e){
			Tools.getExceptionServiceReturns(e);
			//e.printStackTrace();
		}
		return serviceReturns;
	}
	
	public  Map<String, Double> calculateSTD(List<SafetyStockInfo> list){
		Map<String, Double> stdMap = new HashMap<String, Double>();       //k是备件编号，v是对应的STD
		if(!CollectionUtils.isEmpty(list)){
			SafetyStockInfo ssInfo = null;
			String sparePartId = "";
			int num = 0;
			double sums = 0;
			double avgAmount = 0;
			Double STD = null;
			Iterator<SafetyStockInfo> itor = list.iterator();
			while(itor.hasNext()){
				ssInfo = itor.next();
				sparePartId = ssInfo.getDeviceId();
				num = ssInfo.getNum();
				sums = ssInfo.getSums();
				avgAmount = ssInfo.getAvgAmount();
				for(int i=0;i<24-num;i++){
					sums += avgAmount*avgAmount;
				}
				STD = Math.sqrt(sums/24);
				stdMap.put(sparePartId, STD);
			}
		}
		return stdMap;
	}
	
	public List<SafetyStockInfo> calculateSafetyStock(List<String> spIdList, Map<String, Double> stdMap,Map<String, Double> lMap){
		List<SafetyStockInfo> sslist = new ArrayList<SafetyStockInfo>();
		if(!CollectionUtils.isEmpty(spIdList)){
			String key = "";
			Double STD = null;
			Double L = null;
			Double safetyStock = 0.0;
			SafetyStockInfo ssInfo = null;
			for(String string : spIdList){
				key = string;
				if(!CollectionUtils.isEmpty(stdMap)){
					STD = stdMap.get(key);	
				}
				if(!CollectionUtils.isEmpty(lMap)){
					L = lMap.get(key);
				}
				if(Tools.isNull(STD)&&Tools.isNull(L)){
						continue;
						}else if(!Tools.isNull(STD)&&!Tools.isNull(L)){
							ssInfo = new SafetyStockInfo();
							safetyStock = Constants.Z*Math.sqrt(L)*STD;
							ssInfo.setDeviceId(key);
							ssInfo.setSafetyStock(safetyStock);
							sslist.add(ssInfo);	
						}else if(!Tools.isNull(STD)&&Tools.isNull(L)){
							L = 1.0;  //默认值
							ssInfo = new SafetyStockInfo();
							safetyStock = Constants.Z*Math.sqrt(L)*STD;
							ssInfo.setDeviceId(key);
							ssInfo.setSafetyStock(safetyStock);
							sslist.add(ssInfo);	
						}else{
							STD = 1.0;  //默认值
							ssInfo = new SafetyStockInfo();
							safetyStock = Constants.Z*Math.sqrt(L)*STD;
							ssInfo.setDeviceId(key);
							ssInfo.setSafetyStock(safetyStock);
							sslist.add(ssInfo);	
						}
					}	
			}
//		if(!CollectionUtils.isEmpty(stdMap)){
//			sslist = new ArrayList<SafetyStockInfo>();		
//			Map.Entry<String, Double> entry = null;
//			Set<Map.Entry<String, Double>> set = stdMap.entrySet();
//			Iterator<Map.Entry<String, Double>> itor = set.iterator();
//			String key = "";
//			double STD = 0;
//			double L = 0;
//			Double safetyStock = 0.0;
//			SafetyStockInfo ssInfo = null;
//			while(itor.hasNext()){
//				entry = itor.next();
//				ssInfo = new SafetyStockInfo();
//				key = entry.getKey();
//				STD = entry.getValue();
//				if(!CollectionUtils.isEmpty(lMap)){
//					L = lMap.get(key);
//				}else{
//					L=25.0;
//				}
//				safetyStock = Constants.Z*Math.sqrt(L)*STD;
//				ssInfo.setDeviceId(key);
//				ssInfo.setSafetyStock(safetyStock);
//				sslist.add(ssInfo);
//			}
//		}
		return sslist;	
	}
}
