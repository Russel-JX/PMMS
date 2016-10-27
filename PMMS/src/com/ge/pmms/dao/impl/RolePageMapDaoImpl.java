/**
 * ============================================================
 * File : RolePageMapDaoImpl.java
 * Description : 
 * 角色页面映射dao层接口实现类
 * Package : com.ge.pmms.dao.impl
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
package com.ge.pmms.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.ge.pmms.base.impl.BaseDaoImpl;
import com.ge.pmms.dao.RolePageMapDao;
import com.ge.pmms.po.RolePageMap;

@Repository
public class RolePageMapDaoImpl extends BaseDaoImpl<RolePageMap> implements RolePageMapDao {

	public void addRolePageMap(List<RolePageMap> rolePageMaps)throws Exception{
		try{
			saveAll(rolePageMaps);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
//	public void modifyRolePageMap(List<RolePageMap> rolePageMaps)throws Exception{
//		for(int i=0;i<rolePageMaps.size();i++){
//			RolePageMap rolePageMap = new RolePageMap();
//			rolePageMap = rolePageMaps.get(i);
//			try{
//				update(rolePageMap);
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//		}
//	}
	
	public int removeRolePageMap(String role_id)throws Exception{
		String hql = "delete from rolePageMap where role_id =:role_id";
		Query query = getSession().createQuery(hql);
		query.setParameter("role_id", role_id);
		return query.executeUpdate();
	}
	
}
