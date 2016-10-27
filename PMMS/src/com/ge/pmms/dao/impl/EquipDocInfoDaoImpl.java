/**
 * ============================================================
 * File : EquipDocInfoDaoImpl.java
 * Description : 
 * 
 * Package : com.ge.pmms.dao.impl
 * Author : Flash
 * Last Edited By :
 * Version : 1.0
 * Created on : Jan 13, 2015
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
import com.ge.pmms.dao.EquipDocInfoDao;
import com.ge.pmms.po.EquipDocInfo;
/*******************************************************************************
*
* @Author 		: Flash
* @Version 	: 1.0
* @Date Created: Jan 20, 2015
* @Date Modified : 
* @Modified By : 
* @Contact 	:
* @Description : 
* @History		:
*
******************************************************************************/
@Repository
public class EquipDocInfoDaoImpl extends BaseDaoImpl<EquipDocInfo> implements
		EquipDocInfoDao {

	public int updateDocFileInfo(EquipDocInfo docInfo) {
		String hql = "update EquipDocInfo set fileSize=:fileSize,fileCount=:fileCount,updater=:updater,updateTime=:updateTime where equipDocId=:equipDocId";
		Query query = updateByHql(hql);
		query.setParameter("fileSize", docInfo.getFileSize());
		query.setParameter("fileCount", docInfo.getFileCount());
		query.setParameter("updater", docInfo.getUpdater());
		query.setParameter("updateTime", docInfo.getUpdateTime());
		query.setParameter("equipDocId", docInfo.getEquipDocId());
		
		return query.executeUpdate();
	}
	

}
