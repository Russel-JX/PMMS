package com.ge.pmms.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ge.pmms.base.PmmsException;
import com.ge.pmms.base.impl.BaseDaoImpl;
import com.ge.pmms.dao.ChartDao;
import com.ge.pmms.po.ChartInfo;
import com.ge.pmms.po.ChartInfoParam;
import com.ge.pmms.utils.Constants;
import com.ge.pmms.utils.Tools;

@Repository
public class ChartInfoDaoImpl extends BaseDaoImpl<ChartInfo> implements ChartDao {

	//根保养养年份，查询此年份的PM完成率
	public List<ChartInfo> findPMWONumber(String fromYear,String toYear,String woStatus)throws PmmsException{
		
		StringBuilder hql = new StringBuilder();
		
		hql.append( "select equip.dept_id,cast(dept.dept_name as varchar) dept_name,wo.plan_start_month,count(*) from EQUIP_INFO equip,WO_INFO wo,DEPT_INFO dept ");
		hql.append( "where  equip.equip_id=wo.equip_id and dept.dept_id=equip.dept_id and ");
		hql.append( " wo.plan_id is not null and wo.plan_id !='' and ");
		hql.append( " wo.plan_start_month between :fromYear and :toYear ");
		
		if(!StringUtils.isEmpty(woStatus)){
			hql.append( " and wo.wo_status=:woStatus ");
		}
		hql.append( " group by equip.dept_id, dept.dept_name,wo.plan_start_month ");
		hql.append( " order by equip.dept_id ");
		Query query = getSession().createSQLQuery(hql.toString());
		query.setParameter("fromYear", fromYear);
		query.setParameter("toYear", toYear);
		if(!StringUtils.isEmpty(woStatus)){
			query.setParameter("woStatus", woStatus);
		}
		
		List<?> listResult = query.list();
		
		List<ChartInfo> list = new ArrayList<ChartInfo>();
		ChartInfo chartInfo;
		if(!CollectionUtils.isEmpty(listResult)){
			Iterator<?> ite = listResult.iterator();
			while(ite.hasNext()){
				Object[] obj = (Object[])ite.next();
				chartInfo = new ChartInfo();
				chartInfo.setDeptId(obj[0].toString());
				chartInfo.setDeptNm(obj[1].toString());
				chartInfo.setMaintMonth(String.valueOf(((Date)obj[2]).getMonth()+1));
				chartInfo.setCount(Tools.isNull(obj[3])?0.0:Double.parseDouble(String.valueOf(obj[3])));
				list.add(chartInfo);
			}
		}
		return list;
	}
	
	//根据设备编号、年份和计划工单完成状态，查询单个设备的年度计划工单数量。
	public List<ChartInfo> findSinglePMWONumber(String equipId,String fromYear,String toYear,String woStatus)throws PmmsException{
		StringBuilder hql = new StringBuilder();
		hql.append( "select count(wo.plan_start_month) num,DATEPART(month,wo.plan_start_month) maintMonth from WO_INFO wo");
		hql.append( " where wo.plan_id is not null and wo.plan_id !='' and ");
		hql.append( " wo.plan_start_month between :fromYear and :toYear and ");
		hql.append( " wo.equip_id=:equipId ");
		
		if(!StringUtils.isEmpty(woStatus)){
			hql.append( " and wo.wo_status=:woStatus ");
		}
		hql.append( " group by wo.plan_start_month ");
		Query query = getSession().createSQLQuery(hql.toString());
		query.setParameter("equipId", equipId);
		query.setParameter("fromYear", fromYear);
		query.setParameter("toYear", toYear);
		if(!StringUtils.isEmpty(woStatus)){
			query.setParameter("woStatus", woStatus);
		}
		
		List<?> listResult = query.list();
		
		List<ChartInfo> list = new ArrayList<ChartInfo>();
		ChartInfo chartInfo;
		if(!CollectionUtils.isEmpty(listResult)){
			Iterator<?> ite = listResult.iterator();
			while(ite.hasNext()){
				Object[] obj = (Object[])ite.next();
				chartInfo = new ChartInfo();
				chartInfo.setCount(Tools.isNull(obj[0])?0.0:Double.parseDouble(String.valueOf(obj[0])));
				chartInfo.setMaintMonth(String.valueOf(obj[1]));
				list.add(chartInfo);
			}
		}
		return list;
	}
	
