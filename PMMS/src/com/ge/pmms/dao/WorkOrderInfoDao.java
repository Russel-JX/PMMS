/**
 * ============================================================
 * File : WorkOrderInfoDao.java
 * Description : 
 * 
 * Package : com.ge.pmms.dao
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

package com.ge.pmms.dao;

import java.util.Date;
import java.util.List;

import com.ge.pmms.base.BaseDao;
import com.ge.pmms.base.PmmsException;
import com.ge.pmms.po.FaultTypeInfo;
import com.ge.pmms.po.WorkOrderInfo;


/*******************************************************************************
 *
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
public interface WorkOrderInfoDao extends BaseDao<WorkOrderInfo> {
	/**
	 * 获取工单信息
	 * get work orders data
	 * @Author: iGATE 
	 * @param workOrderStatus
	 * @param workOrderInfo
	 * @return
	 * @Description:
	 */
	public List<WorkOrderInfo> getWorkOrders(String workOrderStatus,WorkOrderInfo workOrderInfo) throws PmmsException;
	/**
	 ** 创建工单
	 * @Author: iGATE 
	 * @param workOrderInfo
	 * @Description:
	 */
	public void save(WorkOrderInfo workOrderInfo);
	/**
	 * 根据planId 删除计划工单
	 * @Author: iGATE 
	 * @param planId
	 * @Description:
	 */
	public int deleteByPanId(String planId);
	
	/**
	 * 根据工单号，删除计划工单维修记录
	 * @Author: iGATE 
	 * @param woIds
	 * @Description:
	 */
	public long deletePWMOByWOId(String[] woIds)throws PmmsException;
	/**
	* 根据计划编号查询工单编号
	* @param planId
	* @return
	* @throws Exception
	*/
	public List<String> getWOIdByPlanId(String planId)throws PmmsException;
	/**
	* Find the number of preventive work orders which are completed
	* 查询已完成的计划工单数量
	* @param planId
	* @return
	* the number of result
	* @throws Exception
	*/
	public long getFinishedPWONum(String planId)throws PmmsException;
	/**
	* Find preventive work orders which are completed
	* 查询已完成的计划工单列表
	* @param planId
	* @return
	* @throws Exception
	*/
	public List<WorkOrderInfo> getFinishedPWO(String planId)throws PmmsException;
	/**
	* Find the month of preventive work orders whose maintenance month is not changeable
	* 查询不能修改计划工单月份的月份列表
	* 计划维修月份小于等于当前月份的计划，并且是已完成或在维修的计划工单
	* @param planId
	* @return
	* @throws Exception
	*/
	public List<String> getUnchangeablePWOMonth(String planId,String currMonth)throws PmmsException;
	/**
	* Find the month of preventive work orders which are completed
	* 查询已完成的计划工单月份列表
	* @param planId
	* @return
	* @throws Exception
	*/
	public List<String> getFinishedPWOMonth(String planId)throws PmmsException;
	/**
	* Find preventive work orders which are not completed
	* 查询未完成的计划工单列表
	* @param planId
	* @return
	* @throws Exception
	*/
	public List<WorkOrderInfo> getUnfinishedPWO(String planId)throws PmmsException;
	/**
	 * 查询工单，与备件出库关联
	 * @Author: iGATE 
	 * @param workOrderInfo
	 * @return
	 * @throws PmmsException
	 * @Description:
	 */
	public List<WorkOrderInfo> getWOForSP(WorkOrderInfo workOrderInfo) throws PmmsException;
	/**
	 * 按时间段查询未关闭的工单
	 * @Author: iGATE 
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws PmmsException
	 * @Description:
	 */
	public List<WorkOrderInfo> getWOINProcess(Date startDate,Date endDate) throws PmmsException;
	/**
	 * 获取故障种类的信息
	 * @Author: iGATE 
	 * @return
	 * @throws PmmsException
	 * @Description:
	 */
	public List<FaultTypeInfo> getFaultTypes() throws PmmsException;
	/**
	 * 查询一段时间内 没有关闭的工单。包括维修故障工单和计划保养工单。
	 * @Author: iGATE 
	 * @param firstDayofYear
	 * @param planMonthEnd
	 * @return
	 * @throws PmmsException
	 * @Description:
	 */
	public List<WorkOrderInfo> findUnfinishedWO(String firstDayofYear,String planMonthEnd) throws PmmsException;
}
