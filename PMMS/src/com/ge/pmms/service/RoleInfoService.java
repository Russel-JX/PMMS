/**
 * ============================================================
 * File : RoleInfoService.java
 * Description : 
 * 	角色dao层接口
 * Package : com.ge.pmms.service
 * Author : Xun Jiang
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
package com.ge.pmms.service;


import java.util.List;

import com.ge.pmms.base.ServiceReturns;
import com.ge.pmms.po.RoleInfo;
import com.ge.pmms.po.RolePageMap;


public interface RoleInfoService {
	
	/**
	 * find all roles
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public ServiceReturns getAllRoles()throws Exception;
	
	/**
	 * find all roles
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public ServiceReturns getAllDetaiRoles()throws Exception;
	
	/**
	 * Add a new role
	 * @param roleInfo
	 * @param rolePageMaps
	 * @return
	 * @throws Exception
	 */
	public ServiceReturns addNewRole(RoleInfo roleInfo,List<RolePageMap> rolePageMaps)throws Exception;
	
	/**
	 * Modify role 
	 * @param roleInfo
	 * @param rolePageMaps
	 * @return
	 * @throws Exception
	 */
	public ServiceReturns modifyRole(String role_id,RoleInfo roleInfo,List<RolePageMap> rolePageMaps)throws Exception;
	
//	/**
//	 * Modify an Existing role 
//	 * @param roleInfo
//	 * @param rolePageMaps
//	 * @return
//	 * @throws Exception
//	 */
//	public ServiceReturns modifyExistingRole(RoleInfo roleInfo,List<RolePageMap> rolePageMaps)throws Exception;
	
	/**
	 * Remove a role by role_id
	 * @param role_id
	 * @return
	 * @throws Exception
	 */
	public ServiceReturns removeRole(String role_id)throws Exception;
	
}
