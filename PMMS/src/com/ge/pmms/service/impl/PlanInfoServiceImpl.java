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

import java.util.ArrayList;
//import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.smslib.Message.MessageEncodings;
import org.smslib.OutboundMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ge.pmms.base.BaseService;
import com.ge.pmms.base.PmmsException;
import com.ge.pmms.base.ServiceReturns;
import com.ge.pmms.dao.PlanInfoDao;
import com.ge.pmms.dao.WorkOrderInfoDao;
import com.ge.pmms.po.MailMessageVO;
import com.ge.pmms.po.PlanInfo;
import com.ge.pmms.po.PlanMaintInfo;
import com.ge.pmms.po.ReminderInfo;
import com.ge.pmms.po.User;
import com.ge.pmms.po.WorkOrderInfo;
import com.ge.pmms.service.CommonService;
import com.ge.pmms.service.IMailSendService;
import com.ge.pmms.service.PlanInfoService;
import com.ge.pmms.service.ReminderInfoService;
import com.ge.pmms.service.WorkOrderInfoService;
import com.ge.pmms.utils.Constants;
import com.ge.pmms.utils.Tools;


/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Jan 23, 2015
 * @Date Modified : 
 * @Modified By :
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
@Service
@Transactional
public class PlanInfoServiceImpl extends BaseService<PlanInfo> implements PlanInfoService {
	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Autowired
	private PlanInfoDao planInfoDao;
	@Autowired
	private WorkOrderInfoDao workOrderInfoDao;
	@Autowired
	private WorkOrderInfoService workOrderInfoService;
	@Autowired
	private IMailSendService iMailSendService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private ReminderInfoService reminderInfoService;
	
	//月份和月份字段映射公共方法
	public String getMaintMonth(int maintMonth)throws Exception{
		Map<Integer,String> map = new HashMap<Integer,String>();
		map.put(0,"maint_jan");
		map.put(1,"maint_feb");
		map.put(2,"maint_mar");
		map.put(3,"maint_apr");
		map.put(4,"maint_may");
		map.put(5,"maint_jun");
		map.put(6,"maint_jul");
		map.put(7,"maint_agu");
		map.put(8,"maint_sep");
		map.put(9,"maint_oct");
		map.put(10,"maint_nov");
		map.put(11,"maint_dec");
		return map.get(maintMonth);
	}

	public ServiceReturns findPlan(String planYear, String equipType)
			throws PmmsException {
		serviceReturns = new ServiceReturns();
		try{
			List<PlanInfo> list = planInfoDao.findRichPlan(planYear, equipType);
			
			serviceReturns.putData("list",list);
		}catch(Exception e){
			LOGGER.error("findPlan() method occures exception......Exception:"+e.getMessage());
		}
		return serviceReturns;
	}
	
	//查询当月的保养计划Calendar.DAY_OF_MONTH
	public ServiceReturns findPlanForCurrMonth()throws PmmsException{
		serviceReturns = new ServiceReturns();
		
		String firstDayofYear = Tools.conactStartDt(Tools.getCurrentYear());
		String planMonthStart = Tools.formatDate(Tools.getFirstDayByDate(new Date()));
		String planMonthEnd = Tools.formatDate(Tools.getLastDayByDate(new Date()));
		LOGGER.info("================firstDayofYear="+firstDayofYear+"planMonthStart="+planMonthStart+"planMonthEnd="+planMonthEnd);
		try{
			List<PlanMaintInfo> list = planInfoDao.findPlanForCurrMonth(firstDayofYear,planMonthStart, planMonthEnd);
			
			serviceReturns.putData("list_month",list);
		}catch(Exception e){
			LOGGER.error("findPlanForCurrMonth() method occures exception......Exception:"+e.getMessage());
		}
		return serviceReturns;
	}
	

	public ServiceReturns addSinglePlan(PlanInfo planInfo) throws PmmsException {
		serviceReturns = new ServiceReturns();
		try{
			planInfoDao.addSinglePlan(planInfo);
			//generate planned work order
//			workOrderInfoService.save(woInfo);
			serviceReturns.putData("detail",planInfo);
		}catch(Exception e){
			LOGGER.error("addSinglePlan() method occures exception......Exception:"+e.getMessage());
		}
		return serviceReturns;
	}

