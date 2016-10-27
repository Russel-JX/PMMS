/**
 * ============================================================
 * File : UserMgmtDaoImpl.java
 * Description : 
 * 
 * Package : com.ge.pmms.dao.impl
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

package com.ge.pmms.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ge.pmms.base.impl.BaseDaoImpl;
import com.ge.pmms.dao.UserMgmtDao;
import com.ge.pmms.po.DeptInfo;
import com.ge.pmms.po.EquipInfo;
import com.ge.pmms.po.UserInfo;


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
@Repository
public class UserMgmtDaoImpl extends BaseDaoImpl<UserInfo> implements UserMgmtDao{
	
	public List<UserInfo> getAllUser(){
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("select u.id,u.sso,u.name,u.role,u.contractNo,u.emailId,r.role_name as roleNm,u.isActive,u.pwd ");
		sbQuery.append("from UserInfo u,roleInfo r where u.role=r.role_id and u.isActive=:isActive");
		Query query = getSession().createQuery(sbQuery.toString());
		query.setParameter("isActive","Y");
		 List list=query.list();
		 List<UserInfo> userLst=new ArrayList<UserInfo>();
		 if(!CollectionUtils.isEmpty(list)){
				Iterator itor = list.iterator();
				while(itor.hasNext()){
					UserInfo po = new UserInfo();
					Object[] obj = (Object[])itor.next();
					//po.setEquipType(String.valueOf(obj[1]));
					po.setId(Integer.parseInt(String.valueOf(obj[0])));
					po.setSso(String.valueOf(obj[1]));
					po.setName(String.valueOf(obj[2]));
					po.setRole(String.valueOf(obj[3]));
					po.setContractNo(String.valueOf(obj[4]));
					po.setEmailId(String.valueOf(obj[5]));
					po.setRoleNm(String.valueOf(obj[6]));
					po.setIsActive(String.valueOf(obj[7]));
					po.setPwd(String.valueOf(obj[8]));
					userLst.add(po);
				}
			}
		 return userLst;
	}
	
	public UserInfo getUserBySso(String sso){
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("from UserInfo where sso=:sso");
		Query query = getSession().createQuery(sbQuery.toString());
		query.setParameter("sso", sso);
		List list=query.list();
		if(list.size()>0){
			return (UserInfo) list.get(0);
		} 
		return null;
	}
	
	public int deleteUserById(String id){
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("update UserInfo set isActive=:isActive where id=:id");
		Query query = getSession().createQuery(sbQuery.toString());
		query.setParameter("id", Integer.parseInt(id));
		query.setParameter("isActive", "N");
		return query.executeUpdate();
	}
	
}
