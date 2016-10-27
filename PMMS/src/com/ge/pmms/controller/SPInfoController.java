/**
 * ============================================================
 * File : SPInfoController.java
 * Description : 
 * 
 * Package : com.ge.pmms.controller
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

package com.ge.pmms.controller;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ge.pmms.base.BaseController;
import com.ge.pmms.base.ControllerReturns;
import com.ge.pmms.po.SparePartInfo;
import com.ge.pmms.po.SparePartTransInfo;
import com.ge.pmms.po.User;
import com.ge.pmms.service.SparePartInfoService;
import com.ge.pmms.service.SparePartTransInfoService;
import com.ge.pmms.utils.GsonSingleton;
import com.ge.pmms.utils.PMMSConstants;
import com.ge.pmms.utils.Tools;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


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
@Scope("request")
@Controller
public class SPInfoController extends BaseController {
	@Autowired
	private SparePartInfoService sparePartInfoService;
	@Autowired
	private SparePartTransInfoService sparePartTransInfoService;
	
	@RequestMapping(value="sparePart/spMgmt")
	public ModelAndView frontMgmt(HttpServletRequest request){
		ModelAndView view = new ModelAndView();
		view.setViewName("spMgmt/spMgmt");
		return view;
	}
	
	//获取备件基本信息
	//get spare part info list 
	@RequestMapping(value="sparePart/getSPInfo",produces="text/html;charset=utf-8")
	public @ResponseBody
	String getAllSPInfo(HttpServletRequest request){
		try{
			SparePartInfo sparePartInfo = new SparePartInfo();
			String strSafetyFlag = request.getParameter("safetyFlag");
			boolean safetyFlag = Tools.isEmpty(strSafetyFlag)? false:Boolean.parseBoolean(strSafetyFlag);
			sparePartInfo.setSafetyFlag(safetyFlag);
			
			returns = sparePartInfoService.getSPInfo(sparePartInfo);			
		}catch (Exception e) {
			Tools.getExceptionControllerRetruns(e);
			LOGGER.info(e);
		}
		return returns.generateJsonData();
	}
	//添加备件信息
	//and a spInfo record
	@RequestMapping(value="sparePart/addSPInfo")
	public @ResponseBody
	String addSPInfo(HttpServletRequest request){
		try{
		HttpSession session = request.getSession();
		//当前系统时间
		Date lastUpdatedDate = Tools.getToday();
		//从session中获取用户信息
		User user = (User)session.getAttribute(PMMSConstants.USER_SESSION_DATA);
		String sso = user.getSso();
		
		String spName = request.getParameter("sparePartName");
		String abbrevation = Tools.getPinYinAbbreviation(spName);
		String maxId = sparePartInfoService.getMaxSparePartId(spName);
		
		SparePartInfo sparePartInfo = new SparePartInfo();
		sparePartInfo.setSparePartId(abbrevation+"/"+maxId);
		
		sparePartInfo.setSparePartName(spName);
		sparePartInfo.setSparePartType(request.getParameter("sparePartType"));
		sparePartInfo.setSource(request.getParameter("source"));
		sparePartInfo.setMeasurement(request.getParameter("measurement"));
		String price = request.getParameter("price");
		if(!Tools.isNull(price)){
			sparePartInfo.setPrice(Float.parseFloat(price.trim()));	
		}
		sparePartInfo.setLocation(request.getParameter("location"));
		sparePartInfo.setRemark(request.getParameter("remark"));
		sparePartInfo.setCreatedDate(lastUpdatedDate);
		sparePartInfo.setCreator(sso);
		sparePartInfo.setUpdater(sso);
		sparePartInfo.setLastUpdatedDate(lastUpdatedDate);
		sparePartInfo.setStock(0);
		sparePartInfo.setSafetyStock(0.0);
		//*************test**************
		//System.out.println(new Date());
		//System.out.println(Tools.getToday());
		returns = sparePartInfoService.save(sparePartInfo);
		}catch(Exception e){
			Tools.getExceptionControllerRetruns(e);
			LOGGER.info(e);
		}
		return returns.generateJsonData();
	}
	//编辑保存备件信息
	//Edit a SPInfo record
	@RequestMapping(value="sparePart/updateSPInfo",produces = "text/html;charset=utf-8")
	public@ResponseBody 
	String updateSPInfo(HttpServletRequest request){
		try{
		HttpSession session = request.getSession();	
		User user = (User)session.getAttribute(PMMSConstants.USER_SESSION_DATA);
		String updater = user.getSso();
		//LOGGER.info("--------------sso----------------");
		//LOGGER.info(updater);
		//LOGGER.info("--------------sso----------------");
		SparePartInfo sparePartInfo = new SparePartInfo();
		sparePartInfo.setId(Integer.parseInt(request.getParameter("id").toString()));
		sparePartInfo.setSparePartName(request.getParameter("sparePartName"));
		sparePartInfo.setSparePartType(request.getParameter("sparePartType"));
		sparePartInfo.setSource(request.getParameter("source"));
		sparePartInfo.setMeasurement(request.getParameter("measurement"));
		String price = request.getParameter("price");
		if(!Tools.isNull(price)){
			sparePartInfo.setPrice(Float.parseFloat(price.trim()));
		}
		String ss = request.getParameter("safetyStock");
		if(!Tools.isNull(ss)){
			sparePartInfo.setSafetyStock(Double.parseDouble(ss));
		}
		sparePartInfo.setLocation(request.getParameter("location"));
		sparePartInfo.setRemark(request.getParameter("remark"));
		sparePartInfo.setUpdater(updater);
		returns = sparePartInfoService.updateSPInfoById(sparePartInfo);		
		}catch(Exception e){
			Tools.getExceptionControllerRetruns(e);
			LOGGER.info(e);
		}
		return returns.generateJsonData();
	}
	//删除备件信息
	//delete SPInfo by Ids
	@RequestMapping(value="sparePart/deleteSPInfo",produces = "text/html;charset=utf-8")
	public @ResponseBody
	String deleteSPInfo(HttpServletRequest request) {
		try {
			returns = new ControllerReturns();
			String idStr = request.getParameter("ids");
			returns.putData("deletedNum", sparePartInfoService.deleteByIdStr(SparePartInfo.class, idStr));
		}
		catch (Exception e) {
			Tools.getExceptionControllerRetruns(e);
			LOGGER.info(e);
		}
		return returns.generateJsonData();
	}
	//获取备件信息bySPId
	@RequestMapping(value="sparePart/getSPInfoBySPId",produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getSPInfoBySPId(HttpServletRequest request){
		try{
			String sparePartId = request.getParameter("sparePartId");
			returns = sparePartInfoService.getSPInfoBySPId(sparePartId);
			//System.out.println(returns.generateJsonData());
		}catch(Exception e){
			Tools.getExceptionControllerRetruns(e);
			LOGGER.info(e);
		}
		return returns.generateJsonData();
	}

	//备件入库
	@RequestMapping(value="sparePart/spIn",produces="text/html;charset=utf-8")
	public @ResponseBody
	String sparePartIn(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();	
			User user = (User)session.getAttribute(PMMSConstants.USER_SESSION_DATA);
			String updater = user.getSso();
			
			int account = Integer.parseInt(request.getParameter("account").trim());
			String inRemark = request.getParameter("remark");
			String strPrice = request.getParameter("price").trim();
			Float inPrice = Tools.isEmpty(strPrice)? 0:Float.parseFloat(strPrice);
			String strLeadTimme = request.getParameter("leadTime");
			Float leadTime = Tools.isEmpty(strLeadTimme)? 0: Float.parseFloat(strLeadTimme);
			String jsonSpInfo = request.getParameter("spInfo");
			Gson gson = GsonSingleton.getGsonInstance();
			Type spInfoType = new TypeToken<SparePartInfo>() {}.getType();
			SparePartInfo sparePartInfo = gson.fromJson(jsonSpInfo, spInfoType);
			sparePartInfo.setAccount(account);
			sparePartInfo.setInPrice(inPrice);
			sparePartInfo.setInRemark(inRemark);
			sparePartInfo.setLeadTime(leadTime);
			sparePartInfo.setCreator(updater);
			sparePartInfo.setUpdater(updater);
			returns = sparePartInfoService.sparePartIn(sparePartInfo);						
		}
		catch (Exception e) {
			Tools.getExceptionControllerRetruns(e);
			LOGGER.info(e);
		}
		return returns.generateJsonData();
	}
	
	//备件出库
	@RequestMapping(value="sparePart/spOut",produces="text/html;charset=utf-8")
	public @ResponseBody
	String sparePartOut(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();	
			User user = (User)session.getAttribute(PMMSConstants.USER_SESSION_DATA);
			String updater = user.getSso();
			
			String workOrderId = request.getParameter("workOrderId");
			int account = Integer.parseInt(request.getParameter("account").trim());
			String outRemark = request.getParameter("remark");
			String jsonSpInfo = request.getParameter("spInfo");
			Gson gson = GsonSingleton.getGsonInstance();
			Type spInfoType = new TypeToken<SparePartInfo>() {}.getType();
			SparePartInfo sparePartInfo = gson.fromJson(jsonSpInfo, spInfoType);
			sparePartInfo.setWorkOrderId(workOrderId);
			sparePartInfo.setAccount(account);
			sparePartInfo.setOutRemark(outRemark);
			sparePartInfo.setCreator(updater);
			sparePartInfo.setUpdater(updater);
			returns = sparePartInfoService.sparePartOut(sparePartInfo);				
		}
		catch (Exception e) {
			Tools.getExceptionControllerRetruns(e);
			LOGGER.info(e);
		}
		return returns.generateJsonData();
	}
	//获取备件出入库记录tab008
	@RequestMapping(value="sparePart/getSPTrans",produces="text/html;charset=utf-8")
	public @ResponseBody
	String getSPTrans(HttpServletRequest request){
		try{
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String transType = request.getParameter("transType");
			SparePartTransInfo sparePartTransInfo = new SparePartTransInfo();
			sparePartTransInfo.setSearchFromDate(startDate);
			sparePartTransInfo.setSearchToDate(endDate);
			sparePartTransInfo.setTransType(transType);
			//LOGGER.info(startDate+":::"+endDate+":::"+transType);
			returns = sparePartTransInfoService.getSPTrans(sparePartTransInfo);
		}catch(Exception e){
			Tools.getExceptionControllerRetruns(e);
			LOGGER.info(e);
		}
		return returns.generateJsonData();
	}
	
	//获取备件出入库记录tab010
	@RequestMapping(value="sparePart/getSPTransByWOID",produces="text/html;charset=utf-8")
	public @ResponseBody
	String getSPTransByWOID(HttpServletRequest request){
		try{
			String workOrderId = request.getParameter("workOrderId");
			returns = sparePartTransInfoService.getSPTransByWOID(workOrderId);
		}catch(Exception e){
			Tools.getExceptionControllerRetruns(e);
			LOGGER.info(e);
		}
		return returns.generateJsonData();
	}
	
	//Export to Excel
	//备件基本信息导出
	@RequestMapping(value="sparePart/spInfoExportToExcel",produces="text/html;charset=utf-8")
	public @ResponseBody
	void spInfoExportToExcel(HttpServletRequest request,HttpServletResponse response){
		try {
			LOGGER.info("Spare Part Info start to export to excel");
			//type==1 代表库存信息
			//type==2 代表小于安全库存的备件信息
			String type = request.getParameter("type");
			System.out.println(type);
			HSSFWorkbook hwb = sparePartInfoService.sparePartInfoExportToExcel(type);
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			hwb.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();
			response.setContentType("application/ms-excel;charset=UTF-8");
			response.setContentLength(outArray.length);
			response.setHeader("Expires", "0"); //设置本页面不要被浏览器缓存
			Calendar cal = Calendar.getInstance();
			response.setHeader("Content-Disposition", 
					"attachment; filename=spInfo_"
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
		catch (Exception e) {
			LOGGER.info(e);
		}
	}
	
	
	//备件出入库记录
	@RequestMapping(value="sparePart/spRecordsExportToExcel",produces="text/html;charset=utf-8")
	public @ResponseBody
	void spRecordsExportToExcel(HttpServletRequest request,HttpServletResponse response){
		try {
			LOGGER.info("Spare Part Info start to export to excel");
			HSSFWorkbook hwb = sparePartTransInfoService.spTransExportToExcel();
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			hwb.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();
			response.setContentType("application/ms-excel;charset=UTF-8");
			response.setContentLength(outArray.length);
			response.setHeader("Expires", "0"); //设置本页面不要被浏览器缓存
			Calendar cal = Calendar.getInstance();
			response.setHeader("Content-Disposition", 
					"attachment; filename=Records_"
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
		catch (Exception e) {
			Tools.getExceptionControllerRetruns(e);
			LOGGER.info(e);
		}
	}
	
	//计算建议安全库存
	@RequestMapping(value="sparePart/calculateSysSafetyStock",produces="text/html;charset=utf-8")
	public @ResponseBody
	String calculateSysSafetyStock(HttpServletRequest request){
		try {
			returns = sparePartTransInfoService.updateSysSafetyStock();		
		}
		catch (Exception e) {
			Tools.getExceptionControllerRetruns(e);
			LOGGER.info(e);
		}
		return returns.generateJsonData();
	}
	
	// 根据设备名称模糊查询设备
	@RequestMapping(value = "/sparePart/getSPsBySPName", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getSPsBySPName(HttpServletRequest request) {
		try {
			String spName = request.getParameter("spName");
			returns=sparePartInfoService.getSPsBySPName(spName);
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}	
}