	public ServiceReturns modifySinglePlan(PlanInfo planInfo) throws PmmsException {
		serviceReturns = new ServiceReturns();
		try{
			PlanInfo originalPI = planInfoDao.findPlanByPlanId(planInfo.getPlan_id());
			BeanUtils.copyProperties(originalPI, planInfo, new String[] {
					"maint_jan", "maint_feb", "maint_mar", "maint_apr","maint_may","maint_jun","maint_jul","maint_agu","maint_sep","maint_oct","maint_nov","maint_dec", "updater",
					"last_updated_date", "remark"});
			//relase object in session
			hibernateTemplate.evict(originalPI);
			//modify plan
			planInfoDao.modifySinglePlan(planInfo);
			serviceReturns.putData("detail",planInfo);
			
			//remove WO
			workOrderInfoService.deleteWOByPlanId(planInfo.getPlan_id());
			//add WO
			String[] arrMaint = new String[12];
			arrMaint[0] = planInfo.getMaint_jan();
			arrMaint[1] = planInfo.getMaint_feb();
			arrMaint[2] = planInfo.getMaint_mar();
			arrMaint[3] = planInfo.getMaint_apr();
			arrMaint[4] = planInfo.getMaint_may();
			arrMaint[5] = planInfo.getMaint_jun();
			arrMaint[6] = planInfo.getMaint_jul();
			arrMaint[7] = planInfo.getMaint_agu();
			arrMaint[8] = planInfo.getMaint_sep();
			arrMaint[9] = planInfo.getMaint_oct();
			arrMaint[10] = planInfo.getMaint_nov();
			arrMaint[11] = planInfo.getMaint_dec();
			List<WorkOrderInfo> workOrderInfos = new ArrayList<WorkOrderInfo>();
			WorkOrderInfo workOrderInfo = null;
			for(int i=0;i<arrMaint.length;i++){
				if(!arrMaint[i].equalsIgnoreCase(",,")){//no preventive maintenance month
					workOrderInfo = new WorkOrderInfo();
					workOrderInfo.setPlanStartMonth(Tools.getFirstDayOfMonth(i));
					workOrderInfo.setWorkOrderId(Tools.getTimeStr());
					workOrderInfo.setPlanId(planInfo.getPlan_id());
					workOrderInfo.setWorkOrderType(Constants.WORK_ORDER_TYPE_PLAN);
					workOrderInfo.setWorkOrderStatus(Constants.WORK_ORDER_STATUS_SUBMIT);
//					workOrderInfo.setCreator(creator);
					workOrderInfo.setEquipId(planInfo.getEquip_id());
					workOrderInfo.setFaultDescription(Constants.PLAN_WO_DEFAULT_DESC);
//					workOrderInfo.setSafetyInvolved(safetyInvolved);
//					workOrderInfo.setShutdownFlag(shutdownFlag);
					workOrderInfo.setCreatedate(Tools.getToday());
					workOrderInfos.add(workOrderInfo);
				}
			}
			workOrderInfoService.saveAll(workOrderInfos);
		}catch(Exception e){
			LOGGER.error("modifySinglePlan() method occures exception......Exception:"+e.getMessage());
		}
		
		return serviceReturns;
	}
	
	public ServiceReturns removeSinglePlan(String planId) throws PmmsException {
		serviceReturns = new ServiceReturns();
		//remove plan
		int effectedRowNum;
		try {
			effectedRowNum = planInfoDao.removeSinglePlan(planId);
			serviceReturns.putData("effectedRowNum",effectedRowNum);
			//remove WO and WO_maint
			workOrderInfoService.deleteWOByPlanId(planId);
		}
		catch (Exception e) {
			LOGGER.error("removeSinglePlan() method occures exception......Exception:"+e.getMessage());
		}
		
		
		return serviceReturns;
	}
	
	public ServiceReturns validatePlans(String copyFrom,String copyTo) throws PmmsException{
		serviceReturns = new ServiceReturns();
		long fromNumber = 0 ;
		long toNumber = 0;
		try{
			fromNumber = planInfoDao.getPlansNumber(copyFrom);
			toNumber = planInfoDao.getPlansNumber(copyTo);
		}catch(Exception e){
			LOGGER.error("validatePlans() method occures exception......Exception:"+e.getMessage());
		}
		serviceReturns.putData("fromFlag",fromNumber==0?Constants.FROM_YEAR_NO_PLAN:"");
		serviceReturns.putData("toFlag",toNumber!=0?Constants.TO_YEAR_HAD_PLAN:"");
		return serviceReturns;
	}
	
