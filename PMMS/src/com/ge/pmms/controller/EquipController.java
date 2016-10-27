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
import com.ge.pmms.base.ServiceReturns;
import com.ge.pmms.po.EquipInfo;
import com.ge.pmms.po.User;
import com.ge.pmms.service.EquipInfoService;
import com.ge.pmms.utils.PMMSConstants;
import com.ge.pmms.utils.Tools;



@Scope("request")
@Controller
public class EquipController extends BaseController{
	@Autowired
	private EquipInfoService equipInfoService;

	
	@RequestMapping(value="equipMgmt/equipInfo")
	public ModelAndView frontMgmt(HttpServletRequest request){
		ModelAndView view = new ModelAndView();
		view.setViewName("equipMgmt/equipInfo");
		return view;
	}
	
	
	
	
	@RequestMapping(value = "/EquipInfo/getEquipInfo", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getEquipInfo(HttpServletRequest request) {
		try {
			String equipType=request.getParameter("equipType");
			//String equipId=request.getParameter("equipId");
			EquipInfo equipInfoReq=new EquipInfo();
			if(!Tools.isEmpty(equipType)){
				equipInfoReq.setEquipType(equipType);
				returns=equipInfoService.getEquipInfoByType(equipInfoReq);
			}
						
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	@RequestMapping(value = "/EquipInfo/getEquipInfoByEpId", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getEquipInfoByEpId(HttpServletRequest request) {
		try {
			ServiceReturns sr=new ServiceReturns();
			String equipId=request.getParameter("equipId");
		//	EquipInfo equipInfoReq=new EquipInfo();
			if(!Tools.isEmpty(equipId)){
				EquipInfo equipInfo;
				//equipInfoReq.setEquipId(equipId);
				equipInfo=equipInfoService.getEquipInfoByEpId(equipId);
				sr.putData("detail",equipInfo);
				returns=sr;
			}
			
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	@RequestMapping(value = "/EquipInfo/saveEquip", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String saveEquip(HttpServletRequest request) {
		BufferedReader input =null;
		try {
			User user = (User) request.getSession().getAttribute(PMMSConstants.USER_SESSION_DATA);
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
			if (null != user) {
				jsonObj.put("creator",String.valueOf(user.getSso()));
		      }
			returns=equipInfoService.saveEquip(jsonObj);
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}finally{
			if (input != null) { 
                	try {
						input.close();
					}
					catch (IOException e) {
						LOGGER.error("save equipment  failed", e);
					} 
			}
		}
		return returns.generateJsonData();
	}
	
	 
	@RequestMapping(value = "/EquipInfo/deleteEquipByEpId", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String deleteEquipByEpId(HttpServletRequest request) {
		try {
			String equipId=request.getParameter("equipId");
			EquipInfo equipInfoReq=new EquipInfo();
			equipInfoReq.setEquipId(equipId);
			returns=equipInfoService.deleteEquipByEpId(equipInfoReq);
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	@RequestMapping(value = "/EquipInfo/updateEquip", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String updateEquip(HttpServletRequest request) {
		BufferedReader input =null;
		try {
			request.setCharacterEncoding("utf-8");
			InputStreamReader ins  = new InputStreamReader(request.getInputStream(),"utf-8");
			 input = new BufferedReader(ins);
			// String o=""; 
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
			returns=equipInfoService.updateEquip(json);
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}finally{
			if (input != null) { 
            	try {
					input.close();
				}
				catch (IOException e) {
					LOGGER.error("update equipment  failed", e);
				} 
		}
	}
		return returns.generateJsonData();
	}
	
	/*@RequestMapping(value = "/EquipInfo/updateEquipForDoc", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String updateEquipForDoc(HttpServletRequest request) {
		try {
			String equipId=request.getParameter("equipId");
			String docId=request.getParameter("docId");
			String equipModelId=request.getParameter("equipModelId");
			if(!Tools.isEmpty(docId)&&!Tools.isEmpty(equipId)){
				returns=equipInfoService.updateEpDocByEpID(docId,equipId);
			}
			if(!Tools.isEmpty(docId)&&!Tools.isEmpty(equipNmId)){
				returns=equipInfoService.updateEpDocByEpNmId(docId,equipModelId);
			}
			
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}*/
	
	@RequestMapping(value = "/EquipInfo/getDeptInfo", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getDeptInfo(HttpServletRequest request) {
		try {
			returns=equipInfoService.getDeptInfo();
			
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	@RequestMapping(value = "/EquipInfo/exportToExcel", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String exportToExcel(HttpServletRequest request,HttpServletResponse response) {
		try {
			LOGGER.info("export to excel start");
			HSSFWorkbook hwb=equipInfoService.exportToExcel();
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			hwb.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();
			response.setContentType("application/ms-excel;charset=UTF-8");
			response.setContentLength(outArray.length);
			response.setHeader("Expires:", "0"); // eliminates browser caching
			Calendar cal = Calendar.getInstance();
			//Equip_Facility_Mgmt_
			response.setHeader("Content-Disposition",
					"attachment; filename=Equip_Facility_Mgmt_"
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
		LOGGER.info("export to excel end");
		return null;
	}
	
	// 根据设备名称模糊查询设备
	@RequestMapping(value = "/EquipInfo/getEquipsByEQName", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getEquipsByEQName(HttpServletRequest request) {
		try {
			String eqName = request.getParameter("eqName");
			returns=equipInfoService.getEquipsByEQName(eqName);
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
}