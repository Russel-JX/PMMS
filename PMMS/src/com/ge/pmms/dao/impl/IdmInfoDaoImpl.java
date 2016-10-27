/**
 * ============================================================
 * File : IdmInfoDaoImpl.java
 * Description : 
 * 
 * Package : com.ge.pmms.dao.impl
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Jan 26, 2015
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ge.pmms.base.impl.BaseDaoImpl;
import com.ge.pmms.dao.CommonDao;
import com.ge.pmms.dao.IdmInfoDao;
import com.ge.pmms.po.IdmInfo;
import com.ge.pmms.po.IdmPoInfo;
import com.ge.pmms.po.IdmTransInfo;
import com.ge.pmms.po.SafetyStockInfo;
import com.ge.pmms.po.User;
import com.ge.pmms.utils.Tools;


/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Jan 26, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
@Repository
public class IdmInfoDaoImpl extends BaseDaoImpl<IdmInfo>implements IdmInfoDao{
	@Autowired
	private CommonDao commonDao;
	
	public List<IdmInfo> getAllIdmInfo(){
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("select a.idmId,a.idmNm,a.size,a.source,a.price,a.measurement,a.stockNum,a.safetyNm,a.position,a.typeId,b.SUB_TYPE_NM,b.TYPE_DETAIL_NM,a.remark,a.subTypeId,a.typeDetailId,a.sugSaveStock ");//16
		sbQuery.append(" from idmInfo a,IdmTypeDetail b ");
		sbQuery.append(" where a.typeId=b.TYPE_ID and a.subTypeId=b.SUB_TYPE_ID and a.typeDetailId=b.TYPE_DETAIL_ID order by a.lastUpdateDate desc");
		Query query = getSession().createQuery(sbQuery.toString());
		List<?> list = query.list();
		List<IdmInfo> idmLst = new ArrayList<IdmInfo>();
		if(!CollectionUtils.isEmpty(list)){
			Iterator itor = list.iterator();
			while(itor.hasNext()){
				IdmInfo po = new IdmInfo();
				Object[] obj = (Object[])itor.next();
				po.setIdmId(String.valueOf(obj[0]));
				po.setIdmNm(String.valueOf(obj[1]));
				po.setSize(String.valueOf(obj[2]));
				po.setSource(String.valueOf(obj[3]));
				po.setPrice(String.valueOf(obj[4]));
				po.setMeasurement(String.valueOf(obj[5]));
				po.setStockNum(String.valueOf(obj[6]));
				po.setSafetyNm(String.valueOf(obj[7]));
				po.setPosition(String.valueOf(obj[8]));
				po.setTypeId(String.valueOf(obj[9]));
				po.setSUB_TYPE_NM(String.valueOf(obj[10]));
				po.setTYPE_DETAIL_NM(String.valueOf(obj[11]));
				po.setRemark(String.valueOf(obj[12]));
				po.setSubTypeId(String.valueOf(obj[13]));
				po.setTypeDetailId(String.valueOf(obj[14]));
				po.setSugSaveStock(String.valueOf(obj[15]));
				idmLst.add(po);
			}
		}
		return idmLst;
	}
	
	public List<IdmInfo> getIdmInfoByType(IdmInfo idmInfo){
		StringBuffer sbQuery = new StringBuffer();
		//sbQuery.append("FROM idmInfo where typeId=:typeId order by lastUpdateDate desc");
		sbQuery.append("select a.idmId,a.idmNm,a.size,a.source,a.price,a.measurement,a.stockNum,a.safetyNm,a.position,a.typeId,b.SUB_TYPE_NM,b.TYPE_DETAIL_NM,a.remark,a.subTypeId,a.typeDetailId,a.sugSaveStock ");//16
		sbQuery.append(" from idmInfo a,IdmTypeDetail b ");
		sbQuery.append(" where a.typeId=b.TYPE_ID and a.subTypeId=b.SUB_TYPE_ID and a.typeDetailId=b.TYPE_DETAIL_ID and a.typeId=:typeId order by a.lastUpdateDate desc");
		Query query = getSession().createQuery(sbQuery.toString());
		query.setParameter("typeId",idmInfo.getTypeId());
		List<?> list = query.list();
		List<IdmInfo> idmLst = new ArrayList<IdmInfo>();
		if(!CollectionUtils.isEmpty(list)){
			Iterator itor = list.iterator();
			while(itor.hasNext()){
				IdmInfo po = new IdmInfo();
				Object[] obj = (Object[])itor.next();
				po.setIdmId(String.valueOf(obj[0]));
				po.setIdmNm(String.valueOf(obj[1]));
				po.setSize(String.valueOf(obj[2]));
				po.setSource(String.valueOf(obj[3]));
				po.setPrice(String.valueOf(obj[4]));
				po.setMeasurement(String.valueOf(obj[5]));
				po.setStockNum(String.valueOf(obj[6]));
				po.setSafetyNm(String.valueOf(obj[7]));
				po.setPosition(String.valueOf(obj[8]));
				po.setTypeId(String.valueOf(obj[9]));
				po.setSUB_TYPE_NM(String.valueOf(obj[10]));
				po.setTYPE_DETAIL_NM(String.valueOf(obj[11]));
				po.setRemark(String.valueOf(obj[12]));
				po.setSubTypeId(String.valueOf(obj[13]));
				po.setTypeDetailId(String.valueOf(obj[14]));
				po.setSugSaveStock(String.valueOf(obj[15]));
				idmLst.add(po);
			}
		}
		return idmLst;
	}
	
	public IdmInfo getIdmInfoByIdmId(String idmId){
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("FROM idmInfo where idmId=:idmId");
		Query query = getSession().createQuery(sbQuery.toString());
		query.setParameter("idmId",idmId);
		List<IdmInfo> list = query.list();
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
		
	}
	
	public List<IdmTransInfo> getAllIdmTransRecords(){
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("FROM idmTransInfo order by lastUpdateDate desc");
		Query query = getSession().createQuery(sbQuery.toString());
		List<IdmTransInfo> list = query.list();
		return list;
	}
	
	public IdmTransInfo getTransInfoByIdmId(String idmId){
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("FROM idmTransInfo where idmId=:idmId");
		Query query = getSession().createQuery(sbQuery.toString());
		query.setParameter("idmId",idmId);
		List<IdmTransInfo> list = query.list();
		return list.get(0);
	}
	
	public int deleIdmbyIdmId(IdmInfo idmInfo){
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("delete from idmInfo a where a.idmId=?");
		Query query =getSession().createQuery(sbQuery.toString());
		query.setString(0,idmInfo.getIdmId());
		int status=query.executeUpdate();
		return status;
	}
	
	public List<IdmInfo> getAllUnSaftyIdm(){
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("FROM idmInfo where stockNum<safetyNm order by type");
		Query query = getSession().createQuery(sbQuery.toString());
		List<IdmInfo> list = query.list();
		return list;
	}
	
	public int updateStockNum(String idmId,int amount){
		
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("update idmInfo set stockNum=:stockNum where idmId=:idmId");
		Query query = getSession().createQuery(sbQuery.toString());
		query.setParameter("idmId",idmId);
		query.setParameter("stockNum",String.valueOf(amount));
		int rs=query.executeUpdate();
		return rs;
	}
	
	public String getLastIdmId(IdmInfo idmInfo){
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("select idmId FROM idmInfo where typeId=:typeId and subTypeId=:subTypeId and typeDetailId=:typeDetailId order by idmId desc");
		Query query = getSession().createQuery(sbQuery.toString());
		query.setParameter("typeId",idmInfo.getTypeId());
		query.setParameter("subTypeId",idmInfo.getSubTypeId());
		query.setParameter("typeDetailId",idmInfo.getTypeDetailId());
		List<IdmInfo> list = query.list();
		if(list.size()>0){
			return String.valueOf(list.get(0));
		}
		return null;	
	}
	public List<IdmTransInfo> getIdmRecordByPeriod(IdmTransInfo idmTransInfoReq){
		String searchFromDate=idmTransInfoReq.getStartTime();
		String searchToDate =idmTransInfoReq.getEndTime();
		String transType=idmTransInfoReq.getTransType();
		StringBuffer sbQuery = new StringBuffer();
		List<IdmTransInfo> idmTransInfoLst=null;
		sbQuery.append("select TRANS_ID,IDM_ID,IDM_TYPE,AMOUNT,PRICE,CREATED_DATE,IDM_NAME,TRANS_TYPE,LEAD_TIME,po,creator,receiver FROM IDM_TRANSACTION  where 1=1 ");
		if(!StringUtils.isEmpty(searchFromDate)&&(!StringUtils.isEmpty(searchToDate))){
			searchFromDate += " 00:00:00";
			searchToDate += " 23:59:59";
			sbQuery.append(" and CREATED_DATE between cast(:searchFromDate as datetime) and cast(:searchToDate as datetime)");
		}
		
		if(!Tools.isEmpty(transType)){
			sbQuery.append(" and TRANS_TYPE=:transType");
		}
		sbQuery.append(" order by LAST_UPDATED_DATE desc");
		Query query = getSession().createSQLQuery(sbQuery.toString());
		if(!StringUtils.isEmpty(searchFromDate)&&(!StringUtils.isEmpty(searchToDate))){
			query.setParameter("searchFromDate", searchFromDate);
			query.setParameter("searchToDate", searchToDate);
		}
		if(!StringUtils.isEmpty(transType)){
			query.setParameter("transType", transType);
		}	
		List<?> list = query.list();
		if(!CollectionUtils.isEmpty(list)){
			idmTransInfoLst = new ArrayList<IdmTransInfo>();
			Iterator<?> iterator = list.iterator();
			while(iterator.hasNext()){
				Object[] obj = (Object[]) iterator.next();
				IdmTransInfo po = new IdmTransInfo();
				po.setTransId(String.valueOf(obj[0]));
				po.setIdmId(String.valueOf(obj[1]));
				po.setIdmType(String.valueOf(obj[2]));
				po.setAmount(String.valueOf(obj[3]));
				po.setPrice(String.valueOf(obj[4]));
				po.setStrCreateDate(String.valueOf(obj[5]));
				po.setIdmNm(String.valueOf(obj[6]));
				po.setTransType(String.valueOf(obj[7]));
				po.setLeadTime(String.valueOf(obj[8]));
				po.setPo(String.valueOf(obj[9]));
				if(!Tools.isEmpty(String.valueOf(obj[10]))){
					User keeperUser=commonDao.getUserBySso(String.valueOf(obj[10]));
					String keeperNm="";
					if(!Tools.isEmpty(keeperUser)){
						 keeperNm=keeperUser.getLastName()+keeperUser.getFirstName();
					}
					po.setCreator(keeperNm+"("+String.valueOf(obj[10])+")");
				}
				if(!Tools.isEmpty(String.valueOf(obj[11]))){
					User receiverUser=commonDao.getUserBySso(String.valueOf(obj[11]));
					String receiverNm="";
					if(!Tools.isEmpty(receiverUser)){
						 receiverNm=receiverUser.getLastName()+receiverUser.getFirstName();
					}
					po.setReceiver(receiverNm+"("+String.valueOf(obj[11])+")");
				}
				idmTransInfoLst.add(po);
			}
		}
		return idmTransInfoLst;
	}
	
	public List<SafetyStockInfo> getVariance(Date startDate,Date endDate){
		String strStartDate = Tools.formatDate(startDate);
		String strEndDate = Tools.formatDate(endDate);
		StringBuffer sbQuery = new StringBuffer();
		List<SafetyStockInfo> stdInfoLst=null;
		sbQuery.append("select amont_id,sum((monthAmount-total) * (monthAmount-total)) sum11,total singleTotal,count(amont_id) count from  ");
		sbQuery.append("(select idm_id amont_id,sum(amount) monthAmount from IDM_TRANSACTION ");
		sbQuery.append(" where trans_type='2' and CREATED_DATE between cast(:startDate as datetime) and cast(:endDate as datetime) group by idm_id,DATEADD(mm, DATEDIFF(mm,0,CREATED_DATE), 0)) a, ");
		sbQuery.append(" (select idm_id total_id,sum(amount)/24.0 total from IDM_TRANSACTION ");
		sbQuery.append(" where trans_type='2' and CREATED_DATE between cast(:startDate as datetime) and cast(:endDate as datetime) group by idm_id) b");
		sbQuery.append(" where a.amont_id=b.total_id");
		sbQuery.append(" group by amont_id,total");
		
		Query query = getSession().createSQLQuery(sbQuery.toString());
		query.setParameter("startDate", strStartDate);
		query.setParameter("endDate", strEndDate);
		List<?> list = query.list();
		if(!CollectionUtils.isEmpty(list)){
			stdInfoLst = new ArrayList<SafetyStockInfo>();
			Iterator<?> iterator = list.iterator();
			while(iterator.hasNext()){
				Object[] obj = (Object[]) iterator.next();
				SafetyStockInfo po = new SafetyStockInfo();
				po.setDeviceId(String.valueOf(obj[0]));
				po.setSums(Double.parseDouble(String.valueOf(obj[1])));
				po.setAvgAmount(Double.parseDouble(String.valueOf(obj[2])));
				po.setNum(Integer.parseInt(String.valueOf(obj[3])));
				stdInfoLst.add(po);
			}
		}
		return stdInfoLst;
	}
	
	public Map<String,Double> getL(Date startDate,Date endDate){
		String strStartDate = Tools.formatDate(startDate);
		String strEndDate = Tools.formatDate(endDate);
		StringBuffer sbQuery = new StringBuffer();
		Map<String,Double> leadTmMap=null;
		sbQuery.append("select idm_id,avg(LEAD_TIME) avgLeadTm from IDM_TRANSACTION  ");
		sbQuery.append("where CREATED_DATE between cast(:startDate as datetime) and cast(:endDate as datetime) and trans_type='1' ");
		sbQuery.append("  group by idm_id; ");
		
		Query query = getSession().createSQLQuery(sbQuery.toString());
		query.setParameter("startDate", strStartDate);
		query.setParameter("endDate", strEndDate);
		List<?> list = query.list();
		if(!CollectionUtils.isEmpty(list)){
			leadTmMap = new HashMap<String, Double>();
			Iterator<?> iterator = list.iterator();
			while(iterator.hasNext()){
				Object[] obj = (Object[]) iterator.next();
				//Map<String,Double> poMap =  new HashMap<String, Double>();
				leadTmMap.put(String.valueOf(obj[0]), Double.parseDouble(String.valueOf(obj[1])));
				//leadTmMapLst.add(poMap);
			}
		}
		return leadTmMap;
	}
	
	public void updateSafetyNum(List<SafetyStockInfo> ssList){
		String sql = "";
		if(!CollectionUtils.isEmpty(ssList)){
			sql = "update IDM_INFO set SUG_SAFETY_NUM=:safetyStock where IDM_ID=:idmId";
			Query query = getSession().createSQLQuery(sql);
			for(SafetyStockInfo ssInfo : ssList){
				query.setParameter("safetyStock", ssInfo.getSafetyStock());
				query.setParameter("idmId", ssInfo.getDeviceId());
				query.executeUpdate();
			}
		}
	}
	
	public List<String> getIdmId(){
		List<String> idmIdlist = new ArrayList<String>();
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("select idm_id FROM IDM_INFO");
		Query query =getSession().createSQLQuery(sbQuery.toString());
		List<?> list = query.list();
		if(!CollectionUtils.isEmpty(list)){
			Iterator<?> itor = list.iterator();
			Object obj = null;
			while(itor.hasNext()){
				obj = itor.next();
				idmIdlist.add(obj.toString());
			}
		}
		return idmIdlist;
	}
	
	public List<IdmTransInfo> getPoDetail(IdmTransInfo idmTransInfoReq){
		List<IdmTransInfo> IdmTransInfolist = new ArrayList<IdmTransInfo>();
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("select idm_id,po,sum(amount) inAmount from IDM_TRANSACTION where idm_id=:idmId and TRANS_TYPE='1' group by idm_id,po;");
		Query query =getSession().createSQLQuery(sbQuery.toString());
		query.setParameter("idmId", idmTransInfoReq.getIdmId());
		List<?> list = query.list();
		if(!CollectionUtils.isEmpty(list)){
			Iterator<?> itor = list.iterator();
			while(itor.hasNext()){
				IdmTransInfo po=new IdmTransInfo();
				Object[] obj = (Object[])itor.next();
				po.setIdmId(String.valueOf(obj[0]));
				po.setPo(String.valueOf(obj[1]));
				po.setAmount(String.valueOf(obj[2]));
				po.setRemainAmount(String.valueOf(obj[2]));
				IdmTransInfolist.add(po);
			}
		}
		return IdmTransInfolist;
	}
	
	public int getOutAmount(IdmTransInfo idmTransInfoReq){
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("select sum(amount) outAmount from IDM_TRANSACTION where idm_id=:idm_id and TRANS_TYPE='2' group by idm_id");
		Query query =getSession().createSQLQuery(sbQuery.toString());
		query.setParameter("idm_id", idmTransInfoReq.getIdmId());
		List<?> list = query.list();
		 if(!CollectionUtils.isEmpty(list)){
		      Object tmp = list.get(0);
		      if(!Tools.isEmpty(tmp)){
		        return Integer.parseInt(tmp.toString());
		      }
		    }
		return 0;
	}
	
	public List<IdmPoInfo> getPoInfo(IdmTransInfo idmTransInfoReq){
		List<IdmPoInfo> poInfoLst = new ArrayList<IdmPoInfo>();
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("select IDM_ID,PO,REMAIN_AMOUNT,amount from IDM_PO_INFO where IDM_ID=:idmId ");
		if(!Tools.isEmpty(idmTransInfoReq.getPo())){
			sbQuery.append(" and PO=:po");
		}
		sbQuery.append(" order by created_date");
		Query query =getSession().createSQLQuery(sbQuery.toString());
		query.setParameter("idmId", idmTransInfoReq.getIdmId());
		if(!Tools.isEmpty(idmTransInfoReq.getPo())){
			query.setParameter("po", idmTransInfoReq.getPo());
		}
		
		List<?> list = query.list();
		if(!CollectionUtils.isEmpty(list)){
			Iterator<?> itor = list.iterator();
			while(itor.hasNext()){
				IdmPoInfo po=new IdmPoInfo();
				Object[] obj = (Object[])itor.next();
				po.setIdmId(String.valueOf(obj[0]));
				po.setPo(String.valueOf(obj[1]));
				po.setRemainAmount(String.valueOf(obj[2]));
				po.setInAmount(String.valueOf(obj[3]));
				poInfoLst.add(po);
			}
		}
		return poInfoLst;
	}
	
	public int updateIdmPoInfo(IdmTransInfo idmTransInfoReq){
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("update IdmPoInfo set remainAmount=:remainAmount,updater=:updater,lastUpdateDate=:lastUpdateDate");
		if(!Tools.isEmpty(idmTransInfoReq.getInAmount())){
			sbQuery.append(",inAmount=:inAmount ");
		}
		sbQuery.append("  where idmId=:idmId and po=:po");
		Query query = getSession().createQuery(sbQuery.toString());
		query.setParameter("idmId",idmTransInfoReq.getIdmId());
		query.setParameter("po",idmTransInfoReq.getPo());
		query.setParameter("remainAmount",idmTransInfoReq.getRemainAmount());
		query.setParameter("updater", idmTransInfoReq.getUpdater());
		query.setParameter("lastUpdateDate", idmTransInfoReq.getLastUpdateDate());
		if(!Tools.isEmpty(idmTransInfoReq.getInAmount())){
			query.setParameter("inAmount", idmTransInfoReq.getInAmount());
		}
		
		int rs=query.executeUpdate();
		return rs;
	}
	public int saveIdmPoInfo(IdmTransInfo idmTransInfoReq){
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("INSERT INTO IDM_PO_INFO(IDM_ID,PO,REMAIN_AMOUNT,CREATOR,CREATED_DATE,UPDATER,LAST_UPDATED_DATE,AMOUNT) values(?,?,?,?,?,?,?,?) ");
		Query query =getSession().createSQLQuery(sbQuery.toString());
		query.setParameter(0,idmTransInfoReq.getIdmId());
		query.setParameter(1,idmTransInfoReq.getPo());
		query.setParameter(2,idmTransInfoReq.getAmount());
		query.setParameter(3, idmTransInfoReq.getCreator());
		query.setParameter(4, idmTransInfoReq.getCreateDate());
		query.setParameter(5, idmTransInfoReq.getUpdater());
		query.setParameter(6, idmTransInfoReq.getLastUpdateDate());
		query.setParameter(7, idmTransInfoReq.getAmount());
		int rs=query.executeUpdate();
		return rs;
	}
	
}
