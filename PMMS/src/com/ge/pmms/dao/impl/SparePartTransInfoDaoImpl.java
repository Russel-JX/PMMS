/**
 * ============================================================
 * File : SparePartTransInfoDaoImpl.java
 * Description : 
 * 
 * Package : com.ge.pmms.dao.impl
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Feb 5, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.pmms.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ge.pmms.base.PmmsException;
import com.ge.pmms.base.impl.BaseDaoImpl;
import com.ge.pmms.dao.SparePartTransInfoDao;
import com.ge.pmms.po.SafetyStockInfo;
import com.ge.pmms.po.SparePartTransInfo;
import com.ge.pmms.utils.Tools;


/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Feb 5, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
@Repository
public class SparePartTransInfoDaoImpl extends BaseDaoImpl<SparePartTransInfo>
		implements
			SparePartTransInfoDao {

	@Override
	public List<SparePartTransInfo> getSPTrans(
			SparePartTransInfo sparePartTransInfo) throws PmmsException {
		List<SparePartTransInfo> SPTransList = null;
		String searchFromDate = sparePartTransInfo.getSearchFromDate();
		String searchToDate = sparePartTransInfo.getSearchToDate();
		String transType = sparePartTransInfo.getTransType();
		StringBuilder sql = new StringBuilder(128);
		sql.append("select ID,TRANS_ID,SPARE_PART_ID,SPARE_PART_NAME,SPARE_PART_TYPE,TRANS_TYPE,AMOUNT,");
		sql.append("MEASUREMENT,PRICE,REMARK,CREATED_DATE,LEAD_TIME");
		sql.append(" from SPARE_PART_TRANS_INFO where 1=1");
		if(!StringUtils.isEmpty(searchFromDate)&&(!StringUtils.isEmpty(searchToDate))){
			searchFromDate += " 00:00:00";
			searchToDate += " 23:59:59";
			sql.append(" and CREATED_DATE between cast(:searchFromDate as datetime) and cast(:searchToDate as datetime)");
		}
		if(!StringUtils.isEmpty(transType)){
			sql.append(" and TRANS_TYPE=:transType");
		}
		sql.append(" order by CREATED_DATE desc");
		SQLQuery sqlQuery = getSession().createSQLQuery(sql.toString());
		if(!StringUtils.isEmpty(searchFromDate)&&(!StringUtils.isEmpty(searchToDate))){
			sqlQuery.setParameter("searchFromDate", searchFromDate);
			sqlQuery.setParameter("searchToDate", searchToDate);
		}
		if(!StringUtils.isEmpty(transType)){
			sqlQuery.setParameter("transType", transType);
		}		
		
      	List<?> objList = sqlQuery.list();		
		if(!CollectionUtils.isEmpty(objList)){
			SPTransList = new ArrayList<SparePartTransInfo>();
			Iterator<?> iterator = objList.iterator();
			
			Date tempCreatedDate = null;
			while(iterator.hasNext()){
				Object[] obj = (Object[]) iterator.next();
				SparePartTransInfo transInfo = new SparePartTransInfo();
				transInfo.setId(Integer.parseInt(obj[0].toString()));
				transInfo.setTransId(Tools.isNull(obj[1],""));
				transInfo.setSparePartId(Tools.isNull(obj[2],""));
				transInfo.setSparePartName(Tools.isNull(obj[3],""));
				transInfo.setSparePartType(Tools.isNull(obj[4],""));
				transInfo.setTransType(Tools.isNull(obj[5],"").trim());
				transInfo.setAmount(Tools.isEmpty(obj[6])? 0:Integer.parseInt(obj[6].toString()));
				transInfo.setMeasurement(Tools.isNull(obj[7],""));
				transInfo.setPrice(Tools.isEmpty(obj[8])? 0:Float.parseFloat(obj[8].toString()));
				transInfo.setRemark(Tools.isNull(obj[9],""));
				if(!Tools.isNull(obj[10])){
					tempCreatedDate = (Date) obj[10];
					transInfo.setStrCreatedDate(Tools.formatDate(tempCreatedDate));
				}
				transInfo.setLeadTime(Float.parseFloat(obj[11].toString()));
				SPTransList.add(transInfo);
			}
		}
		return SPTransList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SparePartTransInfo> getSPTransByWOID(String workOrderId)
			throws PmmsException {
		List<SparePartTransInfo> spTrans = null;
		String hql = "from sparePartTransInfo where transType='2' and workOrderId=:workOrderId";
		Query query = getSession().createQuery(hql);
		query.setParameter("workOrderId", workOrderId);
		spTrans = query.list();
		return spTrans;
	}

	@Override
	public List<SafetyStockInfo> getSTDPreparedData(Date startDate, Date endDate)
			throws PmmsException {
//		Criteria criteria = getSession().createCriteria(SparePartTransInfo.class);
//		criteria.add(Restrictions.between("createdDate", startDate, endDate));
//		criteria.addOrder(Order.asc("sparePartId"));
//		List<SparePartTransInfo> list = criteria.list();
		
		String strStartDate = Tools.formatDate(startDate);
		String strEndDate = Tools.formatDate(endDate);
		logger.info("-------------------------------------");
		logger.info(strStartDate);
		logger.info(strEndDate);
		logger.info("-------------------------------------");
		
		List<SafetyStockInfo> sslist = null;
		
		StringBuilder sql = new StringBuilder(128);
//		sql.append("select SPARE_PART_ID,count(SPARE_PART_ID) num,sum(chaPF) sums,avgAmount from (");
//		sql.append("select a.SPARE_PART_ID,(a.monAmount-b.avgAmount)*(a.monAmount-b.avgAmount) chaPF,a.createMonth,b.avgAmount from (");
//		sql.append("select SPARE_PART_ID,SUM(AMOUNT) monAmount,createMonth from (");
//		sql.append("select SPARE_PART_ID,AMOUNT,DATEADD(mm, DATEDIFF(mm,0,CREATED_DATE), 0) createMonth from SPARE_PART_TRANS_INFO where TRANS_TYPE='2'");
//		sql.append(") ");
//		sql.append("as temp group by SPARE_PART_ID,createMonth");
//		sql.append(") as a,");
//		sql.append("(");
//		sql.append("select SPARE_PART_ID,SUM(AMOUNT)/24.0 avgAmount from SPARE_PART_TRANS_INFO where TRANS_TYPE='2' and CREATED_DATE between cast(:startDate as datetime) and cast(:endDate as datetime)  group by SPARE_PART_ID)");
//		sql.append(" as b ");
//		sql.append("where a.SPARE_PART_ID = b.SPARE_PART_ID");
//		sql.append(") as temp1 group by SPARE_PART_ID,avgAmount order by SPARE_PART_ID");
		sql.append("select amont_id,count(amont_id),sum((monthAmount-avgAmount) * (monthAmount-avgAmount)) sums, avgAmount from ");
		sql.append("(select SPARE_PART_ID amont_id,sum(amount) monthAmount ");
		sql.append("from SPARE_PART_TRANS_INFO where trans_type='2' and CREATED_DATE between cast(:startDate as datetime) and cast(:endDate as datetime) group by SPARE_PART_ID,DATEADD(mm, DATEDIFF(mm,0,CREATED_DATE), 0)");
		sql.append(") a,");
		sql.append("(select SPARE_PART_ID total_id,sum(amount)/24.0 avgAmount ");
		sql.append("from SPARE_PART_TRANS_INFO where trans_type='2' and CREATED_DATE between cast(:startDate as datetime) and cast(:endDate as datetime) group by SPARE_PART_ID) b ");
		sql.append("where a.amont_id=b.total_id group by amont_id,avgAmount order by amont_id");		
		Query query = getSession().createSQLQuery(sql.toString());
		query.setParameter("startDate", strStartDate);
		query.setParameter("endDate", strEndDate);
		
		List<?> list = query.list();
		if(!CollectionUtils.isEmpty(list)){
			sslist = new ArrayList<SafetyStockInfo>();
			SafetyStockInfo ssInfo = null;
			String sparePartId = "";
			Iterator<?> ite = list.iterator();
			while(ite.hasNext()){
				Object[] obj = (Object[]) ite.next();
				ssInfo = new SafetyStockInfo();
				sparePartId = Tools.isNull(obj[0], "");
				ssInfo.setDeviceId(sparePartId);
				ssInfo.setNum(Integer.parseInt(obj[1].toString()));
				ssInfo.setSums(Double.parseDouble(obj[2].toString()));
				ssInfo.setAvgAmount(Double.parseDouble(obj[3].toString()));
				sslist.add(ssInfo);
			}
		}
		return sslist;
	}

	@Override
	public Map<String, Double> getLeadTime(Date startDate, Date endDate)
			throws PmmsException {
		String strStartDate = Tools.formatDate(startDate);
		String strEndDate = Tools.formatDate(endDate);
		Map<String, Double> map = new HashMap<String, Double>();;
		StringBuilder sql = new StringBuilder(128);
		sql.append("select SPARE_PART_ID,avg(LEAD_TIME) avgLeadTime from SPARE_PART_TRANS_INFO where ");
		sql.append("CREATED_DATE between cast(:startDate as datetime) and cast(:endDate as datetime) ");
		sql.append("and TRANS_TYPE='1' ");
		sql.append(" group by SPARE_PART_ID");
		
		Query sqlQuery = getSession().createSQLQuery(sql.toString());
		sqlQuery.setParameter("startDate", strStartDate);
		sqlQuery.setParameter("endDate", strEndDate);
		List<?> list = sqlQuery.list();
		
		if(!CollectionUtils.isEmpty(list)){
			Iterator<?> itor = list.iterator();
			while(itor.hasNext()){
				Object[] obj = (Object[]) itor.next(); 
				map.put(obj[0].toString(), Double.parseDouble(obj[1].toString()));
			}
		}		
		return map;
	}
	
	public void updateSS(List<SafetyStockInfo> ssList) throws PmmsException{
		String sql = "";
		if(!CollectionUtils.isEmpty(ssList)){
			sql = "update SPARE_PART_INFO set SYS_SAFETY_STOCK=:safetyStock where SPARE_PART_ID=:sparePartId";
			Query query = getSession().createSQLQuery(sql);
			for(SafetyStockInfo ssInfo : ssList){
				query.setParameter("safetyStock", ssInfo.getSafetyStock());
				query.setParameter("sparePartId", ssInfo.getDeviceId());
				query.executeUpdate();
			}
		}
	}

	@Override
	public List<String> getSPIds() throws PmmsException {
		List<String> spIdlist = new ArrayList<String>();
		String sql = "select SPARE_PART_ID from SPARE_PART_INFO";
		Query query = getSession().createSQLQuery(sql);
		List<?> list = query.list();
		if(!CollectionUtils.isEmpty(list)){
			Iterator<?> itor = list.iterator();
			Object obj = null;
			while(itor.hasNext()){
				obj = itor.next();
				spIdlist.add(obj.toString());
			}
		}
		return spIdlist;
	}
}







