package com.ge.pmms.service;


import java.util.List;
import java.util.Map;

import com.ge.pmms.base.PmmsException;
import com.ge.pmms.base.ServiceReturns;
import com.ge.pmms.po.PlanInfo;
import com.ge.pmms.po.WorkOrderInfo;


/*******************************************************************************
 *
 * @Author 		: Xun Jiang
 * @Version 	: 1.0
 * @Date Created: Jan 23, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
public interface PlanInfoService{
	/**
	 * 
	 * @Author: Xun Jiang 
	 * @param planYear
	 * @param equipType
	 * @return 
	 *  an instance of ServiceReturns which holds the attributes of data.
	 * @throws PmmsException 
	 * @Description:
	 * 	find maintenance plans by the type of equipment and year.
	 */
	public ServiceReturns findPlan(String planYear,String equipType)throws PmmsException;
	
	/**
	 * Find plan by year of plan for current month
	 * @Author: Xun Jiang 
	 * @return
	 * @throws PmmsException
	 */
	public ServiceReturns findPlanForCurrMonth()throws PmmsException;
	
	/**
	 * 
	 * @Author: Xun Jiang 
	 * @param planYear
	 * @param equipType
	 * @return 
	 *  an instance of ServiceReturns which holds the attributes of data.
	 * @throws PmmsException 
	 * @Description:
	 * 	insert one maintenance plan.
	 */
	public ServiceReturns addSinglePlan(PlanInfo planInfo)throws PmmsException;
	/**
	 * 
	 * @Author: Xun Jiang 
	 * @param planYear
	 * @param equipType
	 * @return 
	 *  an instance of ServiceReturns which holds the attributes of data.
	 * @throws PmmsException 
	 * @Description:
	 * 	update a old maintenance plan with a new plan.
	 */
	public ServiceReturns modifySinglePlan(PlanInfo planInfo)throws PmmsException;
	/**
	 * 
	 * @Author: Xun Jiang 
	 * @param planId
	 * @return 
	 *  an instance of ServiceReturns which holds the attributes of data.
	 * @throws PmmsException 
	 * @Description:
	 * 	remove single maintenance plan through the id of this record.
	 */
	public ServiceReturns removeSinglePlan(String planId)throws PmmsException;
	/**
	 * 
	 * @Author: Xun Jiang 
	 * @param copyFrom
	 * @param copyTo
	 * @return 
	 * 	an instance of ServiceReturns which holds the attributes of data.
	 * @throws PmmsException 
	 * @Description:
	 * 	validate plan to check if the plans exist or is empty
	 */
	public ServiceReturns validatePlans(String copyFrom,String copyTo) throws PmmsException;
	/**
	 * 
	 * @Author: Xun Jiang 
	 * @param copyFrom
	 * @param copyTo
	 * @return 
	 * 	an instance of ServiceReturns which holds the attributes of data.
	 * @throws PmmsException 
	 * @Description:
	 * 	generate maintenance plans for the plans of one year based on the plans of another year
	 */
	public ServiceReturns generateWholeYearPlan(String copyFrom,String copyTo) throws PmmsException;
	/**
	 * validate the single plan to modify
	 * 验证计划是否可修改，未完成的计划可以修改
	 * @Author: Xun Jiang 
	 * @param plan_id
	 * @return
	 * 	an instance of ServiceReturns which holds the attributes of data.
	 * @throws PmmsException
	 */
	public ServiceReturns validateSinglePlan(String plan_id)throws PmmsException;
	/**
	* Find preventive work orders which are completed
	* 查询已完成的计划工单列表
	* @Author: Xun Jiang 
	* @param planId
	* @return
	* @throws PmmsException
	*/
	public ServiceReturns getFinishedPWO(String plan_id)throws PmmsException;
	
	/**
	* Find the month of preventive work orders whose maintenance month is not changeable
	* 查询不能修改计划工单月份的月份列表
	* 计划维修月份小于等于当前月份的计划，并且是已完成或在维修的计划工单
	* @Author: Xun Jiang 
	* @param planId
	* @return
	* @throws PmmsException
	*/
	public ServiceReturns getUnchangeablePWOMonth(String planId)throws PmmsException;
	/**
	*Find the month of preventive work orders which are completed
	* 查询已完成的计划工单月份列表
	* @Author: Xun Jiang 
	* @param planId
	* @return
	* @throws PmmsException
	*/
	public ServiceReturns getFinishedPWOMonth(String planId)throws PmmsException;
	/**
	* Find preventive work orders which are not completed
	* 查询未完成的计划工单列表
	* @Author: Xun Jiang 
	* @param planId
	* @return
	* @throws PmmsException
	*/
	public ServiceReturns getUnfinishedPWO(String plan_id)throws PmmsException;
	/**
	 * get the plans will be executed next month
	 * @Author: Xun Jiang 
	 * @param planMonth
	 * @param planYear
	 * @return
	 * 	a list contains the plans of next month
	 * @throws PmmsException
	 */
	public List<WorkOrderInfo> generateMonthPlans(int planMonth,String planYear)throws PmmsException;
	
	/**
	 * send email or text  message to receivers
	 * @Author: Xun Jiang 
	 * @param workOrderInfos
	 * @return
	 * 	
	 * @throws PmmsException
	 */
	public void sendPWOReminder(Map<String,List<WorkOrderInfo>> workOrderInfos)throws PmmsException;
	
}
