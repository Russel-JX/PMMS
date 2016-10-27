/**
 * ============================================================
 * File : RolePageMapDao.java
 * Description : 
 * 	权限管理控制层
 * Package : com.ge.pmms.controller
 * Author : Xun Jiang
 * Last Edited By :
 * Version : 1.0
 * Created on : Feb 12, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.pmms.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ge.pmms.base.BaseController;
import com.ge.pmms.po.RoleInfo;
import com.ge.pmms.po.RolePageMap;
import com.ge.pmms.service.RoleInfoService;
import com.ge.pmms.utils.Constants;
import com.ge.pmms.utils.Tools;
@Scope("request")
@Controller
public class RoleInfoController extends BaseController{
	@Autowired
	private RoleInfoService roleInfoService;
	
	/**
	 * 
	 * @Author: Xun Jiang 
	 * @param request
	 * @return
	 * @Description:forward to roles.jsp
	 */
	@RequestMapping(value = "roleMgmt/index", produces = "text/html;charset=utf-8")
	public String forwardToRoleIndex(HttpServletRequest request) {
		return "roleMgmt/roles";
	}
	
	//查询所有详细角色
	@RequestMapping(value = "role/getAllRoles", produces = "text/html;charset=utf-8")
	public @ResponseBody String getAllRoles(HttpServletRequest request) {
		try {
			returns = roleInfoService.getAllRoles();
		} catch (Exception e) {
			e.printStackTrace();
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}	
	
	//查询所有详细角色
	@RequestMapping(value = "role/getAllDetailRoles", produces = "text/html;charset=utf-8")
	public @ResponseBody String getAllDetailRoles(HttpServletRequest request) {
		try {
			returns = roleInfoService.getAllDetaiRoles();
		} catch (Exception e) {
			e.printStackTrace();
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	//新增角色
	@RequestMapping(value = "/role/saveRoleInfo", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String saveRoleInfo(HttpServletRequest request) {
		String roleName = request.getParameter("roleName");
		String remark = request.getParameter("remark");
		
		String owo = request.getParameter("owo");
		String pwo = request.getParameter("pwo");
		String pm = request.getParameter("pm");
		String spIn = request.getParameter("spIn");
		String spOut = request.getParameter("spOut");
		String pmff = request.getParameter("pmff");
		String ea = request.getParameter("ea");
		String mtbf = request.getParameter("mtbf");
		String mttr = request.getParameter("mttr");
		String mspc = request.getParameter("mspc");
		String mspn = request.getParameter("mspn");
		String em = request.getParameter("em");
		String dm = request.getParameter("dm");
		String idmIn = request.getParameter("idmIn");
		String idmOut = request.getParameter("idmOut");
		String um = request.getParameter("um");
		String rm = request.getParameter("rm");

		String[] arrPages = new String[17];
		arrPages[0] = owo;
		arrPages[1] = pwo;
		arrPages[2] = pm;
		arrPages[3] = spIn;
		arrPages[4] = spOut;
		arrPages[5] = pmff;
		arrPages[6] = ea;
		arrPages[7] = mtbf;
		arrPages[8] = mttr;
		arrPages[9] = mspc;
		arrPages[10] = mspn;
		arrPages[11] = em;
		arrPages[12] = dm;
		arrPages[13] = idmIn;
		arrPages[14] = idmOut;
		arrPages[15] = um;
		arrPages[16] = rm;
		try {
			
			//add role
			RoleInfo roleInfo = new RoleInfo();
			roleInfo.setRole_id(Tools.getTimeStr());
			roleInfo.setRole_name(roleName);
			roleInfo.setRemark(remark);
			roleInfo.setShow_wo(Constants.SHOW_WO);
			roleInfo.setShow_short_link(Constants.SHOW_SHORT_LINK);
//			roleInfo.setCreator("Frank");
			roleInfo.setCreated_date(Tools.getToday());
			
			//add role - page mapping
			List<RolePageMap> rolePageMaps = new ArrayList<RolePageMap>();
			RolePageMap rolePageMap = null;
			for(int i=0;i<Constants.PAGE_DEFINITION.length;i++){//.arrPages.length
				rolePageMap = new RolePageMap();
				rolePageMap.setPage_id(Constants.PAGE_DEFINITION[i]);
				rolePageMap.setPage_name(Tools.getPageConfig(Constants.PAGE_DEFINITION[i]));
				rolePageMap.setRole_id(roleInfo.getRole_id());
				rolePageMap.setPage_role(arrPages[i].equalsIgnoreCase("Y")?Constants.PAGE_ROLE_YES:Constants.PAGE_ROLE_NO);
//				rolePageMap.setCreator(creator);
				rolePageMap.setCreated_date(Tools.getToday());
				rolePageMaps.add(rolePageMap);	
			}
			returns = roleInfoService.addNewRole(roleInfo, rolePageMaps);
		} catch (Exception e) {
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	//修改角色
	@RequestMapping(value = "role/modifyRoleInfo", produces = "text/html;charset=utf-8")
	public @ResponseBody String modifyRoleInfo(HttpServletRequest request) {
		String role_id = request.getParameter("role_id");
		String roleName = request.getParameter("roleName");
		String remark = request.getParameter("remark");
		
		String owo = request.getParameter("owo");
		String pwo = request.getParameter("pwo");
		String pm = request.getParameter("pm");
		String spIn = request.getParameter("spIn");
		String spOut = request.getParameter("spOut");
		String pmff = request.getParameter("pmff");
		String ea = request.getParameter("ea");
		String mtbf = request.getParameter("mtbf");
		String mttr = request.getParameter("mttr");
		String mspc = request.getParameter("mspc");
		String mspn = request.getParameter("mspn");
		String em = request.getParameter("em");
		String dm = request.getParameter("dm");
		String idmIn = request.getParameter("idmIn");
		String idmOut = request.getParameter("idmOut");
		String um = request.getParameter("um");
		String rm = request.getParameter("rm");

		String[] arrPages = new String[17];
		arrPages[0] = owo;
		arrPages[1] = pwo;
		arrPages[2] = pm;
		arrPages[3] = spIn;
		arrPages[4] = spOut;
		arrPages[5] = pmff;
		arrPages[6] = ea;
		arrPages[7] = mtbf;
		arrPages[8] = mttr;
		arrPages[9] = mspc;
		arrPages[10] = mspn;
		arrPages[11] = em;
		arrPages[12] = dm;
		arrPages[13] = idmIn;
		arrPages[14] = idmOut;
		arrPages[15] = um;
		arrPages[16] = rm;
		try {
			//add role
			RoleInfo roleInfo = new RoleInfo();
			roleInfo.setRole_id(Tools.getTimeStr());
			roleInfo.setRole_name(roleName);
			roleInfo.setRemark(remark);
			roleInfo.setShow_wo(Constants.SHOW_WO);
			roleInfo.setShow_short_link(Constants.SHOW_SHORT_LINK);
//			roleInfo.setCreator("Frank");
			roleInfo.setCreated_date(Tools.getToday());
			
			//add role - page mapping
			List<RolePageMap> rolePageMaps = new ArrayList<RolePageMap>();
			RolePageMap rolePageMap = null;
			for(int i=0;i<Constants.PAGE_DEFINITION.length;i++){
				rolePageMap = new RolePageMap();
				rolePageMap.setPage_id(Constants.PAGE_DEFINITION[i]);
				rolePageMap.setPage_name(Tools.getPageConfig(Constants.PAGE_DEFINITION[i]));
				rolePageMap.setRole_id(roleInfo.getRole_id());
				rolePageMap.setPage_role(arrPages[i].equalsIgnoreCase("Y")?Constants.PAGE_ROLE_YES:Constants.PAGE_ROLE_NO);
//				rolePageMap.setCreator(creator);
				rolePageMap.setCreated_date(Tools.getToday());
				rolePageMaps.add(rolePageMap);	
			}
			returns = roleInfoService.modifyRole(role_id, roleInfo, rolePageMaps);
		} catch (Exception e) {
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}		
	
	//删除角色
	@RequestMapping(value = "role/deleteRoleInfo", produces = "text/html;charset=utf-8")
	public @ResponseBody String deleteRoleInfo(HttpServletRequest request) {
		String role_id = request.getParameter("role_id");
		try {
			returns = roleInfoService.removeRole(role_id);
		} catch (Exception e) {
			e.printStackTrace();
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}	
}