	public ServiceReturns generateWholeYearPlan(String copyFrom,String copyTo) throws PmmsException {
		serviceReturns = new ServiceReturns();
		//generate new plans
		List<PlanInfo> newList = new ArrayList<PlanInfo>();
		try{
			//delete old plans
			planInfoDao.removeAllPlansByYear(copyTo);
			List<PlanInfo> list = planInfoDao.findPlan(copyFrom);
			if(!CollectionUtils.isEmpty(list)){
				PlanInfo planInfo;
				PlanInfo newPlan;
				for(int i=0;i<list.size();i++){
//					planInfo = new PlanInfo();
					newPlan = new PlanInfo();
					planInfo = list.get(i);
					
					newPlan.setPlan_id(Tools.getTimeStr());
					newPlan.setEquip_id(planInfo.getEquip_id());
					newPlan.setPlan_type(planInfo.getPlan_type());
					newPlan.setMaint_year(copyTo);
					newPlan.setMaint_jan(planInfo.getMaint_jan());
					newPlan.setMaint_feb(planInfo.getMaint_feb());
					newPlan.setMaint_mar(planInfo.getMaint_mar());
					newPlan.setMaint_apr(planInfo.getMaint_apr());
					newPlan.setMaint_may(planInfo.getMaint_may());
					newPlan.setMaint_jun(planInfo.getMaint_jun());
					newPlan.setMaint_jul(planInfo.getMaint_jul());
					newPlan.setMaint_agu(planInfo.getMaint_agu());
					newPlan.setMaint_sep(planInfo.getMaint_sep());
					newPlan.setMaint_oct(planInfo.getMaint_oct());
					newPlan.setMaint_nov(planInfo.getMaint_nov());
					newPlan.setMaint_dec(planInfo.getMaint_dec());
//					newPlan.setCreator(creator);
					newPlan.setCreated_date(new Date());
					newPlan.setRemark("Generated from "+copyFrom+" year");
					newList.add(newPlan);
				}
			}
			getSession().flush();
			if(!CollectionUtils.isEmpty(newList)){
				saveAll(newList);
			}
		}catch(Exception e){
			LOGGER.error("generateWholeYearPlan() method occures exception......Exception:"+e.getMessage());
		}
		serviceReturns.putData("list",newList);
		return serviceReturns;
	}
	
	//修改时，验证计划是否未完成
	public ServiceReturns validateSinglePlan(String plan_id)throws PmmsException{
		serviceReturns = new ServiceReturns();
		long finishedWONum = 0;
		try{
//			planInfo = planInfoDao.getUnfinishedWO(plan_id);
			finishedWONum = workOrderInfoDao.getFinishedPWONum(plan_id);
			serviceReturns.putData("finishedWONum",finishedWONum);
		}catch(Exception e){
			LOGGER.error("validateSinglePlan() method occures exception......Exception:"+e.getMessage());
		}
		return serviceReturns;
	}
	
	//查询已完成的计划工单列表
	public ServiceReturns getFinishedPWO(String plan_id)throws PmmsException{
		serviceReturns = new ServiceReturns();
		try{
			List<WorkOrderInfo> list = workOrderInfoDao.getFinishedPWO(plan_id);
			serviceReturns.putData("list",list);
		}catch(Exception e){
			LOGGER.error("getFinishedPWO() method occures exception......Exception:"+e.getMessage());
		}
		return serviceReturns;
	}
	//查询不能修改计划工单月份的月份列表（计划维修月份小于等于当前月份的计划，并且是已完成或在维修的计划工单）
	public ServiceReturns getUnchangeablePWOMonth(String planId)throws PmmsException{
		serviceReturns = new ServiceReturns();
		String currMonth = Tools.formatDate(Tools.getFirstDayByDate(new Date()));
		try{
			List<String> list = workOrderInfoDao.getUnchangeablePWOMonth(planId,currMonth);
			serviceReturns.putData("list",list);
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.error("getUnchangeablePWOMonth() method occures exception......Exception:"+e.getMessage());
		}
		return serviceReturns;
	}
	
