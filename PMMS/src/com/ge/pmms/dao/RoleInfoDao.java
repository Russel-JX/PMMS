/**
 * ============================================================
 * File : RoleInfoDao.java
 * Description : 
 * 	角色dao层接口
 * Package : com.ge.pmms.dao
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
package com.ge.pmms.dao;


import java.util.List;

import com.ge.pmms.base.BaseDao;
import com.ge.pmms.po.RoleInfo;


public interface RoleInfoDao extends BaseDao<RoleInfo> {
	
	/**
	 * find all roles
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public List<RoleInfo> getAllRoles()throws Exception;
	
	/**
	 * find all detail roles
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public List<RoleInfo> getAllDetaiRoles()throws Exception;
	
	/**
	 * Add a new role
	 * @param roleInfo
	 * @return
	 * @throws Exception
	 */
	public RoleInfo addRoleInfo(RoleInfo roleInfo)throws Exception;
	
//	/**
//	 * Modify a role 
//	 * @param roleInfo
//	 * @return
//	 * @throws Exception
//	 */
//	public RoleInfo modifyRoleInfo(RoleInfo roleInfo)throws Exception;
	
	/**
	 * Remove a role by role_id
	 * @param role_id
	 * @return
	 * @throws Exception
	 */
	public int removeRoleInfo(String role_id)throws Exception;
	
}