	//按年分组，查询五大部门的计划工单数量。
	public List<ChartInfo> findPMWONumberByYear(String fromYear,String toYear,String woStatus)throws PmmsException{
		StringBuilder hql = new StringBuilder();
		hql.append( "select equip.dept_id,cast(dept.dept_name as varchar) dept_name,DATEPART(year,wo.plan_start_month) planYear,count(*) num from EQUIP_INFO equip,WO_INFO wo,DEPT_INFO dept");
		hql.append( " where  equip.equip_id=wo.equip_id and dept.dept_id=equip.dept_id and ");
		hql.append( " wo.plan_id is not null and wo.plan_id !='' and ");
		hql.append( " wo.plan_start_month between :fromYear and :toYear ");
		
		if(!StringUtils.isEmpty(woStatus)){
			hql.append( " and wo.wo_status=:woStatus ");
		}
		hql.append( " group by equip.dept_id, dept.dept_name,DATEPART(year,wo.plan_start_month) ");
		hql.append( " order by equip.dept_id  ");
		Query query = getSession().createSQLQuery(hql.toString());
		query.setParameter("fromYear", fromYear);
		query.setParameter("toYear", toYear);
		if(!StringUtils.isEmpty(woStatus)){
			query.setParameter("woStatus", woStatus);
		}
		List<?> listResult = query.list();
		
		List<ChartInfo> list = new ArrayList<ChartInfo>();
		ChartInfo chartInfo;
		if(!CollectionUtils.isEmpty(listResult)){
			Iterator<?> ite = listResult.iterator();
			while(ite.hasNext()){
				Object[] obj = (Object[])ite.next();
				chartInfo = new ChartInfo();
				chartInfo.setDeptId(obj[0].toString());
				chartInfo.setDeptNm(obj[1].toString());
				chartInfo.setMaintMonth(String.valueOf(obj[2]));
				chartInfo.setCount(Tools.isNull(obj[3])?0.0:Double.parseDouble(String.valueOf(obj[3])));
				list.add(chartInfo);
			}
		}
		return list;
	}
	
	//查询某一年的故障维修工单
	public List<ChartInfo> findMaintRecordByYear(String miantStartDate,String miantEndDate)throws PmmsException{
		StringBuilder hql = new StringBuilder();
		hql.append( "select dept.dept_id,cast(dept.dept_name as varchar) dept_name,equip.equip_id,wo_maint.maint_start_date,wo_maint.maint_end_date");
		hql.append( " from wo_maint_info wo_maint,wo_info wo,equip_info equip,dept_info dept  ");
		hql.append( " where wo_maint.wo_id = wo.wo_id and wo.equip_id = equip.equip_id and dept.dept_id = equip.dept_id and  ");
		hql.append( " wo_maint.maint_start_date<:miantEndDate and wo_maint.maint_end_date>:miantStartDate ");
		hql.append( " union all ");
		hql.append( "select dept.dept_id,dept.dept_name,equip.equip_id,wo_maint.maint_start_date,wo_maint.maint_end_date");
		hql.append( " from wo_maint_info wo_maint,wo_info wo,equip_info equip,dept_info dept  ");
		hql.append( " where wo_maint.wo_id = wo.wo_id and wo.equip_id = equip.equip_id and dept.dept_id = equip.dept_id and  ");
		hql.append( " wo_maint.maint_start_date<:miantEndDate and wo_maint.maint_end_date is null ");
		
		Query query = getSession().createSQLQuery(hql.toString());
		query.setParameter("miantStartDate", miantStartDate);
		query.setParameter("miantEndDate", miantEndDate);
		List<?> listResult = query.list();
		
		List<ChartInfo> list = new ArrayList<ChartInfo>();
		ChartInfo chartInfo;
		if(!CollectionUtils.isEmpty(listResult)){
			Iterator<?> ite = listResult.iterator();
			while(ite.hasNext()){
				Object[] obj = (Object[])ite.next();
				chartInfo = new ChartInfo();
				chartInfo.setDeptId(obj[0].toString());
				chartInfo.setDeptNm(obj[1].toString());
				chartInfo.setEquipId(obj[2].toString());
				chartInfo.setMaintStartDate((Date)obj[3]);
				chartInfo.setMaintEndDate(Tools.isNull(obj[4])?(new Date()):(Date)obj[4]);//维修结束时间为null,则是当前系统时间
				
				list.add(chartInfo);
			}
		}
		return list;
	}
	
