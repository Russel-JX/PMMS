/**
 * ============================================================
 * File : HistoricalWorkOrder.java
 * Description : 
 * 
 * Package : com.ge.pmms.controller
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Jan 27, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.pmms.controller;

import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ge.pmms.base.BaseController;
import com.ge.pmms.po.User;
import com.ge.pmms.po.WorkOrderInfo;
import com.ge.pmms.service.WorkOrderInfoService;
import com.ge.pmms.utils.GsonSingleton;
import com.ge.pmms.utils.PMMSConstants;
import com.ge.pmms.utils.Tools;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Jan 27, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
@Scope("request")
@Controller
public class HistoricalWorkOrderController extends BaseController {
	@Autowired
	private WorkOrderInfoService workOrderInfoService;
	
	@RequestMapping(value="orderMgmt/fwMgmt")
	public ModelAndView faultWorkOrderMgmt(HttpServletRequest request){
		ModelAndView view = new ModelAndView();
		view.setViewName("WOMgmt/FWMgmt");
		return view;
	}
	
	@RequestMapping(value="orderMgmt/printPage")
	public ModelAndView printPage(HttpServletRequest request){
		ModelAndView view = new ModelAndView();
		String temp = request.getParameter("orderInfo1");
		request.setAttribute("orderInfo2",temp);
		view.setViewName("WOMgmt/printFWO");
		return view;
	}
	
	@RequestMapping(value="orderMgmt/pwMgmt")
	public ModelAndView planWorkOrderMgmt(HttpServletRequest request){
		ModelAndView view = new ModelAndView();
		view.setViewName("WOMgmt/PWMgmt");
		return view;
	}
	
	@RequestMapping(value="Hitorical/getWorkOrders",produces="text/html;charset=utf-8")
	public @ResponseBody 
	String getHisWorkOrders(HttpServletRequest request){
		try {
			WorkOrderInfo workOrderInfo = new WorkOrderInfo();
			String workOrderType = request.getParameter("orderType");
			String orderFlag = request.getParameter("orderFlag");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String department = request.getParameter("department");
			workOrderInfo.setWorkOrderType(workOrderType);
			workOrderInfo.setOrderFlag(orderFlag);
			workOrderInfo.setStartDate(startDate);
			workOrderInfo.setEndDate(endDate);
			workOrderInfo.setDeptId(department);
			returns = workOrderInfoService.getWorkOrders(workOrderInfo);
		}
		catch (Exception e) {
			LOGGER.info(e);
			Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	//编辑更新历史工单
	@RequestMapping(value="Hitorical/updateWorkOrder",produces="text/html;charset=utf-8" )
	public @ResponseBody
	String updateWorkOrderInfo(HttpServletRequest request){
		try {
			HttpSession session = request.getSession();	
			User user = (User)session.getAttribute(PMMSConstants.USER_SESSION_DATA);
			String updater = user.getSso();
			LOGGER.info("-------------sso---------------");
			LOGGER.info(updater);
			LOGGER.info("-------------sso---------------");
			String jsonWorkOrderInfo = request.getParameter("workOrderInfo");
			Gson gson = GsonSingleton.getGsonInstance();
			Type woinfoType = new TypeToken<WorkOrderInfo>() {}.getType();
			WorkOrderInfo workOrderInfo = gson.fromJson(jsonWorkOrderInfo, woinfoType);
			workOrderInfo.setUpdater(updater);
			returns = workOrderInfoService.updateWorkOrder(workOrderInfo);			
		}
		catch (Exception e) {
			LOGGER.info(e);
			Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}


	//编辑更新历史计划保养工单
	@RequestMapping(value="Hitorical/updatePlanWorkOrder",produces="text/html;charset=utf-8" )
	public @ResponseBody
	String updatePlanWorkOrderInfo(HttpServletRequest request){
		try {
			HttpSession session = request.getSession();	
			User user = (User)session.getAttribute(PMMSConstants.USER_SESSION_DATA);
			String updater = user.getSso();
			
			String jsonWorkOrderInfo = request.getParameter("workOrderInfo");
			Gson gson = GsonSingleton.getGsonInstance();
			Type woinfoType = new TypeToken<WorkOrderInfo>() {}.getType();
			WorkOrderInfo workOrderInfo = gson.fromJson(jsonWorkOrderInfo, woinfoType);
			workOrderInfo.setUpdater(updater);
			returns = workOrderInfoService.updatePlanWorkOrder(workOrderInfo);		
		}
		catch (Exception e) {
			LOGGER.info(e);
			Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
}
