package com.ge.pmms.dao;

import java.util.List;
import java.util.Map;

import com.ge.pmms.base.BaseDao;
import com.ge.pmms.base.PmmsException;
import com.ge.pmms.po.ChartInfo;
import com.ge.pmms.po.ChartInfoParam;

public interface ChartDao extends BaseDao<ChartInfo> {
	
	/**
	 * Find preventive maintenance fulfillment number for five department from 'fromYear' to current 'toYear'
	 * if woStatus is not empty, then get all the accomplished preventive maintenance work order number.
	 * @Author: Xun Jiang 
	 * @param fromYear
	 * @param toYear
	 * @param woStatus
	 * @return 
	 * @throws PmmsException
	 */
	public List<ChartInfo> findPMWONumber(String fromYear,String toYear,String woStatus)throws PmmsException;
	
	/**
	 * Find preventive maintenance fulfillment number for five department from 'fromYear' to 'toYear'
	 * if woStatus is not empty, then get all the accomplished preventive maintenance work order number.
	 * @Author: Xun Jiang 
	 * @param fromYear
	 * @param toYear
	 * @param woStatus
	 * @return 
	 * @throws PmmsException
	 */
	public List<ChartInfo> findPMWONumberByYear(String fromYear,String toYear,String woStatus)throws PmmsException;
	
	/**
	 * Find preventive maintenance fulfillment number for single equipment by the selected year
	 * if woStatus is not empty, then get all the accomplished preventive maintenance work order number.
	 * @Author: Xun Jiang 
	 * @param maintYear
	 * @param woStatus
	 * @return 
	 * @throws PmmsException
	 */
	public List<ChartInfo> findSinglePMWONumber(String equipId,String fromYear,String toYear,String woStatus)throws PmmsException;
	
	/**
	 * Find preventive maintenance record by the selected year
	 * @Author: Xun Jiang 
	 * @param miantStartDate
	 * @param miantEndDate
	 * @return 
	 * @throws PmmsException
	 */
	public List<ChartInfo> findMaintRecordByYear(String miantStartDate,String miantEndDate)throws PmmsException;
	
	/**
	 * Find single preventive maintenance record by the selected year
	 * @Author: Xun Jiang 
	 * @param miantStartDate
	 * @param miantEndDate
	 * @return 
	 * @throws PmmsException
	 */
	public List<ChartInfo> findSingleMaintRecordByYear(String equipId,String miantStartDate,String miantEndDate)throws PmmsException;
	
	/**
	 * Find preventive maintenance record for five department from 'fromYear' to 'toYear'
	 * @Author: Xun Jiang 
	 * @param fromYear
	 * @return 
	 * @throws PmmsException
	 */
	public List<ChartInfo> findMaintRecordForYears(String fromYear)throws PmmsException;
	
	public List<ChartInfo> getSpNumBySpId(ChartInfoParam  chartPara);
	
	public List<ChartInfo> getSpNumBydept(ChartInfoParam  chartPara);
	
	public List<ChartInfo> getTotalSpNumByMonth(ChartInfoParam  chartPara);
	
	public List<ChartInfo> getSpNumByYear();
	
	public List<ChartInfo> getTotalSpNumForYear();
	
	public List<ChartInfo> getSpFeeBySpId(ChartInfoParam  chartPara);
	public List<ChartInfo> getSpFeeByDept(ChartInfoParam  chartPara);
	
	public List<ChartInfo> getTotalSpFeeForMonth(ChartInfoParam  chartPara);
	
	public List<ChartInfo> getSpFeeByYear();
	
	public List<ChartInfo> getTotalSpFeeForYear();
	
	
	/**
	 * get work order list Map<dept_name,List[row]>
	 * @return
	 */
	public Map<String,List<Map<String,String>>> getlstWorkorder(ChartInfoParam param);
	
	/**
	 * get equip count by dept
	 * @return
	 */
	public Map<String,String> getEquipCntByDept();
	
	
	/**
	 * find depart name by equip id
	 * @Author: iGATE 
	 * @param equipId
	 * @return
	 * @Description:
	 */
	public String getDeptNmByEqId(String equipId);
}