	//查询单个设备的MTTR
	public List<ChartInfo> findSingleMaintRecordByYear(String equipId,String miantStartDate,String miantEndDate)throws PmmsException{
		StringBuilder hql = new StringBuilder();
		hql.append( "select equip.equip_id,wo_maint.maint_start_date,wo_maint.maint_end_date");
		hql.append( " from wo_maint_info wo_maint,wo_info wo,equip_info equip  ");
		hql.append( " where wo_maint.wo_id = wo.wo_id and wo.equip_id = equip.equip_id and  ");
		hql.append( " wo_maint.maint_start_date<:miantEndDate and wo_maint.maint_end_date>:miantStartDate ");
		hql.append( " and wo.equip_id =:equipId ");
		hql.append( " union all ");
		hql.append( "select equip.equip_id,wo_maint.maint_start_date,wo_maint.maint_end_date");
		hql.append( " from wo_maint_info wo_maint,wo_info wo,equip_info equip  ");
		hql.append( " where wo_maint.wo_id = wo.wo_id and wo.equip_id = equip.equip_id and  ");
		hql.append( " wo_maint.maint_start_date<:miantEndDate and wo_maint.maint_end_date is null ");
		hql.append( " and wo.equip_id =:equipId ");
		
		Query query = getSession().createSQLQuery(hql.toString());
		query.setParameter("equipId", equipId);
		query.setParameter("miantStartDate", miantStartDate);
		query.setParameter("miantEndDate", miantEndDate);
		List<?> listResult = query.list();
		
		List<ChartInfo> list = new ArrayList<ChartInfo>();
		ChartInfo chartInfo;
		if(!CollectionUtils.isEmpty(listResult)){
			Iterator<?> ite = listResult.iterator();
			while(ite.hasNext()){
				Object[] obj = (Object[])ite.next();
				chartInfo = new ChartInfo();
				chartInfo.setEquipId(obj[0].toString());
				chartInfo.setMaintStartDate((Date)obj[1]);
				chartInfo.setMaintEndDate(Tools.isNull(obj[2])?(new Date()):(Date)obj[2]);
				
				list.add(chartInfo);
			}
		}
		return list;
	}
	
	//按部门，查询近5年的MTTR
	public List<ChartInfo> findMaintRecordForYears(String fromYear)throws PmmsException{
		StringBuilder hql = new StringBuilder();
		hql.append( "select dept.dept_id,cast(dept.dept_name as varchar) dept_name,equip.equip_id,wo_maint.maint_start_date,wo_maint.maint_end_date");
		hql.append( " from wo_maint_info wo_maint,wo_info wo,equip_info equip,dept_info dept ");
		hql.append( " where wo_maint.wo_id = wo.wo_id and wo.equip_id = equip.equip_id and dept.dept_id = equip.dept_id and ");
		hql.append( " wo_maint.maint_end_date > :fromYear ");
		hql.append( " union all ");
		hql.append( "select dept.dept_id,cast(dept.dept_name as varchar) dept_name,equip.equip_id,wo_maint.maint_start_date,wo_maint.maint_end_date");
		hql.append( " from wo_maint_info wo_maint,wo_info wo,equip_info equip,dept_info dept ");
		hql.append( " where wo_maint.wo_id = wo.wo_id and wo.equip_id = equip.equip_id and dept.dept_id = equip.dept_id and ");
		hql.append( " wo_maint.maint_end_date is null ");
		
		Query query = getSession().createSQLQuery(hql.toString());
		query.setParameter("fromYear", fromYear);
//		query.setParameter("toYear", toYear);
		List<?> listResult = query.list();
		
		List<ChartInfo> list = new ArrayList<ChartInfo>();
		ChartInfo chartInfo;
		if(!CollectionUtils.isEmpty(listResult)){
			Iterator<?> ite = listResult.iterator();
			while(ite.hasNext()){
				Object[] obj = (Object[])ite.next();
				chartInfo = new ChartInfo();
				chartInfo.setDeptId(obj[0].toString());
				chartInfo.setDeptNm(obj[1].toString());
				chartInfo.setEquipId(obj[2].toString());
				chartInfo.setMaintStartDate((Date)obj[3]);
				chartInfo.setMaintEndDate(Tools.isNull(obj[4])?(new Date()):(Date)obj[4]);
				
				list.add(chartInfo);
			}
		}
		return list;
	}
	
