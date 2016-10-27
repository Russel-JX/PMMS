package com.ge.pmms.service;

import java.text.ParseException;

import com.ge.pmms.base.PmmsException;
import com.ge.pmms.base.ServiceReturns;
import com.ge.pmms.po.ChartInfoParam;


/*******************************************************************************
 *
 * @Author 		: Xun Jiang
 * @Version 	: 1.0
 * @Date Created: Jan 27, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
public interface ChartInfoService{

	/**
	 * Find preventive maintenance fulfillment rate for five department for the current year
	 * @Author: Xun Jiang 
	 * @param maintYear
	 * @param woStatus
	 * @return 
	 * @throws Exception
	 */
	public ServiceReturns getPMWORate(String maintYear,String woStatus)throws PmmsException; 
	
	/**
	 * Find preventive maintenance fulfillment rate for five department from last four years to current year
	 * @Author: Xun Jiang 
	 * @return 
	 * @throws Exception
	 */
	public ServiceReturns getPMWORateByYear()throws PmmsException;
	
	/**
	 * Find preventive maintenance fulfillment rate for single equipment by the selected year
	 * @Author: Xun Jiang 
	 * @param maintYear
	 * @param woStatus
	 * @return 
	 * @throws Exception
	 */
	public ServiceReturns getSinglePMWORate(String equipId,String maintYear)throws PmmsException;
	
	/**
	 * Find MTTR rate for all departments by the selected year
	 * @Author: Xun Jiang 
	 * @param year
	 * @return 
	 * @throws Exception
	 */
	public ServiceReturns getMTTRRateByYear(String year)throws PmmsException;
	
	/**
	 * Find MTTR rate for single equipment by the selected year
	 * @Author: Xun Jiang 
	 * @param year
	 * @return 
	 * @throws Exception
	 */
	public ServiceReturns getSingleMTTRRateByYear(String equipId,String year)throws PmmsException;
	
	/**
	 * Find MTTR rate for single equipment from last four years to current year
	 * @Author: Xun Jiang 
	 * @return 
	 * @throws Exception
	 */
	public ServiceReturns getMTTRRateforYears()throws PmmsException;
	
	
	
	public ServiceReturns getSpNumBySpId(ChartInfoParam  chartPara);
	public ServiceReturns getSpNumBydept(ChartInfoParam  chartPara);
	public ServiceReturns getSpNumByYear();
	public ServiceReturns getSpFeeBySpId(ChartInfoParam  chartPara);
	public ServiceReturns getSpFeeByDept(ChartInfoParam  chartPara);
	public ServiceReturns getSpFeeByYear();
	/**
	 * 设备使用率，按年统计，五大部门最近五年
	 * @Author: iGATE 
	 * @return
	 * @throws ParseException
	 * @Description:
	 */
	public ServiceReturns getEquipUsageByYr() throws ParseException;
	/**
	 * 设备使用率，按月统计，单个设备一年12个月，五大部分一年12个月
	 * @Author: iGATE 
	 * @param equipId
	 * @param year
	 * @return
	 * @throws ParseException
	 * @Description:
	 */
	public ServiceReturns getEquipUsageByMonth(String equipId,int year) throws ParseException;
	
	/**
	 * 按年份和设备id统计
	 * @Author: iGATE 
	 * @param equipId
	 * @return
	 * @throws ParseException
	 * @Description:
	 */
	public ServiceReturns getEquipMTBFByMonth(String equipId,String year) throws ParseException;
	
	/**
	 * 设备使用间隔率，按年统计
	 * @Author: Flash 
	 * @param equipId 设备编号
	 * @return
	 * @throws ParseException
	 * @Description:
	 */
	public ServiceReturns getEquipMTBFByYr() throws ParseException;

	
}
