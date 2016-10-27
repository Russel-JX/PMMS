/**
 * ============================================================
 * File : IdmInfoServiceImpl.java
 * Description : 
 * 
 * Package : com.ge.pmms.service.impl
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Jan 26, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.pmms.service.impl;

import com.ge.pmms.po.MailAttachmentVO;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.CellStyle;
import org.smslib.OutboundMessage;
import org.smslib.Message.MessageEncodings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ge.pmms.base.BaseService;
import com.ge.pmms.base.ServiceReturns;
import com.ge.pmms.dao.IdmInfoDao;
import com.ge.pmms.po.IdmInfo;
import com.ge.pmms.po.IdmTransInfo;
import com.ge.pmms.po.MailMessageVO;
import com.ge.pmms.po.SafetyStockInfo;
import com.ge.pmms.po.User;
import com.ge.pmms.service.CommonService;
import com.ge.pmms.service.IMailSendService;
import com.ge.pmms.service.IdmInfoService;
import com.ge.pmms.service.IdmTransService;
import com.ge.pmms.service.SparePartTransInfoService;
import com.ge.pmms.utils.Constants;
import com.ge.pmms.utils.MsgUtil;
import com.ge.pmms.utils.Tools;


/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Jan 26, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
@Service
@Transactional
public class IdmInfoServiceImpl extends BaseService<IdmInfo> implements IdmInfoService{
	@Autowired
	private IdmInfoDao idmInfoDao;
	
	private List<IdmInfo> comIdmInfoLst;
	
	private List<IdmInfo> comunSafetyIdmLst;
	
	@Autowired
	private IMailSendService iMailSendService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private IdmTransService idmTransService;
	
	@Autowired
	private SparePartTransInfoService sparePartTransInfoService;
	
	public ServiceReturns getIdmInfoByType(IdmInfo idmInfo){
		List<IdmInfo> idmInfoLst;
	    if(idmInfo.getTypeId().equalsIgnoreCase("all")){
	    	idmInfoLst=idmInfoDao.getAllIdmInfo();
	    }else{
	    	idmInfoLst=idmInfoDao.getIdmInfoByType(idmInfo);
	    }
	    comIdmInfoLst=idmInfoLst;
		serviceReturns = new ServiceReturns();
		serviceReturns.putData("detail",idmInfoLst);
		return serviceReturns;
	}
	
	public ServiceReturns getIdmInfoByIdmId(IdmInfo idmInfoReq){
		IdmInfo idmInfoReturn=idmInfoDao.getIdmInfoByIdmId(idmInfoReq.getIdmId());
		if(Tools.isEmpty(idmInfoReturn)){
			serviceReturns.putData("detail",null);
		}else{
			List<IdmInfo> list = new ArrayList<IdmInfo>();
			list.add(idmInfoReturn);
			comIdmInfoLst=list;
			serviceReturns = new ServiceReturns();
			serviceReturns.putData("detail",list);
		}
		return serviceReturns;
	}
	
	public ServiceReturns updateIdmInfo(JSONObject json){
		serviceReturns = new ServiceReturns();
		IdmInfo idmInfo=idmInfoDao.getIdmInfoByIdmId(Tools.getParameters(json,"idmId"));
		idmInfo.setTypeId(Tools.getParameters(json,"typeId"));
		idmInfo.setSubTypeId(Tools.getParameters(json,"subTypeId"));
		idmInfo.setTypeDetailId(Tools.getParameters(json,"detailTypeId"));
		idmInfo.setIdmNm(Tools.getParameters(json,"idmNm"));
		idmInfo.setSource(Tools.getParameters(json,"source"));
		idmInfo.setSize(Tools.getParameters(json,"size"));
		idmInfo.setPrice(Tools.getParameters(json,"price"));
		idmInfo.setMeasurement(Tools.getParameters(json,"measurement"));
		idmInfo.setSafetyNm(Tools.getParameters(json,"safetyNm"));
		idmInfo.setSugSaveStock(Tools.getParameters(json,"sugSaveStock"));
		idmInfo.setPosition(Tools.getParameters(json,"position"));
		idmInfo.setRemark(Tools.getParameters(json,"remark"));
		idmInfo.setUpdater(Tools.getParameters(json,"updater"));
		idmInfo.setLastUpdateDate(new Date());
		getSession().update(idmInfo);
		return serviceReturns;
	}
	
	public ServiceReturns getAllIdmTransRecords(){
		serviceReturns = new ServiceReturns();
		List<IdmTransInfo> idmInfoLst=idmInfoDao.getAllIdmTransRecords();
		serviceReturns.putData("detail",idmInfoLst);
		return serviceReturns;
	}
	
	public ServiceReturns updateTransRecord(JSONObject json){
		serviceReturns = new ServiceReturns();
		IdmTransInfo idmTransInfo=idmInfoDao.getTransInfoByIdmId(Tools.getParameters(json,"idmId"));
		idmTransInfo.setTransId(Tools.getParameters(json,"transId"));
		idmTransInfo.setIdmId(Tools.getParameters(json,"idmId"));
		idmTransInfo.setAmount(Tools.getParameters(json,"amount"));
		idmTransInfo.setPrice(Tools.getParameters(json,"price"));
		idmTransInfo.setInTime(Tools.getParameters(json,"inTime"));
		idmTransInfo.setOutTime(Tools.getParameters(json,"outTime"));
		getSession().update(idmTransInfo);
		return serviceReturns;
	}
	
	public ServiceReturns saveIdm(JSONObject json){
		serviceReturns = new ServiceReturns();
		IdmInfo	idmInfo=new IdmInfo();
		//	idmInfo.setIdmId(Tools.getParameters(json,"idmId"));
			idmInfo.setType(Tools.getParameters(json,"type"));
			idmInfo.setTypeId(Tools.getParameters(json,"typeId"));
			idmInfo.setSubTypeId(Tools.getParameters(json,"subTypeId"));
			idmInfo.setTypeDetailId(Tools.getParameters(json,"detailTypeId"));
			idmInfo.setIdmNm(Tools.getParameters(json,"idmNm"));
			idmInfo.setSource(Tools.getParameters(json,"source"));
			idmInfo.setSize(Tools.getParameters(json,"size"));
			idmInfo.setMeasurement(Tools.getParameters(json,"measurement"));
			idmInfo.setPrice(Tools.getParameters(json,"price"));
			idmInfo.setStockNum("0");
			idmInfo.setSafetyNm("0");
			idmInfo.setSugSaveStock("0");
			idmInfo.setCreator(Tools.getParameters(json,"creator"));
			idmInfo.setCreateDate(new Date());
			idmInfo.setLastUpdateDate(new Date());
			idmInfo.setRemark(Tools.getParameters(json,"remark"));
			idmInfo.setPosition(Tools.getParameters(json,"position"));
			String lastIdmId=idmInfoDao.getLastIdmId(idmInfo);
			String idmId=idmInfo.getTypeId()+idmInfo.getSubTypeId()+idmInfo.getTypeDetailId();
			if(!Tools.isEmpty(lastIdmId)){
			int tempId=Integer.parseInt(lastIdmId.substring(5))+1;
				if(tempId<10){
					idmInfo.setIdmId(idmId+"000"+tempId);
				}else if(tempId<100){
					idmInfo.setIdmId(idmId+"00"+tempId);
				}else if(tempId<1000){
					idmInfo.setIdmId(idmId+"0"+tempId);
				}else{
					idmInfo.setIdmId(idmId+tempId);
				}
			}else{
				idmInfo.setIdmId(idmId+"0001");
			}
			serviceReturns=super.save(idmInfo);
		
		return serviceReturns;
	}
	
	public ServiceReturns deleIdmbyIdmId(IdmInfo idmInfo){
		serviceReturns = new ServiceReturns();
		serviceReturns.putData("detail",idmInfoDao.deleIdmbyIdmId(idmInfo));
		return serviceReturns;
	}
	
	public ServiceReturns getUnSaftyIdm(){
		List<IdmInfo> idmInfoLst=idmInfoDao.getAllUnSaftyIdm();
		comunSafetyIdmLst=idmInfoLst;
		serviceReturns = new ServiceReturns();
		serviceReturns.putData("detail",idmInfoLst);
		return serviceReturns;
	}
	
	public HSSFWorkbook  idmInfoExportToExcel(){
		 HSSFWorkbook hwb = new HSSFWorkbook();
		 HSSFSheet sheet = hwb.createSheet("IdmInfo");
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
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 16)); //合并单元格
			
			heading.setHeightInPoints((short)19);
			heading.createCell(0).setCellValue("库存间接物料表");//创建第一行第一列,并写入值
			heading.getCell(0).setCellStyle(csHead);
			
			//title 样式
			HSSFFont titleFont = hwb.createFont();
			titleFont.setFontHeightInPoints((short) 11);
			titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			HSSFCellStyle csTitle = hwb.createCellStyle();
			csTitle.setFont(titleFont);
			csTitle.setAlignment(CellStyle.ALIGN_CENTER);
			
			title.createCell(0).setCellValue("编码号");
			title.createCell(1).setCellValue("名称");
			title.createCell(2).setCellValue("规格");
			title.createCell(3).setCellValue("生产厂商");
			title.createCell(4).setCellValue("单价(元)");
			title.createCell(5).setCellValue("单位");
			title.createCell(6).setCellValue("库存量");
			title.createCell(7).setCellValue("安全库存量");
			title.createCell(8).setCellValue("建议安全库存");
			title.createCell(9).setCellValue("存放地点");
			title.createCell(10).setCellValue("类别");
			title.createCell(11).setCellValue("子类");
			title.createCell(12).setCellValue("明细项目");
			title.createCell(13).setCellValue("备注");//14
			Tools.setCellStyleForEachCell(title,csTitle);
			
			HSSFRow temp = null;
			for (IdmInfo idmInfo : comIdmInfoLst) {
				temp = sheet.createRow((short) ++rowNum);
				temp.createCell(0).setCellValue(idmInfo.getIdmId());
				temp.createCell(1).setCellValue(idmInfo.getIdmNm());
				temp.createCell(2).setCellValue(idmInfo.getSize());
				temp.createCell(3).setCellValue(idmInfo.getSource());
				temp.createCell(4).setCellValue(idmInfo.getPrice());
				temp.createCell(5).setCellValue(idmInfo.getMeasurement());
				temp.createCell(6).setCellValue(idmInfo.getStockNum());
				temp.createCell(7).setCellValue(idmInfo.getSafetyNm());
				temp.createCell(8).setCellValue(idmInfo.getSugSaveStock());
				temp.createCell(9).setCellValue(idmInfo.getPosition());
				if(idmInfo.getTypeId().equalsIgnoreCase("E")){
					temp.createCell(10).setCellValue("费用");
				}else if(idmInfo.getTypeId().equalsIgnoreCase("P")){
					temp.createCell(10).setCellValue("固定资产");
				}
				temp.createCell(11).setCellValue(idmInfo.getSUB_TYPE_NM());
				temp.createCell(12).setCellValue(idmInfo.getTYPE_DETAIL_NM());
				temp.createCell(13).setCellValue(idmInfo.getRemark());
			}
		 return hwb;
	}
	
	public HSSFWorkbook  idmSafetyExportToExcel(){
		 HSSFWorkbook hwb = new HSSFWorkbook();
		 HSSFSheet sheet = hwb.createSheet("safetyIdmMgmt");
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
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8)); //合并单元格
			
			heading.setHeightInPoints((short)19);
			heading.createCell(0).setCellValue("间接物料--安全库存管理表");//创建第一行第一列,并写入值
			heading.getCell(0).setCellStyle(csHead);
			
			//title 样式
			HSSFFont titleFont = hwb.createFont();
			titleFont.setFontHeightInPoints((short) 11);
			titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			HSSFCellStyle csTitle = hwb.createCellStyle();
			csTitle.setFont(titleFont);
			csTitle.setAlignment(CellStyle.ALIGN_CENTER);
			
			title.createCell(0).setCellValue("编码号");
			title.createCell(1).setCellValue("名称");
			title.createCell(2).setCellValue("规格");
			title.createCell(3).setCellValue("生产厂商");
			title.createCell(4).setCellValue("单价(元)");
			title.createCell(5).setCellValue("库存量");
			title.createCell(6).setCellValue("安全库存量");
		//	title.createCell(7).setCellValue("建议安全库存");
			title.createCell(7).setCellValue("存放地点");
			title.createCell(8).setCellValue("类别");//9
		//	title.createCell(10).setCellValue("子类");
		//	title.createCell(11).setCellValue("明细项目");
		//	title.createCell(12).setCellValue("备注");//13
			Tools.setCellStyleForEachCell(title,csTitle);
			
			HSSFRow temp = null;
			for (IdmInfo idmInfo : comunSafetyIdmLst) {
				temp = sheet.createRow((short) ++rowNum);
				temp.createCell(0).setCellValue(idmInfo.getIdmId());
				temp.createCell(1).setCellValue(idmInfo.getIdmNm());
				temp.createCell(2).setCellValue(idmInfo.getSize());
				temp.createCell(3).setCellValue(idmInfo.getSource());
				temp.createCell(4).setCellValue(idmInfo.getPrice());
				temp.createCell(5).setCellValue(idmInfo.getStockNum());
				temp.createCell(6).setCellValue(idmInfo.getSafetyNm());
			//	temp.createCell(7).setCellValue(idmInfo.getSugSaveStock());
				temp.createCell(7).setCellValue(idmInfo.getPosition());
				if(idmInfo.getTypeId().equalsIgnoreCase("E")){
					temp.createCell(8).setCellValue("费用");
				}else if(idmInfo.getTypeId().equalsIgnoreCase("P")){
					temp.createCell(8).setCellValue("固定资产");
				}
			//	temp.createCell(10).setCellValue(idmInfo.getSUB_TYPE_NM());
			//	temp.createCell(11).setCellValue(idmInfo.getTYPE_DETAIL_NM());
			//	temp.createCell(12).setCellValue(idmInfo.getRemark());
			}
		 return hwb;
	}
	
	//邮件提醒 
			public void emailSafetyIdmReminder(List<IdmInfo> idmInfoLst)throws Exception{
				MailAttachmentVO attachment = new MailAttachmentVO();
				List<MailAttachmentVO> attachments = new ArrayList<MailAttachmentVO>();
				 File file = null;
				 FileOutputStream output = null;
				//是否发送邮件提醒
					String mailFlag = Constants.SYS_CONFIG_MAP.get(Constants.FLAG_MAIL_REMINDER);
				try {
					comunSafetyIdmLst=idmInfoLst;
					HSSFWorkbook workBook=idmSafetyExportToExcel();
					String fileName = "unSafetyIdm.xls";
					file = new File(fileName);
					output = new FileOutputStream(file);
					workBook.write(output);
					attachment.setFile(file);
					attachment.setName(fileName);
					attachments.add(attachment);
					MailMessageVO message=new MailMessageVO();
					message.setSubject("PMMS Project - Idm Mgmt Reminder");
					message.setFrom("PMMS.Team@igate.com");
					String receiver = Constants.SYS_CONFIG_MAP.get(Constants.IDM_EMAIL_RECEIVER);
					String[] receivers = receiver.split(",");
					User user = null;
					String[] contactNOs = new String[receivers.length];
					String[] emails = new String[receivers.length];
					for(int i=0;i<receivers.length;i++){
						user = commonService.getUserBySso(receivers[i]);
						contactNOs[i] = user.getContactNo();
						emails[i] = user.getEmailId();
					}
					
					//邮件
					if(mailFlag.equalsIgnoreCase(Constants.FLAG_REMIND)){
						LOGGER.info("IDM start sending email");
						message.setToEmail(emails);
						StringBuilder sb = new StringBuilder();
						sb.append("Hi,\n\n");
						sb.append("   This is an automated email from PMMS system. \n\n ");
						sb.append("Please kindly find the safety idm data as attached\n\n");
						message.setMessage(sb.toString());
						message.setAttachments(attachments);
						iMailSendService.sendMail(message);
						LOGGER.info("IDM email send end");
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		
		public ServiceReturns calcSafetyNum(){
			//今天
			Date currentDate = Tools.getToday();
			//昨天
			Date lastDate = Tools.getLastDate(currentDate);
			//从昨天往前推两年的日期
			Date lastTwoYrDate = Tools.getLastTwoYrDate(lastDate);
			
			Map<String,Double> leadTmMap=idmInfoDao.getL(lastTwoYrDate,lastDate);
			
			List<SafetyStockInfo> stdInfoLst=idmInfoDao.getVariance(lastTwoYrDate,lastDate);
			
			Map<String, Double> stdMap=sparePartTransInfoService.calculateSTD(stdInfoLst);
			
			List<String> idmIdList = idmInfoDao.getIdmId();
			List<SafetyStockInfo> ssList = sparePartTransInfoService.calculateSafetyStock(idmIdList,stdMap, leadTmMap);
			LOGGER.info("start date is "+lastTwoYrDate+"yester day is "+lastDate);
			idmInfoDao.updateSafetyNum(ssList);
			serviceReturns = new ServiceReturns();
			serviceReturns.putData("leadTmMap", leadTmMap);
			serviceReturns.putData("stdlst", stdMap);
			serviceReturns.putData("safetyLst", ssList);
			return serviceReturns;
		}
	
}