	public List<ChartInfo> getSpNumBySpId(ChartInfoParam  chartPara){
		StringBuilder sql = new StringBuilder();
		sql.append("select SPARE_PART_ID,SPARE_PART_NAME,sum(amount) total,DATEPART(month, CREATE_MONTH) from spare_part_trans_info ");
		sql.append("where SPARE_PART_ID=:spId and TRANS_TYPE='2' and DATEPART(year, CREATE_MONTH)=:currYear group by SPARE_PART_ID,CREATE_MONTH,SPARE_PART_NAME order by CREATE_MONTH");
		Query query = getSession().createSQLQuery(sql.toString());
		query.setParameter("spId", chartPara.getSparePartId());
		//Calendar a=Calendar.getInstance();
		query.setParameter("currYear",chartPara.getYear()); 
		List<?> rsList = query.list();
		List<ChartInfo> list = new ArrayList<ChartInfo>();
		ChartInfo chartInfo;
		if(!CollectionUtils.isEmpty(rsList)){
			Iterator<?> ite = rsList.iterator();
			while(ite.hasNext()){
				Object[] obj = (Object[])ite.next();
				chartInfo = new ChartInfo();
				chartInfo.setSparePartId(String.valueOf(obj[0]));
				chartInfo.setSparePartName(String.valueOf(obj[1]));
				chartInfo.setCount(Tools.isNull(obj[2])?0.0:Double.parseDouble(String.valueOf(obj[2])));
				chartInfo.setMaintMonth(String.valueOf(obj[3]));
				list.add(chartInfo);
			}
		}
		return list;
	}
	
	public List<ChartInfo> getSpNumBydept(ChartInfoParam  chartPara){
		StringBuilder sql = new StringBuilder();
		sql.append("select DATEPART(month, CREATE_MONTH) month,d.DEPT_ID,d.dept_name,sum(a.amount) total ");
		sql.append("from spare_part_trans_info a,wo_info b,EQUIP_INFO c,DEPT_INFO d ");
		sql.append("where a.TRANS_TYPE='2' and a.WO_ID=b.WO_ID and b.EQUIP_ID=c.EQUIP_ID and c.DEPT_ID=d.DEPT_ID and DATEPART(year, CREATE_MONTH)=:currYear ");
		sql.append("group by d.DEPT_ID,d.dept_name,a.CREATE_MONTH ");
		Query query = getSession().createSQLQuery(sql.toString());
	//	Calendar a=Calendar.getInstance();
		query.setParameter("currYear", chartPara.getYear()); 
		List<?> rsList = query.list();
		List<ChartInfo> list = new ArrayList<ChartInfo>();
		ChartInfo chartInfo;
		if(!CollectionUtils.isEmpty(rsList)){
			Iterator<?> ite = rsList.iterator();
			while(ite.hasNext()){
				Object[] obj = (Object[])ite.next();
				chartInfo = new ChartInfo();
				chartInfo.setMaintMonth(String.valueOf(obj[0]));
				chartInfo.setDeptId(String.valueOf(obj[1]));
				chartInfo.setDeptNm(String.valueOf(obj[2]));
				chartInfo.setCount(Tools.isNull(obj[3])?0.0:Double.parseDouble(String.valueOf(obj[3])));
				list.add(chartInfo);
			}
		}
		return list;
	}
	
	public List<ChartInfo> getTotalSpNumByMonth(ChartInfoParam  chartPara){
		StringBuilder sql = new StringBuilder();
		sql.append("select DATEPART(month, CREATE_MONTH) month,sum(a.amount) total ");
		sql.append(" from spare_part_trans_info a,wo_info b,EQUIP_INFO c,DEPT_INFO d ");
		sql.append(" where a.TRANS_TYPE='2' and a.WO_ID=b.WO_ID and b.EQUIP_ID=c.EQUIP_ID and c.DEPT_ID=d.DEPT_ID and DATEPART(year, CREATE_MONTH)=:year ");
		sql.append(" group by a.CREATE_MONTH  ");
		Query query = getSession().createSQLQuery(sql.toString());
	//	Calendar a=Calendar.getInstance();
		query.setParameter("year", chartPara.getYear()); 
		List<?> rsList = query.list();
		List<ChartInfo> list = new ArrayList<ChartInfo>();
		ChartInfo chartInfo;
		if(!CollectionUtils.isEmpty(rsList)){
			Iterator<?> ite = rsList.iterator();
			while(ite.hasNext()){
				Object[] obj = (Object[])ite.next();
				chartInfo = new ChartInfo();
				chartInfo.setMaintMonth(String.valueOf(obj[0]));
				chartInfo.setCount(Tools.isNull(obj[1])?0.0:Double.parseDouble(String.valueOf(obj[1])));
				chartInfo.setDeptNm(Constants.DEPT_AVG_NM);
				list.add(chartInfo);
			}
		}
		return list;
	}
	
