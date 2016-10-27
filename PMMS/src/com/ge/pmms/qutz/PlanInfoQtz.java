package com.ge.pmms.qutz;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.pmms.base.BaseService;
import com.ge.pmms.dao.WorkOrderInfoDao;
import com.ge.pmms.po.WorkOrderInfo;
import com.ge.pmms.service.PlanInfoService;
import com.ge.pmms.utils.Tools;

@SuppressWarnings("rawtypes")
@Component
public class PlanInfoQtz extends BaseService{
	@Autowired
	private PlanInfoService planInfoService;
	@Autowired
	private WorkOrderInfoDao workOrderInfoDao;
	
	//每隔1分钟
//	@Scheduled(cron = "0 0/1 * * * ?") //"0 0 12 L * ?"   每个月最后一天上午12点
//	@Scheduled(cron = "0 0 12 L * ?")
//	@Scheduled(cron = "0 0 12 ? * 6L")//每月的最后一个星期五上午12点触发
//	@Scheduled(cron = "0 0/1 * ? * THU")
	public void generatePWO() {
		try {
			int planMonth = Tools.getCurrentMonth();
			int year = Tools.getCurrentYear();
			String firstDayofYear = year+"-01-01 00:00:00";
			String planMonthEnd =  Tools.getLastDayofDate(new Date());
			//判断是否是年底
			if(planMonth==11){
				year++;
			}
			planMonth++;//下个月
			if(planMonth==12){
				planMonth = 0;
			}
			Map<String,List<WorkOrderInfo>> map = new HashMap<String,List<WorkOrderInfo>>();
			//今年之前月份未完成的工单（计划+故障）
			LOGGER.info("=====================firstDayofYear="+firstDayofYear+"planMonthEnd="+planMonthEnd);
			List<WorkOrderInfo> unfinishedWO = workOrderInfoDao.findUnfinishedWO(firstDayofYear,planMonthEnd);
			String planYear = String.valueOf(year);
			//生成下个月的计划工单
			List<WorkOrderInfo> nextPWO = planInfoService.generateMonthPlans(planMonth, planYear);
			map.put("unfinishedWO", unfinishedWO);
			map.put("nextPWO", nextPWO);
			
			//发邮件或短信提醒
			planInfoService.sendPWOReminder(map);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("generatePWO() method occures exception......Exception:"+e.getMessage());
		}
	}
}
