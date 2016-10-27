/**
 * ============================================================
 * File : UserController.java
 * Description : 
 * 
 * Package : com.ge.pmms.controller
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Feb 10, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.pmms.controller;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ge.pmms.base.BaseController;
import com.ge.pmms.po.UserInfo;
import com.ge.pmms.service.UserMgmtService;
import com.ge.pmms.utils.Tools;


/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Feb 10, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
@Scope("request")
@Controller
public class UserController  extends BaseController{
	@Autowired
	private UserMgmtService userMgmtService;
	
	@RequestMapping(value="UserMgmt/index")
	public ModelAndView frontMgmt(HttpServletRequest request){
		ModelAndView view = new ModelAndView();
		view.setViewName("UserMgmt/index");
		return view;
	}
	
	@RequestMapping(value = "/UserMgmt/saveUser", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String saveUser(HttpServletRequest request) {
		//BufferedReader input =null;
		try {
			String sso=request.getParameter("sso");
			String pwd=request.getParameter("pwd");
			String name=request.getParameter("name");
			String emId=request.getParameter("emailId");
			String contractNo=request.getParameter("contractNo");
			String role=request.getParameter("role");
			UserInfo userInfo=new UserInfo();
			userInfo.setSso(sso);
			userInfo.setPwd(pwd);
			userInfo.setName(name);
			userInfo.setEmailId(emId);
			userInfo.setContractNo(contractNo);
			userInfo.setRole(role);
			userInfo.setIsActive("Y");
			returns=userMgmtService.saveUser(userInfo);
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	@RequestMapping(value = "/UserMgmt/getAllUser", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getAllUser(HttpServletRequest request) {
		try {
			//UserInfo userInfo=new UserInfo();
				returns=userMgmtService.getAllUser();				
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	@RequestMapping(value = "/UserMgmt/getUserBySso", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getUserBySso(HttpServletRequest request) {
		try {
			String sso=request.getParameter("sso");
				returns=userMgmtService.getUserBySso(sso);				
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	@RequestMapping(value = "/UserMgmt/deleteUser", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String deleteUserById(HttpServletRequest request) {
		try {
			String id=request.getParameter("id");
				returns=userMgmtService.deleteUserById(id);				
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	@RequestMapping(value = "/UserMgmt/updateUser", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String updateUser(HttpServletRequest request) {
		try {
			String sso=request.getParameter("sso");
			String pwd=request.getParameter("pwd");
			String name=request.getParameter("name");
			String emId=request.getParameter("emailId");
			String contractNo=request.getParameter("contractNo");
			String role=request.getParameter("role");
			UserInfo userInfo=new UserInfo();
			userInfo.setSso(sso);
			userInfo.setPwd(pwd);
			userInfo.setName(name);
			userInfo.setEmailId(emId);
			userInfo.setContractNo(contractNo);
			userInfo.setRole(role);
				returns=userMgmtService.updateUser(userInfo);				
		} catch (Exception e) {
			returns=Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
}
