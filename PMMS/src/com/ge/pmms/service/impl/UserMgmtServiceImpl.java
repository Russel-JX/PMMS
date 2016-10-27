/**
 * ============================================================
 * File : UserMgmtServiceImpl.java
 * Description : 
 * 
 * Package : com.ge.pmms.service.impl
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Feb 11, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.pmms.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONObject;

import com.ge.pmms.base.BaseService;
import com.ge.pmms.base.ServiceReturns;
import com.ge.pmms.dao.UserMgmtDao;
import com.ge.pmms.po.UserInfo;
import com.ge.pmms.service.UserMgmtService;
import com.ge.pmms.po.IdmInfo;

/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Feb 11, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
@Service
@Transactional
public class UserMgmtServiceImpl extends BaseService<UserInfo> implements UserMgmtService{
	@Autowired
	private UserMgmtDao userMgmtDao;
	
	public ServiceReturns saveUser(UserInfo userInfo){
		return super.save(userInfo);
	}
	
	public ServiceReturns getAllUser(){
		List<UserInfo> userInfoLst=new ArrayList<UserInfo>();
		userInfoLst=userMgmtDao.getAllUser();
		serviceReturns = new ServiceReturns();
		serviceReturns.putData("details",userInfoLst);
		return serviceReturns;
	}
	
	public ServiceReturns getUserBySso(String sso){
		UserInfo userInfo=userMgmtDao.getUserBySso(sso);
		serviceReturns = new ServiceReturns();
		serviceReturns.putData("detail",userInfo);
		return serviceReturns;
	}
	
	public ServiceReturns deleteUserById(String id){
		serviceReturns = new ServiceReturns();
		//List<Integer> ids=new ArrayList<Integer>();
		//ids.add(Integer.parseInt(id));
		serviceReturns.putData("detail",userMgmtDao.deleteUserById(id));
		return serviceReturns;
	}
	
	public ServiceReturns updateUser(UserInfo userInfoReq){
		UserInfo userInfo=userMgmtDao.getUserBySso(userInfoReq.getSso());
		userInfo.setContractNo(userInfoReq.getContractNo());
		userInfo.setEmailId(userInfoReq.getEmailId());
		userInfo.setName(userInfoReq.getName());
		userInfo.setPwd(userInfoReq.getPwd());
		userInfo.setRole(userInfoReq.getRole());
		getSession().update(userInfo);
		return serviceReturns;
	}
}