	public List<ChartInfo> getSpNumByYear(){
		StringBuilder sql = new StringBuilder();
		sql.append("select  DEPT_ID,dept_name,tmp_year,sum(total) total from ");
		sql.append("(select a.CREATE_MONTH,DATEPART(month, CREATE_MONTH) month,DATEPART(year, CREATE_MONTH) tmp_year,d.DEPT_ID,d.dept_name,sum(a.amount) total ");
		sql.append("from spare_part_trans_info a,wo_info b,EQUIP_INFO c,DEPT_INFO d  ");
		sql.append("where a.TRANS_TYPE='2' and a.WO_ID=b.WO_ID and b.EQUIP_ID=c.EQUIP_ID and c.DEPT_ID=d.DEPT_ID ");
		sql.append("group by d.DEPT_ID,d.dept_name,a.CREATE_MONTH) as temp  ");
		sql.append("group by DEPT_ID,dept_name,tmp_year ");
		Query query = getSession().createSQLQuery(sql.toString());
		List<?> rsList = query.list();
		List<ChartInfo> list = new ArrayList<ChartInfo>();
		ChartInfo chartInfo;
		if(!CollectionUtils.isEmpty(rsList)){
			Iterator<?> ite = rsList.iterator();
			while(ite.hasNext()){
				Object[] obj = (Object[])ite.next();
				chartInfo = new ChartInfo();
				chartInfo.setMaintMonth(String.valueOf(obj[2]));
				chartInfo.setDeptId(String.valueOf(obj[0]));
				chartInfo.setDeptNm(String.valueOf(obj[1]));
				chartInfo.setCount(Tools.isNull(obj[3])?0.0:Double.parseDouble(String.valueOf(obj[3])));
				list.add(chartInfo);
			}
		}
		return list;
	}
	
	public List<ChartInfo> getTotalSpNumForYear(){
		StringBuilder sql = new StringBuilder();
		sql.append("select  tmp_year,sum(total) total from ");
		sql.append("(select a.CREATE_MONTH,DATEPART(month, CREATE_MONTH) month,DATEPART(year, CREATE_MONTH) tmp_year,d.DEPT_ID,d.dept_name,sum(a.amount) total ");
		sql.append(" from spare_part_trans_info a,wo_info b,EQUIP_INFO c,DEPT_INFO d  ");
		sql.append(" where a.TRANS_TYPE='2' and a.WO_ID=b.WO_ID and b.EQUIP_ID=c.EQUIP_ID and c.DEPT_ID=d.DEPT_ID  ");
		sql.append(" group by d.DEPT_ID,d.dept_name,a.CREATE_MONTH) as temp  ");
		sql.append(" group by tmp_year ");
		Query query = getSession().createSQLQuery(sql.toString());
		List<?> rsList = query.list();
		List<ChartInfo> list = new ArrayList<ChartInfo>();
		ChartInfo chartInfo;
		if(!CollectionUtils.isEmpty(rsList)){
			Iterator<?> ite = rsList.iterator();
			while(ite.hasNext()){
				Object[] obj = (Object[])ite.next();
				chartInfo = new ChartInfo();
				chartInfo.setMaintMonth(String.valueOf(obj[0]));
				chartInfo.setCount(Tools.isNull(obj[1])?0.0:Double.parseDouble(String.valueOf(obj[1])));
				chartInfo.setDeptNm(Constants.DEPT_AVG_NM);
				list.add(chartInfo);
			}
		}
		return list;
	}
	
