/**
 * ============================================================
 * File : WorkOrderInfoController.java
 * Description : 
 * 
 * Package : com.ge.pmms.controller
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

package com.ge.pmms.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ge.pmms.base.BaseController;
import com.ge.pmms.base.ControllerReturns;
import com.ge.pmms.po.EquipInfo;
import com.ge.pmms.po.User;
import com.ge.pmms.po.WorkOrderInfo;
import com.ge.pmms.service.CommonService;
import com.ge.pmms.service.EquipInfoService;
import com.ge.pmms.service.WorkOrderInfoService;
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
@Scope("request")
@Controller
public class WorkOrderInfoController extends BaseController {

	@Autowired
	private WorkOrderInfoService workOrderInfoService;
	@Autowired
	private EquipInfoService equipInfoService;
	@Autowired
	private CommonService commonService;
	
	
	@RequestMapping(value="front/index")
	public ModelAndView frontMgmt(HttpServletRequest request){
		ModelAndView view = new ModelAndView();
		view.setViewName("frontMgmt/frontMgmt");
		return view;
	}
	/**
	 * *获取工单
	 * *get workOrders data	
	 * @Author: iGATE 
	 * @param request
	 * @return
	 * @Description:
	 */
	@RequestMapping(value="workOrder/getWorkOrders",produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getWorkOrders(HttpServletRequest request){
		try {
			WorkOrderInfo workOrderInfo = new WorkOrderInfo();			
			//测试完删除掉******************************
			String workOrderType = request.getParameter("orderType");
			String orderFlag = request.getParameter("orderFlag");
			String deptId = request.getParameter("deptId");
			String equipType = request.getParameter("equipType");
			LOGGER.info(workOrderType+", "+orderFlag+", "+deptId+", "+equipType);
			//****************************************			
			workOrderInfo.setWorkOrderType(request.getParameter("orderType"));
			workOrderInfo.setOrderFlag(request.getParameter("orderFlag"));
			workOrderInfo.setDeptId(request.getParameter("deptId"));
			workOrderInfo.setEquipType(request.getParameter("equipType"));
			returns = workOrderInfoService.getWorkOrders(workOrderInfo);
		}catch (Exception e) {
			LOGGER.info(e);
			Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	//创建故障维修工单
	@RequestMapping(value = "workOrder/saveWorkOrderInfo", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String saveWorkOrderInfo(HttpServletRequest request) {
		try {
			String creator = request.getParameter("sso");
			String equipId = request.getParameter("equipId");
			boolean ssoFlag = false;
			boolean equipIdFlag = false;
			
			//后台验证用户存不存在
			User user = commonService.getUserBySso(creator);
			if(!Tools.isNull(user)){
				ssoFlag = true;
			}
			EquipInfo equipInfo = equipInfoService.getEquipInfoByEpId(equipId);
			if(!Tools.isNull(equipInfo)){
				equipIdFlag = true;
			}
			if(ssoFlag && equipIdFlag){
				String faultDescription = request.getParameter("faultDescription");
				boolean safetyInvolved = Boolean.parseBoolean(request
						.getParameter("safetyInvolved"));
				boolean shutdownFlag = Boolean.parseBoolean(request
						.getParameter("shutdownFlag"));
				WorkOrderInfo workOrderInfo = new WorkOrderInfo();
				workOrderInfo.setWorkOrderId(Tools.getTimeStr());
				workOrderInfo.setWorkOrderType(Constants.WORK_ORDER_TYPE_FALUT);
				workOrderInfo.setWorkOrderStatus(Constants.WORK_ORDER_STATUS_SUBMIT);
				workOrderInfo.setCreator(creator);
				workOrderInfo.setEquipId(equipId);
				workOrderInfo.setFaultDescription(faultDescription);
				workOrderInfo.setSafetyInvolved(safetyInvolved);
				workOrderInfo.setShutdownFlag(shutdownFlag);
				workOrderInfo.setCreatedate(Tools.getToday());
				workOrderInfo.setLastUpdatedDate(Tools.getToday());
				returns = workOrderInfoService.save(workOrderInfo);	
				returns.putData("addWO", "success");
				workOrderInfoService.woCreatedReminder(workOrderInfo);
			}else{
				returns = new ControllerReturns();
				returns.putData("addWO", null);
			}
		}
		catch (Exception e) {
			LOGGER.info(e);
			Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	//根据用户sso,查询用户信息
	@RequestMapping(value="workOrder/isUser",produces="text/html;charset=utf-8")
	public @ResponseBody
	String getUserBySso(HttpServletRequest request){
		try{
			returns = new ControllerReturns();
			String sso = request.getParameter("sso").trim();
			User temp = commonService.getUserBySso(sso);
			//只返回用户的 sso， firstName  lastName
			User user = null;
			if(!Tools.isNull(temp)){
				user = new User();
				user.setSso(temp.getSso());
				user.setFirstName(temp.getFirstName());
				user.setLastName(temp.getLastName());
			}
			returns.putData("user", user);
		}catch(Exception e){
			LOGGER.info(e);
			Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	//根据用户sso，查询用户是否是维修团队的成员
	@RequestMapping(value="workOrder/isMaintUser",produces="text/html;charset=utf-8")
	public @ResponseBody 
	String isMaintUser(HttpServletRequest request){
		try {
			returns = new ControllerReturns();
			String sso = request.getParameter("sso").trim();
			String maintTeam = Constants.SYS_CONFIG_MAP.get(Constants.MAINTANENCE_TEAM);
			String[] users = maintTeam.split(",");
			boolean flag = false;
			for(String user : users){
				if(sso.equals(user)){
					flag = true;
					break;
				}
			}
			User userEntity = commonService.getUserBySso(sso);
			User user = null;
			if(flag && !(Tools.isNull(userEntity))){
				user = new User();
				user.setSso(userEntity.getSso());
				user.setFirstName(userEntity.getFirstName());
				user.setLastName(userEntity.getLastName());
			}
			returns.putData("maintUser", user);
		}
		catch (Exception e) {
			LOGGER.info(e);
			Tools.getExceptionControllerRetruns(e);
		}
		
		return returns.generateJsonData();
	}
	
	//根据设备ID,查询设备
	@RequestMapping(value="workOrder/isEquip",produces="text/html;charset=utf-8")
	public @ResponseBody
	String getEquipByEqipId(HttpServletRequest request){		
		try{
			returns = new ControllerReturns();
			String equipId = request.getParameter("equipId");
			if(Tools.isEmpty(equipId)){
				returns.putData("equip", null);
			}else{
				EquipInfo temp = equipInfoService.getEquipInfoByEpId(equipId);
				//只返回前台 设备代码  和 设备名称
				EquipInfo equipInfo = null;
				if(!Tools.isNull(temp)){
					equipInfo = new EquipInfo();
					equipInfo.setEquipId(temp.getEquipId());
					equipInfo.setEquipmentName(temp.getEquipmentName());
				}
				returns.putData("equip",equipInfo);				
			}
		}catch(Exception e){
			LOGGER.info(e);
			Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	//获取故障类型
	@RequestMapping(value="workOrder/faultType",produces="text/html;charset=utf-8")
	public @ResponseBody
	String getFaultTypes(HttpServletRequest request){
		try {
			returns = workOrderInfoService.getFaultTypes();
		}
		catch (Exception e) {
			LOGGER.info(e);
			Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	//进行维修
	@RequestMapping(value="workOrder/saveWOMaintInfo",produces="text/html;charset=utf-8")
	public @ResponseBody
	String saveWOMaintInfo(HttpServletRequest request){
		try{
			
			boolean mechianicFlag = false;
			String mechianic = request.getParameter("mechianic");
			//验证维修人员是否存在
			User user = commonService.getUserBySso(mechianic);
			if(!Tools.isNull(user)){
				mechianicFlag = true;
			}
			if(!mechianicFlag){
				returns = new ControllerReturns();
				returns.putData("addMaintWO", null);
			}else{
				WorkOrderInfo workOrderInfo = new WorkOrderInfo();
				//**************测试参数，后期删除*****************************
				int id = Integer.parseInt(request.getParameter("id").trim());
	//			String workOrderType = request.getParameter("orderType");
	//			String workOrderId = request.getParameter("workOrderId");
	//			
	//			String faultType = request.getParameter("faultType");
	//			boolean shutdownFlag = Boolean.parseBoolean(request.getParameter("shutdownFlag"));
	//			String remark = request.getParameter("remark");
	//			LOGGER.info(workOrderId+" "+workOrderType+" "+mechianic+" "+faultType+" "+shutdownFlag+" "+remark);
	//			LOGGER.info(Boolean.parseBoolean(null));
				//***************************************************
				workOrderInfo.setId(id);
				workOrderInfo.setWorkOrderType(request.getParameter("orderType"));
				workOrderInfo.setWorkOrderId(request.getParameter("workOrderId"));
				workOrderInfo.setMechianic(request.getParameter("mechianic"));
				workOrderInfo.setFaultType(request.getParameter("faultType"));
				workOrderInfo.setShutdownFlag(Boolean.parseBoolean(request.getParameter("shutdownFlag")));
				workOrderInfo.setSparePartInvolved(Boolean.parseBoolean(request.getParameter("sparePartInvolved")));
				workOrderInfo.setExternalServiceInvolved(Boolean.parseBoolean(request.getParameter("externalServiceInvolved")));
				workOrderInfo.setRemark(request.getParameter("remark"));
				returns = workOrderInfoService.saveMaintOrderInfo(workOrderInfo);
				returns.putData("addMaintWO", "success");
			}
		}catch(Exception e){
			LOGGER.info(e);
			Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	//关闭维修
	@RequestMapping(value="workOrder/closeWOMaintInfo",produces="text/html;charset=utf-8")
	public @ResponseBody
	String closeWOMaintInfo(HttpServletRequest request){
		try{
			Integer id = Integer.parseInt(request.getParameter("id").trim());
			String strWoMantId = request.getParameter("woMaintId");
			Integer woMaintId = null;
			if(!Tools.isNull(strWoMantId)){
				woMaintId = Integer.parseInt(strWoMantId.trim());
			}
			String operator = request.getParameter("operator"); //关闭工单的人
			String remark = request.getParameter("remark");     //关闭工单时，可以修改备注
			//验证关闭维修人员是否存在
			boolean operatorFlag = false;
			User user = commonService.getUserBySso(operator);
			if(!Tools.isNull(user)){
				operatorFlag = true;
			}
			if(operatorFlag){
				returns = workOrderInfoService.closeMaintOrderInfo(id,woMaintId,operator,remark);
				returns.putData("closeMaint", "success");
			}else{
				returns = new ControllerReturns();
				returns.putData("closeMaint", null);
			}
		}catch(Exception e){
			LOGGER.info(e);
			Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	//备件出库，选择关联的工单
	@RequestMapping(value = "workOrder/getWOForSP", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getWOForSP(HttpServletRequest request) {
		try {
			WorkOrderInfo workOrderInfo = new WorkOrderInfo();
			String startDate = request.getParameter("startDate");         //查询FROM
			String endDate = request.getParameter("endDate");             //查询TO
			String deptId = request.getParameter("deptId");               //部门(区域)
			String equipType = request.getParameter("equipType");         //设备种类
			String workOrderType = request.getParameter("workOrderType");  //工单类型
			workOrderInfo.setStartDate(startDate);
			workOrderInfo.setEndDate(endDate);
			workOrderInfo.setDeptId(deptId);
			workOrderInfo.setEquipType(equipType);
			workOrderInfo.setWorkOrderType(workOrderType);
			returns = workOrderInfoService.getWOForSP(workOrderInfo);
		}
		catch (Exception e) {
			LOGGER.info(e);
			Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	//测试首页工单显示接口
	//----------- 测试后删除------------
	/*
	@RequestMapping(value = "workOrder/getWOInProcess", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getWOInProcess(HttpServletRequest request) {
		try {
			returns = new ControllerReturns();		
			List<WorkOrderInfo> list = workOrderInfoService.getWOInProcess(Tools.parseToDate("2015-03-18 00:00:00",Constants.DATE_PATTEN_SEC), Tools.parseToDate("2015-03-18 23:59:00",Constants.DATE_PATTEN_SEC));
			returns.putData("wolist", list);
		}
		catch (Exception e) {
			LOGGER.info(e);
			Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	*/
	
	//当前故障维修工单导出到excel
	@RequestMapping(value="workOrder/curFMWOExportToExcel",produces = "text/html;charset=utf-8")
	public @ResponseBody
	void curFMWOExportToExcel(HttpServletRequest request,HttpServletResponse response){
		try {
			LOGGER.info("current FM work order export to excel");
			HSSFWorkbook hwb = workOrderInfoService.curFMWOExportToExcel();
			this.commonExport(response, hwb, "CurrentFMWO");
		}
		catch (Exception e) {
			LOGGER.info(e);
		}
	}
	
	//历史故障维修工单导出到excel
	@RequestMapping(value="workOrder/hisFMWOExportToExcel",produces = "text/html;charset=utf-8")
	public @ResponseBody
	void hisFMWOExportToExcel(HttpServletRequest request,HttpServletResponse response){
		try {
			LOGGER.info("Historical FM work order export to excel");
			HSSFWorkbook hwb = workOrderInfoService.hisFMWOExportToExcel();
			this.commonExport(response, hwb, "HisFMWO");
		}
		catch (Exception e) {
			LOGGER.info(e);
		}
	}
	
	//当前计划保养工单导出到excel
	@RequestMapping(value="workOrder/curPMWOExportToExcel",produces = "text/html;charset=utf-8")
	public @ResponseBody
	void curPMWOExportToExcel(HttpServletRequest request,HttpServletResponse response){
		try {
			LOGGER.info("current PM work order export to excel");
			HSSFWorkbook hwb = workOrderInfoService.curPMWOExportToExcel();
			this.commonExport(response, hwb, "CurrentPMWO");
		}
		catch (Exception e) {
			LOGGER.info(e);
		}
	}
	
	//历史计划保养工单导出到excel
	@RequestMapping(value="workOrder/hisPMWOExportToExcel",produces = "text/html;charset=utf-8")
	public @ResponseBody
	void hisPMWOExportToExcel(HttpServletRequest request,HttpServletResponse response){
		try {
			LOGGER.info("Historical PM work order export to excel");
			HSSFWorkbook hwb = workOrderInfoService.hisPMWOExportToExcel();
			this.commonExport(response, hwb, "HisPMWO");
		}
		catch (Exception e) {
			LOGGER.info(e);
		}
	}
	
	//导出excel公共方法
	private void commonExport(HttpServletResponse response, HSSFWorkbook hwb,String fileName) throws IOException{
		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		hwb.write(outByteStream);
		byte[] outArray = outByteStream.toByteArray();
		response.setContentType("application/ms-excel;charset=UTF-8");
		response.setContentLength(outArray.length);
		response.setHeader("Expires", "0"); //设置本页面不要被浏览器缓存
		Calendar cal = Calendar.getInstance();
		response.setHeader("Content-Disposition", 
				"attachment; filename="
				+fileName+"_"
				+ (cal.get(Calendar.MONTH)+1) + ""
				+ cal.get(Calendar.DATE) +""
				+ cal.get(Calendar.YEAR) + ""
				+ cal.get(Calendar.HOUR_OF_DAY)
				+""+""
				+ cal.get(Calendar.MINUTE) +""
				+ cal.get(Calendar.SECOND) + ".xls");
		OutputStream outStream = response.getOutputStream();
		outStream.write(outArray);
		outStream.flush();
		outStream.close();
		outByteStream.close();		
	}
}

















