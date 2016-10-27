/**
 * ============================================================
 * File : SparePartInfoDaoImpl.java
 * Description : 
 * 
 * Package : com.ge.pmms.dao.impl
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Feb 1, 2015
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
import org.springframework.util.CollectionUtils;

import com.ge.pmms.base.impl.BaseDaoImpl;
import com.ge.pmms.dao.SparePartInfoDao;
import com.ge.pmms.po.EquipInfo;
import com.ge.pmms.po.SparePartInfo;
import com.ge.pmms.utils.Tools;


/*******************************************************************************
 *
 * @param <E>
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Feb 1, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
@Repository
public class SparePartInfoDaoImpl<E> extends BaseDaoImpl<SparePartInfo>
		implements
			SparePartInfoDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<SparePartInfo> getSparePartInfo(SparePartInfo sparePartInfo) {
		List<SparePartInfo> listSpartPart = null;
		StringBuilder hql = new StringBuilder(128);
		hql.append("from sparePartInfo where 1=1");
		//logger.info("::::::"+sparePartInfo.isSafetyFlag()+":::::::::");
		//Long start = System.nanoTime();
		//System.out.println("SparePartInfoDaoImpl start........"+start);
		if(sparePartInfo.isSafetyFlag()){
			logger.info("::::::The stock is less than safety Sotck:::::::::");
			hql.append(" and stock < safetyStock");
		}
		Query query = getSession().createQuery(hql.toString());
		listSpartPart = query.list();
		//Long end = System.nanoTime();
		//System.out.println("SparePartInfoDaoImpl start........"+end);
		//System.out.println("use time"+(end-start));
		return listSpartPart;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SparePartInfo getSPInfoBySPId(String sparePartId) {
		SparePartInfo sparePartInfo = null;
		StringBuilder hql = new StringBuilder(128);
		hql.append("from sparePartInfo");
		hql.append(" where sparePartId=:sparePartId");
		Query query = getSession().createQuery(hql.toString());
		query.setString("sparePartId", sparePartId);
		List<Object> obj = query.list();
		if(obj.size()>0){
			sparePartInfo = (SparePartInfo) obj.get(0);
		}
		return sparePartInfo;
	}
	
	public int getSPInfoCnt(String sparePartName) {
    int cnt = 0;
    StringBuilder sql = new StringBuilder(128);
    sql.append("select count(*) cnt from spare_part_info");
    sql.append(" where spare_part_name like :sparePartName");
    Query query = getSession().createSQLQuery(sql.toString());
    query.setString("sparePartName","%"+ sparePartName+"%");
    List<?> obj = query.list();
    if(!CollectionUtils.isEmpty(obj)){
      Object tmp = obj.get(0);
      if(!Tools.isEmpty(tmp)){
        cnt = Integer.parseInt(tmp.toString());
      }
    }
    return cnt;
  }
	
	//根据备件名称模糊查询设备
	public List<SparePartInfo> getSPsBySPName(String spName){
		StringBuilder hql = new StringBuilder();
		hql.append( "from sparePartInfo where sparePartName like :spName ");
		Query query = getSession().createQuery(hql.toString());
		query.setParameter("spName", "%"+spName+"%");
		@SuppressWarnings("unchecked")
		List<SparePartInfo> listResult = query.list();
		return listResult;
	}


//	@Override
//	public String getMaxSparePartId() {
//		String SparePartId = "";
//		String sql = "select max (spare_part_id) as SPARE_PAER_ID from spare_part_info";
//		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
//		List<?> list = sqlQuery.list();
//		if(!CollectionUtils.isEmpty(list)){
//			Object[] obj = (Object[]) list.get(0);
//			SparePartId = obj[0].toString();
//		}
//		return SparePartId;
//	}
}
