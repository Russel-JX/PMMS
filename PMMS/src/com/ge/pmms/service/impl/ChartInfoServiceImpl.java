/**
 * ============================================================
 * File : PlanInfoServiceImpl.java
 * Description : 
 * 
 * Package : com.ge.pmms.service.impl
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Jan 23, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.pmms.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ge.pmms.base.BaseService;
import com.ge.pmms.base.PmmsException;
import com.ge.pmms.base.ServiceReturns;
import com.ge.pmms.dao.ChartDao;
import com.ge.pmms.dao.SparePartInfoDao;
import com.ge.pmms.po.ChartDetailInfo;
import com.ge.pmms.po.ChartInfo;
import com.ge.pmms.po.ChartInfoParam;
import com.ge.pmms.po.EquipInfo;
import com.ge.pmms.service.ChartInfoService;
import com.ge.pmms.service.EquipInfoService;
import com.ge.pmms.utils.Constants;
import com.ge.pmms.utils.Tools;


/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Jan 27, 2015
 * @Date Modified : 
 * @Modified By :
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
@Service
@Transactional
public class ChartInfoServiceImpl extends BaseService<ChartInfo> implements ChartInfoService {
	@Autowired
	private ChartDao chartDao;
	@Autowired
	private SparePartInfoDao sparePartInfoDao;
	@Autowired
	private EquipInfoService equipInfoService;
	
	public ServiceReturns getPMWORate(String maintYear,String woStatus)throws PmmsException{
		serviceReturns = new ServiceReturns();
		int currYearInt = Tools.getCurrentYear();
		String fromYear = "";
		String toYear = "";
		if(Tools.isBlank(maintYear)){
			fromYear = currYearInt+"-01-01 00:00:00";
			toYear = currYearInt+"-12-31 23:59:59";
		}else{
			fromYear = maintYear+"-01-01 00:00:00";
			toYear = maintYear+"-12-31 23:59:59";
		}
		LOGGER.info("==================fromYear="+fromYear+"toYear="+toYear);
		try{
			List<ChartInfo> list_total = chartDao.findPMWONumber(fromYear,toYear, null);
			List<ChartInfo> list_valid = chartDao.findPMWONumber(fromYear,toYear, Constants.WORK_ORDER_STATUS_ClOSED);
			
			List<ChartDetailInfo> list_total_detail = buildDataForDept(list_total,currYearInt,Constants.PERIOD_MONTH);
			List<ChartDetailInfo> list_valid_detail = buildDataForDept(list_valid,currYearInt,Constants.PERIOD_MONTH);
			
			ChartDetailInfo dept_avg_detail = null;//部门平均
			
			//计算五大部门指标
			List<ChartDetailInfo> list = calculateRate(list_total_detail,list_valid_detail);
			//计算总计指标
			dept_avg_detail = calculateRateAvg(list_total_detail,list_valid_detail,Constants.PERIOD_MONTH);
			
			list.add(dept_avg_detail);
			serviceReturns.putData("list",list);
		}catch(Exception e){
			LOGGER.error("getPMWORate() method occures exception......Exception:"+e.getMessage());
		}
		return serviceReturns;
	}
	
	//按年统计PM完成率
	public ServiceReturns getPMWORateByYear()throws PmmsException{
		serviceReturns = new ServiceReturns();
		int currYearInt = Tools.getCurrentYear();
		String currYear = currYearInt+"-12-31 23:59:59";
		String fromYear = (currYearInt-4)+"-01-01 00:00:00";
		try{
			List<ChartInfo> list_total = chartDao.findPMWONumberByYear(fromYear, currYear, null);
			List<ChartInfo> list_valid = chartDao.findPMWONumberByYear(fromYear, currYear, Constants.WORK_ORDER_STATUS_ClOSED);
			
			List<ChartDetailInfo> list_total_detail = buildDataForDept(list_total,currYearInt,Constants.PERIOD_YEAR);
			List<ChartDetailInfo> list_valid_detail = buildDataForDept(list_valid,currYearInt,Constants.PERIOD_YEAR);
			
			ChartDetailInfo dept_avg_detail = null;//部门平均
			
			//计算五大部门指标
			List<ChartDetailInfo> list = calculateRate(list_total_detail,list_valid_detail);
			//计算总计指标
			dept_avg_detail = calculateRateAvg(list_total_detail,list_valid_detail,Constants.PERIOD_YEAR);
			
			list.add(dept_avg_detail);
			serviceReturns.putData("list",list);
			String[] backPeriod = new String[Constants.PERIOD_YEAR];
			for(int i=0;i<Constants.PERIOD_YEAR;i++){
				backPeriod[i] = String.valueOf(currYearInt-Constants.PERIOD_YEAR+i+1);
			}
			
			serviceReturns.putData("backPeriod",backPeriod);
		}catch(Exception e){
			LOGGER.error("getPMWORateByYear() method occures exception......Exception:"+e.getMessage());
		}
		return serviceReturns;
	}	
	
	//查询单个设备PM完成率
	public ServiceReturns getSinglePMWORate(String equipId,String maintYear)throws PmmsException{
		serviceReturns = new ServiceReturns();
		List<ChartDetailInfo> list = new ArrayList<ChartDetailInfo>();
		ChartDetailInfo chartDetailInfo = new ChartDetailInfo();
		//验证编号是否存在
		EquipInfo equipInfo = equipInfoService.getEquipInfoByEpId(equipId);
		if(Tools.isNull(equipInfo)){
			list.add(chartDetailInfo);
			serviceReturns.putData("list",list);
			return serviceReturns;
		}
		
		//查询图表数据
		int currYearInt = Tools.getCurrentYear();
		String fromYear = "";
		String toYear = "";
		if(Tools.isBlank(maintYear)){
			fromYear = currYearInt+"-01-01 00:00:00";
			toYear = currYearInt+"-12-31 23:59:59";
		}else{
			fromYear = maintYear+"-01-01 00:00:00";
			toYear = maintYear+"-12-31 23:59:59";
		}
		try{
			List<ChartInfo> list_total = chartDao.findSinglePMWONumber(equipId,fromYear,toYear, null);
			List<ChartInfo> list_valid = chartDao.findSinglePMWONumber(equipId,fromYear,toYear, Constants.WORK_ORDER_STATUS_ClOSED);
			Double[] arr_back = new Double[12];
			Double[] arr_total = new Double[12];
			Double[] arr_valid = new Double[12];
			
			if(!CollectionUtils.isEmpty(list_total)){
				for(int i=0;i<12;i++){
					for(int k=0;k<list_total.size();k++){
						ChartInfo totalChartInfo = list_total.get(k);
						int totalMaintMonth = Integer.parseInt(totalChartInfo.getMaintMonth());
						if((i+1)==totalMaintMonth){
							arr_total[i] = totalChartInfo.getCount();
							break;
						}
						if(k==list_total.size()-1){
							arr_total[i] = 0.0;
						}
					}
				}
			}
			if(!CollectionUtils.isEmpty(list_valid)){
				for(int i=0;i<12;i++){
					for(int k=0;k<list_valid.size();k++){
						ChartInfo validChartInfo = list_valid.get(k);
						int validMaintMonth = Integer.parseInt(validChartInfo.getMaintMonth());
						if((i+1)==validMaintMonth){
							arr_valid[i] = validChartInfo.getCount();
							break;
						}
						if(k==list_valid.size()-1){
							arr_valid[i] = 0.0;
						}
					}
				}
			}
			
			//当List为没有数据时
			if(list_total.isEmpty()){
				for(int i=0;i<12;i++){
					arr_total[i] = 0.0;
				}
			}
			if(list_valid.isEmpty()){
				for(int i=0;i<12;i++){
					arr_valid[i] = 0.0;
				}
			}
			
			//计算指标值
			if(!CollectionUtils.isEmpty(list_total)){
				for(int i=0;i<12;i++){
					if(arr_total[i] == null||arr_total[i]==0.0){
//						arr_back[i] = null;
					}else{
						arr_back[i] = Tools.roundDecimal2(arr_valid[i]*100/arr_total[i]);
					}
					
				}
			}
			
			//当设备编号不正确，arr_back的12个值都是null，要设置chartDetailInfo.setData(null)供前台判断;
			boolean emptyDataFlag = true;
			for(int i=0;i<arr_back.length;i++){
				if(!Tools.isNull(arr_back[i])){
					emptyDataFlag = false;
					break;
				}
			}
			chartDetailInfo.setName(equipId);
			if(emptyDataFlag==true){
				chartDetailInfo.setData(null);
			}else{
				List<Double> data = Arrays.asList(arr_back);
				chartDetailInfo.setData(data);
			}
			
			list.add(chartDetailInfo);
			serviceReturns.putData("list",list);
		}catch(Exception e){
			LOGGER.error("getSinglePMWORate() method occures exception......Exception:"+e.getMessage());
		}
		return serviceReturns;
	}	
	
	//根据分子、分母计算指标
	public List<ChartDetailInfo> calculateRate(List<ChartDetailInfo> list_total_detail,List<ChartDetailInfo> list_valid_detail)throws PmmsException{
		List<ChartDetailInfo> list = new ArrayList<ChartDetailInfo>();
		
		//list_valid_detail为null时
		if(CollectionUtils.isEmpty(list_valid_detail)){
			ChartDetailInfo trans_detail = new ChartDetailInfo(Constants.DEPT_TRANSFORMER_NM);
			ChartDetailInfo outfit_detail = new ChartDetailInfo(Constants.DEPT_OUTFIT_NM);
			ChartDetailInfo machining_detail = new ChartDetailInfo(Constants.DEPT_MACHINING_NM);
			ChartDetailInfo switch_detail = new ChartDetailInfo(Constants.DEPT_SWITCH_NM);
			ChartDetailInfo other_detail = new ChartDetailInfo(Constants.DEPT_OTHER_NM);
			list.add(trans_detail);
			list.add(outfit_detail);
			list.add(machining_detail);
			list.add(switch_detail);
			list.add(other_detail);
			return list;
		}
		for(int i=0;i<list_total_detail.size();i++){
//			ChartDetailInfo total_detail = new ChartDetailInfo();
//			ChartDetailInfo valid_detail = new ChartDetailInfo();
//			ChartDetailInfo avg_detail = new ChartDetailInfo();
			ChartDetailInfo total_detail = null;
			ChartDetailInfo valid_detail = null;
			ChartDetailInfo avg_detail = new ChartDetailInfo();
			//集合的item值可能是null或0（name属性为部门名称；data属性为12个月的数据，可能为null，即此部门12个月中都没有数据，
			//或者为数字表示12个月中都有数据，其中0表示没有数据，而不是为0）
			total_detail = list_total_detail.get(i);
			valid_detail = list_valid_detail.get(i);
//			List<Double> total_data = new ArrayList<Double>();
//			List<Double> valid_data = new ArrayList<Double>();
			List<Double> total_data = null;
			List<Double> valid_data = null;
			List<Double> data = null;
//			data.clear();
			
			total_data = total_detail.getData();
			valid_data = valid_detail.getData();
			if(!Tools.isNull(total_data)){//此部门下12个月中不是都没有任何数据
				data = new ArrayList<Double>();
				if(Tools.isNull(valid_data)){//有效数值12个月都没有数据
					for(int k=0;k<total_data.size();k++){
						if(total_data.get(k)==0.0){
							data.add(null);
						}else{
							data.add(0.0);
						}
					}
				}else{//有效数值12个月中有部分数据
					for(int j=0;j<total_data.size();j++){
						if(total_data.get(j)!=0.0){
							data.add(Tools.roundDecimal2(valid_data.get(j)*100/total_data.get(j)));
						}else{
							data.add(null);
						}
					}
				}
			}	
			avg_detail.setData(data);
			avg_detail.setName(total_detail.getName());
			list.add(avg_detail);
		}
		return list;
	}
	
	//计算总计
	public ChartDetailInfo calculateRateAvg(List<ChartDetailInfo> list_total_detail,List<ChartDetailInfo> list_valid_detail,int period)throws PmmsException{
		ChartDetailInfo dept_avg_detail = new ChartDetailInfo();//部门平均
		List<Double> dept_avg_data = new ArrayList<Double>();//部门平均值
		dept_avg_detail.setName(Constants.DEPT_AVG_NM);
		
		//去除data属性为null的部门
		for(int i=0;i<list_total_detail.size();i++){
			if(Tools.isNull(list_total_detail.get(i).getData())){
				list_total_detail.remove(i);
				i--;
			}
		}
		//****集合每次remove之后，会自动重组本集合***
		for(int m=0;m<list_valid_detail.size();m++){
			if(Tools.isNull(list_valid_detail.get(m).getData())){
				list_valid_detail.remove(m);
				m--;
			}
		}
		ChartDetailInfo[] in1=new ChartDetailInfo[list_total_detail.size()];
		ChartDetailInfo[] in2=new ChartDetailInfo[list_valid_detail.size()];
		ChartDetailInfo[] totalDetailInfos = (ChartDetailInfo[]) list_total_detail.toArray(in1);
		ChartDetailInfo[] validDetailInfos = (ChartDetailInfo[]) list_valid_detail.toArray(in2);
		
		//将data集合数值为null的，转成0.0。
		for(int j=0;j<totalDetailInfos.length;j++){
			for(int n=0;n<period;n++){
				if(Tools.isNull(totalDetailInfos[j].getData().get(n))){
					totalDetailInfos[j].getData().set(n, 0.0);
				}
			}
		}
		for(int j=0;j<validDetailInfos.length;j++){
			for(int n=0;n<period;n++){
				//data的值为null（开关制造部没有数据，本应该不在validDetailInfos中）
				if(Tools.isNull(validDetailInfos[j].getData().get(n))){
					validDetailInfos[j].getData().set(n, 0.0);
				}
			}
		}
		for(int k=0;k<period;k++){
			Double aa = 0.0;
			Double bb = 0.0;
			boolean allNull = true;//如果不同部门的某个月的值都为null，则不展示
			for(int n=0;n<totalDetailInfos.length;n++){
				 if(totalDetailInfos[n].getData().get(k)!=0.0){
					 allNull = false;
					 break;
				 }
			}
			if(allNull==true){
				dept_avg_data.add(null);
			}else{
				for(int n=0;n<totalDetailInfos.length;n++){
					 aa = aa+totalDetailInfos[n].getData().get(k);
				}
				for(int n=0;n<validDetailInfos.length;n++){
					 bb = bb+validDetailInfos[n].getData().get(k);
				}
				dept_avg_data.add(Tools.roundDecimal2(bb*100/aa));
			}
			
		}
		
		dept_avg_detail.setData(dept_avg_data);
		return dept_avg_detail;
	}

	/**
	 * 1.将五大部门查询结果分开分组
	 * @param list
	 * 	DB中的原始接分组数据。
	 * @currYear
	 * 	统计的年份。
	 * @period
	 * 	统计的周期。用于方便循环判断的上限。如：按年统计，或者按月份统计
	 * @return List<ChartDetailInfo>
	 * 此集合包含五大区域对象组成原始数据。（DB中没有的统计周期不在此集合中）
	 * 结构如下：
	 * 	[
	 * 		{"name":"机械加工部",data:[0,0,0,2,2]},
	 * 		{"name":"开关制造部",data:null},
	 * 		{"name":"变压器制造部",data:[3,0,0,1,0]},
	 * 		...
	 * ]
	 * 注意：如果该本部没有任何数据，此部门的data为null；
	 * 否则，有数据的统计周期就是原来的值，此统计周期没有任何数据的值是0.
	 * 
	 */
	public List<ChartDetailInfo> buildDataForDept(List<ChartInfo> list,int currYear,int period)throws PmmsException{
		List<ChartDetailInfo> back_list = new ArrayList<ChartDetailInfo>();
		try{
			//判断查询结果中，1至12月份的数量
			ChartInfo chartInfo = null;
			List<ChartInfo> machining_list = new ArrayList<ChartInfo>();
			List<ChartInfo> switch_list = new ArrayList<ChartInfo>();
			List<ChartInfo> transformer_list = new ArrayList<ChartInfo>();
			List<ChartInfo> outfit_list = new ArrayList<ChartInfo>();
			List<ChartInfo> other_list = new ArrayList<ChartInfo>();
			
			ChartDetailInfo machining_detail = new ChartDetailInfo(Constants.DEPT_MACHINING_NM);
			ChartDetailInfo switch_detail = new ChartDetailInfo(Constants.DEPT_SWITCH_NM);
			ChartDetailInfo transformer_detail = new ChartDetailInfo(Constants.DEPT_TRANSFORMER_NM);
			ChartDetailInfo outfit_detail = new ChartDetailInfo(Constants.DEPT_OUTFIT_NM);
			ChartDetailInfo other_detail = new ChartDetailInfo(Constants.DEPT_OTHER_NM);
			
			if(!CollectionUtils.isEmpty(list)){
				for(int i=0;i<list.size();i++){
					chartInfo = list.get(i);
					//部门
					String deptId = chartInfo.getDeptId();
					if(deptId.equalsIgnoreCase(Constants.DEPT_MACHINING)){
						machining_list.add(chartInfo);
					}else if(deptId.equalsIgnoreCase(Constants.DEPT_SWITCH)){
						switch_list.add(chartInfo);
					}else if(deptId.equalsIgnoreCase(Constants.DEPT_TRANSFORMER)){
						transformer_list.add(chartInfo);
					}else if(deptId.equalsIgnoreCase(Constants.DEPT_OUTFIT)){
						outfit_list.add(chartInfo);
					}else if(deptId.equalsIgnoreCase(Constants.DEPT_OTHER)){
						other_list.add(chartInfo);
					}else{
						LOGGER.error("equipment department id unmatched!");
					}
				}
				//返回五大部门的数据
				machining_detail = initialPMWODataByYear(machining_list,machining_detail,currYear,period);
				switch_detail = initialPMWODataByYear(switch_list,switch_detail,currYear,period);
				transformer_detail = initialPMWODataByYear(transformer_list,transformer_detail,currYear,period);
				outfit_detail = initialPMWODataByYear(outfit_list,outfit_detail,currYear,period);
				other_detail = initialPMWODataByYear(other_list,other_detail,currYear,period);
				
				//将五大部门数据转成JSON
				back_list.add(transformer_detail);
				back_list.add(outfit_detail);
				back_list.add(machining_detail);
				back_list.add(switch_detail);
				back_list.add(other_detail);
				
			}
		}catch(Exception e){
			LOGGER.error("buildDataForDept() method occures exception......Exception:"+e.getMessage());
		}
		return back_list;
	}
	
	/*
	 *2.按年统计PM，初始化数据（查询不到的年份Data值用0表示）
	 *@param list 单个部门的查询结果
	 */
	public ChartDetailInfo initialPMWODataByYear(List<ChartInfo> list,ChartDetailInfo rawDetailInfo,int currYear,int period)throws PmmsException{
		Double[] arr_back = new Double[period];
//		//月份集合
//		List<String> month_list = new ArrayList<String>();
//		//数值集合
//		List<Double> data_list = new ArrayList<Double>();
		List<Double> data = null;
		ChartDetailInfo chartDetailInfo = new ChartDetailInfo();
		chartDetailInfo.setName(rawDetailInfo.getName());
		
		int added = 0;
		//
		if(period==Constants.PERIOD_YEAR){
			added = currYear-Constants.PERIOD_YEAR;
		}
		
		if(!CollectionUtils.isEmpty(list)){
			for(int i=0;i<period;i++){
				for(int k=0;k<list.size();k++){
					ChartInfo chartInfo = list.get(k);
					int maintMonth = Integer.parseInt(chartInfo.getMaintMonth());
					if((i+1+added)==maintMonth){
						arr_back[i] = chartInfo.getCount();
						break;
					}
					if(k==list.size()-1){
						arr_back[i] = 0.0;
					}
				}
			}
			data = Arrays.asList(arr_back);
		}
//		else{//统计的周期中，这个部门没有数据
//			data = null;
//		}
		chartDetailInfo.setData(data);
		
		return chartDetailInfo;
	}
	
	//查询平均维修时间。一年12个月内，五大部门。
	public ServiceReturns getMTTRRateByYear(String year)throws PmmsException{
		serviceReturns = new ServiceReturns();
		List<ChartDetailInfo> back_list = new ArrayList<ChartDetailInfo>();
		
		int currYearInt = Tools.getCurrentYear();
		String miantStartDate = "";
		String miantEndDate = "";
		int queryYear = 0;
		if(Tools.isBlank(year)){
			queryYear = currYearInt;
			miantStartDate = currYearInt+"-01-01 00:00:00";
			miantEndDate = currYearInt+"-12-31 23:59:59";
		}else{
			queryYear = Integer.parseInt(year);
			miantStartDate = year+"-01-01 00:00:00";
			miantEndDate = year+"-12-31 23:59:59";
		}
		LOGGER.info("In getMTTRRateByYear() method...... miantStartDate="+miantStartDate+";miantEndDate="+miantEndDate);
		//按部门分组
		
		
//		List<ChartDetailInfo> back_list = new ArrayList<ChartDetailInfo>();
		try{
			List<ChartInfo> list = chartDao.findMaintRecordByYear(miantStartDate, miantEndDate);
			//判断查询结果中，1至12月份的数量
			ChartInfo chartInfo = null;
			List<ChartInfo> machining_list = new ArrayList<ChartInfo>();
			List<ChartInfo> switch_list = new ArrayList<ChartInfo>();
			List<ChartInfo> transformer_list = new ArrayList<ChartInfo>();
			List<ChartInfo> outfit_list = new ArrayList<ChartInfo>();
			List<ChartInfo> other_list = new ArrayList<ChartInfo>();
			List<ChartInfo> avg_list = null;//总计
			
			ChartDetailInfo machining_detail = new ChartDetailInfo(Constants.DEPT_MACHINING_NM);
			ChartDetailInfo switch_detail = new ChartDetailInfo(Constants.DEPT_SWITCH_NM);
			ChartDetailInfo transformer_detail = new ChartDetailInfo(Constants.DEPT_TRANSFORMER_NM);
			ChartDetailInfo outfit_detail = new ChartDetailInfo(Constants.DEPT_OUTFIT_NM);
			ChartDetailInfo other_detail = new ChartDetailInfo(Constants.DEPT_OTHER_NM);
			ChartDetailInfo avg_detail = new ChartDetailInfo(Constants.DEPT_AVG_NM);
			
			if(!CollectionUtils.isEmpty(list)){
				for(int i=0;i<list.size();i++){
					chartInfo = list.get(i);
					String deptId = chartInfo.getDeptId();
					if(deptId.equalsIgnoreCase(Constants.DEPT_MACHINING)){
						machining_list.add(chartInfo);
					}else if(deptId.equalsIgnoreCase(Constants.DEPT_SWITCH)){
						switch_list.add(chartInfo);
					}else if(deptId.equalsIgnoreCase(Constants.DEPT_TRANSFORMER)){
						transformer_list.add(chartInfo);
					}else if(deptId.equalsIgnoreCase(Constants.DEPT_OUTFIT)){
						outfit_list.add(chartInfo);
					}else if(deptId.equalsIgnoreCase(Constants.DEPT_OTHER)){
						other_list.add(chartInfo);
					}else{
						LOGGER.error("equipment department id unmatched!");
					}
				}
				//返回五大部门的数据
				machining_list = initialMTTRData(machining_list,machining_detail,Constants.PERIOD_MONTH,queryYear);
				switch_list = initialMTTRData(switch_list,switch_detail,Constants.PERIOD_MONTH,queryYear);
				transformer_list = initialMTTRData(transformer_list,transformer_detail,Constants.PERIOD_MONTH,queryYear);
				outfit_list = initialMTTRData(outfit_list,outfit_detail,Constants.PERIOD_MONTH,queryYear);
				other_list = initialMTTRData(other_list,other_detail,Constants.PERIOD_MONTH,queryYear);
				
				List<List<ChartInfo>> total_list = new ArrayList<List<ChartInfo>>();
				total_list.add(machining_list);
				total_list.add(switch_list);
				total_list.add(transformer_list);
				total_list.add(outfit_list);
				total_list.add(other_list);
				
				avg_list = calculateMTTRRateAvg(total_list,Constants.PERIOD_MONTH);
				
				//计算五大部门指标值
				machining_detail = calculateMTTRRate(machining_list,machining_detail,Constants.PERIOD_MONTH);
				switch_detail = calculateMTTRRate(switch_list,switch_detail,Constants.PERIOD_MONTH);
				transformer_detail = calculateMTTRRate(transformer_list,transformer_detail,Constants.PERIOD_MONTH);
				outfit_detail = calculateMTTRRate(outfit_list,outfit_detail,Constants.PERIOD_MONTH);
				other_detail = calculateMTTRRate(other_list,other_detail,Constants.PERIOD_MONTH);
				avg_detail = calculateMTTRRate(avg_list,avg_detail,Constants.PERIOD_MONTH);
				
				//将五大部门数据转成JSON
				back_list.add(transformer_detail);
				back_list.add(outfit_detail);
				back_list.add(machining_detail);
				back_list.add(switch_detail);
				back_list.add(other_detail);
				back_list.add(avg_detail);
				
				
			}
			serviceReturns.putData("list",back_list);
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.error("getMTTRRateByYear() method occures exception......Exception:"+e.getMessage());
		}
		return serviceReturns;
		
	}
	
	//查询单个设备的MTTR
	public ServiceReturns getSingleMTTRRateByYear(String equipId,String year)throws PmmsException{
		serviceReturns = new ServiceReturns();
		List<ChartDetailInfo> back_list = new ArrayList<ChartDetailInfo>();
		ChartDetailInfo chartDetailInfo = new ChartDetailInfo();
		int queryYear = 0;
		//验证编号是否存在
		EquipInfo equipInfo = equipInfoService.getEquipInfoByEpId(equipId);
		if(Tools.isNull(equipInfo)){
			back_list.add(chartDetailInfo);
			serviceReturns.putData("list",back_list);
			return serviceReturns;
		}
		
		//查询图表数据
		chartDetailInfo.setName(equipId);
		int currYearInt = Tools.getCurrentYear();
		String miantStartDate = "";
		String miantEndDate = "";
		if(Tools.isBlank(year)){
			queryYear = currYearInt;
			miantStartDate = currYearInt+"-01-01 00:00:00";
			miantEndDate = currYearInt+"-12-31 23:59:59";
		}else{
			queryYear = Integer.parseInt(year);
			miantStartDate = year+"-01-01 00:00:00";
			miantEndDate = year+"-12-31 23:59:59";
		}
		try{
			List<ChartInfo> list = chartDao.findSingleMaintRecordByYear(equipId, miantStartDate, miantEndDate);
			list = initialMTTRData(list,null ,Constants.PERIOD_MONTH,queryYear);
			chartDetailInfo = calculateMTTRRate(list,chartDetailInfo,Constants.PERIOD_MONTH);
		}catch(Exception e){
			LOGGER.error("getSingleMTTRRateByYear() method occures exception......Exception:"+e.getMessage());
		}
		back_list.add(chartDetailInfo);
		serviceReturns.putData("list",back_list);
		return serviceReturns;
	}
	
	//按部门，查询近5年的MTTR
	public ServiceReturns getMTTRRateforYears()throws PmmsException{
		serviceReturns = new ServiceReturns();
		List<ChartDetailInfo> back_list = new ArrayList<ChartDetailInfo>();
		int currYearInt = Tools.getCurrentYear();
//		String currYear = currYearInt+"-12-31 23:59:59";
		String fromYear = (currYearInt-4)+"-01-01 00:00:00";
		LOGGER.info("In getMTTRRateforYears() method...... fromYear="+fromYear);
		//按部门分组
		try{
			List<ChartInfo> list = chartDao.findMaintRecordForYears(fromYear);
			ChartInfo chartInfo = null;
			List<ChartInfo> machining_list = new ArrayList<ChartInfo>();
			List<ChartInfo> switch_list = new ArrayList<ChartInfo>();
			List<ChartInfo> transformer_list = new ArrayList<ChartInfo>();
			List<ChartInfo> outfit_list = new ArrayList<ChartInfo>();
			List<ChartInfo> other_list = new ArrayList<ChartInfo>();
			List<ChartInfo> avg_list = null;//总计
			
			ChartDetailInfo machining_detail = new ChartDetailInfo(Constants.DEPT_MACHINING_NM);
			ChartDetailInfo switch_detail = new ChartDetailInfo(Constants.DEPT_SWITCH_NM);
			ChartDetailInfo transformer_detail = new ChartDetailInfo(Constants.DEPT_TRANSFORMER_NM);
			ChartDetailInfo outfit_detail = new ChartDetailInfo(Constants.DEPT_OUTFIT_NM);
			ChartDetailInfo other_detail = new ChartDetailInfo(Constants.DEPT_OTHER_NM);
			ChartDetailInfo avg_detail = new ChartDetailInfo(Constants.DEPT_AVG_NM);
			
			if(!CollectionUtils.isEmpty(list)){
				for(int i=0;i<list.size();i++){
					chartInfo = list.get(i);
					String deptId = chartInfo.getDeptId();
					if(deptId.equalsIgnoreCase(Constants.DEPT_MACHINING)){
						machining_list.add(chartInfo);
					}else if(deptId.equalsIgnoreCase(Constants.DEPT_SWITCH)){
						switch_list.add(chartInfo);
					}else if(deptId.equalsIgnoreCase(Constants.DEPT_TRANSFORMER)){
						transformer_list.add(chartInfo);
					}else if(deptId.equalsIgnoreCase(Constants.DEPT_OUTFIT)){
						outfit_list.add(chartInfo);
					}else if(deptId.equalsIgnoreCase(Constants.DEPT_OTHER)){
						other_list.add(chartInfo);
					}else{
						LOGGER.error("equipment department id unmatched!");
					}
				}
				//返回五大部门的数据
				machining_list = initialMTTRDataForYears(machining_list,machining_detail,Constants.PERIOD_YEAR);
				switch_list = initialMTTRDataForYears(switch_list,switch_detail,Constants.PERIOD_YEAR);
				transformer_list = initialMTTRDataForYears(transformer_list,transformer_detail,Constants.PERIOD_YEAR);
				outfit_list = initialMTTRDataForYears(outfit_list,outfit_detail,Constants.PERIOD_YEAR);
				other_list = initialMTTRDataForYears(other_list,other_detail,Constants.PERIOD_YEAR);
				
				List<List<ChartInfo>> total_list = new ArrayList<List<ChartInfo>>();
				total_list.add(machining_list);
				total_list.add(switch_list);
				total_list.add(transformer_list);
				total_list.add(outfit_list);
				total_list.add(other_list);
				
				avg_list = calculateMTTRRateAvg(total_list,Constants.PERIOD_YEAR);
				
				//计算五大部门指标值
				machining_detail = calculateMTTRRate(machining_list,machining_detail,Constants.PERIOD_YEAR);
				switch_detail = calculateMTTRRate(switch_list,switch_detail,Constants.PERIOD_YEAR);
				transformer_detail = calculateMTTRRate(transformer_list,transformer_detail,Constants.PERIOD_YEAR);
				outfit_detail = calculateMTTRRate(outfit_list,outfit_detail,Constants.PERIOD_YEAR);
				other_detail = calculateMTTRRate(other_list,other_detail,Constants.PERIOD_YEAR);
				avg_detail = calculateMTTRRate(avg_list,avg_detail,Constants.PERIOD_YEAR);
				
				//将五大部门数据转成JSON
				back_list.add(transformer_detail);
				back_list.add(outfit_detail);
				back_list.add(machining_detail);
				back_list.add(switch_detail);
				back_list.add(other_detail);
				back_list.add(avg_detail);
				
				String[] backPeriod = new String[Constants.PERIOD_YEAR];
				for(int i=0;i<Constants.PERIOD_YEAR;i++){
					backPeriod[i] = String.valueOf(currYearInt-Constants.PERIOD_YEAR+i+1);
				}
				
				serviceReturns.putData("backPeriod",backPeriod);
				
				
			}
			serviceReturns.putData("list",back_list);
		}catch(Exception e){
			LOGGER.error("getMTTRRateforYears() method occures exception......Exception:"+e.getMessage());
		}
		return serviceReturns;
	}
	
	//MTTR计算总计
	public List<ChartInfo> calculateMTTRRateAvg(List<List<ChartInfo>>  list,int period){
		List<ChartInfo> dept_avg_list = new ArrayList<ChartInfo>();//总计
		
		double[] amouts = new double[period];
		int[] counter = new int[period];
		for(int k=0;k<list.size();k++){
			List<ChartInfo> lst = list.get(k);
			for(int p=0;p<lst.size();p++){
				amouts[p] = amouts[p]+lst.get(p).getAmount();
				counter[p] = counter[p]+lst.get(p).getCounter();
//				System.out.println("amount="+lst.get(p).getAmount()+"counter="+lst.get(p).getCounter());
			}
//			System.out.println("===========");
		}
//		System.out.println("amouts="+amouts+"counter="+counter);
		
		for(int i=0;i<period;i++){
			ChartInfo chartInfo = new ChartInfo();
			chartInfo.setAmount(amouts[i]);
			chartInfo.setCounter(counter[i]);
			dept_avg_list.add(chartInfo);
		}
		
		return dept_avg_list;
	}
	
	//MTTR 部门分组
	public List<ChartInfo> initialMTTRData(List<ChartInfo>  list,ChartDetailInfo chartDetailInfo,int period,int queryYear){
		List<ChartInfo> backList = new ArrayList<ChartInfo>();
		int currYear = Tools.getCurrentYear();
//		String currYearBegin = Tools.getCurrentYear()+"-01-01 00:00:00";
		String currYearBegin = queryYear+"-01-01 00:00:00";
		String currYearEnd = queryYear+"-12-31 23:59:59";
		for(int i=0;i<period;i++){
			backList.add(new ChartInfo());
		}
		double[] arrMonthData = new double[period];//一个部门下12个月的维修时间
		int[] arrCount = new int[period];//每个月的维修次数
		if(!CollectionUtils.isEmpty(list)){
			ChartInfo chartInfo = null;
			for(int i=0;i<list.size();i++){
				ChartInfo chartInfoBack = new ChartInfo();
				ChartInfo chartInfoBack2 = new ChartInfo();
				chartInfo = list.get(i);
				Date sDate = chartInfo.getMaintStartDate();//维修开始时间
				Date eDate = chartInfo.getMaintEndDate();//维修结束时间
				Date lastDateMonth = Tools.getLastDayByDate(sDate);//维修开始月的最后一天
				Date beginDateMonth = Tools.getFirstDayByDate(eDate);//维修结束月的第一天
				Date lastDateYear = Tools.getLastDayofYearByDate(sDate);//维修开始年的最后一天
				int sMonth = 0;//维修开始月份
				int eMonth = 0;//维修结束月份
				int gapMonth = 0;//维修起止月份的差距月份
				
				sMonth = Tools.getMonthByDate(sDate)+1;
				eMonth = Tools.getMonthByDate(eDate)+1;
				gapMonth = eMonth-sMonth;
				
				//跨年的（从前些年而来）
				try {
					if(sDate.getTime()<Tools.parseToDate(currYearBegin,Constants.DATE_PATTEN_SEC).getTime()){
						if(eDate.getTime()<Tools.parseToDate(currYearEnd,Constants.DATE_PATTEN_SEC).getTime()){//跨部分年
							arrMonthData[eMonth-1] = arrMonthData[eMonth-1]+Tools.getGapWithHour(beginDateMonth, eDate);
							arrCount[eMonth-1]++;
							chartInfoBack2.setAmount(arrMonthData[eMonth-1]);
							chartInfoBack2.setCounter(arrCount[eMonth-1]);
							backList.set(eMonth-1, chartInfoBack2);
							
							eMonth--;
						}else{//跨了整个当前年
							eMonth = period;
						}
						for(int k=0;k<eMonth;k++){
//							arrMonthData[k] = arrMonthData[k]+Tools.getTotalHrsofMonth(currYear, (k+1));
							arrMonthData[k] = arrMonthData[k]+Tools.getTotalHrsofMonth(queryYear, (k+1));
							arrCount[k]++;
							
							ChartInfo tempChartInfo = new ChartInfo();
							
							tempChartInfo.setAmount(arrMonthData[k]);
							tempChartInfo.setCounter(arrCount[k]);
							backList.set(k, tempChartInfo);
						}
					}else{//不是前些年跨过来的(计算所查询的年的值)
						if(eDate.getTime()>Tools.parseToDate(currYearEnd,Constants.DATE_PATTEN_SEC).getTime()){//维修结束日期大于所查询的年份(只要获取sDate到这一年底的时间差)
							eMonth = 12;
							eDate = lastDateYear; 
							gapMonth = eMonth-sMonth;
						}
						if(gapMonth == 0){
							arrMonthData[sMonth-1] = arrMonthData[sMonth-1]+Tools.getGapWithHour(sDate, eDate);
							arrCount[sMonth-1]++;
							chartInfoBack.setAmount(arrMonthData[sMonth-1]);
							chartInfoBack.setCounter(arrCount[sMonth-1]);
							backList.set(sMonth-1, chartInfoBack);
						}else if(gapMonth >= 1){
							arrMonthData[sMonth-1] = arrMonthData[sMonth-1]+Tools.getGapWithHour(sDate, lastDateMonth);
							arrMonthData[eMonth-1] = arrMonthData[eMonth-1]+Tools.getGapWithHour(beginDateMonth, eDate);
							arrCount[sMonth-1]++;
							arrCount[eMonth-1]++;
							
							chartInfoBack.setAmount(arrMonthData[sMonth-1]);
							chartInfoBack.setCounter(arrCount[sMonth-1]);
							backList.set(sMonth-1, chartInfoBack);
							
							chartInfoBack2.setAmount(arrMonthData[eMonth-1]);
							chartInfoBack2.setCounter(arrCount[eMonth-1]);
							backList.set(eMonth-1, chartInfoBack2);
							if(gapMonth>1){
								for(int k=sMonth;k<eMonth;k++){
									arrMonthData[k] = arrMonthData[k]+Tools.getTotalHrsofMonth(Tools.getYearByDate(sDate), (k+1));/*getYearByDate未测试*/
									arrCount[k]++;
									
									ChartInfo tempChartInfo = new ChartInfo();
									
									tempChartInfo.setAmount(arrMonthData[k]);
									tempChartInfo.setCounter(arrCount[k]);
									backList.set(k, tempChartInfo);
								}
							}
						}else{//????还要添加一个大的if else,计算跨到下一年的数据????(计算所查询的年的下一年的值)
							LOGGER.error("maint_start_date is greater than maint_end_date!");
						}
					}
				}
				catch (ParseException e) {
					LOGGER.error("initialMTTRData method occures exception......Exception:"+e.getMessage());
				}
				
			}
		}
		
		return backList;
	}
	
	//MTTR 5年统计，初始化数据
	public List<ChartInfo> initialMTTRDataForYears(List<ChartInfo>  list,ChartDetailInfo chartDetailInfo,int period){
		List<ChartInfo> backList = new ArrayList<ChartInfo>();
		int currYear = Tools.getCurrentYear();
		for(int i=0;i<period;i++){
			backList.add(new ChartInfo());
		}
		double[] arrYearData = new double[period];//一个部门下5年的维修时间
		int[] arrCount = new int[period];//每个月的维修次数
		if(!CollectionUtils.isEmpty(list)){
			ChartInfo chartInfo = null;
			for(int i=0;i<list.size();i++){
				ChartInfo chartInfoBack = new ChartInfo();
				ChartInfo chartInfoBack2 = new ChartInfo();
				chartInfo = list.get(i);
				Date sDate = chartInfo.getMaintStartDate();//维修开始时间
				Date eDate = chartInfo.getMaintEndDate();//维修结束时间
				Date lastDateYear = Tools.getLastDayofYearByDate(sDate);//维修开始年的最后一天
				Date beginDateYear = Tools.getFirstDayofYearByDate(eDate);//维修结束年的第一天
				Date beginDateofYears = Tools.firstDateStrOfYrDate(currYear-period+1);//五年中第一年的第一天
				
				int sYear = 0;//维修开始年份
				int eYear = 0;//维修结束年份
				int gapYear = 0;//维修起止年份的差距年份
				int incr = currYear-period;//增量
				
				sYear = Tools.getYearByDate(sDate);
				eYear = Tools.getYearByDate(eDate);
				gapYear = eYear-sYear;
				
				if(sYear<(currYear-period+1)){//跨年的（从前些年而来）。sDate在5年区间第一年的左边
					if(eYear-(incr+1)==0){//在第一年结束
						arrYearData[eYear-(incr+1)] = arrYearData[eYear-(incr+1)]+Tools.getGapWithHour(beginDateofYears, eDate);//arrYearData[0] = ...
						arrCount[eYear-(incr+1)]++;
						chartInfoBack2.setAmount(arrYearData[eYear-(incr+1)]);
						chartInfoBack2.setCounter(arrCount[eYear-(incr+1)]);
						backList.set(eYear-(incr+1), chartInfoBack2);
					}else if(eYear-(incr+1)>=1){//中间横跨了整年
						for(int k=incr+1;k<eYear;k++){
							arrYearData[k-1-incr] = arrYearData[k-1-incr]+Tools.getTotalHrsofYear(k);
							arrCount[k-1-incr]++;
							
							ChartInfo tempChartInfo = new ChartInfo();
							
							tempChartInfo.setAmount(arrYearData[k-1-incr]);
							tempChartInfo.setCounter(arrCount[k-1-incr]);
							backList.set(k-1-incr, tempChartInfo);
						}
					}
				}else{//sDate和eDate都在5年区间内部
					if(gapYear == 0){
						arrYearData[sYear-1-incr] = arrYearData[sYear-1-incr]+Tools.getGapWithHour(sDate, eDate);
						arrCount[sYear-1-incr]++;
						chartInfoBack.setAmount(arrYearData[sYear-1-incr]);
						chartInfoBack.setCounter(arrCount[sYear-1-incr]);
						backList.set(sYear-1-incr, chartInfoBack);
					}else if(gapYear >= 1){
						//sDate到sYear这年的最后一天时差
						arrYearData[sYear-1-incr] = arrYearData[sYear-1-incr]+Tools.getGapWithHour(sDate, lastDateYear);
						//eDate到eYear这年的第一天时差
						arrYearData[eYear-1-incr] = arrYearData[eYear-1-incr]+Tools.getGapWithHour(beginDateYear, eDate);
						arrCount[sYear-1-incr]++;
						arrCount[eYear-1-incr]++;
						
						chartInfoBack.setAmount(arrYearData[sYear-1-incr]);
						chartInfoBack.setCounter(arrCount[sYear-1-incr]);
						backList.set(sYear-1-incr, chartInfoBack);
						
						chartInfoBack2.setAmount(arrYearData[eYear-1-incr]);
						chartInfoBack2.setCounter(arrCount[eYear-1-incr]);
						backList.set(eYear-1-incr, chartInfoBack2);
						if(gapYear>1){
							for(int k=sYear+1;k<eYear;k++){
								arrYearData[k-1-incr] = arrYearData[k-1-incr]+Tools.getTotalHrsofYear(k);
								arrCount[k-1-incr]++;
								
								ChartInfo tempChartInfo = new ChartInfo();
								
								tempChartInfo.setAmount(arrYearData[k-1-incr]);
								tempChartInfo.setCounter(arrCount[k-1-incr]);
								backList.set(k-1-incr, tempChartInfo);
							}
						}
					}else{
						LOGGER.error("maint_start_date is greater than maint_end_date!");
					}
				}
				
			}
		}
		
		return backList;
	}
	
	//MTTR 计算指标值（有问题异常）
	public ChartDetailInfo calculateMTTRRate(List<ChartInfo>  list,ChartDetailInfo chartDetailInfo,int period){
		ChartDetailInfo chartDetailBack = new ChartDetailInfo();
		List<Double> data = new ArrayList<Double>();
		for(int i=0;i<period;i++){
			data.add(0.0);
		}
		chartDetailBack.setName(chartDetailInfo.getName());
		if(!CollectionUtils.isEmpty(list)){
			for(int i=0;i<list.size();i++){
//				ChartInfo chartInfo = new ChartInfo();
				ChartInfo chartInfo = null;
				chartInfo = list.get(i);
				if(Tools.isNull(chartInfo)){
					data.set(i, 0.0);
				}else{
					double amount = chartInfo.getAmount();
					int counter = chartInfo.getCounter();
					if(counter==0){
						data.set(i, 0.0);
					}else{
						data.set(i, Tools.roundDecimal2(amount/counter));
					}
				}
				
			}
		}
		chartDetailBack.setData(data);
		
		
		return chartDetailBack;
	}
	
	/**
	 * 单个维修备件使用数量统计
	 * @Author:  
	 * @param spId 备件编号
	 * @return
	 * @throws 
	 * @Description:
	 */
	@SuppressWarnings("serial")
	public ServiceReturns getSpNumBySpId(ChartInfoParam  chartPara){
		ChartInfo chartInfo = null;
		String spId=chartPara.getSparePartId();
		List<ChartInfo> spList=chartDao.getSpNumBySpId(chartPara);
		List<ChartDetailInfo> chartDetailLst=new ArrayList<ChartDetailInfo>();
		ChartDetailInfo chartDetail = new ChartDetailInfo();
		serviceReturns = new ServiceReturns();
		List<Double> singleSPDataLst = new ArrayList<Double>();
		for(int i=0;i<12;i++){
			singleSPDataLst.add(0.0);
		}
		List<Integer> monLst= new ArrayList<Integer>();
		if(!CollectionUtils.isEmpty(spList)){
			 chartDetail.setName(spList.get(0).getSparePartName());
			for(int i=0;i<spList.size();i++){
				chartInfo=spList.get(i);
				int month=Integer.parseInt(chartInfo.getMaintMonth());
				monLst.add(month-1);
				//singleSPDataLst.add(month-1,chartInfo.getCount());
				singleSPDataLst.set(month-1,chartInfo.getCount());
			}
			
		}else if(!Tools.isNull(sparePartInfoDao.getSPInfoBySPId(spId))){
			chartDetail.setName(sparePartInfoDao.getSPInfoBySPId(spId).getSparePartName());
		}
		chartDetail.setData(singleSPDataLst);
		chartDetailLst.add(chartDetail);
		serviceReturns.putData("list",chartDetailLst);
		return serviceReturns;	
	}
	/**
	 * 维修备件使用数量统计 ---按部门
	 * @Author:  
	 * @param 
	 * @return
	 * @throws 
	 * @Description:
	 */
	public ServiceReturns getSpNumBydept(ChartInfoParam  chartPara){
		serviceReturns = new ServiceReturns();
		List<ChartInfo> spList=chartDao.getSpNumBydept(chartPara);
		List<ChartDetailInfo> back_list = new ArrayList<ChartDetailInfo>();
		//ChartDetailInfo chartDetail = new ChartDetailInfo();
		ChartInfo chartInfo = null;
		List<ChartInfo> machining_list = new ArrayList<ChartInfo>();
		List<ChartInfo> switch_list = new ArrayList<ChartInfo>();
		List<ChartInfo> transformer_list = new ArrayList<ChartInfo>();
		List<ChartInfo> outfit_list = new ArrayList<ChartInfo>();
		List<ChartInfo> other_list = new ArrayList<ChartInfo>();
		List<ChartInfo> total_list =chartDao.getTotalSpNumByMonth(chartPara);
		List<Double> spDataLst = new ArrayList<Double>();
		for(int i=0;i<12;i++){
			spDataLst.add(0.0);
		}
		ChartDetailInfo machining_detail = new ChartDetailInfo(Constants.DEPT_MACHINING_NM);
		ChartDetailInfo switch_detail = new ChartDetailInfo(Constants.DEPT_SWITCH_NM);
		ChartDetailInfo transformer_detail = new ChartDetailInfo(Constants.DEPT_TRANSFORMER_NM);
		ChartDetailInfo outfit_detail = new ChartDetailInfo(Constants.DEPT_OUTFIT_NM);
		ChartDetailInfo other_detail = new ChartDetailInfo(Constants.DEPT_OTHER_NM);
		ChartDetailInfo total_detail = new ChartDetailInfo(Constants.DEPT_AVG_NM);
		
		if(!CollectionUtils.isEmpty(spList)){
			for(int i=0;i<spList.size();i++){
				chartInfo = spList.get(i);
				//部门
				String deptId = chartInfo.getDeptId();
				if(deptId.equalsIgnoreCase(Constants.DEPT_MACHINING)){
					machining_list.add(chartInfo);
				//	machining_data.add(chartInfo.getCount());
				}else if(deptId.equalsIgnoreCase(Constants.DEPT_SWITCH)){
					switch_list.add(chartInfo);
				//	switch_data.add(chartInfo.getCount());
				}else if(deptId.equalsIgnoreCase(Constants.DEPT_TRANSFORMER)){
					transformer_list.add(chartInfo);
				//	transformer_data.add(chartInfo.getCount());
				}else if(deptId.equalsIgnoreCase(Constants.DEPT_OUTFIT)){
					outfit_list.add(chartInfo);
				//	outfit_data.add(chartInfo.getCount());
				}else if(deptId.equalsIgnoreCase(Constants.DEPT_OTHER)){
					other_list.add(chartInfo);
				//	other_data.add(chartInfo.getCount());
				}else{
					LOGGER.error("equipment department id unmatched!");
				}
			}
			
			machining_detail.setData(spDataLst);
			if(!CollectionUtils.isEmpty(machining_list)){
				 machining_detail=formatSpDataByMonth(machining_list);
			}
			switch_detail.setData(spDataLst);
			if(!CollectionUtils.isEmpty(switch_list)){
				switch_detail=formatSpDataByMonth(switch_list);
			}
			transformer_detail.setData(spDataLst);
			if(!CollectionUtils.isEmpty(transformer_list)){
				transformer_detail=formatSpDataByMonth(transformer_list);
			}
			outfit_detail.setData(spDataLst);
			if(!CollectionUtils.isEmpty(outfit_list)){
				outfit_detail=formatSpDataByMonth(outfit_list);
			}
			other_detail.setData(spDataLst);
			if(!CollectionUtils.isEmpty(other_list)){
				other_detail=formatSpDataByMonth(other_list);
			}
			total_detail.setData(spDataLst);
			if(!CollectionUtils.isEmpty(total_list)){
				total_detail=formatSpDataByMonth(total_list);
			}
			
			
		}else{
			machining_detail.setData(null);
			switch_detail.setData(null);
			transformer_detail.setData(null);
			outfit_detail.setData(null);
			other_detail.setData(null);
			total_detail.setData(null);
		}
		//将五大部门数据转成JSON
		back_list.add(transformer_detail);
		back_list.add(outfit_detail);
		back_list.add(machining_detail);
		back_list.add(switch_detail);
		back_list.add(other_detail);
		back_list.add(total_detail);
		serviceReturns.putData("list",back_list);
		return serviceReturns;	
	}
	
	public ChartDetailInfo formatSpDataByMonth(List<ChartInfo> chartInfoLst){
		ChartInfo chartInfo =null;
		ChartDetailInfo chartDetail = new ChartDetailInfo();
		List<Double> spDataLst = new ArrayList<Double>();
		for(int i=0;i<12;i++){
			spDataLst.add(0.0);
		}
		//List<Integer> monLst= new ArrayList<Integer>();
			 chartDetail.setName(chartInfoLst.get(0).getDeptNm());
			for(int i=0;i<chartInfoLst.size();i++){
				chartInfo=chartInfoLst.get(i);
				int month=Integer.parseInt(chartInfo.getMaintMonth());
			//	monLst.add(month-1);
				//singleSPDataLst.add(month-1,chartInfo.getCount());
				spDataLst.set(month-1,chartInfo.getCount());
			}
		
		chartDetail.setData(spDataLst);
		return chartDetail;
	}
	/**
	 * 维修备件使用数量统计 ---按年
	 * @Author:  
	 * @param 
	 * @return
	 * @throws 
	 * @Description:
	 */
	public ServiceReturns getSpNumByYear(){
		serviceReturns = new ServiceReturns();
		List<ChartInfo> spList=chartDao.getSpNumByYear();
		List<ChartDetailInfo> back_list = new ArrayList<ChartDetailInfo>();
		ChartInfo chartInfo =null;
		List<ChartInfo> machining_list = new ArrayList<ChartInfo>();
		List<ChartInfo> switch_list = new ArrayList<ChartInfo>();
		List<ChartInfo> transformer_list = new ArrayList<ChartInfo>();
		List<ChartInfo> outfit_list = new ArrayList<ChartInfo>();
		List<ChartInfo> other_list = new ArrayList<ChartInfo>();
		List<ChartInfo> total_list =chartDao.getTotalSpNumForYear();
		List<Double> spDataLst = new ArrayList<Double>();
		for(int i=0;i<5;i++){
			spDataLst.add(0.0);
		}
		ChartDetailInfo machining_detail = new ChartDetailInfo(Constants.DEPT_MACHINING_NM);
		ChartDetailInfo switch_detail = new ChartDetailInfo(Constants.DEPT_SWITCH_NM);
		ChartDetailInfo transformer_detail = new ChartDetailInfo(Constants.DEPT_TRANSFORMER_NM);
		ChartDetailInfo outfit_detail = new ChartDetailInfo(Constants.DEPT_OUTFIT_NM);
		ChartDetailInfo other_detail = new ChartDetailInfo(Constants.DEPT_OTHER_NM);
		ChartDetailInfo total_detail = new ChartDetailInfo(Constants.DEPT_AVG_NM);
		
		if(!CollectionUtils.isEmpty(spList)){
			for(int i=0;i<spList.size();i++){
				chartInfo = spList.get(i);
				//部门
				String deptId = chartInfo.getDeptId();
				if(deptId.equalsIgnoreCase(Constants.DEPT_MACHINING)){
					machining_list.add(chartInfo);
				//	machining_data.add(chartInfo.getCount());
				}else if(deptId.equalsIgnoreCase(Constants.DEPT_SWITCH)){
					switch_list.add(chartInfo);
				//	switch_data.add(chartInfo.getCount());
				}else if(deptId.equalsIgnoreCase(Constants.DEPT_TRANSFORMER)){
					transformer_list.add(chartInfo);
				//	transformer_data.add(chartInfo.getCount());
				}else if(deptId.equalsIgnoreCase(Constants.DEPT_OUTFIT)){
					outfit_list.add(chartInfo);
				//	outfit_data.add(chartInfo.getCount());
				}else if(deptId.equalsIgnoreCase(Constants.DEPT_OTHER)){
					other_list.add(chartInfo);
				//	other_data.add(chartInfo.getCount());
				}else{
					LOGGER.error("equipment department id unmatched!");
				}
			}
			machining_detail.setData(spDataLst);
			if(!CollectionUtils.isEmpty(machining_list)){
				 machining_detail=formatSpDataByYear(machining_list);
			}
			switch_detail.setData(spDataLst);
			if(!CollectionUtils.isEmpty(switch_list)){
				switch_detail=formatSpDataByYear(switch_list);
			}
			transformer_detail.setData(spDataLst);
			if(!CollectionUtils.isEmpty(transformer_list)){
				transformer_detail=formatSpDataByYear(transformer_list);
			}
			outfit_detail.setData(spDataLst);
			if(!CollectionUtils.isEmpty(outfit_list)){
				outfit_detail=formatSpDataByYear(outfit_list);
			}
			other_detail.setData(spDataLst);
			if(!CollectionUtils.isEmpty(other_list)){
				other_detail=formatSpDataByYear(other_list);
			}
			total_detail.setData(spDataLst);
			if(!CollectionUtils.isEmpty(total_list)){
				total_detail=formatSpDataByYear(total_list);
			}
			
		}else{
			transformer_detail.setData(null);
			outfit_detail.setData(null);
			machining_detail.setData(null);
			switch_detail.setData(null);
			other_detail.setData(null);
			total_detail.setData(null);
		}
		//将五大部门数据转成JSON
		back_list.add(transformer_detail);
		back_list.add(outfit_detail);
		back_list.add(machining_detail);
		back_list.add(switch_detail);
		back_list.add(other_detail);
		back_list.add(total_detail);
		
		serviceReturns.putData("list",back_list);
		int currYearInt = Tools.getCurrentYear();
		String[] backPeriod = new String[Constants.PERIOD_YEAR];
					for(int i=0;i<Constants.PERIOD_YEAR;i++){
						backPeriod[i] = String.valueOf(currYearInt-Constants.PERIOD_YEAR+i+1);
					}
					
		serviceReturns.putData("backPeriod",backPeriod);
		return serviceReturns;	
	}
	
	public ChartDetailInfo formatSpDataByYear(List<ChartInfo> chartInfoLst){
		ChartInfo chartInfo = null;
		ChartDetailInfo chartDetail = new ChartDetailInfo();
		List<Double> spDataLst = new ArrayList<Double>();
		Calendar a=Calendar.getInstance();
		//当前年往前5年
		int startYear=a.get(Calendar.YEAR)-4;
		for(int i=0;i<5;i++){
			spDataLst.add(0.0);
		}
		//List<Integer> monLst= new ArrayList<Integer>();
			 chartDetail.setName(chartInfoLst.get(0).getDeptNm());
			for(int i=0;i<chartInfoLst.size();i++){
				chartInfo=chartInfoLst.get(i);
				int year=Integer.parseInt(chartInfo.getMaintMonth());
			//	monLst.add(month-1);
				//singleSPDataLst.add(month-1,chartInfo.getCount());
				spDataLst.set(year-startYear,chartInfo.getCount());
			}
		
		chartDetail.setData(spDataLst);
		return chartDetail;
	}	
	/**
	 * 单个维修备件费用统计 
	 * @Author:  
	 * @param 
	 * @return
	 * @throws 
	 * @Description:
	 */
	public ServiceReturns getSpFeeBySpId(ChartInfoParam  chartPara){
		ChartInfo chartInfo = null;
		String spId=chartPara.getSparePartId();
		List<ChartInfo> spList=chartDao.getSpFeeBySpId(chartPara);
		List<ChartDetailInfo> chartDetailLst=new ArrayList<ChartDetailInfo>();
		ChartDetailInfo chartDetail = new ChartDetailInfo();
		serviceReturns = new ServiceReturns();
		List<Double> singleSPDataLst = new ArrayList<Double>();
		for(int i=0;i<12;i++){
			singleSPDataLst.add(0.0);
		}
		List<Integer> monLst= new ArrayList<Integer>();
		if(!CollectionUtils.isEmpty(spList)){
			 chartDetail.setName(spList.get(0).getSparePartName());
			for(int i=0;i<spList.size();i++){
				chartInfo=spList.get(i);
				int month=Integer.parseInt(chartInfo.getMaintMonth());
				monLst.add(month-1);
				//singleSPDataLst.add(month-1,chartInfo.getCount());
				singleSPDataLst.set(month-1,chartInfo.getCount());
			}
			
		}else if(!Tools.isNull(sparePartInfoDao.getSPInfoBySPId(spId))){
			chartDetail.setName(sparePartInfoDao.getSPInfoBySPId(spId).getSparePartName());
		}
		chartDetail.setData(singleSPDataLst);
		chartDetailLst.add(chartDetail);
		serviceReturns.putData("list",chartDetailLst);
		return serviceReturns;
	}
	
	/**
	 * 维修备件费用统计 ---按部门
	 * @Author:  
	 * @param 
	 * @return
	 * @throws 
	 * @Description:
	 */
	public ServiceReturns getSpFeeByDept(ChartInfoParam  chartPara){
		serviceReturns = new ServiceReturns();
		List<ChartInfo> spList=chartDao.getSpFeeByDept(chartPara);
		List<ChartDetailInfo> back_list = new ArrayList<ChartDetailInfo>();
		ChartInfo chartInfo = null;
		List<ChartInfo> machining_list = new ArrayList<ChartInfo>();
		List<ChartInfo> switch_list = new ArrayList<ChartInfo>();
		List<ChartInfo> transformer_list = new ArrayList<ChartInfo>();
		List<ChartInfo> outfit_list = new ArrayList<ChartInfo>();
		List<ChartInfo> other_list = new ArrayList<ChartInfo>();
		List<ChartInfo> total_list =chartDao.getTotalSpFeeForMonth(chartPara);
		List<Double> spDataLst = new ArrayList<Double>();
		for(int i=0;i<12;i++){
			spDataLst.add(0.0);
		}
		ChartDetailInfo machining_detail = new ChartDetailInfo(Constants.DEPT_MACHINING_NM);
		ChartDetailInfo switch_detail = new ChartDetailInfo(Constants.DEPT_SWITCH_NM);
		ChartDetailInfo transformer_detail = new ChartDetailInfo(Constants.DEPT_TRANSFORMER_NM);
		ChartDetailInfo outfit_detail = new ChartDetailInfo(Constants.DEPT_OUTFIT_NM);
		ChartDetailInfo other_detail = new ChartDetailInfo(Constants.DEPT_OTHER_NM);
		ChartDetailInfo total_detail = new ChartDetailInfo(Constants.DEPT_AVG_NM);
		
		if(!CollectionUtils.isEmpty(spList)){
			for(int i=0;i<spList.size();i++){
				chartInfo = spList.get(i);
				//部门
				String deptId = chartInfo.getDeptId();
				if(deptId.equalsIgnoreCase(Constants.DEPT_MACHINING)){
					machining_list.add(chartInfo);
				//	machining_data.add(chartInfo.getCount());
				}else if(deptId.equalsIgnoreCase(Constants.DEPT_SWITCH)){
					switch_list.add(chartInfo);
				//	switch_data.add(chartInfo.getCount());
				}else if(deptId.equalsIgnoreCase(Constants.DEPT_TRANSFORMER)){
					transformer_list.add(chartInfo);
				//	transformer_data.add(chartInfo.getCount());
				}else if(deptId.equalsIgnoreCase(Constants.DEPT_OUTFIT)){
					outfit_list.add(chartInfo);
				//	outfit_data.add(chartInfo.getCount());
				}else if(deptId.equalsIgnoreCase(Constants.DEPT_OTHER)){
					other_list.add(chartInfo);
				//	other_data.add(chartInfo.getCount());
				}else{
					LOGGER.error("equipment department id unmatched!");
				}
			}
			machining_detail.setData(spDataLst);
			if(!CollectionUtils.isEmpty(machining_list)){
				 machining_detail=formatSpDataByMonth(machining_list);
			}
			switch_detail.setData(spDataLst);
			if(!CollectionUtils.isEmpty(switch_list)){
				switch_detail=formatSpDataByMonth(switch_list);
			}
			transformer_detail.setData(spDataLst);
			if(!CollectionUtils.isEmpty(transformer_list)){
				transformer_detail=formatSpDataByMonth(transformer_list);
			}
			outfit_detail.setData(spDataLst);
			if(!CollectionUtils.isEmpty(outfit_list)){
				outfit_detail=formatSpDataByMonth(outfit_list);
			}
			other_detail.setData(spDataLst);
			if(!CollectionUtils.isEmpty(other_list)){
				other_detail=formatSpDataByMonth(other_list);
			}
			total_detail.setData(spDataLst);
			if(!CollectionUtils.isEmpty(total_list)){
				total_detail=formatSpDataByMonth(total_list);
			}
			
			
		}else{
			machining_detail.setData(null);
			switch_detail.setData(null);
			transformer_detail.setData(null);
			outfit_detail.setData(null);
			other_detail.setData(null);
			total_detail.setData(null);
		}
		
		//将五大部门数据转成JSON
		back_list.add(transformer_detail);
		back_list.add(outfit_detail);
		back_list.add(machining_detail);
		back_list.add(switch_detail);
		back_list.add(other_detail);
		back_list.add(total_detail);
		
		serviceReturns.putData("list",back_list);
		return serviceReturns;	
	}
	/**
	 * 维修备件费用统计 ---按年跟部门
	 * @Author:  
	 * @param 
	 * @return
	 * @throws 
	 * @Description:
	 */
	public ServiceReturns getSpFeeByYear(){
		serviceReturns = new ServiceReturns();
		List<ChartInfo> spList=chartDao.getSpFeeByYear();
		List<ChartDetailInfo> back_list = new ArrayList<ChartDetailInfo>();
		ChartInfo chartInfo = null;
		List<ChartInfo> machining_list = new ArrayList<ChartInfo>();
		List<ChartInfo> switch_list = new ArrayList<ChartInfo>();
		List<ChartInfo> transformer_list = new ArrayList<ChartInfo>();
		List<ChartInfo> outfit_list = new ArrayList<ChartInfo>();
		List<ChartInfo> other_list = new ArrayList<ChartInfo>();
		List<ChartInfo> total_list =chartDao.getTotalSpFeeForYear();
		List<Double> spDataLst = new ArrayList<Double>();
		for(int i=0;i<5;i++){
			spDataLst.add(0.0);
		}
		ChartDetailInfo machining_detail = new ChartDetailInfo(Constants.DEPT_MACHINING_NM);
		ChartDetailInfo switch_detail = new ChartDetailInfo(Constants.DEPT_SWITCH_NM);
		ChartDetailInfo transformer_detail = new ChartDetailInfo(Constants.DEPT_TRANSFORMER_NM);
		ChartDetailInfo outfit_detail = new ChartDetailInfo(Constants.DEPT_OUTFIT_NM);
		ChartDetailInfo other_detail = new ChartDetailInfo(Constants.DEPT_OTHER_NM);
		ChartDetailInfo total_detail = new ChartDetailInfo(Constants.DEPT_AVG_NM);
		
		if(!CollectionUtils.isEmpty(spList)){
			for(int i=0;i<spList.size();i++){
				chartInfo = spList.get(i);
				//部门
				String deptId = chartInfo.getDeptId();
				
				if(deptId.equalsIgnoreCase(Constants.DEPT_MACHINING)){
					machining_list.add(chartInfo);
				}else if(deptId.equalsIgnoreCase(Constants.DEPT_SWITCH)){
					switch_list.add(chartInfo);
				}else if(deptId.equalsIgnoreCase(Constants.DEPT_TRANSFORMER)){
					transformer_list.add(chartInfo);
				}else if(deptId.equalsIgnoreCase(Constants.DEPT_OUTFIT)){
					outfit_list.add(chartInfo);
				}else if(deptId.equalsIgnoreCase(Constants.DEPT_OTHER)){
					other_list.add(chartInfo);
				}else{
					LOGGER.error("equipment department id unmatched!");
				}
			}
			machining_detail.setData(spDataLst);
			if(!CollectionUtils.isEmpty(machining_list)){
				 machining_detail=formatSpDataByYear(machining_list);
			}
			switch_detail.setData(spDataLst);
			if(!CollectionUtils.isEmpty(switch_list)){
				switch_detail=formatSpDataByYear(switch_list);
			}
			transformer_detail.setData(spDataLst);
			if(!CollectionUtils.isEmpty(transformer_list)){
				transformer_detail=formatSpDataByYear(transformer_list);
			}
			outfit_detail.setData(spDataLst);
			if(!CollectionUtils.isEmpty(outfit_list)){
				outfit_detail=formatSpDataByYear(outfit_list);
			}
			other_detail.setData(spDataLst);
			if(!CollectionUtils.isEmpty(other_list)){
				other_detail=formatSpDataByYear(other_list);
			}
			total_detail.setData(spDataLst);
			if(!CollectionUtils.isEmpty(total_list)){
				total_detail=formatSpDataByYear(total_list);
			}
			
			
		}else{
			machining_detail.setData(null);
			switch_detail.setData(null);
			transformer_detail.setData(null);
			outfit_detail.setData(null);
			other_detail.setData(null);
			total_detail.setData(null);
		}
		
		//将五大部门数据转成JSON
		back_list.add(transformer_detail);
		back_list.add(outfit_detail);
		back_list.add(machining_detail);
		back_list.add(switch_detail);
		back_list.add(other_detail);
		back_list.add(total_detail);
		
		serviceReturns.putData("list",back_list);
		int currYearInt = Tools.getCurrentYear();
		String[] backPeriod = new String[Constants.PERIOD_YEAR];
			for(int i=0;i<Constants.PERIOD_YEAR;i++){
				backPeriod[i] = String.valueOf(currYearInt-Constants.PERIOD_YEAR+i+1);
		}
					
		serviceReturns.putData("backPeriod",backPeriod);
		return serviceReturns;	
	}
	
	/**
	 * 返回月份数据对象Map<Month,List[实际停机天数，该月总天数,当月停机次数]>
	 * @Author: iGATE 
	 * @return
	 * @Description:
	 */
	private Map<String,List<String>> getMonthsMap(){
		Map<String,List<String>> map = new LinkedHashMap<String, List<String>>();
		List<String> data = null;
		for(int i=0; i < Constants.PERIOD_MONTH;i++){
			data = new ArrayList<String>();
			data.add("0");
			data.add("0");
			data.add("0");
			
			map.put(String.valueOf(i+1), data);
		}
		return map;
	}
	
	/**
   * 返回月份数据对象Map<Yr,List[实际停机天数，当前年设备总工作时长,总停机次数]>
   * @Author: iGATE 
   * @return
   * @Description:
   */
  private Map<String,List<String>> getYrsMap(){
    Map<String,List<String>> map = new LinkedHashMap<String, List<String>>();
    //Map<String,List<String>> map = new TreeMap<String, List<String>>();
    List<String> data = null;
    int currentYr = Tools.getCurrentYear();
    int endYr = Tools.getCurrentYear() - Constants.PERIOD_YEAR +  1;
    for(int i=endYr; i <= currentYr;i++){
      data = new ArrayList<String>();
      data.add("0");
      //data.add(String.valueOf(Tools.getTotalDaysOfYr(i)));
      data.add("0");
      
      data.add("0");
      map.put(String.valueOf(i), data);
    }
    return map;
  }
	
	/**
	 * 计算获得月份停机总时长
	 * @Author: iGATE 
	 * @param equipId 设备编号
	 * @return
	 * @throws ParseException
	 * @Description:
	 */
	private Map<String,List<ChartInfo>> getEquipByMonth(Map<String,List<Map<String,String>>> maps,int year,boolean isSingle) throws ParseException,PmmsException{
		Map<String,List<ChartInfo>> dataMap = new LinkedHashMap<String,List<ChartInfo>>();
		
		Map<String,String> equipMap = chartDao.getEquipCntByDept();
		
		if(!CollectionUtils.isEmpty(maps) ){
			Map.Entry<String, List<Map<String,String>>> entry = null;
			Set<Map.Entry<String, List<Map<String,String>>>> set = maps.entrySet();
			Iterator<Map.Entry<String, List<Map<String,String>>>> itor = set.iterator();
			String deptName = "";
			List<Map<String,String>> rows = null;
			String maint_st = "";
			String maint_ed = "";
			
			//int index = 0;
			int equipCnt = 0;
			while (itor.hasNext()){
				entry = itor.next();
				deptName = entry.getKey();
				rows = entry.getValue();
				
				//该部门下没有任何数据
				if(null  ==  rows){
					dataMap.put(deptName, null);
					continue;
				}
				Map<String,List<String>> months = getMonthsMap();
				Iterator<Map<String,String>> itor1 = rows.iterator();
				
				//index = 0;
				int[] ymdAry_st = null;
				int[] ymdAry_ed = null;
				equipCnt = Integer.parseInt(equipMap.get(deptName));
				while (itor1.hasNext()){
					Map<String,String> map = itor1.next();
					maint_st = map.get("maint_start_date");
					maint_ed = map.get("maint_end_date");
					
					ymdAry_st = Tools.splitDate(maint_st);
			    ymdAry_ed = Tools.splitDate(maint_ed);
			    //处理跨年问题(如果跨年，只需要取年份的最后一天即可)
			    if(ymdAry_st[0] < ymdAry_ed[0]){
			      maint_ed = Tools.conactEndDt(year);
			    }
			    
					calcDays(months,maint_st,maint_ed);
					//index++;
				}
				//所有数据遍历完毕，计算时间实际用天数
				List<ChartInfo> data = getChartDetail(months,equipCnt,isSingle);
				dataMap.put(deptName, data);
				
			}
		}else{
			throw new PmmsException("Invalid parameters!");
		}
		return dataMap;
	}
	
	
	/*private Map<String,List<ChartInfo>> getEquipByYr(int year) throws ParseException,PmmsException{
		ChartInfoParam param = new ChartInfoParam(null,Tools.conactStartDt(year),Tools.conactEndDt(year));
		
		Map<String,List<Map<String,String>>> maps = chartDao.getlstWorkorder(param);
		
		Map<String,String> equipMap = chartDao.getEquipCntByDept();
		
		Map<String,List<ChartInfo>> dataMap = new HashMap<String,List<ChartInfo>>();
		
		if(!CollectionUtils.isEmpty(maps) ){
			Map.Entry<String, List<Map<String,String>>> entry = null;
			Set<Map.Entry<String, List<Map<String,String>>>> set = maps.entrySet();
			Iterator<Map.Entry<String, List<Map<String,String>>>> itor = set.iterator();
			String deptName = "";
			List<Map<String,String>> rows = null;
			String maint_st = "";
			String maint_ed = "";
			
			int index = 0;
			while (itor.hasNext()){
				entry = itor.next();
				deptName = entry.getKey();
				rows = entry.getValue();
				
				Map<String,List<String>> months = getMonthsMap();
				Iterator<Map<String,String>> itor1 = rows.iterator();
				
				index = 0;
				while (itor1.hasNext()){
					Map<String,String> map = itor1.next();
					maint_st = map.get("maint_start_date");
					maint_ed = map.get("maint_end_date");
					
					calcDays(months,maint_st,maint_ed);
					index++;
				}
				//所有数据遍历完毕，计算时间实际用天数
				List<ChartInfo> data = getChartDetail(months,index,Integer.parseInt(equipMap.get(deptName)));
				dataMap.put(deptName, data);
				
			}
		}else{
			throw new PmmsException("Invalid parameters!");
		}
		return dataMap;
	}*/
	
	/**
	 * 设备使用间隔率，按年统计
	 * @Author: Flash 
	 * @param equipId 设备编号
	 * @return
	 * @throws ParseException
	 * @Description:
	 */
	public ServiceReturns getEquipMTBFByYr() throws ParseException {
		serviceReturns = new ServiceReturns();
		int year = Tools.getCurrentYear();
		ChartInfoParam param = new ChartInfoParam(null,Tools.conactStartDt(year),Tools.conactEndDt(year));
		
		Map<String, List<Map<String, String>>> maps = chartDao.getlstWorkorder(param);
		//按年份组装数据
		Map<String, List<ChartInfo>> map = getEquipUsageByYrCommon(maps);
		//根据公式计算报表数据
		List<ChartDetailInfo> lstDetail = calcMTBRRate(map,true);
		serviceReturns.putData("list", lstDetail);
		
		//年份list
		String[] backPeriod = new String[Constants.PERIOD_YEAR];
		for(int i=0;i<Constants.PERIOD_YEAR;i++){
			backPeriod[i] = String.valueOf(year-Constants.PERIOD_YEAR+i+1);
		}
		serviceReturns.putData("backPeriod",backPeriod);
		
		return serviceReturns;
	}
	
	
	/**
	 * 设备使用间隔率，单个统计
	 * @Author: Flash 
	 * @param equipId 设备编号为空时查询所有设备
	 * @param year 年份
	 * @return
	 * @throws ParseException
	 * @Description:
	 */
  public ServiceReturns getEquipMTBFByMonth(String equipId, String year) throws ParseException {
    serviceReturns = new ServiceReturns();
    try {
      ChartInfoParam param = new ChartInfoParam(equipId, null, null);
      if (!StringUtils.isEmpty(year)) {
        param = new ChartInfoParam(equipId, Tools.conactStartDt(Integer.parseInt(year)),
            Tools.conactEndDt(Integer.parseInt(year)));
      }
      
      Map<String, List<Map<String, String>>> maps = chartDao.getlstWorkorder(param);
      //按月份组装数据
      Map<String, List<ChartInfo>> map = getEquipByMonth(maps,Integer.parseInt(year),!StringUtils.isEmpty(equipId));
      //根据公式计算报表数据
      List<ChartDetailInfo> lstDetail = calcMTBRRate(map, false);
      if(!StringUtils.isEmpty(equipId)){
        if (!CollectionUtils.isEmpty(lstDetail)) {
          lstDetail.get(0).setName(equipId);
          lstDetail.remove(lstDetail.size()-1);
        }
      }
      
      serviceReturns.putData("list", lstDetail);
    } catch (PmmsException e) {
      LOGGER.error("获取单个平均维修间隔时间失败！", e);
    }
    return serviceReturns;
  }
	
	
	/**
	 * 设备使用间隔率，按指定年统计
	 * @Author: Flash 
	 * @param equipId 设备编号
	 * @return
	 * @throws ParseException
	 * @throws PmmsException 
	 * @Description:
	 */
	/*public ServiceReturns getSingleYrEquipMTBF(int year,String equipId) throws ParseException, PmmsException{
		serviceReturns = new ServiceReturns();
		ChartInfoParam param = new ChartInfoParam(equipId,Tools.conactStartDt(year),Tools.conactEndDt(year));
        Map<String,List<Map<String,String>>> maps = chartDao.getlstWorkorder(param);
		
		//按年份和部门组装数据
		Map<String,List<ChartInfo>> map = getEquipByMonth(maps);
		
		//根据公式计算报表数据
		List<ChartDetailInfo> lstDetail = calcMTBRRate(map,false);
		
		serviceReturns.putData("list", lstDetail);
		
		return serviceReturns;
	}*/
	
	private List<Double> getNullMonths(){
		List<Double> list  = new ArrayList<Double>();
		for(int i=0; i < Constants.PERIOD_MONTH;i++){
			list.add(null);
		}
		return list;
	}
	
	 private List<Double> getNullYrs(){
	    List<Double> list  = new ArrayList<Double>();
	    for(int i=0; i < Constants.PERIOD_YEAR;i++){
	      list.add(null);
	    }
	    return list;
	  }
	 
	 
	
	private Map<String,List<String>> getTolYrMap(){
	  Map<String,List<String>> map = new LinkedHashMap<String,List<String>>();
	  int min = Tools.getCurrentYear()-Constants.PERIOD_YEAR+1;
		for(int i=min; i <= Tools.getCurrentYear();i++){
      List<String> list = new LinkedList<String>();
      list.add("0");
      list.add("0");
      map.put(String.valueOf(i), list);
    }
		return map;
	}

	
	private Map<String,List<String>> getTolMonthMap(){
    Map<String,List<String>> map = new LinkedHashMap<String,List<String>>();
    for(int i=0; i < Constants.PERIOD_MONTH;i++){
      List<String> list = new LinkedList<String>();
      list.add("0");
      list.add("0");
      map.put(String.valueOf(i+1), list);
    }
    return map;
  }
  
 
  
  
	/**
	 * 使用公式计算停机间隔
	 * @Author: iGATE 
	 * @param map 工单数据集合
	 * @param isYear 是否是按年统计
	 * @return
	 * @Description:
	 */
  private List<ChartDetailInfo> calcMTBRRate(Map<String, List<ChartInfo>> map, boolean isYear) {
    List<ChartDetailInfo> lstDetail = null;
    try {
      if (!CollectionUtils.isEmpty(map)) {
        Map.Entry<String, List<ChartInfo>> entry = null;
        Set<Map.Entry<String, List<ChartInfo>>> set = map.entrySet();
        Iterator<Map.Entry<String, List<ChartInfo>>> itor = set.iterator();
        String deptName = "";
        List<ChartInfo> lstChart = null;
        lstDetail = new ArrayList<ChartDetailInfo>();
        ChartDetailInfo chartDetail = null;
        Map<String, List<String>> totalMonthMap = getTolMonthMap();
        Map<String, List<String>> totalYrMap = getTolYrMap();
        double rate = 0D;
        String period = "";
        double total1 = 0;
        double total2 = 0;
        List<String> valList = null;
        while (itor.hasNext()) {
          entry = itor.next();
          deptName = entry.getKey();
          lstChart = entry.getValue();
          chartDetail = new ChartDetailInfo();
          chartDetail.setName(deptName);
          //该部门下没有任何数据
          if (null == lstChart) {
            if (isYear) {
              chartDetail.setData(getNullYrs());
            } else {
              chartDetail.setData(getNullMonths());
            }
            lstDetail.add(chartDetail);
            continue;
          }
          Iterator<ChartInfo> itor1 = lstChart.iterator();
          List<Double> list = new ArrayList<Double>();
          while (itor1.hasNext()) {
            ChartInfo info = itor1.next();
            period = info.getMaintMonth();
            if (0 != info.getCounter()) {
              total1 = info.getAmount() - info.getCount();
              total2 = info.getCounter();
              rate = Tools.roundDecimal2((info.getAmount() - info.getCount()) / info.getCounter());
              //按统计指标求总和(年份或月份)
              
              if (isYear) {
                if (totalYrMap.containsKey(period)) {
                  sumTotal1(totalYrMap, total1, period);
                  sumTotal2(totalYrMap, total2, period);
                }
              } else {
                if (totalMonthMap.containsKey(period)) {
                  sumTotal1(totalMonthMap, total1, period);
                  sumTotal2(totalMonthMap, total2, period);
                }
              }
              
              
              if (0.0 == rate) {
                list.add(null);
              } else {
                list.add(Double.valueOf(rate));
              }
            } else {
              //全年下没有停机时间
              list.add(null);
            }
          }
          chartDetail.setData(list);
          lstDetail.add(chartDetail);
        }
        //增加总计指标项
        ChartDetailInfo totalDetail = new ChartDetailInfo();
        totalDetail.setName("总计");
        List<Double> list = null;
        if (isYear) {
          list = calcTotal(totalYrMap.values(),false);
        } else {
          list = calcTotal(totalMonthMap.values(),false);
        }
        totalDetail.setData(list);
        lstDetail.add(totalDetail);
      }
    } catch (Exception e) {
      LOGGER.error("failed to cald MTBR Rate", e);
    }
    return lstDetail;
  }

  private List<Double> calcTotal(Collection<List<String>> collect,boolean needPercentage) {
    List<Double> list = new ArrayList<Double>();
    List<String> tmp;
    Iterator<List<String>> itor1 = collect.iterator();
     double totl = 0;
     while (itor1.hasNext()){
       tmp = itor1.next();
       if(Double.parseDouble(tmp.get(0)) != 0.0){
         if(needPercentage){
           totl = Tools.roundDecimal2((Double.parseDouble(tmp.get(0)) / Double.parseDouble(tmp.get(1)))*100);
           list.add(Double.valueOf(totl));
         }else{
           totl = Tools.roundDecimal2((Double.parseDouble(tmp.get(0)) / Double.parseDouble(tmp.get(1))));
           list.add(Double.valueOf(totl));
         }
         
       }else{
         list.add(null);
       }
     }
     return list;
  }
	
	
	private List<ChartInfo> getChartDetail(Map<String,List<String>> monthsMap,int equipCnt,boolean isSingle){
		Map.Entry<String,List<String>> entry = null;
		Set<Map.Entry<String,List<String>>> set = monthsMap.entrySet();
		Iterator<Map.Entry<String,List<String>>> itor = set.iterator();
		List<ChartInfo> data = new ArrayList<ChartInfo>();
		
		ChartInfo chart = null;
		long outageDays = 0L;
		long totalDaysOfMonth = 0L;
		String month = "";
		int cnt = 0;
		while(itor.hasNext()){
			entry = itor.next();
			month = entry.getKey();
			List<String> value = entry.getValue();
			
			outageDays = Long.parseLong(value.get(0));
			totalDaysOfMonth = Long.parseLong(value.get(1));
			cnt = Integer.parseInt(value.get(2));
			
			chart = new ChartInfo();
			chart.setMaintMonth(month);
			chart.setCount(outageDays);
			if(isSingle){
			  chart.setAmount(totalDaysOfMonth);
			}else{
			  chart.setAmount(totalDaysOfMonth*equipCnt);
			}
			chart.setCounter(cnt);
			
			data.add(chart);
		}
		return data;
	}
	
	
	private void addValue(List<String> monthMapValue,long newAmount,long finalDay){
	  long amount = Long.parseLong(monthMapValue.get(0));
	  monthMapValue.set(0, String.valueOf(amount+newAmount));
	  monthMapValue.set(1, String.valueOf(finalDay));
	  int cnt = Integer.parseInt(monthMapValue.get(2));
	  monthMapValue.set(2, String.valueOf(cnt+1));
	  
	  //return monthMapValue;
	}
	/**
	 * 计算每个月的实际使用的天数(如果没使用的使用默认值,默认null)
	 * @param monthsMap 对应于各月份集合
	 * @param maint_st 开始日期
	 * @param maint_ed 结束日期
	 * @throws ParseException 
	 */
	private void calcDays(Map<String,List<String>> monthsMap,String maint_st,String maint_ed) throws ParseException,PmmsException{
		int[] ymdAry_st = Tools.splitDate(maint_st);
		int[] ymdAry_ed = Tools.splitDate(maint_ed);
		
		if(ymdAry_st[0] == ymdAry_ed[0]){
			int start_m = ymdAry_st[1];
			int end_m = ymdAry_ed[1];
			//时间跨度在一个月以内
			if(start_m == end_m){
				long gaps = Tools.getDaysGap(maint_st, maint_ed);
				long finalDay = Tools.getFinalDayofMonth(ymdAry_st[0], start_m);
				
				//合并实际使用的天数(可能出现不连续的时间区间，整合该月份的全部实际使用天数)
				List<String> data= monthsMap.get(String.valueOf(start_m));
				/*long tmp = Long.parseLong(data.get(0));
				data.set(0, String.valueOf(gaps+tmp));
				data.set(1, String.valueOf(finalDay));*/
				addValue(data,gaps,finalDay);
				
			}else{
			  //开始时间和结束时间的之间的间隔差值
			  long gaps = 0L;
				//时间跨度超过1个月
				for(int i = start_m; i <= end_m;i++){
					//当前总共天数
					long finalDay = Tools.getFinalDayofMonth(ymdAry_st[0], i);
					
					//起始月
					if(i == start_m){
						//计算起始月的停机的天数
						gaps = Tools.getDaysGapToEnd(maint_st);
						
						//
						List<String> data= monthsMap.get(String.valueOf(i));
						/*long tmp = Long.parseLong(data.get(0));
						data.set(0, String.valueOf(gaps+tmp));
						data.set(1, String.valueOf(finalDay));*/
						addValue(data,gaps,finalDay);
						
					}else if(i == end_m){//结束月
						gaps = Tools.getDaysGapFromFirst(maint_ed);
						
						List<String> data= monthsMap.get(String.valueOf(i));
						/*long tmp = Long.parseLong(data.get(0));
						data.set(0, String.valueOf(gaps+tmp));
						data.set(1, String.valueOf(finalDay));*/
						addValue(data,gaps,finalDay);
					}else{
						//全月都是停机，没有实际使用天数
						List<String> data= monthsMap.get(String.valueOf(i));
						
						//data.set(1, String.valueOf(finalDay));
						addValue(data,finalDay,finalDay);
					}
				}
			}
			
		}else{//跨年问题
			/*if(!(ymdAry_st[1] >= ymdAry_ed[1] &&  ymdAry_st[2] > ymdAry_ed[2])){
				throw new PmmsException("Date period more than one year!");
			}*/
		  
		  
		}
	}
	
	
	/**
	 * 按年统计设备使用率
	 */
	@SuppressWarnings("unchecked")
  public ServiceReturns getEquipUsageByYr() throws ParseException {
    serviceReturns = new ServiceReturns();
    int year = Tools.getCurrentYear();
    ChartInfoParam param = new ChartInfoParam(null, Tools.conactStartDt(year
        - Constants.PERIOD_YEAR + 1), Tools.conactEndDt(year));
    Map<String, List<Map<String, String>>> list = chartDao.getlstWorkorder(param);
    Map<String, List<ChartInfo>> dataMap = getEquipUsageByYrCommon(list);
    //根据公式计算报表数据
    List<ChartDetailInfo> lstDetail = calcUsageRate(dataMap, true);
    
    serviceReturns.putData("list", lstDetail);
    
  //年份list
    String[] backPeriod = new String[Constants.PERIOD_YEAR];
    for(int i=0;i<Constants.PERIOD_YEAR;i++){
      backPeriod[i] = String.valueOf(year-Constants.PERIOD_YEAR+i+1);
    }
    serviceReturns.putData("backPeriod",backPeriod);
    
    return serviceReturns;
  }
	
	
	/**
	 * 按月统计设备使用率
	 */
	public ServiceReturns getEquipUsageByMonth(String equipId, int year)
			throws ParseException {
		serviceReturns = new ServiceReturns();
		try {
			Map<String, List<Map<String, String>>> maps = chartDao.getlstWorkorder(new ChartInfoParam(equipId, Tools.conactStartDt(year), Tools.conactEndDt(year)));
			//按月份组装数据
			Map<String,List<ChartInfo>> map = getEquipByMonth(maps,year,!StringUtils.isEmpty(equipId));
			
			//根据公式计算报表数据
			List<ChartDetailInfo> lstDetail = calcUsageRate(map,false);
			
			if(!StringUtils.isEmpty(equipId)){
        if (!CollectionUtils.isEmpty(lstDetail)) {
          lstDetail.get(0).setName(equipId);
          lstDetail.remove(lstDetail.size()-1);
        }
      }
			
			serviceReturns.putData("list", lstDetail);
		}
		catch (Exception e) {
			LOGGER.error("获取单个平均维修间隔时间失败！", e);
		}
		return serviceReturns;
	}
	
	/**
	 * 设备使用率的计算
	 * @Author: iGATE 
	 * @param map
	 * @return
	 * @Description:
	 */
  private List<ChartDetailInfo> calcUsageRate(Map<String, List<ChartInfo>> map, boolean isYear) {
    List<ChartDetailInfo> listDetail = null;
    if (!CollectionUtils.isEmpty(map)) {
      Map.Entry<String, List<ChartInfo>> entry = null;
      Set<Map.Entry<String, List<ChartInfo>>> set = map.entrySet();
      Iterator<Map.Entry<String, List<ChartInfo>>> itor = set.iterator();
      String deptName = "";
      List<ChartInfo> lstChart = null;
      listDetail = new ArrayList<ChartDetailInfo>();
      ChartDetailInfo chartDetailInfo = null;
      Map<String, List<String>> totalYrMap = getTolYrMap();
      Map<String, List<String>> totalMonthMap = getTolMonthMap();
      //总计数据的分子
      double total1 = 0;
      //总计数据的分母
      double total2 = 0;
      String period = "";
      double rate = 0;
      while (itor.hasNext()) {
        entry = itor.next();
        deptName = entry.getKey();
        lstChart = entry.getValue();
        chartDetailInfo = new ChartDetailInfo();
        chartDetailInfo.setName(deptName);
        if (lstChart == null) {
          if (isYear) {
            chartDetailInfo.setData(getNullYrs());
          } else {
            chartDetailInfo.setData(getNullMonths());
          }
          listDetail.add(chartDetailInfo);
          continue;
        }
        Iterator<ChartInfo> itor1 = lstChart.iterator();
        List<Double> list = new ArrayList<Double>();
        while (itor1.hasNext()) {
          ChartInfo info = itor1.next();
          period = info.getMaintMonth();
          if (info.getAmount() == 0) {
            list.add(null);
            continue;
          }
          total1 = info.getAmount() - info.getCount();
          total2 = info.getAmount();
          //rate = Tools.roundDecimal4((info.getAmount() - info.getCount()) / info.getAmount());
          rate = Tools.roundDecimal2(((info.getAmount() - info.getCount()) / info.getAmount())*100);
          
          list.add(Double.valueOf(rate));
          //按统计指标求总和(年份或月份)
          if (isYear) {
            if (totalYrMap.containsKey(period)) {
              sumTotal1(totalYrMap, total1, period);
              sumTotal2(totalYrMap, total2, period);
            }
          } else {
            if (totalMonthMap.containsKey(period)) {
              sumTotal1(totalMonthMap, total1, period);
              sumTotal2(totalMonthMap, total2, period);
            }
          }
        }
        chartDetailInfo.setData(list);
        listDetail.add(chartDetailInfo);
      }
      //增加总计指标项
      ChartDetailInfo totalDetail = new ChartDetailInfo();
      totalDetail.setName("总计");
      List<Double> data = null;
      if (isYear) {
        data = calcTotal(totalYrMap.values(),true);
      } else {
        data = calcTotal(totalMonthMap.values(),true);
      }
      totalDetail.setData(data);
      listDetail.add(totalDetail);
    }
    return listDetail;
  }
	
	private void sumTotal1(Map<String, List<String>> totalYrMap,double total1,String mapKey){
	  List<String> valList = totalYrMap.get(mapKey);
    double total1_old = Double.parseDouble(valList.get(0)) + total1;
    valList.set(0, String.valueOf(total1_old));
    //totalYrMap.put(mapKey, valList);
	}
	
	private void sumTotal2(Map<String, List<String>> totalYrMap,double total2,String mapKey){
    List<String> valList = totalYrMap.get(mapKey);
    total2 += Double.parseDouble(valList.get(1));
    valList.set(1, String.valueOf(total2));
    //totalYrMap.put(mapKey, valList);
  }
	
	
	private void addWorkTime(Map<String,List<String>> map,double outageValue,String mapKey){
	  List<String> totalVal = map.get(String.valueOf(mapKey));
    
    totalVal.set(0,String.valueOf(Double.parseDouble(totalVal.get(0))+outageValue));
    
    //map.put(String.valueOf(mapKey), totalVal);
	}
	
	private void setTotalWorkTime(Map<String,List<String>> map,double totalWorkTime,String mapKey){
    List<String> totalVal = map.get(String.valueOf(mapKey));
    
    totalVal.set(1,String.valueOf(totalWorkTime));
    
    //map.put(String.valueOf(mapKey), totalVal);
  }
	
	private void setDownCnt(Map<String,List<String>> map,int shutDownCnt,String mapKey){
    List<String> totalVal = map.get(String.valueOf(mapKey));
    
    totalVal.set(2,String.valueOf(shutDownCnt));
    
   // map.put(String.valueOf(mapKey), totalVal);
  }
	
	/**
	 * 按年统计设备使用率 提取公共方法
	 */
	@SuppressWarnings("unchecked")
	public Map<String, List<ChartInfo>> getEquipUsageByYrCommon(Map<String,List<Map<String,String>>> maps) throws ParseException{
		Map<String, List<ChartInfo>> chartMap = null;
		try{
		Map<String,String> equipCnt = chartDao.getEquipCntByDept();
		if(!CollectionUtils.isEmpty(maps)){
			Map.Entry<String, List<Map<String,String>>> entry = null;
			Set<Map.Entry<String, List<Map<String,String>>>> set = maps.entrySet();
			Iterator<Map.Entry<String, List<Map<String,String>>>> itor = set.iterator();
			String deptName = "";
			Map<String,String> firstRow = null;
			List<Map<String,String>> rows = null;
			String maint_st= "";
			String maint_ed = "";
			//用于区分不同年份的临时变量
			int[] ymd_tmp = null;
			int[] ymdAry_st = null;
			int[] ymdAry_ed = null;
			long periodsGap = 0L;
			//long outageHrsGap = 0;
			long totalWorkTime = 0;
			Integer totalDays = 0;
			
			//Map<String,List> mapRate = null; //String 代表年份，mapRate 每一年同一个部门所有机器工作总时长，总停机时长，停机次数
			//List<String> lstTemp = null;     //存储机器工作总时长，停机总时长，停机次数
			Integer shutDownCnt = 0;         //停机次数
			
			//List<ChartDetailInfo> chartDetail = new ArrayList<ChartDetailInfo>();
			chartMap = new LinkedHashMap<String, List<ChartInfo>>();
			while(itor.hasNext()){
				entry = itor.next();
				deptName = entry.getKey();
				rows = entry.getValue();
				if(!Tools.isNull(rows)){
					firstRow = rows.get(0);
					maint_st = firstRow.get("maint_start_date");
					ymd_tmp = Tools.splitDate(maint_st);
					
					Map<String,List<String>> YrsMap = getYrsMap();
					Iterator<Map<String,String>> itor1 = rows.iterator();
				  long nextYrPeriodsGap = 0;
				  String firstDateStr = null;
				  shutDownCnt = 0;
					while (itor1.hasNext()){
						Map<String,String> map = itor1.next();
						maint_st = map.get("maint_start_date");
						maint_ed = map.get("maint_end_date");
						
						ymdAry_st = Tools.splitDate(maint_st);
						ymdAry_ed = Tools.splitDate(maint_ed);
						
						//是否是同一年的数据
						if (ymd_tmp[0] == ymdAry_st[0]) {
							shutDownCnt ++;        //停机次数，也是工单个数
							//not in the same year, recalculate the end date
							//如果结束时间是在下一年，只统计到当前年的最后一天,即,(12-31)
							if(ymdAry_st[0] != ymdAry_ed[0]){
								maint_ed = Tools.finalDateStrOfYr(ymdAry_st[0]);
								
								firstDateStr = Tools.firstDateStrOfYr(ymdAry_ed[0]);
								
								nextYrPeriodsGap = Tools.getDaysGap(firstDateStr,maint_ed);
								
								//set next yr's value
								addWorkTime(YrsMap,nextYrPeriodsGap,String.valueOf(ymdAry_ed[0]));
								
							}
							 
							//periodsGap += Tools.getDaysGap(maint_st,maint_ed);
							periodsGap = Tools.getDaysGap(maint_st,maint_ed);
							
							//set current yr's value
							addWorkTime(YrsMap,periodsGap,String.valueOf(ymd_tmp[0]));
							
						}else{
						  
							//当前年下的总天数,按年份找出当前年份的数据，并求和
							totalDays = Tools.getTotalDaysOfYr(ymd_tmp[0]);
							//计算该部门下全部设备的总工作时长(每天24小时，每周5天不间断运行)
							totalWorkTime = Integer.parseInt(equipCnt.get(deptName))*totalDays;
							//outageHrsGap = periodsGap;
							
							//所有机器总down机时间
							setTotalWorkTime(YrsMap,totalWorkTime,String.valueOf(ymd_tmp[0]));
							//所有设备的总停机次数
							setDownCnt(YrsMap,shutDownCnt,String.valueOf(ymd_tmp[0]));
							
							maint_st = map.get("maint_start_date");
							//更新比较值
							ymd_tmp = Tools.splitDate(maint_st);
							maint_ed = map.get("maint_end_date");
							
							periodsGap = Tools.getDaysGap(maint_st,maint_ed);
							
						//set current yr's value
            addWorkTime(YrsMap,periodsGap,String.valueOf(ymd_tmp[0]));
						}									
					}
					
				//当前年下的总天数,按年份找出当前年份的数据，并求和
          totalDays = Tools.getTotalDaysOfYr(ymd_tmp[0]);
          //计算该部门下全部设备的总工作时长(每天24小时，每周7天不间断运行)
          totalWorkTime = Integer.parseInt(equipCnt.get(deptName))*totalDays;
          //outageHrsGap = periodsGap;
          
          //所有机器总down机时间
          setTotalWorkTime(YrsMap,totalWorkTime,String.valueOf(ymd_tmp[0]));
          //所有设备的总停机次数
          setDownCnt(YrsMap,shutDownCnt,String.valueOf(ymd_tmp[0]));
          
					Set<Map.Entry<String, List<String>>> chartSet = YrsMap.entrySet();
					Iterator<Map.Entry<String, List<String>>> chartIte = chartSet.iterator();
					Map.Entry<String, List<String>> entryTemp = null;
					List<String> listTemp = null;
					List<ChartInfo> listTemp2 = new ArrayList<ChartInfo>();
 					while(chartIte.hasNext()){
 						ChartInfo chartInfo = new ChartInfo();
 						entryTemp = chartIte.next();
 						chartInfo.setMaintMonth(entryTemp.getKey());
 						listTemp = entryTemp.getValue();
 						if(!Tools.isNull(listTemp)){
 							chartInfo.setAmount(Double.parseDouble(listTemp.get(1).toString()));	
 							chartInfo.setCount(Double.parseDouble(listTemp.get(0).toString()));
 							chartInfo.setCounter(Integer.parseInt(listTemp.get(2).toString()));
 							
 							listTemp2.add(chartInfo);
 						}else{
 							listTemp2.add(chartInfo);
 						}
 						
					}
 					chartMap.put(deptName, listTemp2);
				}else{
					chartMap.put(deptName, null);
				}
			}
		}
		}
		catch(Exception e){
		  e.printStackTrace();
		}
		return chartMap;
	}
	
}