	//查询已完成的计划工单月份列表
	public ServiceReturns getFinishedPWOMonth(String planId)throws PmmsException{
		serviceReturns = new ServiceReturns();
		try{
			List<String> list = workOrderInfoDao.getFinishedPWOMonth(planId);
			serviceReturns.putData("list",list);
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.error("getFinishedPWOMonth() method occures exception......Exception:"+e.getMessage());
		}
		return serviceReturns;
	}
	
	public ServiceReturns getUnfinishedPWO(String plan_id)throws PmmsException{
		serviceReturns = new ServiceReturns();
		try{
			List<WorkOrderInfo> list = workOrderInfoDao.getUnfinishedPWO(plan_id);
			serviceReturns.putData("list",list);
		}catch(Exception e){
			LOGGER.error("getUnfinishedPWO() method occures exception......Exception:"+e.getMessage());
		}
		return serviceReturns;
	}
	
	//定时器调用生成下个月计划工单
	public List<WorkOrderInfo> generateMonthPlans(int planMonth,String planYear)throws PmmsException{
		List<WorkOrderInfo> workOrderInfos = null;
		try{
			//查询这个月的保养计划
			List<PlanInfo> planInfos = planInfoDao.getMonthPlans(getMaintMonth(planMonth), planYear);
			//planned work order
			workOrderInfos = new ArrayList<WorkOrderInfo>();
			WorkOrderInfo workOrderInfo = null;
			PlanInfo planInfo = null;
			
			if(!CollectionUtils.isEmpty(planInfos)){
				for(int k=0;k<planInfos.size();k++){
//					planInfo = new PlanInfo();
					workOrderInfo = new WorkOrderInfo();
					planInfo = planInfos.get(k);
					
					workOrderInfo.setPlanStartMonth(Tools.getFirstDayOfMonth(planMonth));
					workOrderInfo.setWorkOrderId(Tools.getTimeStr());
					workOrderInfo.setPlanId(planInfo.getPlan_id());
					workOrderInfo.setWorkOrderType(Constants.WORK_ORDER_TYPE_PLAN);
					workOrderInfo.setWorkOrderStatus(Constants.WORK_ORDER_STATUS_SUBMIT);
//					workOrderInfo.setCreator(creator);
					workOrderInfo.setEquipId(planInfo.getEquip_id());
					workOrderInfo.setFaultDescription(Constants.PLAN_WO_DEFAULT_DESC);
//					workOrderInfo.setSafetyInvolved(safetyInvolved);
//					workOrderInfo.setShutdownFlag(shutdownFlag);
					workOrderInfo.setCreatedate(Tools.getToday());
					workOrderInfos.add(workOrderInfo);
					
					workOrderInfoService.saveAll(workOrderInfos);
				}
			}
		}catch(Exception e){
			LOGGER.error("generateMonthPlans() method occures exception......Exception:"+e.getMessage());
		}
		return workOrderInfos;
	}
	
