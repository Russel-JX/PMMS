/**
 * ============================================================
 * File : IdmMgmtController.java
 * Description : 
 * 
 * Package : com.ge.pmms.controller
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

package com.ge.pmms.controller;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ge.pmms.base.BaseController;
import com.ge.pmms.po.IdmInfo;
import com.ge.pmms.po.IdmTransInfo;
import com.ge.pmms.po.IdmTypeDetail;
import com.ge.pmms.po.User;
import com.ge.pmms.service.IdmInfoService;
import com.ge.pmms.service.IdmTransService;
import com.ge.pmms.service.IdmTypeService;
import com.ge.pmms.utils.PMMSConstants;
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
@Scope("request")
@Controller
public class IdmMgmtController extends BaseController{
	
	@Autowired
	private IdmInfoService idmInfoService;
	
	@Autowired
	private IdmTransService idmTransService;
	
	@Autowired
	private IdmTypeService idmTypeService;
	
//	@Autowired
//	private IdmInfoDao idmInfoDao;

	@RequestMapping(value="idmMgmt/idmMgmt")
	public ModelAndView frontMgmt(HttpServletRequest request){
		ModelAndView view = new ModelAndView();
		view.setViewName("idmMgmt/idmMgmt");
		return view;
	}
	
	@RequestMapping(value = "/IdmMgmt/getIdmInfoByTypeId", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getIdmInfoByType(HttpServletRequest request) {
		try {
			String idmTypeId=request.getParameter("idmTypeId");
			IdmInfo idmInfo=new IdmInfo();
			if(!Tools.isEmpty(idmTypeId)){
				idmInfo.setTypeId(idmTypeId);
				returns=idmInfoService.getIdmInfoByType(idmInfo);
			}		
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	@RequestMapping(value = "/IdmMgmt/getIdmInfoByIdmId", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getIdmInfoByIdmId(HttpServletRequest request) {
		try {
			String idmId=request.getParameter("idmId");
			IdmInfo idmInfo=new IdmInfo();
			if(!Tools.isEmpty(idmId)){
				idmInfo.setIdmId(idmId);
				LOGGER.info("getIdmInfoByIdmId 's idmId "+idmId);
				returns=idmInfoService.getIdmInfoByIdmId(idmInfo);
			}		
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	@RequestMapping(value = "/IdmMgmt/updateIdmInfo", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String updateIdmInfo(HttpServletRequest request) {
		BufferedReader input =null;
		try {
			request.setCharacterEncoding("utf-8");
			InputStreamReader ins  = new InputStreamReader(request.getInputStream(),"utf-8");
			 input = new BufferedReader(ins);
			//String o=""; 
			String str="";
			StringBuffer o = new StringBuffer();
			while (null != ((str=input.readLine()))) {
				// o=o+str;
				o.append(str);
				} 
			JSONObject json = JSONObject.fromObject(o.toString());
			User user = (User) request.getSession().getAttribute(PMMSConstants.USER_SESSION_DATA);
			if (null != user) {
				json.put("updater",String.valueOf(user.getSso()));
		      }
			returns=idmInfoService.updateIdmInfo(json);
				
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}finally{
			if (input != null) { 
            	try {
					input.close();
				}
				catch (IOException e) {
					LOGGER.error("update idm  failed", e);
				} 
		}
	}
		return returns.generateJsonData();
	}
	
	@RequestMapping(value = "/IdmMgmt/getIdmTransRecords", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getIdmTransRecords(HttpServletRequest request) {
		try {
				returns=idmInfoService.getAllIdmTransRecords();
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	@RequestMapping(value = "/IdmMgmt/updateTransRecord", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String updateTransRecord(HttpServletRequest request) {
		BufferedReader input =null;
		try {
			request.setCharacterEncoding("utf-8");
			InputStreamReader ins  = new InputStreamReader(request.getInputStream(),"utf-8");
			 input = new BufferedReader(ins);
			//String o=""; 
			String str="";
			StringBuffer o = new StringBuffer();
			while (null != ((str=input.readLine()))) {
				// o=o+str;
				 o.append(str);
				} 
			JSONObject json = JSONObject.fromObject(o.toString());
			returns=idmInfoService.updateTransRecord(json);
				
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}finally{
			if (input != null) { 
            	try {
					input.close();
				}
				catch (IOException e) {
					LOGGER.error("update trans record failed", e);
				} 
		}
	}
		return returns.generateJsonData();
	}
	
	//新增间接物料
	@RequestMapping(value = "/IdmMgmt/saveIdm", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String saveIdm(HttpServletRequest request) {
		BufferedReader input =null;
		try {
			request.setCharacterEncoding("utf-8");
			InputStreamReader ins  = new InputStreamReader(request.getInputStream(),"utf-8");
			 input = new BufferedReader(ins);
			//String o=""; 
			String str="";
			StringBuffer o = new StringBuffer();
			while (null != ((str=input.readLine()))) {
				// o=o+str;
				 o.append(str);
				} 
			JSONObject json = JSONObject.fromObject(o.toString());
			JSONObject jsonObj = json;
			User user = (User) request.getSession().getAttribute(PMMSConstants.USER_SESSION_DATA);
			if (null != user) {
				json.put("creator",String.valueOf(user.getSso()));
		      }
			returns=idmInfoService.saveIdm(jsonObj);
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}finally{
			if (input != null) { 
                	try {
						input.close();
					}
					catch (IOException e) {
						LOGGER.error("save idm failed", e);
					} 
			}
		}
		return returns.generateJsonData();
	}
	
	@RequestMapping(value = "/IdmMgmt/deleIdmbyIdmId", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String deleIdmbyIdmId(HttpServletRequest request) {
		try {
			String idmId=request.getParameter("idmId");
			IdmInfo idmInfo=new IdmInfo();
			idmInfo.setIdmId(idmId);
			returns=idmInfoService.deleIdmbyIdmId(idmInfo);
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	@RequestMapping(value = "/IdmMgmt/getUnSaftyIdmInfo", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getUnSaftyIdm(HttpServletRequest request) {
		try {
			returns=idmInfoService.getUnSaftyIdm();
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	
	@RequestMapping(value = "/IdmMgmt/scanInStock", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String insertRecordTb(HttpServletRequest request) {
		try {
			String idmId=request.getParameter("idmId");
			String amount=request.getParameter("amount");
			String transId=Tools.getTimeStr();
			String leadTime=request.getParameter("leadTime");
			String poIn=request.getParameter("poIn");
			IdmTransInfo idmTransInfo=new IdmTransInfo();
			User user = (User) request.getSession().getAttribute(PMMSConstants.USER_SESSION_DATA);
			if (null != user) {
				//json.put("creator",String.valueOf(user.getSso()));
				idmTransInfo.setCreator(String.valueOf(user.getSso()));
		      }
			if(!Tools.isEmpty(idmId)){
				idmTransInfo.setIdmId(idmId);
				idmTransInfo.setAmount(amount);
				idmTransInfo.setTransId(transId);
				idmTransInfo.setLeadTime(leadTime);
				idmTransInfo.setPo(poIn);
				returns=idmTransService.insertRecordTb(idmTransInfo);
			}		
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	@RequestMapping(value = "/IdmMgmt/scanOutStock", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String scanOutStock(HttpServletRequest request) {
		try {
			String idmId=request.getParameter("idmId");
			String amount=request.getParameter("amount");
			String receiver=request.getParameter("receiver");
			String transId=Tools.getTimeStr();
			IdmTransInfo idmTransInfo=new IdmTransInfo();
			User user = (User) request.getSession().getAttribute(PMMSConstants.USER_SESSION_DATA);
			if (null != user) {
				//json.put("creator",String.valueOf(user.getSso()));
				idmTransInfo.setCreator(String.valueOf(user.getSso()));
		      }
			if(!Tools.isEmpty(idmTransInfo)){
				idmTransInfo.setIdmId(idmId);
				idmTransInfo.setAmount(amount);
				idmTransInfo.setTransId(transId);
				idmTransInfo.setReceiver(receiver);
				returns=idmTransService.scanOutStock(idmTransInfo);
			}		
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}
		if(returns==null){
			return null;
		}else{
			return returns.generateJsonData();
		}
	}  
	
	@RequestMapping(value = "/IdmMgmt/insertSubType", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String insertSubType(HttpServletRequest request) {
		try {
			String typeId=request.getParameter("typeId");
			String subType=request.getParameter("subType");
			String subTypeId=request.getParameter("subTypeId");
			String detailType=request.getParameter("detailType");
			IdmTypeDetail idmTypeDetail=new IdmTypeDetail();
			idmTypeDetail.setTYPE_ID(typeId);
			idmTypeDetail.setSUB_TYPE_NM(subType);
			idmTypeDetail.setSUB_TYPE_ID(subTypeId);
			idmTypeDetail.setTYPE_DETAIL_NM(detailType);
			if(!Tools.isEmpty(idmTypeDetail)){
				returns=idmTypeService.insertSubType(idmTypeDetail);
			}		
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	@RequestMapping(value = "/IdmMgmt/insertDetailType", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String insertDetailType(HttpServletRequest request) {
		try {
			String typeId=request.getParameter("typeId");
			String subTypeId=request.getParameter("subTypeId");
		//	String subTypeNm=request.getParameter("subTypeNm");
			String typeDetail=request.getParameter("typeDetail");
			IdmTypeDetail idmTypeDetail=new IdmTypeDetail();
			idmTypeDetail.setTYPE_ID(typeId);
			idmTypeDetail.setSUB_TYPE_ID(subTypeId);
		//	idmTypeDetail.setSUB_TYPE_NM(subTypeNm);
			idmTypeDetail.setTYPE_DETAIL_NM(typeDetail);
			if(!Tools.isEmpty(idmTypeDetail)){
				returns=idmTypeService.insertDetailType(idmTypeDetail);
			}		
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	@RequestMapping(value = "/IdmMgmt/getAssignDetailTypeId", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getAssignDetailTypeId(HttpServletRequest request) {
		try {
			String typeId=request.getParameter("typeId");
			String subTypeId=request.getParameter("subTypeId");
			IdmTypeDetail idmTypeDetail=new IdmTypeDetail();
			idmTypeDetail.setTYPE_ID(typeId);
			idmTypeDetail.setSUB_TYPE_ID(subTypeId);
			if(!Tools.isEmpty(idmTypeDetail)){
				returns=idmTypeService.getAssignDetailTypeId(idmTypeDetail);
			}		
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	@RequestMapping(value = "/IdmMgmt/getSubTypeByTypeId", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getSubTypeByTypeId(HttpServletRequest request) {
		try {
			String typeId=request.getParameter("typeId");
			String subTypeId=request.getParameter("subTypeId");
			IdmTypeDetail idmTypeDetail=new IdmTypeDetail();
			idmTypeDetail.setTYPE_ID(typeId);
			idmTypeDetail.setSUB_TYPE_ID(subTypeId);
			if(!Tools.isEmpty(idmTypeDetail)){
				returns=idmTypeService.getSubTypeByTypeId(idmTypeDetail);
			}		
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	@RequestMapping(value = "/IdmMgmt/getdetailType", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getdetailType(HttpServletRequest request) {
		try {
			String typeId=request.getParameter("typeId");
			String subTypeId=request.getParameter("subTypeId");
			IdmTypeDetail idmTypeDetail=new IdmTypeDetail();
			idmTypeDetail.setTYPE_ID(typeId);
			idmTypeDetail.setSUB_TYPE_ID(subTypeId);
			if(!Tools.isEmpty(idmTypeDetail)){
				returns=idmTypeService.getdetailType(idmTypeDetail);
			}		
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	//根据时间范围查询出入库记录
	@RequestMapping(value = "/IdmMgmt/getIdmRecordByTimePeriod", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getIdmRecordByPeriod(HttpServletRequest request) {
		try {
			String startTime=request.getParameter("startTime");
			String endTime=request.getParameter("endTime");
			String transType=request.getParameter("transType");
			IdmTransInfo idmTransInfo=new IdmTransInfo();
			idmTransInfo.setStartTime(startTime);
			idmTransInfo.setEndTime(endTime);
			idmTransInfo.setTransType(transType);
			if(!Tools.isEmpty(idmTransInfo)){
				returns=idmTransService.getIdmRecordByPeriod(idmTransInfo);
			}		
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	@RequestMapping(value = "/IdmMgmt/idmInfoExportToExcel", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String idmInfoExportToExcel(HttpServletRequest request,HttpServletResponse response) {
		try {
			LOGGER.info("IDM info export to excel start");
			HSSFWorkbook hwb=idmInfoService.idmInfoExportToExcel();
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			hwb.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();
			response.setContentType("application/ms-excel;charset=UTF-8");
			response.setContentLength(outArray.length);
			response.setHeader("Expires:", "0"); // eliminates browser caching
			Calendar cal = Calendar.getInstance();
			response.setHeader("Content-Disposition",
					"attachment; filename=idmInfo_"
							+ cal.get(Calendar.MONTH) + ""
							+ cal.get(Calendar.DATE) +""
							+ cal.get(Calendar.YEAR) + ""
							+ cal.get(Calendar.HOUR_OF_DAY)
							+ "" + ""
							+ cal.get(Calendar.MINUTE) +""
							+ cal.get(Calendar.SECOND) + ".xls");
			OutputStream outStream = response.getOutputStream();
			outStream.write(outArray);
			outStream.flush();
			outStream.close();
			outByteStream.close();
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}
		LOGGER.info("IDM info export to excel end");
		return null;
	}
	
	@RequestMapping(value = "/IdmMgmt/idmRecordExportToExcel", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String idmRecordExportToExcel(HttpServletRequest request,HttpServletResponse response) {
		try {
			LOGGER.info("IDM info export to excel start");
			HSSFWorkbook hwb=idmTransService.idmRecordExportToExcel();
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			hwb.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();
			response.setContentType("application/ms-excel;charset=UTF-8");
			response.setContentLength(outArray.length);
			response.setHeader("Expires:", "0"); // eliminates browser caching
			Calendar cal = Calendar.getInstance();
			response.setHeader("Content-Disposition",
					"attachment; filename=idm_transaction_record_"
							+ cal.get(Calendar.MONTH) + ""
							+ cal.get(Calendar.DATE) +""
							+ cal.get(Calendar.YEAR) + ""
							+ cal.get(Calendar.HOUR_OF_DAY)
							+ "" + ""
							+ cal.get(Calendar.MINUTE) +""
							+ cal.get(Calendar.SECOND) + ".xls");
			OutputStream outStream = response.getOutputStream();
			outStream.write(outArray);
			outStream.flush();
			outStream.close();
			outByteStream.close();
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}
		LOGGER.info("IDM info export to excel end");
		return null;
	}
	
	@RequestMapping(value = "/IdmMgmt/idmSafetyExportToExcel", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String idmSafetyExportToExcel(HttpServletRequest request,HttpServletResponse response) {
		try {
			LOGGER.info("IDM info export to excel start");
			HSSFWorkbook hwb=idmInfoService.idmSafetyExportToExcel();
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			hwb.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();
			response.setContentType("application/ms-excel;charset=UTF-8");
			response.setContentLength(outArray.length);
			response.setHeader("Expires:", "0"); // eliminates browser caching
			Calendar cal = Calendar.getInstance();
			response.setHeader("Content-Disposition",
					"attachment; filename=safety_idm_mgmt_"
							+ cal.get(Calendar.MONTH) + ""
							+ cal.get(Calendar.DATE) +""
							+ cal.get(Calendar.YEAR) + ""
							+ cal.get(Calendar.HOUR_OF_DAY)
							+ "" + ""
							+ cal.get(Calendar.MINUTE) +""
							+ cal.get(Calendar.SECOND) + ".xls");
			OutputStream outStream = response.getOutputStream();
			outStream.write(outArray);
			outStream.flush();
			outStream.close();
			outByteStream.close();
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}
		LOGGER.info("IDM info export to excel end");
		return null;
	}
	
//	@RequestMapping(value = "/IdmMgmt/testSendMail", produces = "text/html;charset=utf-8")
//	public @ResponseBody
//	String testSendMail(HttpServletRequest request) {
//		try {
//			List<IdmInfo> idmInfoLst=idmInfoDao.getAllUnSaftyIdm();
//		idmInfoService.emailSafetyIdmReminder(idmInfoLst);
//		
//		} catch (Exception e) {
//			returns=Tools.getExceptionControllerRetruns(e);
//		}
//		return null;
//	}
	
	@RequestMapping(value = "/IdmMgmt/calcSafetyNum", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String calcSafetyNum(HttpServletRequest request) {
		try {
				returns=idmInfoService.calcSafetyNum();
					
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	@RequestMapping(value = "/IdmMgmt/getPoDetail", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getPoDetail(HttpServletRequest request) {
		try {
			String idmId=request.getParameter("idmId");
			IdmTransInfo idmTransInfo=new IdmTransInfo();
			idmTransInfo.setIdmId(idmId);
			if(!Tools.isEmpty(idmTransInfo)){
				returns=idmTransService.getPoDetail(idmTransInfo);
			}		
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
}
