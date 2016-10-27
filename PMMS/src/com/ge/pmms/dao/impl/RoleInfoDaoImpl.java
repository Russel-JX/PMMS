/**
 * ============================================================
 * File : RoleInfoImpl.java
 * Description : 
 * 	角色dao层接口实现类
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ge.pmms.base.impl.BaseDaoImpl;
import com.ge.pmms.dao.RoleInfoDao;
import com.ge.pmms.po.PlanInfo;
import com.ge.pmms.po.RoleInfo;
import com.ge.pmms.utils.Tools;

@Repository
public class RoleInfoDaoImpl extends BaseDaoImpl<RoleInfo> implements RoleInfoDao {
	
	//查询所有详细角色
	public List<RoleInfo> getAllRoles()throws Exception{
		String hql = "from roleInfo ";
		Query query = getSession().createQuery(hql);
		@SuppressWarnings("unchecked")
		List<RoleInfo> list = query.list();
		return list;
	}
	
	//查询所有详细角色
	public List<RoleInfo> getAllDetaiRoles()throws Exception{
		StringBuilder hql = new StringBuilder();
		hql.append( "select a.id,a.role_id,a.role_name,a.show_wo,a.show_short_link, ");
		hql.append( "a.creator,a.created_date,a.updater,a.last_updated_date,a.remark,b.id,b.page_id,b.page_name,b.page_role ");
		hql.append( " from ROLE_INFO a inner join ROLE_PAGE_MAP b ");
		hql.append( "on a.role_id = b.role_id ");
		
		Query query = getSession().createSQLQuery(hql.toString());
		List<?> listResult = query.list();
		
		List<RoleInfo> list = new ArrayList<RoleInfo>();
		RoleInfo roleInfo;
		if(!CollectionUtils.isEmpty(listResult)){
			Iterator<?> ite = listResult.iterator();
			while(ite.hasNext()){
				Object[] obj = (Object[])ite.next();
				roleInfo = new RoleInfo();
				roleInfo.setId(Integer.parseInt(obj[0].toString()));
				roleInfo.setRole_id(obj[1].toString());
				roleInfo.setRole_name(obj[2].toString());
				roleInfo.setShow_wo(Tools.isNull(obj[3])?"":obj[3].toString());
				roleInfo.setShow_short_link(Tools.isNull(obj[4])?"":obj[4].toString());
				roleInfo.setCreator(Tools.isNull(obj[5])?"":obj[5].toString());
				roleInfo.setCreated_date(Tools.parseToDate(obj[6].toString(), "yyyy-MM-dd HH:mm:ss"));
				roleInfo.setUpdater(Tools.isNull(obj[7])?"":obj[7].toString());
				roleInfo.setLast_updated_date(Tools.isNull(obj[8])?null:Tools.parseToDate(obj[8].toString(), "yyyy-MM-dd HH:mm:ss"));
				roleInfo.setRemark(Tools.isNull(obj[9])?"":obj[9].toString());
				roleInfo.setIdPage(Integer.parseInt(obj[10].toString()));
				roleInfo.setPage_id(obj[11].toString());
				roleInfo.setPage_name(Tools.isNull(obj[12])?"":obj[12].toString());
				roleInfo.setPage_role(Tools.isNull(obj[13])?"":obj[13].toString());
				
				list.add(roleInfo);
			}
		}
		
		
		return list;
	}
	
	//新增角色
	public RoleInfo addRoleInfo(RoleInfo roleInfo)throws Exception{
		try{
			save(roleInfo);
		}catch(Exception e){
			e.printStackTrace();
		}
		return roleInfo;
	}
	
//	//修改角色
//	public RoleInfo modifyRoleInfo(RoleInfo roleInfo)throws Exception{
//		try{
//			update(roleInfo);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return roleInfo;
//	}

	//根据角色编号删除角色
	public int removeRoleInfo(String role_id)throws Exception{
		String hql = "delete from roleInfo where role_id =:role_id";
		Query query = getSession().createQuery(hql);
		query.setParameter("role_id", role_id);
		return query.executeUpdate();
	}
	
}
