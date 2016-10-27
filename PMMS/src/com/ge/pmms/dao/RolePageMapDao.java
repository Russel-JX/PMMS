/**
 * ============================================================
 * File : RolePageMapDao.java
 * Description : 
 * 	角色页面映射dao层接口
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
import com.ge.pmms.po.RolePageMap;


public interface RolePageMapDao extends BaseDao<RolePageMap> {
	
	/**
	 * Add a new role - page mapping
	 * @param a list of RolePageMap
	 * @return
	 * @throws Exception
	 */
	public void addRolePageMap(List<RolePageMap> rolePageMaps)throws Exception;
	
	
//	/**
//	 * Modify a role - page mapping
//	 * @param a list of RolePageMap
//	 * @return
//	 * @throws Exception
//	 */
//	public void modifyRolePageMap(List<RolePageMap> rolePageMaps)throws Exception;
	
	/**
	 * Remove a role - page mapping by role_id
	 * @param role_id
	 * @return
	 * @throws Exception
	 */
	public int removeRolePageMap(String role_id)throws Exception;
	
}
