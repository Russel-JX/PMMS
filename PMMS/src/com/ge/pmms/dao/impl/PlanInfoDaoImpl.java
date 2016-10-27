package com.ge.pmms.dao.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ge.pmms.base.PmmsException;
import com.ge.pmms.base.impl.BaseDaoImpl;
import com.ge.pmms.dao.PlanInfoDao;
import com.ge.pmms.po.PlanInfo;
import com.ge.pmms.po.PlanMaintInfo;
import com.ge.pmms.utils.Constants;
import com.ge.pmms.utils.Tools;

@Repository
public class PlanInfoDaoImpl extends BaseDaoImpl<PlanInfo> implements PlanInfoDao {

	public PlanInfo findPlanByPlanId(String planId)throws PmmsException{
		String str = "from planInfo a where a.plan_id=:planId";
		Query query = getSession().createQuery(str);
		query.setParameter("planId", planId);
		@SuppressWarnings("unchecked")
		List<PlanInfo> list = query.list();
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public List<PlanInfo> findPlan(String planYear)
			throws PmmsException {
		String str = "from planInfo a where a.maint_year=:planYear";
		Query query = getSession().createQuery(str);
		query.setParameter("planYear", planYear);
		@SuppressWarnings("unchecked")
		List<PlanInfo> list = query.list();
		return list;
	}
	
	public List<PlanInfo> findRichPlan(String planYear, String equipType)
			throws PmmsException, ParseException {
		StringBuilder hql = new StringBuilder();
		
		hql.append( "select a.id,a.plan_id,a.equip_id,a.maint_year, ");
		hql.append( "a.maint_jan,a.maint_feb,a.maint_mar,a.maint_apr,a.maint_may,a.maint_jun,a.maint_jul,a.maint_agu,a.maint_sep,a.maint_oct,a.maint_nov,a.maint_dec,");
		hql.append( "a.creator,a.created_date,a.updater,a.last_updated_date,a.remark,b.equipModel,c.equipmentName,c.equipmentNameId,c.equipType,d.deptId,d.deptNm ");
		hql.append( " from planInfo a ,equipInfo b,equipNmInfo c,deptInfo d ");
		hql.append( "where a.equip_id = b.equipId and b.equipNmId = c.equipmentNameId and b.deptId = d.deptId ");
		
		if(!StringUtils.isEmpty(planYear)){
			hql.append( " and a.maint_year=:planYear ");
		}
		if(!StringUtils.isEmpty(equipType)){
			hql.append( " and c.equipType=:equipType ");
		}
		Query query = getSession().createQuery(hql.toString());
		if(!StringUtils.isEmpty(planYear)){
			query.setParameter("planYear", planYear);
		}
		if(!StringUtils.isEmpty(equipType)){
			query.setParameter("equipType", equipType);
		}
		
		List<?> listResult = query.list();
		
		List<PlanInfo> list = new ArrayList<PlanInfo>();
		PlanInfo planInfo;
		if(!CollectionUtils.isEmpty(listResult)){
			Iterator<?> ite = listResult.iterator();
			while(ite.hasNext()){
				Object[] obj = (Object[])ite.next();
				planInfo = new PlanInfo();
				planInfo.setId(Integer.parseInt(obj[0].toString()));
				planInfo.setPlan_id(obj[1].toString());
				planInfo.setEquip_id(obj[2].toString());
//				planInfo.setPlan_type(obj[3].toString());
				planInfo.setMaint_year(obj[3].toString());
				planInfo.setMaint_jan(obj[4].toString());
				planInfo.setMaint_feb(obj[5].toString());
				planInfo.setMaint_mar(obj[6].toString());
				planInfo.setMaint_apr(obj[7].toString());
				planInfo.setMaint_may(obj[8].toString());
				planInfo.setMaint_jun(obj[9].toString());
				planInfo.setMaint_jul(obj[10].toString());
				planInfo.setMaint_agu(obj[11].toString());
				planInfo.setMaint_sep(obj[12].toString());
				planInfo.setMaint_oct(obj[13].toString());
				planInfo.setMaint_nov(obj[14].toString());
				planInfo.setMaint_dec(obj[15].toString());
				
				planInfo.setCreator(Tools.isNull(obj[16])?"":obj[16].toString());
				planInfo.setCreated_date(Tools.parseToDate(obj[17].toString(), "yyyy-MM-dd HH:mm:ss"));
				planInfo.setUpdater(Tools.isNull(obj[18])?"":obj[18].toString());
				planInfo.setLast_updated_date(Tools.isNull(obj[19])?null:Tools.parseToDate(obj[19].toString(), "yyyy-MM-dd HH:mm:ss"));
				planInfo.setRemark(Tools.isNull(obj[20])?"":obj[20].toString());
				planInfo.setEquipModel(Tools.isNull(obj[21])?"":obj[21].toString());
				planInfo.setEquipmentName(obj[22].toString());
				planInfo.setEquipmentNameId(obj[23].toString());
				planInfo.setEquipType(Tools.isNull(obj[24])?"":obj[24].toString());
				planInfo.setDeptId(Tools.isNull(obj[25])?"":obj[25].toString());
				planInfo.setDeptNm(Tools.isNull(obj[26])?"":obj[26].toString());
				
				list.add(planInfo);
			}
		}
		
		
		return list;
	}
	
	//查询当前月份的保养计划
	public List<PlanMaintInfo> findPlanForCurrMonth(String firstDayofYear,String planMonthStart, String planMonthEnd)throws PmmsException{
		StringBuilder sql = new StringBuilder();
		
		sql.append( "select temp.wo_status,temp.equip_name,temp.equipModel,temp.equip_id,temp.planMonth, ");
		sql.append( "wo_maint.mechanic,users.firstname,users.lastname,temp.plan_start_month,wo_maint.maint_start_date,wo_maint.maint_end_date ");
		sql.append( "from wo_maint_info wo_maint right join ");
		sql.append( " (select wo.wo_id as wo_id, wo.wo_status as wo_status,equip_name.equip_name as equip_name,cast(equip.equip_model as varchar) as equipModel,  ");
		sql.append( " equip.equip_id as equip_id,DATEPART(MONTH,wo.plan_start_month) as planMonth,wo.plan_start_month as plan_start_month ");
		sql.append( " from equip_name_info equip_name, equip_info equip,wo_info wo ");
		sql.append( " where equip_name.equip_name_id=equip.equip_name_id and equip.equip_id=wo.equip_id and plan_id is not null and plan_id !='' ");
		sql.append( " and wo.plan_start_month between :planMonthStart and :planMonthEnd and wo.wo_status in(:woClosedStatus) ");
		
		sql.append( " union all ");
		sql.append( " select wo.wo_id as wo_id, wo.wo_status as wo_status,equip_name.equip_name as equip_name,cast(equip.equip_model as varchar) as equipModel, ");
		sql.append( " equip.equip_id as equip_id,DATEPART(MONTH,wo.plan_start_month) as planMonth,wo.plan_start_month as plan_start_month ");
		sql.append( " from equip_name_info equip_name, equip_info equip,wo_info wo ");
		sql.append( " where equip_name.equip_name_id=equip.equip_name_id and equip.equip_id=wo.equip_id and plan_id is not null and plan_id !='' ");
		sql.append( " and wo.plan_start_month between :firstDayofYear and :planMonthEnd and wo.wo_status in(:woSubmitStatus,:woMaintStatus) ");
		
		sql.append( " ) as temp ");
		sql.append( " on wo_maint.wo_id = temp.wo_id ");
		sql.append( " left join users on wo_maint.mechanic = users.sso ");
		
		Query query = getSession().createSQLQuery(sql.toString());
		query.setParameter("firstDayofYear", firstDayofYear);
		query.setParameter("planMonthStart", planMonthStart);
		query.setParameter("planMonthEnd", planMonthEnd);
		query.setParameter("woClosedStatus", Constants.WORK_ORDER_STATUS_ClOSED);
		query.setParameter("woSubmitStatus", Constants.WORK_ORDER_STATUS_SUBMIT);
		query.setParameter("woMaintStatus", Constants.WORK_ORDER_STATUS_MAINT);
		
		List<?> listResult = query.list();
		
		List<PlanMaintInfo> list = new ArrayList<PlanMaintInfo>();
		PlanMaintInfo planMaintInfo;
		if(!CollectionUtils.isEmpty(listResult)){
			Iterator<?> ite = listResult.iterator();
			while(ite.hasNext()){
				Object[] obj = (Object[])ite.next();
				planMaintInfo = new PlanMaintInfo();
				
				planMaintInfo.setWoStatus(obj[0].toString());
				planMaintInfo.setEquipName(obj[1].toString());
				planMaintInfo.setEquipModel(obj[2].toString());
				planMaintInfo.setEquipId(obj[3].toString());
				planMaintInfo.setPlanMonth(obj[4].toString());
				planMaintInfo.setMechanicSSO(Tools.isNull(obj[5])?"":obj[5].toString());
				planMaintInfo.setFirstname(Tools.isNull(obj[6])?"":obj[6].toString());
				planMaintInfo.setLastname(Tools.isNull(obj[7])?"":obj[7].toString());
				planMaintInfo.setPlan_start_month(obj[8].toString());
				planMaintInfo.setMaint_start_date(Tools.isNull(obj[9])?"":obj[9].toString());
				planMaintInfo.setMaint_end_date(Tools.isNull(obj[10])?"":obj[10].toString());
				
				list.add(planMaintInfo);
			}
		}
		
		
		return list;
	}
	
	public PlanInfo addSinglePlan(PlanInfo planInfo) throws PmmsException {
		try{
			save(planInfo);
		}catch(Exception e){
			e.printStackTrace();
		}
		return planInfo;
	}

	public PlanInfo modifySinglePlan(PlanInfo planInfo) throws PmmsException {
		try{
			update(planInfo);
		}catch(Exception e){
			e.printStackTrace();
		}
		return planInfo;
	}

	public int removeSinglePlan(String planId) throws PmmsException {
		String hql = "delete from planInfo where plan_id =:planId";
		Query query = getSession().createQuery(hql);
		query.setParameter("planId", planId);
		return query.executeUpdate();
	}
	
	public long getPlansNumber(String planYear)throws PmmsException{
		String hql = "select count(*) from planInfo where maint_year =:planYear";
		Query query = getSession().createQuery(hql);
		query.setParameter("planYear", planYear);
		Object obj = query.uniqueResult();
		if(Tools.isNull(obj)){
			return 0;
		}else{
			return (Long)obj;
		}
	}
	
	public void removeAllPlansByYear(String planYear)throws PmmsException{
		String hql = "delete from planInfo where maint_year =:planYear";
		Query query = getSession().createQuery(hql);
		query.setParameter("planYear", planYear);
		query.executeUpdate();
	}
	
	public List<PlanInfo> getMonthPlans(String planMonth,String planYear)throws PmmsException{
		String hql = "from planInfo where "+planMonth+" !=',,' and maint_year=:planYear";
		Query query = getSession().createQuery(hql);
		query.setParameter("planYear", planYear);
		@SuppressWarnings("unchecked")
		List<PlanInfo> list = query.list();
		return list;
	}
	
	//验证本条计划是否是未完成的计划
//	public PlanInfo getUnfinishedWO(String plan_id)throws Exception{
//		StringBuilder hql = new StringBuilder();
//		hql.append( "select a.id,a.plan_id,a.equip_id,a.plan_type,a.maint_year, ");
//		hql.append( "a.maint_jan,a.maint_feb,a.maint_mar,a.maint_apr,a.maint_may,a.maint_jun,a.maint_jul,a.maint_agu,a.maint_sep,a.maint_oct,a.maint_nov,a.maint_dec,");
//		hql.append( "a.creator,a.created_date,a.updater,a.last_updated_date,a.remark,b.workOrderId ");
//		hql.append( " from planInfo a,workOrderInfo b ");
//		hql.append( " where a.plan_id=:plan_id and a.plan_id=b.planId and b.workOrderStatus not in(1,2) ");
//		Query query = getSession().createQuery(hql.toString());
//		query.setParameter("plan_id", plan_id);
//		
//		List<?> listResult = query.list();
//		List<PlanInfo> list = new ArrayList<PlanInfo>();
//		PlanInfo planInfo;
//		if(!CollectionUtils.isEmpty(listResult)){
//			Iterator<?> ite = listResult.iterator();
//			while(ite.hasNext()){
//				Object[] obj = (Object[])ite.next();
//				planInfo = new PlanInfo();
//				planInfo.setId(Integer.parseInt(obj[0].toString()));
//				planInfo.setPlan_id(obj[1].toString());
//				planInfo.setEquip_id(obj[2].toString());
//				planInfo.setPlan_type(obj[3].toString());
//				planInfo.setMaint_year(obj[4].toString());
//				planInfo.setMaint_jan(obj[5].toString());
//				planInfo.setMaint_feb(obj[6].toString());
//				planInfo.setMaint_mar(obj[7].toString());
//				planInfo.setMaint_apr(obj[8].toString());
//				planInfo.setMaint_may(obj[9].toString());
//				planInfo.setMaint_jun(obj[10].toString());
//				planInfo.setMaint_jul(obj[11].toString());
//				planInfo.setMaint_agu(obj[12].toString());
//				planInfo.setMaint_sep(obj[13].toString());
//				planInfo.setMaint_oct(obj[14].toString());
//				planInfo.setMaint_nov(obj[15].toString());
//				planInfo.setMaint_dec(obj[16].toString());
//				
//				planInfo.setCreator(Tools.isNull(obj[17])?"":obj[17].toString());
//				planInfo.setCreated_date(Tools.parseToDate(obj[18].toString(), "yyyy-MM-dd HH:mm:ss"));
//				planInfo.setUpdater(Tools.isNull(obj[19])?"":obj[19].toString());
//				planInfo.setLast_updated_date(Tools.isNull(obj[20])?null:Tools.parseToDate(obj[20].toString(), "yyyy-MM-dd HH:mm:ss"));
//				planInfo.setRemark(Tools.isNull(obj[21])?"":obj[21].toString());
//				planInfo.setWorkOrderId(obj[22].toString());
//				
//				list.add(planInfo);
//			}
//		}
//		if(list.size()>0){
//			return list.get(0);
//		}
//		return null;
//	}
	
}
