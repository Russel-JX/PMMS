/**
 * ============================================================
 * File : WorkOrderInfoServiceImpl.java
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
import org.smslib.OutboundMessage;
import org.smslib.Message.MessageEncodings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ge.pmms.base.BaseService;
import com.ge.pmms.base.PmmsException;
import com.ge.pmms.base.ServiceReturns;
import com.ge.pmms.dao.WorkOrderInfoDao;
import com.ge.pmms.dao.WorkOrderMaintInfoDao;
//import com.ge.pmms.po.MailMessageVO;
import com.ge.pmms.po.FaultTypeInfo;
import com.ge.pmms.po.User;
import com.ge.pmms.po.WorkOrderInfo;
import com.ge.pmms.po.WorkOrderMaintenanceInfo;
import com.ge.pmms.service.CommonService;
//import com.ge.pmms.service.IMailSendService;
import com.ge.pmms.service.WorkOrderInfoService;
import com.ge.pmms.utils.Constants;
import com.ge.pmms.utils.MsgUtil;
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
@SuppressWarnings("deprecation")
@Service
@Transactional
public class WorkOrderInfoServiceImpl extends BaseService<WorkOrderInfo>
		implements
			WorkOrderInfoService {
	@Autowired
	private WorkOrderInfoDao workOrderInfoDao;
	@Autowired
	private WorkOrderMaintInfoDao workOrderMaintInfoDao;
	@Autowired
	private CommonService commonService;
	//@Autowired
	//private IMailSendService iMailSendService;
	
	private List<WorkOrderInfo> workOrderList;
	
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public ServiceReturns getWorkOrders(WorkOrderInfo workOrderInfo) throws PmmsException{
		ServiceReturns returns = new ServiceReturns();
		String orderFlag = workOrderInfo.getOrderFlag();
		String workOrderStatus = "";
		if(Constants.ORDER_FLAG_NOT_CLOSED.equals(orderFlag)){
			workOrderStatus=Constants.WORK_ORDER_STATUS_SUBMIT+","+Constants.WORK_ORDER_STATUS_MAINT;
		}else{
			workOrderStatus=Constants.WORK_ORDER_STATUS_ClOSED;
		}
		List<WorkOrderInfo> workOrders = workOrderInfoDao.getWorkOrders(workOrderStatus,workOrderInfo);
		workOrderList = workOrders;
		returns.putData("workOrders", workOrders);
		return returns;
	}
	
	public ServiceReturns saveMaintOrderInfo(WorkOrderInfo workOrderInfo) throws PmmsException{
		serviceReturns = new ServiceReturns();
		Date lastUpdateDate = Tools.getToday();
		WorkOrderInfo workOrder  =	workOrderInfoDao.getById(WorkOrderInfo.class, workOrderInfo.getId());
		workOrder.setWorkOrderStatus("2");
		workOrder.setLastUpdatedDate(lastUpdateDate);
		if((workOrderInfo.getWorkOrderType()).equals(Constants.WORK_ORDER_TYPE_PLAN)){
			workOrder.setShutdownFlag(workOrderInfo.isShutdownFlag());
		}
		//workOrderInfoDao.update(workOrderInfo);
		//同一个session,用flush()更新,不能用update()
		getSession().flush();		
//		String workOrderId = request.getParameter("workOrderId");
//		String mechianic = request.getParameter("mechianic");
//		String faultType = request.getParameter("faultType");
//		String remark = request.getParameter("remark");
//		boolean sparePartInvolved = Boolean.parseBoolean(request.getParameter("sparePartInvolved"));
//		boolean externalServiceInvolved = Boolean.parseBoolean(request.getParameter("externalServiceInvolved"));		
		WorkOrderMaintenanceInfo workOrderMaintenanceInfo = new WorkOrderMaintenanceInfo();
		workOrderMaintenanceInfo.setWorkOrderMaintanceId(Tools.getTimeStr());
		workOrderMaintenanceInfo.setWorkOrderId(workOrderInfo.getWorkOrderId());
		workOrderMaintenanceInfo.setMechianic(workOrderInfo.getMechianic());
		workOrderMaintenanceInfo.setFaultType(workOrderInfo.getFaultType());
		workOrderMaintenanceInfo.setSparePartInvolved(workOrderInfo.isSparePartInvolved());
		workOrderMaintenanceInfo.setExternalServiceInvolved(workOrderInfo.isExternalServiceInvolved());
		workOrderMaintenanceInfo.setRemark(workOrderInfo.getRemark());
		workOrderMaintenanceInfo.setCreatedDate(lastUpdateDate);
		workOrderMaintenanceInfo.setMaintanceStartDate(lastUpdateDate);
		workOrderMaintenanceInfo.setLastUpdatedDate(lastUpdateDate);
		workOrderMaintInfoDao.save(workOrderMaintenanceInfo);
		return serviceReturns;
	}

	@Override
	public ServiceReturns closeMaintOrderInfo(Integer id, Integer woMaintId,String operator,String remark) throws PmmsException {
		serviceReturns= new ServiceReturns();
		Date lastUpdateDate = Tools.getToday();
		WorkOrderInfo workOrderInfo  =	workOrderInfoDao.getById(WorkOrderInfo.class, id);
		workOrderInfo.setWorkOrderStatus(Constants.WORK_ORDER_STATUS_ClOSED);
		workOrderInfo.setLastUpdatedDate(lastUpdateDate);
		WorkOrderMaintenanceInfo workOrderMaintenanceInfo = workOrderMaintInfoDao.getById(WorkOrderMaintenanceInfo.class, woMaintId);
		workOrderMaintenanceInfo.setMaintanceEndDate(lastUpdateDate);
		workOrderMaintenanceInfo.setOperator(operator);
		workOrderMaintenanceInfo.setLastUpdatedDate(lastUpdateDate);
		workOrderMaintenanceInfo.setRemark(remark);
		getSession().flush();		
		return serviceReturns;
	}

	@Override
	public ServiceReturns deleteWOByPlanId(String planId) throws PmmsException {
		serviceReturns = new ServiceReturns();	
		try {
			List<String> woIdList = workOrderInfoDao.getWOIdByPlanId(planId);
			serviceReturns.putData("delNum", workOrderInfoDao.deleteByPanId(planId));
			if(!CollectionUtils.isEmpty(woIdList)){
				String [] woIds = woIdList.toArray(new String[woIdList.size()]);
				workOrderInfoDao.deletePWMOByWOId(woIds);
			}
		}catch(Exception e){
			LOGGER.error("deleteWOByPlanId() method occures exception......Exception:"+e.getMessage());
		}
		
		return serviceReturns;
	}
	
//	public ServiceReturns deletePWMOByWOId(String[] woIds) throws PmmsException{
//		serviceReturns = new ServiceReturns();	
//		serviceReturns.putData("delNum", workOrderInfoDao.d
//		return serviceReturns;
//	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public ServiceReturns getWOForSP(WorkOrderInfo workOrderInfo)
			throws PmmsException {
		serviceReturns = new ServiceReturns();
		serviceReturns.putData("orderList", workOrderInfoDao.getWOForSP(workOrderInfo));
		return serviceReturns;
	}

	@Override
	public ServiceReturns updateWorkOrder(WorkOrderInfo workOrderInfo)
			throws PmmsException {
		serviceReturns = new ServiceReturns();
		Date lastUpdatedDate = Tools.getToday();
		Integer id = workOrderInfo.getId();                                //工单的ID
		Integer woMaintId = Integer.parseInt(workOrderInfo.getWoMaintId());//维修工单的ID
		WorkOrderInfo orderInfo = workOrderInfoDao.getById(WorkOrderInfo.class, id);
		orderInfo.setFaultDescription(workOrderInfo.getFaultDescription());
		orderInfo.setLastUpdatedDate(lastUpdatedDate);
		WorkOrderMaintenanceInfo orderMaintenanceInfo = workOrderMaintInfoDao.getById(WorkOrderMaintenanceInfo.class, woMaintId);
		orderMaintenanceInfo.setFaultType(workOrderInfo.getFaultType());
		orderMaintenanceInfo.setRemark(workOrderInfo.getRemark());
		orderMaintenanceInfo.setLastUpdatedDate(lastUpdatedDate);
		orderMaintenanceInfo.setUpdater(workOrderInfo.getUpdater());
		getSession().flush();
		return serviceReturns;
	}

	@Override
	public ServiceReturns updatePlanWorkOrder(WorkOrderInfo workOrderInfo)
			throws PmmsException {
		serviceReturns = new ServiceReturns();
		Date lastUpdatedDate = Tools.getToday();
		Integer id = workOrderInfo.getId();                                         //工单的ID
		Integer woMaintId = Integer.parseInt(workOrderInfo.getWoMaintId());         //维修工单的ID
		WorkOrderInfo orderInfo = workOrderInfoDao.getById(WorkOrderInfo.class, id);
		orderInfo.setLastUpdatedDate(lastUpdatedDate);                              //工单表插入最后更新时间
		//orderInfo.setFaultDescription(workOrderInfo.getFaultDescription());
		WorkOrderMaintenanceInfo orderMaintenanceInfo = workOrderMaintInfoDao.getById(WorkOrderMaintenanceInfo.class, woMaintId);
		//orderMaintenanceInfo.setFaultType(workOrderInfo.getFaultType());
		orderMaintenanceInfo.setRemark(workOrderInfo.getRemark());
		orderMaintenanceInfo.setLastUpdatedDate(lastUpdatedDate);
		orderMaintenanceInfo.setUpdater(workOrderInfo.getUpdater());
		getSession().flush();
		return serviceReturns;
	}

	@Override
	public List<WorkOrderInfo> getWOInProcess(Date startDate, Date endDate)
			throws PmmsException {
		List<WorkOrderInfo> list = workOrderInfoDao.getWOINProcess(startDate, endDate);
		return list;
	}

	@Override
	public HSSFWorkbook curFMWOExportToExcel() throws PmmsException {
		HSSFWorkbook hwb = new HSSFWorkbook();
		
		HSSFSheet sheet = hwb.createSheet("当前故障维修工单");
		
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
		firtRow.createCell(0).setCellValue("当前故障维修工单");
		Tools.setCellStyleForEachCell(firtRow, csHead0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 13));
		
			
		HSSFRow title = sheet.createRow(rowNum);
		
		HSSFFont headFont = hwb.createFont();
		headFont.setFontHeightInPoints((short)11);
		headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		HSSFCellStyle csHead = hwb.createCellStyle();
		csHead.setFont(headFont);
		csHead.setAlignment(CellStyle.ALIGN_CENTER);
		
		title.createCell(0).setCellValue("工单号");
		title.createCell(1).setCellValue("工单状态"); 
		title.createCell(2).setCellValue("设备编号");
		title.createCell(3).setCellValue("设备名称");
		title.createCell(4).setCellValue("设备型号");
		title.createCell(5).setCellValue("固定资产号");
		title.createCell(6).setCellValue("故障描述");
		title.createCell(7).setCellValue("申报人");
		title.createCell(8).setCellValue("申报时间");
		title.createCell(9).setCellValue("涉及安全隐患");
		title.createCell(10).setCellValue("是否需停机");
		title.createCell(11).setCellValue("维修工");  
		title.createCell(12).setCellValue("维修开始时间"); 
		title.createCell(13).setCellValue("备注"); 
		Tools.setCellStyleForEachCell(title, csHead);
		LOGGER.info("set head each cell style");
		
		HSSFRow temp = null;
		for(WorkOrderInfo workOrderInfo : workOrderList){
			temp = sheet.createRow((short)++rowNum);
			temp.createCell(0).setCellValue(workOrderInfo.getWorkOrderId());
			String status = workOrderInfo.getWorkOrderStatus();
			if(Constants.WORK_ORDER_STATUS_SUBMIT.equals(status)){
				temp.createCell(1).setCellValue(Constants.WORK_ORDER_STATUS_SUBMIT_DESC);
			}else{
				temp.createCell(1).setCellValue(Constants.WORK_ORDER_STATUS_MAINT_DESC);
			}
			temp.createCell(2).setCellValue(workOrderInfo.getEquipId());
			temp.createCell(3).setCellValue(workOrderInfo.getEquipName());
			temp.createCell(4).setCellValue(workOrderInfo.getEquipModel());
			temp.createCell(5).setCellValue(workOrderInfo.getAssetId());
			temp.createCell(6).setCellValue(workOrderInfo.getFaultDescription());
			temp.createCell(7).setCellValue(workOrderInfo.getCreator());
			temp.createCell(8).setCellValue(workOrderInfo.getStrCreateDate());
			if(workOrderInfo.isSafetyInvolved()){
				temp.createCell(9).setCellValue(Constants.SAFETY_INVOLVED_DESC);
			}else{
				temp.createCell(9).setCellValue(Constants.NOT_SAFETY_INVOLVED_DESC);
			}
			if(workOrderInfo.isShutdownFlag()){
				temp.createCell(10).setCellValue(Constants.SHUTDOWN_DESC);
			}else{
				temp.createCell(10).setCellValue(Constants.NOT_SHUTDOWN_DESC);
			}
			temp.createCell(11).setCellValue(workOrderInfo.getMechianic());
			temp.createCell(12).setCellValue(workOrderInfo.getStrMaintanceStartDate());
			temp.createCell(13).setCellValue(workOrderInfo.getRemark());
		}
		return hwb;
	}

	@Override
	public HSSFWorkbook hisFMWOExportToExcel() throws PmmsException {
		HSSFWorkbook hwb = new HSSFWorkbook();
		
		HSSFSheet sheet = hwb.createSheet("历史故障维修工单");
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
		firtRow.createCell(0).setCellValue("历史故障维修工单");
		Tools.setCellStyleForEachCell(firtRow, csHead0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 13));
		
			
		HSSFRow title = sheet.createRow(rowNum);
		
		HSSFFont headFont = hwb.createFont();
		headFont.setFontHeightInPoints((short)11);
		headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		HSSFCellStyle csHead = hwb.createCellStyle();
		csHead.setFont(headFont);
		csHead.setAlignment(CellStyle.ALIGN_CENTER);
		
		title.createCell(0).setCellValue("工单号");
		title.createCell(1).setCellValue("设备编号");
		title.createCell(2).setCellValue("设备名称");
		title.createCell(3).setCellValue("设备型号");
		title.createCell(4).setCellValue("固定资产号");
		title.createCell(5).setCellValue("故障描述");
		title.createCell(6).setCellValue("申报人");
		title.createCell(7).setCellValue("申报时间");
		title.createCell(8).setCellValue("涉及安全隐患");
		title.createCell(9).setCellValue("是否需停机");
		title.createCell(10).setCellValue("维修工");  
		title.createCell(11).setCellValue("维修开始时间"); 
		title.createCell(12).setCellValue("维修结束时间"); 
		title.createCell(13).setCellValue("备注"); 
		Tools.setCellStyleForEachCell(title, csHead);
		LOGGER.info("set head each cell style");
		
		HSSFRow temp = null;
		for(WorkOrderInfo workOrderInfo : workOrderList){
			temp = sheet.createRow((short)++rowNum);
			temp.createCell(0).setCellValue(workOrderInfo.getWorkOrderId());
			temp.createCell(1).setCellValue(workOrderInfo.getEquipId());
			temp.createCell(2).setCellValue(workOrderInfo.getEquipName());
			temp.createCell(3).setCellValue(workOrderInfo.getEquipModel());
			temp.createCell(4).setCellValue(workOrderInfo.getAssetId());
			temp.createCell(5).setCellValue(workOrderInfo.getFaultDescription());
			temp.createCell(6).setCellValue(workOrderInfo.getCreator());
			temp.createCell(7).setCellValue(workOrderInfo.getStrCreateDate());
			if(workOrderInfo.isSafetyInvolved()){
				temp.createCell(8).setCellValue(Constants.SAFETY_INVOLVED_DESC);
			}else{
				temp.createCell(8).setCellValue(Constants.NOT_SAFETY_INVOLVED_DESC);
			}
			if(workOrderInfo.isShutdownFlag()){
				temp.createCell(9).setCellValue(Constants.SHUTDOWN_DESC);
			}else{
				temp.createCell(9).setCellValue(Constants.NOT_SHUTDOWN_DESC);
			}
			temp.createCell(10).setCellValue(workOrderInfo.getMechianic());
			temp.createCell(11).setCellValue(workOrderInfo.getStrMaintanceStartDate());
			temp.createCell(12).setCellValue(workOrderInfo.getStrMaintanceEndDate());
			temp.createCell(13).setCellValue(workOrderInfo.getRemark());
		}
		return hwb;
	}

	@Override
	public HSSFWorkbook curPMWOExportToExcel() throws PmmsException {
		HSSFWorkbook hwb = new HSSFWorkbook();
		
		HSSFSheet sheet = hwb.createSheet("当前计划保养工单");
		
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
		firtRow.createCell(0).setCellValue("当前计划保养工单");
		Tools.setCellStyleForEachCell(firtRow, csHead0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
			
		HSSFRow title = sheet.createRow(rowNum);
		
		HSSFFont headFont = hwb.createFont();
		headFont.setFontHeightInPoints((short)11);
		headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		HSSFCellStyle csHead = hwb.createCellStyle();
		csHead.setFont(headFont);
		csHead.setAlignment(CellStyle.ALIGN_CENTER);
		
		title.createCell(0).setCellValue("工单号");
		title.createCell(1).setCellValue("工单状态"); 
		title.createCell(2).setCellValue("设备编号");
		title.createCell(3).setCellValue("设备名称");
		title.createCell(4).setCellValue("设备型号");
		title.createCell(5).setCellValue("固定资产号");
		title.createCell(6).setCellValue("是否需停机");
		title.createCell(7).setCellValue("维修工");  
		title.createCell(8).setCellValue("维修开始时间"); 
		title.createCell(9).setCellValue("计划开始月"); 
		title.createCell(10).setCellValue("备注"); 
		Tools.setCellStyleForEachCell(title, csHead);
		LOGGER.info("set head each cell style");
		
		HSSFRow temp = null;
		for(WorkOrderInfo workOrderInfo : workOrderList){
			temp = sheet.createRow((short)++rowNum);
			temp.createCell(0).setCellValue(workOrderInfo.getWorkOrderId());
			String status = workOrderInfo.getWorkOrderStatus();
			if(Constants.WORK_ORDER_STATUS_SUBMIT.equals(status)){
				temp.createCell(1).setCellValue(Constants.WORK_ORDER_STATUS_SUBMIT_DESC);
			}else{
				temp.createCell(1).setCellValue(Constants.WORK_ORDER_STATUS_MAINT_DESC);
			}
			temp.createCell(2).setCellValue(workOrderInfo.getEquipId());
			temp.createCell(3).setCellValue(workOrderInfo.getEquipName());
			temp.createCell(4).setCellValue(workOrderInfo.getEquipModel());
			temp.createCell(5).setCellValue(workOrderInfo.getAssetId());
			if(workOrderInfo.isShutdownFlag()){
				temp.createCell(6).setCellValue(Constants.SHUTDOWN_DESC);
			}else{
				temp.createCell(6).setCellValue(Constants.NOT_SHUTDOWN_DESC);
			}
			temp.createCell(7).setCellValue(workOrderInfo.getMechianic());
			temp.createCell(8).setCellValue(workOrderInfo.getStrMaintanceStartDate());
			temp.createCell(9).setCellValue(workOrderInfo.getStrPlanStartMonth());
			temp.createCell(10).setCellValue(workOrderInfo.getRemark());
		}
		return hwb;
	}

	@Override
	public HSSFWorkbook hisPMWOExportToExcel() throws PmmsException {
		HSSFWorkbook hwb = new HSSFWorkbook();
		
		HSSFSheet sheet = hwb.createSheet("历史计划保养工单");
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
		firtRow.createCell(0).setCellValue("历史计划保养工单");
		Tools.setCellStyleForEachCell(firtRow, csHead0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
		
		
		//标题
		HSSFRow title = sheet.createRow(1);
		
		HSSFFont headFont = hwb.createFont();
		headFont.setFontHeightInPoints((short)11);
		headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		HSSFCellStyle csHead = hwb.createCellStyle();
		csHead.setFont(headFont);
		csHead.setAlignment(CellStyle.ALIGN_CENTER);
		
		title.createCell(0).setCellValue("工单号");
		title.createCell(1).setCellValue("设备编号");
		title.createCell(2).setCellValue("设备名称");
		title.createCell(3).setCellValue("设备型号");
		title.createCell(4).setCellValue("固定资产号");
		title.createCell(5).setCellValue("是否需停机");
		title.createCell(6).setCellValue("维修工");  
		title.createCell(7).setCellValue("维修开始时间"); 
		title.createCell(8).setCellValue("维修结束时间"); 
		title.createCell(9).setCellValue("计划开始月"); 
		title.createCell(10).setCellValue("备注"); 
		Tools.setCellStyleForEachCell(title, csHead);
		LOGGER.info("set head each cell style");
		
		HSSFRow temp = null;
		for(WorkOrderInfo workOrderInfo : workOrderList){
			temp = sheet.createRow((short)++rowNum);
			temp.createCell(0).setCellValue(workOrderInfo.getWorkOrderId());
			temp.createCell(1).setCellValue(workOrderInfo.getEquipId());
			temp.createCell(2).setCellValue(workOrderInfo.getEquipName());
			temp.createCell(3).setCellValue(workOrderInfo.getEquipModel());
			temp.createCell(4).setCellValue(workOrderInfo.getAssetId());
			if(workOrderInfo.isShutdownFlag()){
				temp.createCell(5).setCellValue(Constants.SHUTDOWN_DESC);
			}else{
				temp.createCell(5).setCellValue(Constants.NOT_SHUTDOWN_DESC);
			}
			temp.createCell(6).setCellValue(workOrderInfo.getMechianic());
			temp.createCell(7).setCellValue(workOrderInfo.getStrMaintanceStartDate());
			temp.createCell(8).setCellValue(workOrderInfo.getStrMaintanceEndDate());
			temp.createCell(9).setCellValue(workOrderInfo.getStrPlanStartMonth());
			temp.createCell(10).setCellValue(workOrderInfo.getRemark());
		}
		return hwb;
	}

	@Override
	public void woCreatedReminder(WorkOrderInfo workOrderInfo)
			throws PmmsException {
		//是否发邮件标志
//		String mailFlag = Constants.SYS_CONFIG_MAP.get(Constants.FLAG_MAIL_REMINDER);
		//是否发短信标志
		String msgFlag = Constants.SYS_CONFIG_MAP.get(Constants.FLAG_MSG_REMINDER);
		//邮件模板、
//		StringBuilder mailTemp = new StringBuilder(128);
//		mailTemp.append("Hi,\n\n");
//		mailTemp.append("This is an automated email from PMMS system. \n\n ");
//		mailTemp.append("There is a  Fault Maintenance Work Order is raised,Please deal with it in time.\n\n");
//		mailTemp.append("The NO. of WO as follows.\n\n");
		//短信模板
		StringBuilder msgTemp = new StringBuilder(128);
		msgTemp.append("你好,PMMS平台提醒你，有一个故障维修工单产生，请即时处理。工单编号如下:");
		
		try {
//			MailMessageVO message = new MailMessageVO();
//			message.setSubject("PMMS Project - WO Created Reminder");
//			message.setFrom("PMMS.Team@igate.com");
			
			//获取收件人
			String receiver = Constants.SYS_CONFIG_MAP.get(Constants.MAINTANENCE_TEAM);
			String[] receivers = receiver.split(",");
			User user = null;
			
			//短信提醒内容文本
			List<OutboundMessage> msgs = new ArrayList<OutboundMessage>();
			String msgContent = msgTemp.toString()+System.getProperty("line.separator")+workOrderInfo.getWorkOrderId();
						
			//邮件内容
//			String mailContent = mailTemp.toString()+workOrderInfo.getWorkOrderId();
			
			//获取联系人的Email 和 phone number
			String[] ContractNOs = new String[receivers.length];
//			String[] emails = new String[receivers.length];
			for(int i=0; i<receivers.length;i++){
				user = commonService.getUserBySso(receivers[i]);
				
				OutboundMessage obm = new OutboundMessage(user.getContactNo(),msgContent);
				obm.setEncoding(MessageEncodings.ENCUCS2);
				msgs.add(obm);
				
				ContractNOs[i] = user.getContactNo();
//				emails[i] = user.getEmailId();
			}
			//邮件
//			if(mailFlag.equalsIgnoreCase(Constants.FLAG_REMIND)){
//				  LOGGER.info("WO Created Send Email reminder Starting...... ");
//				  message.setToEmail(emails);
//				  message.setMessage(mailContent);
//				  iMailSendService.sendMail(message);
//				  LOGGER.info("WO Created Send Email reminder Ending...... ");
//			}
			//短信
			if(msgFlag.equalsIgnoreCase(Constants.FLAG_REMIND)){
				  LOGGER.info("WO Created Send MSG reminder Starting...... ");
				  MsgUtil.open(Constants.MSG_PORT, Constants.MSG_GATE_WAY);
				  MsgUtil.sendMutipleSms(msgs);
				  LOGGER.info("WO Created Send MSG reminder Ended...... ");
			}
		}
		catch (Exception e) {
			LOGGER.info(e);
			LOGGER.info("woCreatedReminder() method occures exception.....Exception:"+e.getMessage());
		}
		
		
	}

	@Override
	public ServiceReturns getFaultTypes() throws PmmsException {
		List<FaultTypeInfo> list = null;
		try {
			serviceReturns = new ServiceReturns();
			list = workOrderInfoDao.getFaultTypes();
			serviceReturns.putData("typeList", list);
		}
		catch (Exception e) {
			LOGGER.info(e);
			LOGGER.info("getFaultTypes() method occures exception.....Exception:"+e.getMessage());
		}
		
		return serviceReturns;
	}
	
	
}