	public List<ChartInfo> getSpFeeBySpId(ChartInfoParam  chartPara){
		StringBuilder sql = new StringBuilder();
		sql.append("select SPARE_PART_ID,SPARE_PART_NAME,CREATE_MONTH,DATEPART(month, CREATE_MONTH) month,sum(totalFee) from  ");
		sql.append("(select SPARE_PART_ID,SPARE_PART_NAME,CREATE_MONTH,DATEPART(month, CREATE_MONTH) month,amount* price totalFee ");
		sql.append(" from spare_part_trans_info  ");
		sql.append(" where SPARE_PART_ID=:spId and TRANS_TYPE='2' and DATEPART(year, CREATE_MONTH)=:currYear) as b ");
		sql.append("group by SPARE_PART_ID,SPARE_PART_NAME,CREATE_MONTH ");
		Query query = getSession().createSQLQuery(sql.toString());
		query.setParameter("spId", chartPara.getSparePartId());
		//Calendar a=Calendar.getInstance();
		query.setParameter("currYear", chartPara.getYear()); 
		List<?> rsList = query.list();
		List<ChartInfo> list = new ArrayList<ChartInfo>();
		ChartInfo chartInfo;
		if(!CollectionUtils.isEmpty(rsList)){
			Iterator<?> ite = rsList.iterator();
			while(ite.hasNext()){
				Object[] obj = (Object[])ite.next();
				chartInfo = new ChartInfo();
				chartInfo.setSparePartId(String.valueOf(obj[0]));
				chartInfo.setSparePartName(String.valueOf(obj[1]));
				chartInfo.setCount(Tools.isNull(obj[4])?0.0:Double.parseDouble(String.valueOf(obj[4])));
				chartInfo.setMaintMonth(String.valueOf(obj[3]));
				list.add(chartInfo);
			}
		}
		return list;
	}
	
	public List<ChartInfo> getSpFeeByDept(ChartInfoParam  chartPara){   
		StringBuilder sql = new StringBuilder();
		sql.append("select DATEPART(month, CREATE_MONTH) month,DEPT_ID,dept_name,sum(toFee) totalFee from ");
		sql.append("(select  a.SPARE_PART_ID ,a.CREATE_MONTH,DATEPART(month, CREATE_MONTH) month,d.DEPT_ID,d.dept_name,a.amount,a.price,amount*price toFee ");
		sql.append(" from spare_part_trans_info a,wo_info b,EQUIP_INFO c,DEPT_INFO d ");
		sql.append(" where a.TRANS_TYPE='2' and a.WO_ID=b.WO_ID and b.EQUIP_ID=c.EQUIP_ID and c.DEPT_ID=d.DEPT_ID and DATEPART(year, CREATE_MONTH)=:currYear) as e ");
		sql.append(" group by DEPT_ID,dept_name,CREATE_MONTH ");
		Query query = getSession().createSQLQuery(sql.toString());
		//Calendar a=Calendar.getInstance();
		query.setParameter("currYear", chartPara.getYear()); 
		List<?> rsList = query.list();
		List<ChartInfo> list = new ArrayList<ChartInfo>();
		ChartInfo chartInfo;
		if(!CollectionUtils.isEmpty(rsList)){
			Iterator<?> ite = rsList.iterator();
			while(ite.hasNext()){
				Object[] obj = (Object[])ite.next();
				chartInfo = new ChartInfo();
				chartInfo.setMaintMonth(String.valueOf(obj[0]));
				chartInfo.setDeptId(String.valueOf(obj[1]));
				chartInfo.setDeptNm(String.valueOf(obj[2]));
				chartInfo.setCount(Tools.isNull(obj[3])?0.0:Double.parseDouble(String.valueOf(obj[3])));
				list.add(chartInfo);
			}
		}
		return list;
	}
	
	public List<ChartInfo> getTotalSpFeeForMonth(ChartInfoParam  chartPara){
		StringBuilder sql = new StringBuilder();
		sql.append("select DATEPART(month, CREATE_MONTH) month,sum(toFee) totalFee from ");
		sql.append("(select  a.SPARE_PART_ID ,a.CREATE_MONTH,DATEPART(month, CREATE_MONTH) month,d.DEPT_ID,d.dept_name,a.amount,a.price,amount*price toFee ");
		sql.append(" from spare_part_trans_info a,wo_info b,EQUIP_INFO c,DEPT_INFO d  ");
		sql.append(" where a.TRANS_TYPE='2' and a.WO_ID=b.WO_ID and b.EQUIP_ID=c.EQUIP_ID and c.DEPT_ID=d.DEPT_ID and DATEPART(year, CREATE_MONTH)=:year) as e ");
		sql.append(" group by CREATE_MONTH ");
		Query query = getSession().createSQLQuery(sql.toString());
		//Calendar a=Calendar.getInstance();
		query.setParameter("year", chartPara.getYear()); 
		List<?> rsList = query.list();
		List<ChartInfo> list = new ArrayList<ChartInfo>();
		ChartInfo chartInfo;
		if(!CollectionUtils.isEmpty(rsList)){
			Iterator<?> ite = rsList.iterator();
			while(ite.hasNext()){
				Object[] obj = (Object[])ite.next();
				chartInfo = new ChartInfo();
				chartInfo.setMaintMonth(String.valueOf(obj[0]));
				chartInfo.setCount(Tools.isNull(obj[1])?0.0:Double.parseDouble(String.valueOf(obj[1])));
				chartInfo.setDeptNm(Constants.DEPT_AVG_NM);
				list.add(chartInfo);
			}
		}
		return list;
	}
	
