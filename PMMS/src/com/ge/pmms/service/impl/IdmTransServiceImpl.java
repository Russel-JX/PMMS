/**
 * ============================================================
 * File : IdmTransServiceImpl.java
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
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ge.pmms.base.BaseService;
import com.ge.pmms.base.PmmsException;
import com.ge.pmms.base.ServiceReturns;
import com.ge.pmms.dao.IdmInfoDao;
import com.ge.pmms.po.IdmInfo;
import com.ge.pmms.po.IdmPoInfo;
import com.ge.pmms.po.IdmTransInfo;
import com.ge.pmms.service.IdmTransService;
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
public class IdmTransServiceImpl extends BaseService<IdmTransInfo> implements IdmTransService{
	
	@Autowired
	private IdmInfoDao idmInfoDao;
	private ServiceReturns serviceReturns;
	private List<IdmTransInfo> comRecordInfoLst;
	
	public ServiceReturns insertRecordTb(IdmTransInfo idmTransInfoReq){
		serviceReturns=new ServiceReturns();
		IdmInfo idmInfo=idmInfoDao.getIdmInfoByIdmId(idmTransInfoReq.getIdmId());
		if(!Tools.isEmpty(idmInfo)){
			IdmTransInfo idmTransInfo=new IdmTransInfo();
			idmTransInfo.setTransId(idmTransInfoReq.getTransId());
			idmTransInfo.setIdmId(idmTransInfoReq.getIdmId());
			idmTransInfo.setIdmType(idmInfo.getType());
			idmTransInfo.setIdmNm(idmInfo.getIdmNm());
			idmTransInfo.setAmount(idmTransInfoReq.getAmount());
			idmTransInfo.setMeasurement(idmInfo.getMeasurement());
			idmTransInfo.setPrice(idmInfo.getPrice());
			idmTransInfo.setRemark(idmInfo.getRemark());
			idmTransInfo.setCreateDate(new Date());
			idmTransInfo.setLastUpdateDate(new Date());
			idmTransInfo.setTransType("1");
			if(Tools.isEmpty(idmTransInfoReq.getLeadTime())){
				idmTransInfo.setLeadTime("7");
			}else{
				idmTransInfo.setLeadTime(idmTransInfoReq.getLeadTime());
			}
			idmTransInfo.setPo(idmTransInfoReq.getPo());
			idmTransInfo.setCreator(idmTransInfoReq.getCreator());
			idmTransInfo.setReceiver("");
			//String inTime =Tools.formatDate(new Date());
			//idmTransInfo.setInTime(inTime);
			int stockNum=Integer.parseInt(idmInfo.getStockNum());
			int amount=Integer.parseInt(idmTransInfoReq.getAmount());
			stockNum=stockNum+amount;
			serviceReturns=super.save(idmTransInfo);
			idmInfoDao.updateStockNum(idmInfo.getIdmId(),stockNum);
			List<IdmPoInfo> poLst=idmInfoDao.getPoInfo(idmTransInfoReq);
			if(poLst.size()>0){
				int remainAmount=Integer.parseInt(poLst.get(0).getRemainAmount());
				remainAmount+=amount;
				idmTransInfoReq.setRemainAmount(String.valueOf(remainAmount));
				idmTransInfoReq.setUpdater(idmTransInfoReq.getCreator());
				idmTransInfoReq.setLastUpdateDate(new Date());
				idmTransInfoReq.setInAmount(String.valueOf(remainAmount));
				idmInfoDao.updateIdmPoInfo(idmTransInfoReq);
			}else{
				idmInfoDao.saveIdmPoInfo(idmTransInfo);
			}
		}else{
			serviceReturns.putData("details",null);
		}
		return serviceReturns;
	}
	
	public ServiceReturns scanOutStock(IdmTransInfo idmTransInfoReq) throws PmmsException{
		serviceReturns=new ServiceReturns();
		IdmInfo idmInfo=idmInfoDao.getIdmInfoByIdmId(idmTransInfoReq.getIdmId());
		if(!Tools.isEmpty(idmInfo)){
			List<IdmTransInfo> idmTransLst=new ArrayList<IdmTransInfo>();
			

			int stockNum=Integer.parseInt(idmInfo.getStockNum());
			int amount=Integer.parseInt(idmTransInfoReq.getAmount());

			if(amount<=stockNum){
				
				//对po表的剩余量进行加减操作
				List<IdmPoInfo> poLst=idmInfoDao.getPoInfo(idmTransInfoReq);
				int outAmount=amount;
				idmTransInfoReq.setUpdater(idmTransInfoReq.getCreator());
				for(IdmPoInfo poDetail:poLst){
					IdmTransInfo idmTransInfo=new IdmTransInfo();
					idmTransInfo.setTransId(idmTransInfoReq.getTransId());
					idmTransInfo.setIdmId(idmTransInfoReq.getIdmId());
					idmTransInfo.setIdmType(idmInfo.getType());
					idmTransInfo.setIdmNm(idmInfo.getIdmNm());
					idmTransInfo.setAmount(idmTransInfoReq.getAmount());
					idmTransInfo.setMeasurement(idmInfo.getMeasurement());
					idmTransInfo.setPrice(idmInfo.getPrice());
					idmTransInfo.setRemark(idmInfo.getRemark());
					idmTransInfo.setCreateDate(new Date());
					idmTransInfo.setLastUpdateDate(new Date());
					idmTransInfo.setTransType("2");
					idmTransInfo.setCreator(idmTransInfoReq.getCreator());
					idmTransInfo.setReceiver(idmTransInfoReq.getReceiver());

					idmTransInfoReq.setPo(poDetail.getPo());
					idmTransInfoReq.setLastUpdateDate(new Date());
					int remainAmount=Integer.parseInt(poDetail.getRemainAmount());
					if(remainAmount!=0){
						if(outAmount<=remainAmount){
							//poDetail.setRemainAmount(String.valueOf(remainAmount-outAmount));
							idmTransInfoReq.setRemainAmount(String.valueOf(remainAmount-outAmount));
							
							idmInfoDao.updateIdmPoInfo(idmTransInfoReq);
							
							idmTransInfo.setPo(poDetail.getPo());
							idmTransInfo.setAmount(String.valueOf(outAmount));
							idmTransLst.add(idmTransInfo);
							break;
						}else{
							idmTransInfoReq.setRemainAmount("0");
							idmTransInfo.setPo(poDetail.getPo());
							idmTransInfo.setAmount(String.valueOf(remainAmount));
							idmTransLst.add(idmTransInfo);
							idmInfoDao.updateIdmPoInfo(idmTransInfoReq);
							outAmount=outAmount-remainAmount;
						}
					}
				}
				//serviceReturns=super.save(idmTransInfo);
				super.saveAll(idmTransLst);
				stockNum=stockNum-amount;
				idmInfoDao.updateStockNum(idmInfo.getIdmId(),stockNum);
				serviceReturns.putData("details","0");
			}else{
				serviceReturns.putData("details","1");
			}
		}else{
			serviceReturns.putData("details",null);
		}
		return serviceReturns;
	}
	
	public ServiceReturns getIdmRecordByPeriod(IdmTransInfo idmTransInfoReq){
		serviceReturns=new ServiceReturns();
		List<IdmTransInfo> recordInfoLst=idmInfoDao.getIdmRecordByPeriod(idmTransInfoReq);
		comRecordInfoLst=recordInfoLst;
		serviceReturns.putData("detail",recordInfoLst);
		return serviceReturns;
	}
	
	public HSSFWorkbook  idmRecordExportToExcel(){
		 HSSFWorkbook hwb = new HSSFWorkbook();
		 HSSFSheet sheet = hwb.createSheet("IdmTransRecord");
		 int rowNum = 0;
		 HSSFRow  heading= sheet.createRow(rowNum);
			HSSFRow title = sheet.createRow(++rowNum);
			//heading样式
			HSSFFont headFont = hwb.createFont();
			headFont.setFontHeightInPoints((short) 17);
			headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			HSSFCellStyle csHead = hwb.createCellStyle();
			csHead.setFont(headFont);
			csHead.setAlignment(CellStyle.ALIGN_CENTER);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7)); //合并单元格
			
			heading.setHeightInPoints((short)19);
			heading.createCell(0).setCellValue("间接物料出入库流水表");//创建第一行第一列,并写入值
			heading.getCell(0).setCellStyle(csHead);
			
			//title 样式
			HSSFFont titleFont = hwb.createFont();
			titleFont.setFontHeightInPoints((short) 11);
			titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			HSSFCellStyle csTitle = hwb.createCellStyle();
			csTitle.setFont(titleFont);
			csTitle.setAlignment(CellStyle.ALIGN_CENTER);
			
			title.createCell(0).setCellValue("流水号");
			title.createCell(1).setCellValue("编码号");
			title.createCell(2).setCellValue("名称");
			title.createCell(3).setCellValue("单价");
			title.createCell(4).setCellValue("数量");
			title.createCell(5).setCellValue("类别");
			title.createCell(6).setCellValue("入库/出库");
			title.createCell(7).setCellValue("操作时间");//8  
			title.createCell(8).setCellValue("订货交付时间差（天）");
			title.createCell(9).setCellValue("仓库管理员");
			title.createCell(10).setCellValue("领料人");
			title.createCell(11).setCellValue("批次号");//12
			Tools.setCellStyleForEachCell(title,csTitle);
						
			HSSFRow temp = null;
			for (IdmTransInfo recordInfo : comRecordInfoLst) {
				temp = sheet.createRow((short) ++rowNum);
				temp.createCell(0).setCellValue(recordInfo.getTransId());
				temp.createCell(1).setCellValue(recordInfo.getIdmId());
				temp.createCell(2).setCellValue(recordInfo.getIdmNm());
				temp.createCell(3).setCellValue(recordInfo.getPrice());
				temp.createCell(4).setCellValue(recordInfo.getAmount());
				temp.createCell(5).setCellValue(recordInfo.getIdmType());
			//	temp.createCell(6).setCellValue(recordInfo.getTransType());
				if(recordInfo.getTransType().equalsIgnoreCase("1")){
					temp.createCell(6).setCellValue("入库");
				}else{
					temp.createCell(6).setCellValue("出库");
				}
				temp.createCell(7).setCellValue(recordInfo.getStrCreateDate());
				temp.createCell(8).setCellValue(recordInfo.getLeadTime());
				temp.createCell(9).setCellValue(recordInfo.getCreator());
				temp.createCell(10).setCellValue(recordInfo.getReceiver());
				temp.createCell(11).setCellValue(recordInfo.getPo());
			}
		 return hwb;
	}
	
	public ServiceReturns getPoDetail(IdmTransInfo idmTransInfoReq){
		serviceReturns=new ServiceReturns();
		List<IdmPoInfo> poDetailLst=idmInfoDao.getPoInfo(idmTransInfoReq);
//		int outAmount=idmInfoDao.getOutAmount(idmTransInfoReq);
//		for(IdmTransInfo poDetail:poDetailLst){
//			int InAmount=Integer.parseInt(poDetail.getAmount());
//			if(outAmount<=InAmount){
//				poDetail.setRemainAmount(String.valueOf(InAmount-outAmount));
//				break;
//			}else{
//				poDetail.setRemainAmount("0");
//				outAmount=outAmount-InAmount;
//			}
//		}
		serviceReturns.putData("detail",poDetailLst);
		return serviceReturns;
	}
	
}
