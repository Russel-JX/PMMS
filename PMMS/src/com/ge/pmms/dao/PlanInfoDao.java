package com.ge.pmms.dao;

import java.text.ParseException;
import java.util.List;

import com.ge.pmms.base.BaseDao;
import com.ge.pmms.base.PmmsException;
import com.ge.pmms.po.PlanInfo;
import com.ge.pmms.po.PlanMaintInfo;
import com.ge.pmms.po.ReminderInfo;

public interface PlanInfoDao extends BaseDao<PlanInfo> {
	
	/**
	 * Find plan by planId
	 * @param planId
	 * @return
	 * @throws PmmsException
	 */
	public PlanInfo findPlanByPlanId(String planId)throws PmmsException;
	
	/**
	 * Find plan by year of plan
	 * @param planYear
	 * @return
	 * @throws PmmsException
	 */
	public List<PlanInfo> findPlan(String planYear)throws PmmsException;
	
	/**
	 * Find plan by year of plan and equipType
	 * @Author: Xun Jiang 
	 * @param planYear
	 * @param equipType
	 * @return
	 * @throws PmmsException
	 */
	public List<PlanInfo> findRichPlan(String planYear, String equipType)throws PmmsException, ParseException;
	
	/**
	 * Find plan by year of plan for current month
	 * @Author: Xun Jiang 
	 * @param planMonthStart
	 * @param planMonthEnd
	 * @return
	 * @throws PmmsException
	 */
	public List<PlanMaintInfo> findPlanForCurrMonth(String firstDayofYear,String planMonthStart, String planMonthEnd)throws PmmsException;
	
	/**
	 * Add plan for single equipment
	 * @Author: Xun Jiang 
	 * @param planInfo
	 * @return
	 * @throws PmmsException
	 */
	public PlanInfo addSinglePlan(PlanInfo planInfo)throws PmmsException;
	
	/**
	 * Modify plan for single equipment
	 * @Author: Xun Jiang 
	 * @param planInfo
	 * @return
	 * @throws PmmsException
	 */
	public PlanInfo modifySinglePlan(PlanInfo planInfo)throws PmmsException;
	
	/**
	 * Remove plan for single equipment
	 * @Author: Xun Jiang 
	 * @param planId
	 * @return
	 * @throws PmmsException
	 */
	public int removeSinglePlan(String planId)throws PmmsException;
	
	/**
	 * get the number of plans of planYear
	 * @Author: Xun Jiang 
	 * @param planYear
	 * @return
	 * 	the number of plans in this year
	 * @throws PmmsException
	 */
	public long getPlansNumber(String planYear)throws PmmsException;
	
	/**
	 * remove all the plans of one year
	 * @Author: Xun Jiang 
	 * @param planYear
	 * @return
	 * 	the number of plans in this year
	 * @throws PmmsException
	 */
	public void removeAllPlansByYear(String planYear)throws PmmsException;
	
	/**
	 * get the plans will be executed next month
	 * @Author: Xun Jiang 
	 * @param planMonth
	 * @param planYear
	 * @return
	 * 	a list contains the plans of next month
	 * @throws PmmsException
	 */
	public List<PlanInfo> getMonthPlans(String planMonth,String planYear)throws PmmsException;
	
//	/**
//	 * get the plans the are generated in last month for this month.
//	 * (The plans which are not completed)
//	 * @param planMonth
//	 * @param planYear
//	 * @return
//	 * 	a list contains the plans of next month
//	 * @throws PmmsException
//	 */
////	public List<PlanInfo> getExistingWO(String planMonth,String planYear)throws PmmsException;
	
//	/**
//	 * get the plan that is not finished by plan_id
//	 * @Author: Xun Jiang 
//	 * @param plan_id
//	 * @return
//	 * 	a instant of PlanInfo
//	 * @throws PmmsException
//	 */
//	public PlanInfo getUnfinishedWO(String plan_id)throws PmmsException;
	
}