	public List<ChartInfo> getSpFeeByYear(){
		StringBuilder sql = new StringBuilder();
		sql.append("select year,DEPT_ID,dept_name,sum(fee) totalFee from ");
		sql.append("(select DATEPART(year, CREATE_MONTH) year,DATEPART(month, CREATE_MONTH) month,d.DEPT_ID,d.dept_name,a.amount,a.price,a.amount*a.price fee ");
		sql.append("from spare_part_trans_info a,wo_info b,EQUIP_INFO c,DEPT_INFO d  ");
		sql.append("where a.TRANS_TYPE='2' and a.WO_ID=b.WO_ID and b.EQUIP_ID=c.EQUIP_ID and c.DEPT_ID=d.DEPT_ID) as e ");
		sql.append("group by DEPT_ID,dept_name,year;  ");
		Query query = getSession().createSQLQuery(sql.toString());
		List<?> rsList = query.list();
		List<ChartInfo> list = new ArrayList<ChartInfo>();
		ChartInfo chartInfo;
		if(!CollectionUtils.isEmpty(rsList)){
			Iterator<?> ite = rsList.iterator();
			while(ite.hasNext()){
				Object[] obj = (Object[])ite.next();
				chartInfo = new ChartInfo();
				chartInfo.setMaintMonth(String.valueOf(obj[0]));
				chartInfo.setDeptId(String.valueOf(obj[1]));
				chartInfo.setDeptNm(String.valueOf(obj[2]));
				chartInfo.setCount(Tools.isNull(obj[3])?0.0:Double.parseDouble(String.valueOf(obj[3])));
				list.add(chartInfo);
			}
		}
		return list;
	}
	
	public List<ChartInfo> getTotalSpFeeForYear(){
		StringBuilder sql = new StringBuilder();
		sql.append("select year,sum(fee) totalFee from ");
		sql.append("(select DATEPART(year, CREATE_MONTH) year,DATEPART(month, CREATE_MONTH) month,d.DEPT_ID,d.dept_name,a.amount,a.price,a.amount*a.price fee ");
		sql.append(" from spare_part_trans_info a,wo_info b,EQUIP_INFO c,DEPT_INFO d  ");
		sql.append(" where a.TRANS_TYPE='2' and a.WO_ID=b.WO_ID and b.EQUIP_ID=c.EQUIP_ID and c.DEPT_ID=d.DEPT_ID) as e ");
		sql.append(" group by year ");
		Query query = getSession().createSQLQuery(sql.toString());
		List<?> rsList = query.list();
		List<ChartInfo> list = new ArrayList<ChartInfo>();
		ChartInfo chartInfo;
		if(!CollectionUtils.isEmpty(rsList)){
			Iterator<?> ite = rsList.iterator();
			while(ite.hasNext()){
				Object[] obj = (Object[])ite.next();
				chartInfo = new ChartInfo();
				chartInfo.setMaintMonth(String.valueOf(obj[0]));
				chartInfo.setDeptNm(Constants.DEPT_AVG_NM);
				chartInfo.setCount(Tools.isNull(obj[1])?0.0:Double.parseDouble(String.valueOf(obj[1])));
				list.add(chartInfo);
			}
		}
		return list;
	}
	
	public Map<String,String> getEquipCntByDept(){
		StringBuilder sql = new StringBuilder();
		sql.append("select d.dept_name,count(*) cnt ");
		sql.append("from EQUIP_INFO c,DEPT_INFO d  ");
		sql.append("where c.DEPT_ID=d.DEPT_ID ");
		sql.append("group by d.dept_name ");
		Query query = getSession().createSQLQuery(sql.toString());
		List<?> rsList = query.list();
		
		Map<String,String> map = new HashMap<String, String>();
		if(!CollectionUtils.isEmpty(rsList)){
			Iterator<?> ite = rsList.iterator();
			while(ite.hasNext()){
				Object[] obj = (Object[])ite.next();
				map.put(String.valueOf(obj[0]), String.valueOf(obj[1]));
			}
		}
		return map;
	}
	
	
	
