/**
 * ============================================================
 * File : EquipDocDetailDaoImpl.java
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ge.pmms.base.impl.BaseDaoImpl;
import com.ge.pmms.dao.EquipDocDetailDao;
import com.ge.pmms.po.EquipDocDetail;
import com.ge.pmms.utils.Tools;

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
public class EquipDocDetailDaoImpl extends BaseDaoImpl<EquipDocDetail>
		implements
			EquipDocDetailDao {

	public List<EquipDocDetail> getEquipDocsInfo(EquipDocDetail docDetail) {
		StringBuilder builder = new StringBuilder(128);
		builder.append(" select distinct a.ID,a.DOC_DETAIL_ID,a.EQUIP_DOC_ID,a.FILE_NAME,a.FILE_PATH,a.FILE_SIZE,cast(d.EQUIP_NAME as varchar) EQUIP_NAME,b.DOC_TYPE,cast(c.EQUIP_MODEL as varchar),b.DESCRIPTION,b.ID docId,c.EQUIP_ID");
		builder.append(" from EQUIP_DOC_DETAIL a,EQUIP_DOC_INFO b left join EQUIP_INFO c on b.EQUIP_DOC_ID=c.EQUIP_DOC_ID left join EQUIP_NAME_INFO d on c.EQUIP_NAME_ID=d.EQUIP_NAME_ID");
		builder.append(" where 1=1 and a.EQUIP_DOC_ID=b.EQUIP_DOC_ID and a.FILE_SIZE>0");
		List<EquipDocDetail> lstDetail = null;
		
		if (null != docDetail) {
			if (!StringUtils.isEmpty(docDetail.getEquipDocId())) {
				builder.append(" and a.EQUIP_DOC_ID = :equipDocId");
			}
			if (!StringUtils.isEmpty(docDetail.getFileName())) {
				builder.append(" and a.FILE_NAME like :fileName");
			}
			if (!StringUtils.isEmpty(docDetail.getEquipNmId())) {
				builder.append(" and d.EQUIP_NAME=:equipNmId");
			}
			if (!StringUtils.isEmpty(docDetail.getEquipId())) {
				builder.append(" and c.EQUIP_ID=:equipId");
			}
			Query query = getSession().createSQLQuery(builder.toString());
			
			if (!StringUtils.isEmpty(docDetail.getEquipDocId())) {
				query.setParameter("equipDocId", docDetail.getEquipDocId());
			}
			if (!StringUtils.isEmpty(docDetail.getFileName())) {
				query.setParameter("fileName", "%"
						+ docDetail.getFileName().trim() + "%");
			}
			if (!StringUtils.isEmpty(docDetail.getEquipNmId())) {
				query.setParameter("equipNmId", docDetail.getEquipNmId());
			}
			if (!StringUtils.isEmpty(docDetail.getEquipId())) {
				query.setParameter("equipId", docDetail.getEquipId());
			}
			List<?> list = query.list();
			if (!CollectionUtils.isEmpty(list)) {
				lstDetail = new ArrayList<EquipDocDetail>();
				Iterator<?> itor = list.iterator();
				while (itor.hasNext()) {
					Object[] obj = (Object[]) itor.next();
					EquipDocDetail detail = new EquipDocDetail();
					detail.setId(Integer.parseInt(obj[0].toString()));
					detail.setDocDetailId(String.valueOf(obj[1]));
					detail.setEquipDocId(String.valueOf(obj[2]));
					detail.setFileName(String.valueOf(obj[3]));
					detail.setFilePath(String.valueOf(obj[4]));
					detail.setFileSize(String.valueOf(obj[5]));
					detail.setEquipName(Tools.isNull((obj[6]),""));
					detail.setDocType(Tools.isNull((obj[7]),""));
					detail.setEquipModel(Tools.isNull((obj[8]),""));
					detail.setDescription(Tools.isNull((obj[9]),""));
					detail.setDocId(Tools.isNull((obj[10]),""));
					detail.setEquipId(Tools.isNull((obj[11]),""));
					
					lstDetail.add(detail);
				}
			}
		}
		return lstDetail;
	}
	
	@SuppressWarnings("unchecked")
	public List<EquipDocDetail> getDocDetailByDocId(String equipDocId){
		String hql = "from EquipDocDetail where equipDocId=?";
		return findByHql(hql, new String[]{equipDocId});
	}
	
	
	public int deletlBulk(List<Integer> ids){
		String hql = "delete from EquipDocDetail where id in (:detailKeyIds)";
		Query query = getSession().createQuery(hql);
		query.setParameterList("detailKeyIds", ids);
		
		return query.executeUpdate();
	}
}
