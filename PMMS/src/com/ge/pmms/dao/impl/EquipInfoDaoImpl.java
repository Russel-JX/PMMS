/**
 * ============================================================
 * File : EquipInfoDaoImpl.java
 * Description : 
 * 
 * Package : com.ge.pmms.dao.impl
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Jan 14, 2015
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
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ge.pmms.base.impl.BaseDaoImpl;
import com.ge.pmms.dao.EquipInfoDao;
import com.ge.pmms.po.DeptInfo;
import com.ge.pmms.po.EquipInfo;
import com.ge.pmms.po.PlanInfo;
import com.ge.pmms.utils.Tools;



/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Jan 14, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
@Repository
public  class EquipInfoDaoImpl extends BaseDaoImpl<EquipInfo>implements EquipInfoDao{

	@SuppressWarnings("unchecked")
	public List<EquipInfo> getEquipInfoByType(EquipInfo equipInfoReq){
		StringBuffer sbQuery = new StringBuffer();
	//	sbQuery.append("select i.id,i.equipType,i.equipId,i.assetId,n.equipmentName,i.equipModel,i.source,i.powerConsum,i.installDate,i.size,i.weight,i.inUse,i.deptId,i.remark ");
	//	sbQuery.append("FROM equipInfo i,com.ge.pmms.po.EquipNameInfo n where equipType=:type and equipmentNameId=equipNmId");
		sbQuery.append("select a.id,b.equipType,a.equipId,a.assetId,b.equipmentName,a.equipModel,a.source,a.powerConsum,a.installDate,a.size,a.weight,a.inUse,a.factoryNo,a.releaseDate,a.equipNmId,a.deptId,a.remark,a.equipDocId,c.deptNm,a.originPrice,a.currPrice ");//21
		sbQuery.append(" FROM equipInfo a,equipNmInfo b,deptInfo c where a.equipNmId=b.equipmentNameId and a.deptId=c.deptId and b.equipType=:type order by a.lastUpdateDate desc");
		Query query = getSession().createQuery(sbQuery.toString());
		query.setParameter("type", equipInfoReq.getEquipType());
		List<?> list = query.list();
		List<EquipInfo> equipLst = new ArrayList<EquipInfo>();
		if(!CollectionUtils.isEmpty(list)){
			Iterator itor = list.iterator();
			while(itor.hasNext()){
				EquipInfo po = new EquipInfo();
				Object[] obj = (Object[])itor.next();
		//		po.setId(String.valueOf(obj[0]));
		//		po.setId(Integer.parseInt(obj[0].toString()));
				po.setEquipType(String.valueOf(obj[1]));
				po.setEquipId(String.valueOf(obj[2]));
				po.setAssetId(String.valueOf(obj[3]));
				po.setEquipmentName(String.valueOf(obj[4]));
				po.setEquipModel(String.valueOf(obj[5]));
				po.setSource(String.valueOf(obj[6]));
				po.setPowerConsum(String.valueOf(obj[7]));
			//	po.setInstallDate(String.valueOf(obj[8]));
				String installDate=StringUtils.split(String.valueOf(obj[8]), " ")[0];
					if(null != installDate){
						po.setInstallDate(installDate);
					}
//					else{
//						po.setInstallDate(Tools.isNull(installDate, ""));
//					}
				po.setSize(String.valueOf(obj[9]));
				po.setWeight(String.valueOf(obj[10]));
				po.setInUse(String.valueOf(obj[11]));
				po.setFactoryNo(String.valueOf(obj[12]));
			//	po.setReleaseDate(String.valueOf(obj[13]));
				String releaseDate=StringUtils.split(String.valueOf(obj[13]), " ")[0];
				if(null != releaseDate){
					po.setReleaseDate(releaseDate);
				}
//				else{
//					po.setReleaseDate(Tools.isNull(releaseDate, ""));
//				}
				po.setEquipNmId(String.valueOf(obj[14]));
				po.setDeptId(String.valueOf(obj[15]));
				po.setRemark(String.valueOf(obj[16]));
				po.setEquipDocId(String.valueOf(obj[17]));
				po.setDeptNm(String.valueOf(obj[18]));
				po.setOriginPrice(String.valueOf(obj[19]));
				po.setCurrPrice(String.valueOf(obj[20]));
				equipLst.add(po);
			}
		}
		return equipLst;
	}

	public List<EquipInfo> getAllEquipInfo(EquipInfo equipInfoReq){
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select a.id,b.equipType,a.equipId,a.assetId,b.equipmentName,a.equipModel,a.source,a.powerConsum,a.installDate,a.size,a.weight,a.inUse,a.factoryNo,a.releaseDate,a.equipNmId,a.deptId,a.remark,a.equipDocId,c.deptNm,a.originPrice,a.currPrice ");//21
			sbQuery.append(" FROM equipInfo a,equipNmInfo b,deptInfo c where a.equipNmId=b.equipmentNameId and a.deptId=c.deptId order by a.lastUpdateDate desc");
			Query query = getSession().createQuery(sbQuery.toString());
			List<?> list = query.list();
			List<EquipInfo> equipLst = new ArrayList<EquipInfo>();
			if(!CollectionUtils.isEmpty(list)){
				Iterator itor = list.iterator();
				while(itor.hasNext()){
					EquipInfo po = new EquipInfo();
					Object[] obj = (Object[])itor.next();
				//	po.setId(String.valueOf(obj[0]));
				//	po.setId(Integer.parseInt(obj[0].toString()));
					po.setEquipType(String.valueOf(obj[1]));
					po.setEquipId(String.valueOf(obj[2]));
					po.setAssetId(String.valueOf(obj[3]));
					po.setEquipmentName(String.valueOf(obj[4]));
					po.setEquipModel(String.valueOf(obj[5]));
					po.setSource(String.valueOf(obj[6]));
					po.setPowerConsum(String.valueOf(obj[7]));
					String installDate=StringUtils.split(String.valueOf(obj[8]), " ")[0];
				//	logger.info("========= "+installDate.split(" ")[0]);
					if("null" != installDate){
						po.setInstallDate(installDate);
					}
//					else{
//						po.setInstallDate(Tools.isNull(installDate, ""));
//					}
					
				//	po.setInstallDate((Date)obj[8]);
					po.setSize(String.valueOf(obj[9]));
					po.setWeight(String.valueOf(obj[10]));
					po.setInUse(String.valueOf(obj[11]));
					po.setFactoryNo(String.valueOf(obj[12]));
					//String releaseDate=String.valueOf(obj[13]);
					String releaseDate=StringUtils.split(String.valueOf(obj[13]), " ")[0];
					if("null" != releaseDate){
						po.setReleaseDate(releaseDate);
					}
//					else{
//						po.setReleaseDate(Tools.isNull(releaseDate, ""));
//					}
				//	po.setReleaseDate(Tools.parseToDate(releaseDate));
				//	po.setReleaseDate((Date)obj[13]);
					po.setEquipNmId(String.valueOf(obj[14]));
					po.setDeptId(String.valueOf(obj[15]));
					po.setRemark(String.valueOf(obj[16]));
					po.setEquipDocId(String.valueOf(obj[17]));
					po.setDeptNm(String.valueOf(obj[18]));
					po.setOriginPrice(String.valueOf(obj[19]));
					po.setCurrPrice(String.valueOf(obj[20]));
					equipLst.add(po);
				}
			}
			return equipLst;
	}
	
	public EquipInfo getEquipInfoByEpId(String equipId){
		StringBuffer sbQuery = new StringBuffer();
	//	sbQuery.append("select i.id,i.equipType,i.equipId,i.assetId,n.equipmentName,i.equipModel,i.source,i.powerConsum,i.installDate,i.size,i.weight,i.inUse,i.deptId,i.remark ");
	//	sbQuery.append("FROM equipInfo i,com.ge.pmms.po.EquipNameInfo n where equipType=:type and equipmentNameId=equipNmId");
		sbQuery.append("select a.id,b.equipType,a.equipId,a.assetId,b.equipmentName,a.equipModel,a.source,a.powerConsum,a.installDate,a.size,a.weight,a.inUse,a.factoryNo,a.releaseDate,a.equipNmId,a.deptId,a.remark,a.originPrice,a.currPrice,a.creator,a.createDate,a.updater,a.lastUpdateDate,a.equipDocId ");
		//sbQuery.append();
		sbQuery.append(" FROM equipInfo a,equipNmInfo b where a.equipNmId=b.equipmentNameId and a.equipId=:equipId");
		Query query = getSession().createQuery(sbQuery.toString());
		query.setParameter("equipId",equipId);
		List<?> list = query.list();
		List<EquipInfo> equipLst = new ArrayList<EquipInfo>();
		if(!CollectionUtils.isEmpty(list)){
			Iterator itor = list.iterator();
			while(itor.hasNext()){
				EquipInfo po = new EquipInfo();
				Object[] obj = (Object[])itor.next();
			//	po.setId(String.valueOf(obj[0]));
				po.setId(Integer.parseInt(obj[0].toString()));
				po.setEquipType(String.valueOf(obj[1]));
				po.setEquipId(String.valueOf(obj[2]));
				po.setAssetId(String.valueOf(obj[3]));
				po.setEquipmentName(String.valueOf(obj[4]));
				po.setEquipModel(String.valueOf(obj[5]));
				po.setSource(String.valueOf(obj[6]));
				po.setPowerConsum(String.valueOf(obj[7]));
				po.setInstallDate(String.valueOf(obj[8]));
				po.setSize(String.valueOf(obj[9]));
				po.setWeight(String.valueOf(obj[10]));
				po.setInUse(String.valueOf(obj[11]));
				po.setFactoryNo(String.valueOf(obj[12]));
				po.setReleaseDate(String.valueOf(obj[13]));
				po.setEquipNmId(String.valueOf(obj[14]));
				po.setDeptId(String.valueOf(obj[15]));
				po.setRemark(String.valueOf(obj[16]));
				po.setOriginPrice(String.valueOf(obj[17]));
				po.setCurrPrice(String.valueOf(obj[18]));
				po.setCreator(String.valueOf(obj[19]));
				po.setCreateDate((Date)obj[20]);
				po.setUpdater(String.valueOf(obj[21]));
				po.setLastUpdateDate((Date)obj[22]);
				po.setEquipDocId(String.valueOf(obj[23]));
				equipLst.add(po);
			}
		}
		if(equipLst.size()>0){
			return equipLst.get(0);
		}
		return null;
	}
	
	
	public int deleteEquipByEpId(EquipInfo equipInfoReq){	
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("delete from equipInfo a where a.equipId=?");
		Query query =getSession().createQuery(sbQuery.toString());
		query.setString(0,equipInfoReq.getEquipId());
		int status=query.executeUpdate();
		return status;
	}
	
	public int updateEquip(EquipInfo equipInfoReq){	
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("update equipInfo set ");
		sbQuery.append("equipNmId=:equipNmId,assetId=:assetId");
		sbQuery.append(" where a.equipId=:equipId");
		Query query =getSession().createQuery(sbQuery.toString());
		query.setParameter("equipNmId", equipInfoReq.getEquipNmId());
		query.setParameter("assetId", equipInfoReq.getAssetId());
		query.setParameter("equipId", equipInfoReq.getEquipId());
		int status=query.executeUpdate();
		return status;
	}
	
	public int updateEpDocByEpId(String equipDocId,String equipId){
		String hql ="update equipInfo set equipDocId=:equipDocId where equipId=:equipId";
		//Query query = getSession().getSessionFactory().get
		Query query =updateByHql(hql);
		query.setParameter("equipDocId", equipDocId);
		query.setParameter("equipId", equipId);
		return query.executeUpdate();
		
	}
	
	//这个方法不能用
	public int updateEpDocByEpModelId(String equipDocId,String equipModel){
		String hql ="update equipInfo set equipDocId=:equipDocId where equipModel=:equipModel";
		Query query =updateByHql(hql);
		query.setParameter("equipDocId", equipDocId);
		query.setParameter("equipModel", equipModel);
		return query.executeUpdate();
	}
		
	@SuppressWarnings("unchecked")
	public List<DeptInfo> getDeptInfo(){
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("from deptInfo");
		Query query = getSession().createQuery(sbQuery.toString());
		List<DeptInfo> lstDeptInfo=query.list();
		return lstDeptInfo;
	}
	
	@SuppressWarnings("unchecked")
	public List getEquipIdByEpModel(String epModel){
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("from equipInfo where equipModel=:equipModel");
		Query query = getSession().createQuery(sbQuery.toString());
		query.setParameter("equipModel", epModel);
		List<EquipInfo> list=query.list();
		return list;
	}
	
	//根据设备名称模糊查询设备
	public List<EquipInfo> getEquipsByEQName(String eqName){
		StringBuilder hql = new StringBuilder();
//		hql.append( "select a.equipId,b.equipmentNameId,b.equipmentName,c.deptNm from equipInfo a, equipNmInfo b,deptInfo c ");
//		hql.append( "where a.equipNmId = b.equipmentNameId and a.deptId=c.deptId and ");
//		hql.append( "b.equipmentName like :eqName ");
		
		hql.append( "select new equipInfo(a.equipId,b.equipmentNameId,b.equipmentName,c.deptNm) from equipInfo a, equipNmInfo b,deptInfo c ");
		hql.append( "where a.equipNmId = b.equipmentNameId and a.deptId=c.deptId and ");
		hql.append( "b.equipmentName like :eqName ");
		Query query = getSession().createQuery(hql.toString());
		query.setParameter("eqName", "%"+eqName+"%");
		@SuppressWarnings("unchecked")
		List<EquipInfo> listResult = query.list();
//		List<EquipInfo> list = new ArrayList<EquipInfo>();
//		EquipInfo equipInfo;
//		if(!CollectionUtils.isEmpty(listResult)){
//			Iterator<?> ite = listResult.iterator();
//			while(ite.hasNext()){
//				Object[] obj = (Object[])ite.next();
//				equipInfo = new EquipInfo();
//				equipInfo.setEquipId(obj[0].toString());
//				equipInfo.setEquipNmId(obj[1].toString());
//				equipInfo.setEquipmentName(Tools.isNull(obj[2])?"":obj[2].toString());
//				equipInfo.setDeptNm(Tools.isNull(obj[3])?"":obj[3].toString());
//				list.add(equipInfo);
//			}
//		}
		return listResult;
	}
}
