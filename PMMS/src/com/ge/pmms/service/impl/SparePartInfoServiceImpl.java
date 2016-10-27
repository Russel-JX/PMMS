/**
 * ============================================================
 * File : SparePartInfoServiceImpl.java
 * Description : 
 * 
 * Package : com.ge.pmms.service.impl
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Feb 1, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.pmms.service.impl;

import java.io.File;
import java.io.FileOutputStream;
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
import org.smslib.Message.MessageEncodings;
import org.smslib.OutboundMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ge.pmms.base.BaseService;
import com.ge.pmms.base.PmmsException;
import com.ge.pmms.base.ServiceReturns;
import com.ge.pmms.dao.SparePartInfoDao;
import com.ge.pmms.dao.SparePartTransInfoDao;
import com.ge.pmms.po.EquipInfo;
import com.ge.pmms.po.MailAttachmentVO;
import com.ge.pmms.po.MailMessageVO;
import com.ge.pmms.po.SparePartInfo;
import com.ge.pmms.po.SparePartTransInfo;
import com.ge.pmms.po.User;
import com.ge.pmms.service.CommonService;
import com.ge.pmms.service.IMailSendService;
import com.ge.pmms.service.SparePartInfoService;
import com.ge.pmms.utils.Constants;
import com.ge.pmms.utils.MsgUtil;
import com.ge.pmms.utils.Tools;

/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Feb 1, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
@SuppressWarnings("deprecation")
@Service
@Transactional
public class SparePartInfoServiceImpl extends BaseService<SparePartInfo>
		implements
			SparePartInfoService {

	@Autowired
	private SparePartInfoDao sparePartInfoDao;
	@Autowired
	private SparePartTransInfoDao sparePartTransInfoDao; 
	@Autowired
	private CommonService commonService;
	@Autowired
	private IMailSendService iMailSendService;
	
	private List<SparePartInfo> SPInfoList;
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public ServiceReturns getSPInfo(SparePartInfo sparePartInfo) throws PmmsException {
		try {
			//Long start = System.nanoTime();
			//System.out.println("SparePartInfoServiceImpl start........"+start);
			serviceReturns = new ServiceReturns();
			List<SparePartInfo> listSpareInfo = sparePartInfoDao.getSparePartInfo(sparePartInfo);
			SPInfoList = listSpareInfo;
			serviceReturns.putData("listSpareInfo",listSpareInfo);
			//Long end = System.nanoTime();
			//System.out.println("SparePartInfoServiceImpl end........"+end);
			//System.out.println("use time "+(end-start));
		}
		catch (Exception e) {
			LOGGER.info(e);
			Tools.getExceptionServiceReturns(e);
		}
		return serviceReturns;
	}

	public ServiceReturns updateSPInfoById(SparePartInfo sparePartInfo)
			throws PmmsException {
		try {
			Date lastUpdatedDate = Tools.getToday();
			serviceReturns = new ServiceReturns();
			SparePartInfo spInfo = sparePartInfoDao.getById(SparePartInfo.class, sparePartInfo.getId());
			//spInfo.setSparePartName(sparePartInfo.getSparePartName());
			spInfo.setSparePartType(sparePartInfo.getSparePartType());
			spInfo.setSource(sparePartInfo.getSource());
			spInfo.setMeasurement(sparePartInfo.getMeasurement());
			spInfo.setPrice(sparePartInfo.getPrice());
			spInfo.setSafetyStock(sparePartInfo.getSafetyStock());
			spInfo.setLocation(sparePartInfo.getLocation());
			spInfo.setRemark(sparePartInfo.getRemark());
			spInfo.setLastUpdatedDate(lastUpdatedDate);
			spInfo.setUpdater(sparePartInfo.getUpdater());
			getSession().flush();
		}
		catch (Exception e) {
			LOGGER.info(e);
			Tools.getExceptionServiceReturns(e);
		}
		return serviceReturns;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public ServiceReturns getSPInfoBySPId(String sparePartId) throws PmmsException {
		try{
			serviceReturns = new ServiceReturns();
			SparePartInfo sparePartInfo = sparePartInfoDao.getSPInfoBySPId(sparePartId);			
			serviceReturns.putData("spInfo", sparePartInfo);										
		}catch(Exception e){
			LOGGER.info(e);
			Tools.getExceptionServiceReturns(e);
		}
		return serviceReturns;
	}

	@Override
	public synchronized ServiceReturns sparePartIn(SparePartInfo sparePartInfo)
			throws PmmsException {
		try {
			
			Date lastUpdatedDate = Tools.getToday();
			serviceReturns = new ServiceReturns();
			SparePartTransInfo sparePartTransInfo = new SparePartTransInfo();
			SparePartInfo spInfo = sparePartInfoDao.getSPInfoBySPId(sparePartInfo.getSparePartId());
			spInfo.setStock(spInfo.getStock()+sparePartInfo.getAccount());
			sparePartInfoDao.update(spInfo);
			/******************sparePartTransInfo***************************/
			sparePartTransInfo.setTransId(Tools.getTimeStr());
			sparePartTransInfo.setSparePartId(sparePartInfo.getSparePartId());
			sparePartTransInfo.setSparePartName(sparePartInfo.getSparePartName());
			sparePartTransInfo.setSparePartType(sparePartInfo.getSparePartType());
			sparePartTransInfo.setTransType(Constants.TRANS_TYPE_IN);
			sparePartTransInfo.setAmount(sparePartInfo.getAccount());
			sparePartTransInfo.setMeasurement(sparePartInfo.getMeasurement());
			sparePartTransInfo.setPrice(sparePartInfo.getInPrice());
			//***待完善***----------------------------------------
			//sparePartTransInfo.setReceiver("Mike/502164725");
			//-------------------------------------------------------
			sparePartTransInfo.setCreator(sparePartInfo.getCreator());
			sparePartTransInfo.setUpdater(sparePartInfo.getUpdater());
			sparePartTransInfo.setCreatedDate(lastUpdatedDate);
			sparePartTransInfo.setLastUpdatedDate(lastUpdatedDate);
			sparePartTransInfo.setRemark(sparePartInfo.getInRemark());
			sparePartTransInfo.setLeadTime(sparePartInfo.getLeadTime());
			sparePartTransInfo.setCreateMonth(Tools.parseToDate(Tools.getFirstDayOfMonth(Tools.getCurrentMonth())));
			//System.out.println(Tools.parseToDate(Tools.getFirstDayOfMonth(Tools.getCurrentMonth())));
			sparePartTransInfoDao.save(sparePartTransInfo);
			
		}
		catch (Exception e) {
			LOGGER.info(e);
			Tools.getExceptionServiceReturns(e);
		}
		return serviceReturns;
	}

	@Override
	public synchronized ServiceReturns sparePartOut(SparePartInfo sparePartInfo)
			throws PmmsException {
		serviceReturns = new ServiceReturns();
		try{
			Date lastUpdatedDate = Tools.getToday();
			SparePartTransInfo sparePartTransInfo = new SparePartTransInfo();
			SparePartInfo spInfo = sparePartInfoDao.getSPInfoBySPId(sparePartInfo.getSparePartId());
			Integer account = sparePartInfo.getAccount();
			Integer stock = spInfo.getStock();
			if(account <= stock){
				spInfo.setStock(stock-account);
				sparePartInfoDao.update(spInfo);
			}else{
				serviceReturns.putData("feedback", "库存不足!");
				return serviceReturns;
			}
			sparePartTransInfo.setTransId(Tools.getTimeStr());
			sparePartTransInfo.setSparePartId(sparePartInfo.getSparePartId());
			sparePartTransInfo.setSparePartName(sparePartInfo.getSparePartName());
			sparePartTransInfo.setSparePartType(sparePartInfo.getSparePartType());
			sparePartTransInfo.setTransType(Constants.TRANS_TYPE_OUT);
			sparePartTransInfo.setAmount(sparePartInfo.getAccount());
			sparePartTransInfo.setMeasurement(sparePartInfo.getMeasurement());
			sparePartTransInfo.setPrice(sparePartInfo.getPrice());
			sparePartTransInfo.setWorkOrderId(sparePartInfo.getWorkOrderId());
			//***待完善***
			sparePartTransInfo.setReceiver("Mike/502164725");
			//**********
			sparePartTransInfo.setCreator(sparePartInfo.getCreator());
			sparePartTransInfo.setUpdater(sparePartInfo.getUpdater());
			sparePartTransInfo.setCreatedDate(lastUpdatedDate);
			sparePartTransInfo.setLastUpdatedDate(lastUpdatedDate);
			sparePartTransInfo.setRemark(sparePartInfo.getOutRemark());
			sparePartTransInfo.setCreateMonth(Tools.parseToDate(Tools.getFirstDayOfMonth(Tools.getCurrentMonth())));
			sparePartTransInfoDao.save(sparePartTransInfo);
			serviceReturns.putData("feedback", null);			
		}catch (Exception e) {
			LOGGER.info(e);
			Tools.getExceptionServiceReturns(e);
		}
		return serviceReturns;
	}
	
	public HSSFWorkbook sparePartInfoExportToExcel(String type) throws PmmsException{
		String firstRowInfo = "";
		if("1".equals(type)){
			System.out.println("1".equals(type));
			firstRowInfo = "备件库存信息";
		}else{
			firstRowInfo = "小于安全库存的备件信息";
		}
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
		firtRow.createCell(0).setCellValue(firstRowInfo);
		Tools.setCellStyleForEachCell(firtRow, csHead0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
		
		
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
		
		title.createCell(0).setCellValue("编码号");
		title.createCell(1).setCellValue("名称");
		title.createCell(2).setCellValue("规格型号");
		title.createCell(3).setCellValue("生产厂商");
		title.createCell(4).setCellValue("单位");
		title.createCell(5).setCellValue("单价(RMB)");
		title.createCell(6).setCellValue("库存");
		title.createCell(7).setCellValue("小计");
		title.createCell(8).setCellValue("存放地点");
		title.createCell(9).setCellValue("备注");
		Tools.setCellStyleForEachCell(title, csHead);
		LOGGER.info("set head each cell style");
		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
		HSSFRow temp = null;
		for(SparePartInfo sparePartInfo : SPInfoList){
			temp = sheet.createRow((short) ++ rowNum);
			temp.createCell(0).setCellValue(sparePartInfo.getSparePartId());
			temp.createCell(1).setCellValue(sparePartInfo.getSparePartName());
			temp.createCell(2).setCellValue(sparePartInfo.getSparePartType());
			temp.createCell(3).setCellValue(sparePartInfo.getSource());
			temp.createCell(4).setCellValue(sparePartInfo.getMeasurement());
			temp.createCell(5).setCellValue(sparePartInfo.getPrice());
			temp.createCell(6).setCellValue(sparePartInfo.getStock());
			temp.createCell(7).setCellValue("");
			temp.createCell(8).setCellValue(sparePartInfo.getLocation());
			temp.createCell(9).setCellValue(sparePartInfo.getRemark());
		}
		return hwb;
	}

	@Override
	public HSSFWorkbook spInfoLessSafetyStockExportToExcel()
			throws PmmsException {
		return null;
	}

	
  public String getMaxSparePartId(String sparePartName) {
    int cnt =  sparePartInfoDao.getSPInfoCnt(sparePartName);
    cnt = cnt + 1;
    if(cnt < 10){
      return "00"+cnt;
    }else if (10 < cnt && cnt < 99){
      return "0"+cnt;
    }else{
      return cnt+"";
    }
  }
	
  //安全库存的email
  @SuppressWarnings("unused")
public void emailSafetySPReminder(List<SparePartInfo> spList) throws PmmsException{
	  System.out.println("excute emailSafetySPReminder() method");
	  /**
	   * 从系统参数中获取信息
	   */
	  //是否发邮件标志
	  String mailFlag = Constants.SYS_CONFIG_MAP.get(Constants.FLAG_MAIL_REMINDER);
	  //是否发短信标志
	  String msgFlag = Constants.SYS_CONFIG_MAP.get(Constants.FLAG_MSG_REMINDER);
	  //邮件模板
	  //String mailTemp = "";
	  StringBuilder mailTemp = new StringBuilder();
	  mailTemp.append("Hi,\n\n");
	  mailTemp.append("This is an automated email from PMMS system. \n\n ");
	  mailTemp.append("Please kindly find the safety spm data as attached\n\n");
	  //短信模板
	  //String msgTemp = "";
	  StringBuilder msgTemp = new StringBuilder();
	  msgTemp.append("你好，PMMS平台提醒您，您获取的是少于安全库存的备件信息，请及时处理，具体信息如下。备件编号：");
	   
	  //添加附件
	  MailAttachmentVO attachment = new MailAttachmentVO();
	  List<MailAttachmentVO> attachments = new ArrayList<MailAttachmentVO>();
	  File file = null;
	  FileOutputStream output = null;
	  try{
		  SPInfoList = spList;
		  HSSFWorkbook hwb = sparePartInfoExportToExcel("2");
		  String fileName="unSafetyStock.xls";
		  file = new File(fileName);
		  output = new FileOutputStream(file);
		  hwb.write(output);
		  attachment.setFile(file);
		  attachment.setName(fileName);
		  attachments.add(attachment);
		  
		  MailMessageVO message = new MailMessageVO();
		  message.setSubject("PMMS Project - Spare Part Mgmt Reminder");
		  message.setFrom("PMMS.Team@igate.com");
		  
		  //获取收件人
		  String receiver = Constants.SYS_CONFIG_MAP.get(Constants.SPM_EMAIL_RECEIVER);
		  String[] receivers = receiver.split(",");
		  User user = null;
		  
		  //低于安全库存的备件信息
		  SparePartInfo sparePartInfo = null;
		  StringBuilder spEmail = new StringBuilder(128);
		  StringBuilder spMsg = new StringBuilder(128);
		  for(int i=0;i<SPInfoList.size();i++){
			  sparePartInfo = SPInfoList.get(i);
			  spEmail.append(" "+sparePartInfo.getSparePartId()+","+sparePartInfo.getSparePartName()+","+sparePartInfo.getSparePartType()+"\n");
			  spMsg.append(" "+sparePartInfo.getSparePartId()+",");
		  }
		  
		  //短信提醒内容文本
		  List<OutboundMessage> msgs = new ArrayList<OutboundMessage>();
//		  String mailContent = String.format(mailTemp.toString(),System.getProperty("line.separator"),System.getProperty("line.separator"),System.getProperty("line.separator"), 
//			  spEmail.toString());
		  String mailContent = mailTemp.toString();
//		  String msgContent = String.format(msgTemp.toString(), System.getProperty("line.separator"),System.getProperty("line.separator"),System.getProperty("line.separator"),
//				  spMsg.toString());
		  String msgContent = msgTemp.toString()+System.getProperty("line.separator")+spMsg.toString();
		  
		  //获取联系人的Email和phone number
		  String[] contractNOs = new String[receivers.length];
		  String[] emails = new String[receivers.length];
		  for(int i=0;i<receivers.length;i++){
			  user = commonService.getUserBySso(receivers[i]);
			  
			  OutboundMessage obm = new OutboundMessage(user.getContactNo(), msgContent);
			  obm.setEncoding(MessageEncodings.ENCUCS2);
			  msgs.add(obm);
			  
			  contractNOs[i] = user.getContactNo();
			  emails[i] = user.getEmailId();
		  }
		  
		  //邮件
		  if(mailFlag.equalsIgnoreCase(Constants.FLAG_REMIND)){
			  LOGGER.info("SafetySPReminder Send Email reminder Starting...... ");
			  message.setToEmail(emails);
			  message.setMessage(mailContent);
			  message.setAttachments(attachments);
			  iMailSendService.sendMail(message);
			  LOGGER.info("SafetySPReminder Send Email reminder Ending...... ");
		  }
		  //短信
		  if(false){
			  LOGGER.info("SafetySPReminder Send MSG reminder Starting...... ");
			  MsgUtil.open(Constants.MSG_PORT, Constants.MSG_GATE_WAY);
			  MsgUtil.sendMutipleSms(msgs);
			  MsgUtil.close();
			  LOGGER.info("SafetySPReminder Send MSG reminder Ending...... ");
		  }
	  }catch(Exception e){
		  e.printStackTrace();
		  LOGGER.error("emailSafetySPReminder() method occures exception......Exception:"+e.getMessage());
	  }
  }
  
  	//根据备件名称模糊查询设备
	public ServiceReturns getSPsBySPName(String spName){
		serviceReturns = new ServiceReturns();
		try{
			List<SparePartInfo> list = sparePartInfoDao.getSPsBySPName(spName);
			serviceReturns.putData("spNameList",list);
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.error("getSPsBySPName() method occures exception......Exception:"+e.getMessage());
		}
		return serviceReturns;
	}
  
}






















