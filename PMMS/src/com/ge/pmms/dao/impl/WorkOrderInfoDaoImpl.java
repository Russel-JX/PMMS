/**
 * ============================================================
 * File : WorkOrderInfoDaoImpl.java
 * Description : 
 * 
 * Package : com.ge.pmms.dao.impl
 * Author : iGATE
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
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ge.pmms.base.PmmsException;
import com.ge.pmms.base.impl.BaseDaoImpl;
import com.ge.pmms.dao.WorkOrderInfoDao;
import com.ge.pmms.po.FaultTypeInfo;
import com.ge.pmms.po.WorkOrderInfo;
import com.ge.pmms.utils.Constants;
import com.ge.pmms.utils.Tools;

/*******************************************************************************
 *
 * @param <E>
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Jan 13, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
@Repository
public class WorkOrderInfoDaoImpl<E> extends BaseDaoImpl<WorkOrderInfo>
		implements
			WorkOrderInfoDao {
	
	@Override
	public List<WorkOrderInfo> getWorkOrders(String workOrderStatus,WorkOrderInfo workOrderInfo) throws PmmsException {
		List<WorkOrderInfo> workOrders = new ArrayList<WorkOrderInfo>();	
		String workOrderType = workOrderInfo.getWorkOrderType();
		String startDate = workOrderInfo.getStartDate();
		String endDate = workOrderInfo.getEndDate();
		String deptId = workOrderInfo.getDeptId();
		String equipType = workOrderInfo.getEquipType();
		StringBuilder sql = new StringBuilder(128);
		sql.append("select a.WO_ID,a.EQUIP_ID,a.CREATOR,a.FAULT_DESCRIPTION,a.CREATED_DATE,a.SHUTDOWN_FLAG,a.SAFETY_INVOLVED,a.WO_TYPE,a.WO_STATUS,b.MAINT_START_DATE,b.MAINT_END_DATE,b.REMARK,");
		sql.append("a.ID,b.MECHANIC,b.SPARE_PART_INVOLVED,b.EXTERNAL_SERVICE_INVOLVED,b.ID WOM_ID,b.FAULT_TYPE,c.ASSETS_ID,cast(c.EQUIP_MODEL as varchar) EQUIP_MODEL,cast(d.EQUIP_NAME as varchar) EQUIP_NAME,");
		sql.append("c.DEPT_ID,d.EQUIP_NAME_ID,a.PLAN_START_MONTH,a.LAST_UPDATED_DATE,b.OPERATOR,");
		sql.append("e.FIRSTNAME CREATOR_FIRSTNAME, e.LASTNAME CREATOR_LASTNAME,");
		sql.append("(select f.FIRSTNAME from USERS f where b.MECHANIC=f.SSO) MECHANIC_FIRSTNAME,");
		sql.append("(select g.LASTNAME from USERS g where b.MECHANIC=g.SSO) MECHANIC_LASTNAME,");
		sql.append("(select h.FIRSTNAME from USERS h where b.OPERATOR=h.SSO) OPERATOR_FIRSTNAME,");
		sql.append("(select i.LASTNAME from USERS i where b.OPERATOR=i.SSO) OPERATOR_LASTNAME,");
		sql.append("j.DEPT_NAME");
		sql.append(" from WO_INFO a");
		sql.append(" left join WO_MAINT_INFO b on a.WO_ID=b.WO_ID");
		sql.append(" left join USERS e on a.CREATOR= e.SSO,");
		sql.append("EQUIP_INFO c,");
		sql.append("EQUIP_NAME_INFO d,");
		sql.append("DEPT_INFO j");
		sql.append(" where");
		sql.append(" a.EQUIP_ID=c.EQUIP_ID");
		sql.append(" and c.EQUIP_NAME_ID=d.EQUIP_NAME_ID");
		sql.append(" and a.WO_TYPE=:workOrderType");
		sql.append(" and a.WO_STATUS in (:workOrderStatus)");
		sql.append(" and c.DEPT_ID=j.DEPT_ID");
		if(!StringUtils.isEmpty(startDate)&&!StringUtils.isEmpty(endDate)){
			startDate+=" 00:00:00";
			endDate+=" 23:59:59";
			sql.append(" and a.CREATED_DATE between cast(:startDate as datetime) and cast(:endDate as datetime)");
			logger.info("时间查询");
		}
		if(!StringUtils.isEmpty(deptId)){
			sql.append(" and c.DEPT_ID=:deptId");
			logger.info("区域（部门）查询");
		}
		if(!StringUtils.isEmpty(equipType)){
			sql.append(" and d.EQUIP_TYPE=:equipType");
			logger.info("设备种类查询");
		}
		sql.append(" order by a.LAST_UPDATED_DATE desc");
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setParameter("workOrderType", workOrderType);
		query.setParameterList("workOrderStatus", StringUtils.split(workOrderStatus,","));
		if(!StringUtils.isEmpty(startDate)&&!StringUtils.isEmpty(endDate)){
			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);
		}
		if(!StringUtils.isEmpty(deptId)){
			query.setParameter("deptId", deptId);
		}
		if(!StringUtils.isEmpty(equipType)){
			query.setParameter("equipType", equipType);
		}
		List<?> list = query.list();
	    Iterator<?> iterator = list.iterator();
	    
	    
	    Date tmpCreatedDate = null;            //创建时间
	    Date tmpMaintanceStartDate = null;     //开始维修时间
	    Date tmpMaintanceEndDate = null;       //结束维修时间
	    Date tmpPlanStartMonth = null;         //开始维修月
	    double timeGap = 0.0;                  //维修间隔
	    
	    while(iterator.hasNext()){
	    	Object[] objects =  (Object[])iterator.next();
	    	WorkOrderInfo workOrder = new WorkOrderInfo();
	    	workOrder.setWorkOrderId(Tools.isNull(objects[0], ""));
	    	workOrder.setEquipId(Tools.isNull(objects[1],""));
	    	workOrder.setCreator(Tools.isNull(objects[2],""));
	    	workOrder.setFaultDescription(Tools.isNull(objects[3],""));	
	    	if(!Tools.isNull(objects[4])){
	    		tmpCreatedDate = (Date) objects[4];
	    		workOrder.setStrCreateDate(Tools.formatDate(tmpCreatedDate));
	    	}
	    	workOrder.setShutdownFlag(Boolean.parseBoolean(objects[5].toString()));
	    	workOrder.setSafetyInvolved(Boolean.parseBoolean(objects[6].toString()));
	    	workOrder.setWorkOrderType(objects[7].toString());
	    	workOrder.setWorkOrderStatus(objects[8].toString());
	    	if(!Tools.isNull(objects[9])){
	    		tmpMaintanceStartDate = (Date) objects[9];
	    		workOrder.setStrMaintanceStartDate(Tools.formatDate(tmpMaintanceStartDate));
	    		//System.out.println(Tools.formatDate(tmpMaintanceStartDate));
	    	}
	    	if(!Tools.isNull(objects[10])){
	    		tmpMaintanceEndDate = (Date) objects[10];
	    		workOrder.setStrMaintanceEndDate(Tools.formatDate(tmpMaintanceEndDate));
	    		//System.out.println(Tools.formatDate(tmpMaintanceEndDate));
	    	}
	    	workOrder.setRemark(Tools.isNull(objects[11],""));	 
	    	workOrder.setId(Integer.parseInt(objects[12].toString()));
	    	workOrder.setMechianic(Tools.isNull(objects[13],""));
	    	workOrder.setSparePartInvolved(Tools.isNull(objects[14])? false : Boolean.parseBoolean(objects[14].toString()));
	    	workOrder.setExternalServiceInvolved(Tools.isNull(objects[15])? false : Boolean.parseBoolean(objects[15].toString()));
	    	workOrder.setWoMaintId(Tools.isNull(objects[16],""));
	    	workOrder.setFaultType(Tools.isNull(objects[17],""));
	    	workOrder.setAssetId(Tools.isNull(objects[18],""));
	    	workOrder.setEquipModel(Tools.isNull(objects[19],""));
	    	workOrder.setEquipName(Tools.isNull(objects[20],""));
	    	workOrder.setDeptId(Tools.isNull(objects[21],""));
	    	workOrder.setEquipNameId(Tools.isNull(objects[22],""));
	    	
	    	if(!Tools.isNull(objects[23])){
	    		tmpPlanStartMonth = (Date) objects[23];
	    		workOrder.setStrPlanStartMonth(Tools.formatDate(tmpPlanStartMonth, Constants.DATE_PATTEN_MONTH));
	    		//System.out.println(Tools.formatDate(tmpPlanStartMonth, Constants.DATE_PATTEN_MONTH));
	    	}
	    	if((!Tools.isNull(tmpMaintanceStartDate)) && (!Tools.isNull(tmpMaintanceEndDate))){
	    		timeGap = (tmpMaintanceEndDate.getTime() - tmpMaintanceStartDate.getTime())/Constants.HOURS;
				//System.out.println(timeGap);
				//timeGap = Tools.roundDecimal2(timeGap);
				//System.out.println(timeGap);
				workOrder.setTimeGap(timeGap);
	    	}
	    	workOrder.setOperator(Tools.isNull(objects[25],""));
	    	workOrder.setCreatorFirtName(Tools.isNull(objects[26], ""));
	    	workOrder.setCreatorLastName(Tools.isNull(objects[27], ""));
	    	workOrder.setMechianicFirtName(Tools.isNull(objects[28], ""));
	    	workOrder.setMechianicLastName(Tools.isNull(objects[29], ""));
	    	workOrder.setOperationFirstName(Tools.isNull(objects[30], ""));
	    	workOrder.setOperationFirstName(Tools.isNull(objects[31], ""));
	    	workOrder.setDeptNM(Tools.isNull(objects[32], ""));
	    	workOrders.add(workOrder);
	    }
		return workOrders;
	}

	@Override
	public int deleteByPanId(String planId) {
		StringBuilder hql = new StringBuilder(128);
		hql.append("delete from workOrderInfo where planId=:planId");
		Query query = getSession().createQuery(hql.toString());
		query.setString("planId", planId);
		int num = query.executeUpdate();
		return num;
	}
	
	public long deletePWMOByWOId(String[] woIds)throws PmmsException{
		String hql = "delete from workOrderMaintenanceInfo where workOrderId in(:woIds)";
		Query query = getSession().createQuery(hql);
		query.setParameterList("woIds", woIds);
		return query.executeUpdate();
	}
	public List<String> getWOIdByPlanId(String planId)throws PmmsException{
		StringBuilder sql = new StringBuilder();
		sql.append( "select wo_id from wo_info where plan_id =:planId ");
		Query query = getSession().createSQLQuery(sql.toString());
		query.setParameter("planId", planId);
		List<?> listResult = query.list();
		List<String> list = new ArrayList<String>();
		if(!CollectionUtils.isEmpty(listResult)){
			Iterator<?> ite = listResult.iterator();
			while(ite.hasNext()){
				Object obj = ite.next();
				list.add(obj.toString());
			}
		}
		return list;
	}
	
	public long getFinishedPWONum(String planId)throws PmmsException{
	String hql = "select count(*) from workOrderInfo where planId=:planId and workOrderStatus in(:wosA,:wosB) ";
	Query query = getSession().createQuery(hql);
	query.setParameter("planId", planId);
	query.setParameter("wosA", Constants.WORK_ORDER_STATUS_ClOSED);
	query.setParameter("wosB", Constants.WORK_ORDER_STATUS_MAINT);
	Object obj = query.uniqueResult();
	if(Tools.isNull(obj)){
	return 0;
	}
	return (Long)obj;
	}
	
	public List<WorkOrderInfo> getFinishedPWO(String planId)throws PmmsException{
	String hql = "from workOrderInfo where planId=:planId and workOrderStatus =:workOrderStatus ";
	Query query = getSession().createQuery(hql);
	query.setParameter("planId", planId);
	query.setParameter("workOrderStatus", Constants.WORK_ORDER_STATUS_ClOSED);
	@SuppressWarnings("unchecked")
	List<WorkOrderInfo> list = query.list();
	return list;
	}
	
	public List<String> getUnchangeablePWOMonth(String planId,String currMonth)throws PmmsException{
		StringBuilder sql = new StringBuilder();
		sql.append( "select  DATEPART(MONTH,plan_start_month) from wo_info where plan_id =:planId and wo_status in(:wosA,:wosB) ");
		sql.append( " and plan_start_month<=:currMonth ");
		Query query = getSession().createSQLQuery(sql.toString());
		query.setParameter("planId", planId);
		query.setParameter("wosA", Constants.WORK_ORDER_STATUS_ClOSED);
		query.setParameter("wosB", Constants.WORK_ORDER_STATUS_MAINT);
		query.setParameter("currMonth", currMonth);
		List<?> listResult = query.list();
		List<String> list = new ArrayList<String>();
		if(!CollectionUtils.isEmpty(listResult)){
			Iterator<?> ite = listResult.iterator();
			while(ite.hasNext()){
				Object obj = ite.next();
				list.add(obj.toString());
			}
		}
		return list;
	}
	
	public List<String> getFinishedPWOMonth(String planId)throws PmmsException{
		StringBuilder sql = new StringBuilder();
		sql.append( "select  DATEPART(MONTH,plan_start_month) from wo_info where plan_id =:planId and wo_status=:workOrderStatus ");
		Query query = getSession().createSQLQuery(sql.toString());
		query.setParameter("planId", planId);
		query.setParameter("workOrderStatus", Constants.WORK_ORDER_STATUS_ClOSED);
		List<?> listResult = query.list();
		List<String> list = new ArrayList<String>();
		if(!CollectionUtils.isEmpty(listResult)){
			Iterator<?> ite = listResult.iterator();
			while(ite.hasNext()){
				Object obj = ite.next();
				list.add(obj.toString());
			}
		}
		return list;
	}

	public List<WorkOrderInfo> getUnfinishedPWO(String planId)throws PmmsException{
	String hql = "from workOrderInfo where planId=:planId and workOrderStatus in(:wosA,:wosB) ";
	Query query = getSession().createQuery(hql);
	query.setParameter("planId", planId);
	query.setParameter("wosA", Constants.WORK_ORDER_STATUS_SUBMIT);
	query.setParameter("wosB", Constants.WORK_ORDER_STATUS_MAINT);
	@SuppressWarnings("unchecked")
	List<WorkOrderInfo> list = query.list();
	return list;
	}
	
	public List<WorkOrderInfo> getWOForSP(WorkOrderInfo workOrderInfo)
			throws PmmsException {
		List<WorkOrderInfo> workOrderList = null; 
		String startDate = workOrderInfo.getStartDate();
		String endDate = workOrderInfo.getEndDate();
		String deptId = workOrderInfo.getDeptId();
		String equipType = workOrderInfo.getEquipType();
		String workOrderType = workOrderInfo.getWorkOrderType();
		StringBuilder sql = new StringBuilder(128);
		sql.append("select a.WO_ID,a.EQUIP_ID,a.WO_TYPE,a.WO_STATUS,a.CREATED_DATE,");
		sql.append("b.MAINT_START_DATE,b.MAINT_END_DATE,");
		sql.append("cast(c.EQUIP_MODEL as varchar) EQUIP_MODEL,");
		sql.append("cast(d.EQUIP_NAME as varchar) EQUIP_NAME");
		sql.append(" from WO_INFO a left join WO_MAINT_INFO b on a.WO_ID=b.WO_ID,EQUIP_INFO c,EQUIP_NAME_INFO d");
		sql.append(" where a.EQUIP_ID=c.EQUIP_ID");
		sql.append(" and c.EQUIP_NAME_ID=d.EQUIP_NAME_ID");
		if(!StringUtils.isEmpty(startDate)&&!StringUtils.isEmpty(endDate)){
			startDate += " 00:00:00";
			endDate += " 23:59:59";
			sql.append(" and a.CREATED_DATE between cast(:startDate as datetime) and cast(:endDate as datetime)");
		}
		if(!StringUtils.isEmpty(deptId)){
			sql.append(" and c.DEPT_ID=:deptId");
		}
		if(!StringUtils.isEmpty(equipType)){
			sql.append(" and d.EQUIP_TYPE=:equipType");
		}
		if(!StringUtils.isEmpty(workOrderType)){
			sql.append(" and a.WO_TYPE=:workOrderType");
		}
		Query query = getSession().createSQLQuery(sql.toString());
		if(!StringUtils.isEmpty(startDate)&&!StringUtils.isEmpty(endDate)){
			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);
		}
		if(!StringUtils.isEmpty(deptId)){
			query.setParameter("deptId", deptId);
		}
		if(!StringUtils.isEmpty(equipType)){
			query.setParameter("equipType", equipType);
		}
		if(!StringUtils.isEmpty(workOrderType)){
			query.setParameter("workOrderType", workOrderType);
		}
		List<?> list = query.list();
		if(!CollectionUtils.isEmpty(list)){
			workOrderList = new ArrayList<WorkOrderInfo>();
			Iterator<?> iterator = list.iterator();
			Date tmpCreatedDate = null;
			while(iterator.hasNext()){
				Object[] obj = (Object[]) iterator.next();
				WorkOrderInfo orderInfo = new WorkOrderInfo();
				orderInfo.setWorkOrderId(Tools.isNull(obj[0],""));
				orderInfo.setEquipId(Tools.isNull(obj[1],""));
				orderInfo.setWorkOrderType(Tools.isNull(obj[2],""));
				orderInfo.setWorkOrderStatus(Tools.isNull(obj[3],""));
				if(!Tools.isNull(obj[4])){
					tmpCreatedDate = (Date) obj[4];
					orderInfo.setStrCreateDate(Tools.formatDate(tmpCreatedDate));
				}
				orderInfo.setStrMaintanceStartDate(Tools.isNull(obj[5],""));
				orderInfo.setStrMaintanceEndDate(Tools.isNull(obj[6],""));
				orderInfo.setEquipModel(Tools.isNull(obj[7],""));
				orderInfo.setEquipName(Tools.isNull(obj[8],""));
				workOrderList.add(orderInfo);
			}
		}
		return workOrderList;
	}

	@Override
	public List<WorkOrderInfo> getWOINProcess(Date startDate, Date endDate)
			throws PmmsException {
		List<WorkOrderInfo> workOrderList = null; 
//		try{
		String startDate1 =	Tools.formatDate(startDate);
		String endDate1 = Tools.formatDate(endDate);
		logger.info("-----------------------");
		logger.info(startDate1);
		logger.info(endDate1);
		logger.info("-----------------------");
		StringBuilder sql = new StringBuilder(128);
		sql.append("select a.WO_ID,a.EQUIP_ID,a.WO_TYPE,a.WO_STATUS,a.CREATED_DATE,");
		sql.append("b.MAINT_START_DATE,b.MAINT_END_DATE,");
		sql.append("cast(c.EQUIP_MODEL as varchar) EQUIP_MODEL,");
		sql.append("cast(d.EQUIP_NAME as varchar) EQUIP_NAME,");
		sql.append("e.SSO,e.FIRSTNAME,e.LASTNAME,a.FAULT_DESCRIPTION");
		sql.append(" from WO_INFO a left join WO_MAINT_INFO b on a.WO_ID=b.WO_ID left join USERS e on a.CREATOR=e.SSO,EQUIP_INFO c,EQUIP_NAME_INFO d");
		sql.append(" where a.EQUIP_ID=c.EQUIP_ID");
		sql.append(" and c.EQUIP_NAME_ID=d.EQUIP_NAME_ID");
		sql.append(" and c.EQUIP_NAME_ID=d.EQUIP_NAME_ID and WO_STATUS IN (1,2)");
		if(!StringUtils.isEmpty(startDate1)&&!StringUtils.isEmpty(endDate1)){
			sql.append(" and a.CREATED_DATE between cast(:startDate as datetime) and cast(:endDate as datetime)");
		}
		Query query = getSession().createSQLQuery(sql.toString());
		if(!StringUtils.isEmpty(startDate1)&&!StringUtils.isEmpty(endDate1)){
			query.setParameter("startDate", startDate1);
			query.setParameter("endDate", endDate1);
		}
		List<?> list = query.list();
		if(!CollectionUtils.isEmpty(list)){
			workOrderList = new ArrayList<WorkOrderInfo>();
			Iterator<?> iterator = list.iterator();
			
			String WOType = "";
			String WOStatus = "";
			Date tmpCreatedDate = null;
			while(iterator.hasNext()){
				Object[] obj = (Object[]) iterator.next();
				WorkOrderInfo orderInfo = new WorkOrderInfo();
				orderInfo.setWorkOrderId(Tools.isNull(obj[0],""));
				orderInfo.setEquipId(Tools.isNull(obj[1],""));
			    WOType = Tools.isNull(obj[2],"");
				orderInfo.setWorkOrderType(WOType);
				if(Constants.WORK_ORDER_TYPE_FALUT.equals(WOType)){
					orderInfo.setWOType(Constants.FAULT_WO);
				}else{
					orderInfo.setWOType(Constants.PLAN_WO);
				}
			    WOStatus = Tools.isNull(obj[3],"");
				orderInfo.setWorkOrderStatus(WOStatus);
				if(Constants.WORK_ORDER_STATUS_SUBMIT.equals(WOStatus)){
					orderInfo.setWOStatus(Constants.WORK_ORDER_STATUS_SUBMIT_DESC);
				}else{
					orderInfo.setWOStatus(Constants.WORK_ORDER_STATUS_MAINT_DESC);
				}
				
				if(!Tools.isNull(obj[4])){
					tmpCreatedDate = (Date) obj[4];
					orderInfo.setStrCreateDate(Tools.formatDate(tmpCreatedDate));
				}
				orderInfo.setStrMaintanceStartDate(Tools.isNull(obj[5],""));
				orderInfo.setStrMaintanceEndDate(Tools.isNull(obj[6],""));
				orderInfo.setEquipModel(Tools.isNull(obj[7],""));
				orderInfo.setEquipName(Tools.isNull(obj[8],""));
				orderInfo.setSSO(Tools.isNull(obj[9], ""));
//				orderInfo.setFirtName(Tools.isNull(obj[10], ""));
//				orderInfo.setLastName(Tools.isNull(obj[11], ""));
				orderInfo.setName(Tools.isNull(obj[10], "")+" "+Tools.isNull(obj[11], ""));
				orderInfo.setFaultDescription(Tools.isNull(obj[12], ""));
				workOrderList.add(orderInfo);
			}
		}
//		
//		}catch(Exception e){
//			e.printStackTrace();
//		}
		return workOrderList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FaultTypeInfo> getFaultTypes() throws PmmsException {
		List<FaultTypeInfo> faultTypeInfoList = null;
		String hql = "from faultTypeInfo";
		Query query = getSession().createQuery(hql);
		faultTypeInfoList = query.list();
		return faultTypeInfoList;
	}

	@Override
	public List<WorkOrderInfo> findUnfinishedWO(String firstDayofYear,
			String planMonthEnd) throws PmmsException {
		List<WorkOrderInfo> woList = null;
		StringBuilder sql = new StringBuilder(128);
		sql.append("select a.WO_ID,a.WO_TYPE,a.CREATED_DATE,a.PLAN_START_MONTH,a.EQUIP_ID,cast(b.EQUIP_MODEL as varchar) EQUIP_MODEL ");
		sql.append("from WO_INFO a left join EQUIP_INFO b on a.EQUIP_ID=b.EQUIP_ID ");
		sql.append("where a.WO_STATUS in (1,2) ");
		if(!StringUtils.isEmpty(firstDayofYear) && !StringUtils.isEmpty(planMonthEnd)){
			sql.append("and a.CREATED_DATE between cast(:startDate as datetime) and cast(:endDate as datetime) and a.WO_TYPE='1' ");
			sql.append(" or a.PLAN_START_MONTH between cast(:startDate as datetime) and cast(:endDate as datetime) and a.WO_TYPE='2' and a.WO_STATUS in (1,2) ");
		}
		Query query = getSession().createSQLQuery(sql.toString());
		if(!StringUtils.isEmpty(firstDayofYear) && !StringUtils.isEmpty(planMonthEnd)){
			query.setParameter("startDate", firstDayofYear);
			query.setParameter("endDate", planMonthEnd);
		}
		List<?> list = query.list();
		if(!CollectionUtils.isEmpty(list)){
			woList = new ArrayList<WorkOrderInfo>();
			Iterator<?> itor = list.iterator();
			WorkOrderInfo workOrderInfo = null;
			Object[] obj = null;
			Date tempCreatedDate = null;
			Date tempPlanStartMonth = null;
			while(itor.hasNext()){
			    obj = (Object[]) itor.next();
			    workOrderInfo = new WorkOrderInfo();
			    workOrderInfo.setWorkOrderId(Tools.isNull(obj[0], ""));
			    workOrderInfo.setWorkOrderType(Tools.isNull(obj[1], ""));
			    if(!Tools.isNull(obj[2])){
			    	tempCreatedDate = (Date) obj[2];
			    	workOrderInfo.setStrCreateDate(Tools.formatDate(tempCreatedDate));
			    }
			    if(!Tools.isNull(obj[3])){
			    	tempPlanStartMonth = (Date) obj[3];
			    	workOrderInfo.setStrPlanStartMonth(Tools.formatDate(tempPlanStartMonth));
			    }
			    workOrderInfo.setEquipId(Tools.isNull(obj[4], ""));
			    workOrderInfo.setEquipModel(Tools.isNull(obj[5], ""));
			    woList.add(workOrderInfo);
			}
		}
		return woList;
	}
}
