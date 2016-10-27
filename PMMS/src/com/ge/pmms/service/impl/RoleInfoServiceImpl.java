/**
 * ============================================================
 * File : RoleInfoServiceImpl.java
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ge.pmms.base.BaseService;
import com.ge.pmms.base.ServiceReturns;
import com.ge.pmms.dao.RoleInfoDao;
import com.ge.pmms.dao.RolePageMapDao;
import com.ge.pmms.po.RoleInfo;
import com.ge.pmms.po.RolePageMap;
import com.ge.pmms.service.RoleInfoService;

@Service
@Transactional
public class RoleInfoServiceImpl extends BaseService<RoleInfo> implements RoleInfoService {

	@Autowired
	private RoleInfoDao roleInfoDao;
	@Autowired
	private RolePageMapDao rolePageMapDao;
	
	//查询所有详细角色
	public ServiceReturns getAllRoles(){
		serviceReturns = new ServiceReturns();
		List<RoleInfo> list = new ArrayList<RoleInfo>();
		try{
			list = roleInfoDao.getAllRoles();
			serviceReturns.putData("list",list);
		}catch(Exception e){
			LOGGER.error("get all roles faied!", e);
		}
		return serviceReturns;
	}
	
	//查询所有详细角色
	public ServiceReturns getAllDetaiRoles(){
		serviceReturns = new ServiceReturns();
		List<RoleInfo> list = new ArrayList<RoleInfo>();
		try{
			list = roleInfoDao.getAllDetaiRoles();
			serviceReturns.putData("list",list);
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceReturns;
	}
	
	//新增角色和对应的权限
	public ServiceReturns addNewRole(RoleInfo roleInfo,List<RolePageMap> rolePageMaps){
		serviceReturns = new ServiceReturns();
		RoleInfo role = new RoleInfo();
		try{
			role = roleInfoDao.addRoleInfo(roleInfo);
			rolePageMapDao.addRolePageMap(rolePageMaps);
			serviceReturns.putData("detail",role);
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceReturns;
	}
	
	//修改角色
	public ServiceReturns modifyRole(String role_id,RoleInfo roleInfo,List<RolePageMap> rolePageMaps){
		serviceReturns = new ServiceReturns();
		RoleInfo role = new RoleInfo();
		try{
			int effectedRowNumRole = roleInfoDao.removeRoleInfo(role_id);
			int effectedRowNumMap = rolePageMapDao.removeRolePageMap(role_id);
			
			role = roleInfoDao.addRoleInfo(roleInfo);
			rolePageMapDao.addRolePageMap(rolePageMaps);
			
			serviceReturns.putData("effectedRowNumRole",effectedRowNumRole);
			serviceReturns.putData("effectedRowNumMap",effectedRowNumMap);
			serviceReturns.putData("detail",role);
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceReturns;
	}
	
//	//修改角色和对应的权限
//	public ServiceReturns modifyExistingRole(RoleInfo roleInfo,List<RolePageMap> rolePageMaps)throws Exception{
//		serviceReturns = new ServiceReturns();
//		PlanInfo originalPI = planInfoDao.findPlanByPlanId(planInfo.getPlan_id());
//		BeanUtils.copyProperties(originalPI, planInfo, new String[] {
//				"maint_jan", "maint_feb", "maint_mar", "maint_apr","maint_may","maint_jun","maint_jul","maint_agu","maint_sep","maint_oct","maint_nov","maint_dec", "updater",
//				"last_updated_date", "remark"});
//		//relase object in session
//		hibernateTemplate.evict(originalPI);
//		try{
//			
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		
//		return serviceReturns;
//	}
	
	//删除角色和对应的权限
	public ServiceReturns removeRole(String role_id){
		serviceReturns = new ServiceReturns();
		try{
			int effectedRowNumRole = roleInfoDao.removeRoleInfo(role_id);
			int effectedRowNumMap = rolePageMapDao.removeRolePageMap(role_id);
			serviceReturns.putData("effectedRowNumRole",effectedRowNumRole);
			serviceReturns.putData("effectedRowNumMap",effectedRowNumMap);
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceReturns;
	}
	
}