	public Map<String,List<Map<String,String>>> getlstWorkorder(ChartInfoParam param){
		StringBuilder sql = new StringBuilder();
		sql.append("select d.DEPT_ID,d.dept_name,b.equip_id ,a.maint_start_date,a.maint_end_date,b.shutdown_flag,a.created_date ");
		sql.append("from wo_maint_info a,wo_info b,EQUIP_INFO c,DEPT_INFO d ");
		sql.append("where a.WO_ID=b.WO_ID and b.EQUIP_ID=c.EQUIP_ID and c.DEPT_ID=d.DEPT_ID and a.maint_end_date is not null");
		if(!StringUtils.isEmpty(param.getEquipId())){
			sql.append(" and b.equip_id=:equipId");
		}
		
		if(!StringUtils.isEmpty(param.getStartDate()) && !StringUtils.isEmpty(param.getEndDate())){
			sql.append(" and a.maint_start_date between cast(:startDt as datetime) and cast(:endDt as datetime)");
		}
		sql.append(" order by d.DEPT_ID,b.equip_id ,a.maint_start_date ");
		Map<String,List<Map<String,String>>> map = new HashMap<String, List<Map<String,String>>>();
		Map<String,List<Map<String,String>>> retMap = new LinkedHashMap<String, List<Map<String,String>>>();
		
		try{
			Query query = getSession().createSQLQuery(sql.toString());
			if(!StringUtils.isEmpty(param.getEquipId())){
				query.setParameter("equipId", param.getEquipId());
			}
			
			if(!StringUtils.isEmpty(param.getStartDate()) && !StringUtils.isEmpty(param.getEndDate())){
				query.setParameter("startDt", param.getStartDate());
				query.setParameter("endDt", param.getEndDate());
			}
			
			//query.setParameter("spId", spId); 
			List<?> rsList = query.list();
			if(!CollectionUtils.isEmpty(rsList)){
				Iterator<?> ite = rsList.iterator();
				Map<String,String> row = null;
				String deptId = "";
				String deptName = "";
				List<Map<String,String>> list = null;
				while(ite.hasNext()){
					Object[] obj = (Object[])ite.next();
					deptId = String.valueOf(obj[0]);
					deptName = String.valueOf(obj[1]);
					row = new HashMap<String,String>();			
					row.put("DEPT_ID", deptId);
					row.put("dept_name", deptName);
					row.put("equip_id", String.valueOf(obj[2]));
					row.put("maint_start_date", String.valueOf(obj[3]));
					row.put("maint_end_date", String.valueOf(obj[4]));
					row.put("shutdown_flag", String.valueOf(obj[5]));
					row.put("created_date", String.valueOf(obj[6]));
					if(map.containsKey(deptName)){
						list = map.get(deptName);
						list.add(row);
					}else{
						list = new ArrayList<Map<String,String>>();
						map.put(deptName, list);
						list.add(row);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//System.out.println("map 1 :"+map);
		if(Tools.isEmpty(param.getEquipId())){
		  List<String> deptList = getDeptNames();
	    if(!CollectionUtils.isEmpty(deptList)){
	      Iterator<String> itor = deptList.iterator();
	      while (itor.hasNext()){
	        String deptName = itor.next();
	        if(map.containsKey(deptName)){
	          retMap.put(deptName, map.get(deptName));
	        }else{
	          retMap.put(deptName, null);
	        }
	      }
	    }
	    
		}else{
		  String deptName = getDeptNmByEqId(param.getEquipId());
			if(CollectionUtils.isEmpty(map)){
				if(!StringUtils.isEmpty(deptName)){
				  retMap.put(deptName, null);
				}
			}else{
			  retMap.put(deptName, map.get(deptName));
			}
		}
		//System.out.println("map 2 :"+map);
		
		
		
		return retMap;
	}
	
	public List<String> getDeptNames(){
		List<String> deptList = null;
		String sql = "select DEPT_NAME from DEPT_INFO";
		SQLQuery query = getSession().createSQLQuery(sql);
		List<?> list = query.list();
		deptList = (List<String>) list;
		return deptList;
	}
	
	
	public String getDeptNmByEqId(String equipId){
		String sql = "select DEPT_NAME from dept_info a,EQUIP_INFO b where a.dept_id = b.dept_id and  equip_id=:equipId order by a.dept_id";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("equipId", equipId);
		List<?> list = query.list();
		if(!CollectionUtils.isEmpty(list)){
			return list.get(0).toString();
		}else{
			return "";
		}
		
	}
}