	//邮件|短信提醒计划工单
	public void sendPWOReminder(Map<String,List<WorkOrderInfo>> woMap)throws PmmsException{
		try {
			MailMessageVO message=new MailMessageVO();
			message.setSubject("PMMS Project - Work Order Reminder");
			message.setFrom("PMMS@ge.com");
			
			//是否发送邮件提醒
			String mailFlag = Constants.SYS_CONFIG_MAP.get(Constants.FLAG_MAIL_REMINDER);
//			//是否发送短信提醒
			String msgFlag = Constants.SYS_CONFIG_MAP.get(Constants.FLAG_MSG_REMINDER);
			//邮件模板
			String mailTemp = Constants.SYS_CONFIG_MAP.get(Constants.FW_EMAIL_FORMAT);
//			//短信模板
//			String msgTemp = Constants.SYS_CONFIG_MAP.get(Constants.FW_MSG_FORMAT);
			
			//获取收件人
			String receiver = Constants.SYS_CONFIG_MAP.get(Constants.FW_EMAIL_RECEIVER);
			String[] receivers = receiver.split(",");
			User user = null;
			
			//工单信息
			WorkOrderInfo woi = null;
			StringBuilder woEmail = new StringBuilder();
			StringBuilder woMsg = new StringBuilder();
			String lineBreak = System.getProperty("line.separator");
			
			//提醒内容文本
			List<OutboundMessage> msgs = null;
//			String msgContent = "";
			String mailContent = "";
			List<WorkOrderInfo> unfinishedWO = woMap.get("unfinishedWO");
			List<WorkOrderInfo> nextPWO = woMap.get("nextPWO");
			woEmail.append("  Unfinished WO List:"+lineBreak);
			if(!CollectionUtils.isEmpty(unfinishedWO)){
				for(int i=0;i<unfinishedWO.size();i++){
					woi = unfinishedWO.get(i);
					woEmail.append("  "+woi.getWorkOrderId()+","+(woi.getWorkOrderType()=="2"?"PWO":"FWO")+","+woi.getEquipId()+","+woi.getEquipModel()+","+woi.getStrPlanStartMonth()+","+woi.getStrCreateDate()+lineBreak);
//					woMsg.append(woi.getWorkOrderId()+","+lineBreak);
				}
			}
			woEmail.append(lineBreak+"  Next Month Prevnetive Work Order List:"+lineBreak);
			if(!CollectionUtils.isEmpty(nextPWO)){
				for(int i=0;i<nextPWO.size();i++){
					woi = nextPWO.get(i);
					woEmail.append("  "+woi.getWorkOrderId()+","+(woi.getWorkOrderType()=="2"?"PWO":"FWO")+","+woi.getEquipId()+","+woi.getEquipModel()+","+Tools.formatDate(woi.getPlanStartMonth())+","+Tools.formatDate(woi.getCreatedate())+lineBreak);
//					woMsg.append(woi.getWorkOrderId()+lineBreak);
				}
			}
			//邮件内容占位符注入
			mailContent = String.format(mailTemp, lineBreak,lineBreak,
					lineBreak,Tools.getCurrentMonth()+2,lineBreak,lineBreak,lineBreak,woEmail.toString());
//			//短信提醒内容文本
			msgs = new ArrayList<OutboundMessage>();
//			msgContent = String.format(msgTemp, (Tools.getCurrentMonth()+2),woMsg);
			
			
			String[] contactNOs = new String[receivers.length];
			String[] emails = new String[receivers.length];
			for(int i=0;i<receivers.length;i++){
				user = commonService.getUserBySso(receivers[i]);
				
				OutboundMessage obm = new OutboundMessage(user.getContactNo(),"蒋迅 testing2");//"蒋迅 testing2"   msgContent
				obm.setEncoding(MessageEncodings.ENCUCS2);
				msgs.add(obm);
				
				contactNOs[i] = user.getContactNo();
				emails[i] = user.getEmailId();
			}
			//邮件
			if(mailFlag.equalsIgnoreCase(Constants.FLAG_REMIND)){
				LOGGER.info("PWO Send Email reminder Starting...... ");
				message.setToEmail(emails);
				message.setMessage(mailContent);
				iMailSendService.sendMail(message);
				LOGGER.info("PWO Send Email reminder Ended...... ");
			}
			//短信
//			if(msgFlag.equalsIgnoreCase(Constants.FLAG_REMIND)){
//				LOGGER.info("PWO Send MSG reminder Starting...... ");
//				MsgUtil.open(Constants.MSG_PORT,Constants.MSG_GATE_WAY);
//				MsgUtil.sendMutipleSms(msgs);
////				MsgUtil.close();
//				LOGGER.info("PWO Send MSG reminder Ending...... ");
//			}
			
			//reminder module insert
			ReminderInfo reminderInfo = new ReminderInfo();
			reminderInfo.setTitleInfo("您有 "+(Tools.getCurrentMonth()+2)+" 月份的工单需要处理");
			reminderInfo.setModuleId(Constants.MODULE_PM);
			reminderInfo.setStatus(Constants.REMINDER_YES);
			reminderInfo.setReceiver(receiver);
			reminderInfo.setCreatedDate(new Date());
			reminderInfoService.insertReminder(reminderInfo);
		}
		catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("emailPWOReminder() method occures exception......Exception:"+e.getMessage());
		}
	}
	
}
