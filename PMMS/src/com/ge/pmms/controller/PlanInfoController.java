package com.ge.pmms.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ge.pmms.base.BaseController;
import com.ge.pmms.base.ControllerReturns;
import com.ge.pmms.po.EquipInfo;
import com.ge.pmms.po.PlanInfo;
import com.ge.pmms.po.PlanMaintInfo;
import com.ge.pmms.po.User;
import com.ge.pmms.po.WorkOrderInfo;
import com.ge.pmms.service.EquipInfoService;
import com.ge.pmms.service.PlanInfoService;
import com.ge.pmms.service.WorkOrderInfoService;
import com.ge.pmms.utils.Constants;
import com.ge.pmms.utils.PMMSConstants;
import com.ge.pmms.utils.Tools;
@Scope("request")
@Controller
public class PlanInfoController extends BaseController{
	@Autowired
	private PlanInfoService planInfoService;
	@Autowired
	private EquipInfoService equipInfoService;
	@Autowired
	private WorkOrderInfoService workOrderInfoService;
	
	@RequestMapping(value = "/plan/aa", produces = "text/html;charset=utf-8")
	public @ResponseBody String aa(HttpServletRequest request) {
		int planMonth = Tools.getCurrentMonth();
		int year = Tools.getCurrentYear();
		//判断是否是年底
		if(planMonth==11){
			year++;
		}
		planMonth++;//下个月
		if(planMonth==12){
			planMonth = 0;
		}
		String planYear = String.valueOf(year);
		try {
			planInfoService.generateMonthPlans(planMonth, planYear);
		}
		catch (Exception e) {
			LOGGER.error("aa() method occures exception......Exception:"+e.getMessage());
		}
		return "plan/plan-year";
	}
	
	/**
	 * 
	 * @Author: Xun Jiang 
	 * @param request
	 * @return
	 * @Description:forward to plan-year.jsp
	 */
	@RequestMapping(value = "/plan/index", produces = "text/html;charset=utf-8")
	public String forwardToPlanIndex(HttpServletRequest request) {
		return "plan/plan-year";
	}
	/**
	 * 
	 * @Author: Xun Jiang 
	 * @param request
	 * @return
	 * @Description:forward to plan-copy.jsp
	 */
	@RequestMapping(value = "/plan/copy", produces = "text/html;charset=utf-8")
	public String forwardToPlanCopy(HttpServletRequest request) {
		return "plan/plan-copy";
	}
	
	//查询一年内所有计划
	@RequestMapping(value = "/plan/getAllPlans", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getAllPlans(HttpServletRequest request) {
		try {
			String maintYear = request.getParameter("maintYear");
			if(Tools.isBlank(maintYear)){
				maintYear = String.valueOf(Tools.getCurrentYear());
			}
			String equipType = request.getParameter("equipType");
			returns = planInfoService.findPlan(maintYear, equipType);
		} catch (Exception e) {
			LOGGER.error("getAllPlans() method occures exception......Exception:"+e.getMessage());
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	//查询当前月所有计划
	@RequestMapping(value = "/plan/getCurrMonthPlans", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getCurrMonthPlans(HttpServletRequest request) {
		try {
			returns = planInfoService.findPlanForCurrMonth();
		} catch (Exception e) {
			LOGGER.error("getCurrMonthPlans() method occures exception......Exception:"+e.getMessage());
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}

	//验证设备编号有效性
	@RequestMapping(value = "/plan/validateEquip", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String validateEquip(HttpServletRequest request) {
		try {
			String equip_id = request.getParameter("equipId");
			EquipInfo equipInfo = equipInfoService.getEquipInfoByEpId(equip_id);
			returns = new ControllerReturns();
			returns.putData("detail",equipInfo);
		} catch (Exception e) {
			LOGGER.error("validateEquip() method occures exception......Exception:"+e.getMessage());
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	//保存单个计划
	@RequestMapping(value = "/plan/savePlanInfo", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String savePlanInfo(HttpServletRequest request) {
		try {
			String equip_id = request.getParameter("equipId");
//			String plan_type = request.getParameter("plan_type");
			String maint_jan = request.getParameter("maint_jan");
			String maint_feb = request.getParameter("maint_feb");
			String maint_mar = request.getParameter("maint_mar");
			String maint_apr = request.getParameter("maint_apr");
			String maint_may = request.getParameter("maint_may");
			String maint_jun = request.getParameter("maint_jun");
			String maint_jul = request.getParameter("maint_jul");
			String maint_agu = request.getParameter("maint_agu");
			String maint_sep = request.getParameter("maint_sep");
			String maint_oct = request.getParameter("maint_oct");
			String maint_nov = request.getParameter("maint_nov");
			String maint_dec = request.getParameter("maint_dec");
			
			String creator = ((User)request.getSession().getAttribute(PMMSConstants.USER_SESSION_DATA)).getSso();
			
			String[] arrMaint = new String[12];
			arrMaint[0] = maint_jan;
			arrMaint[1] = maint_feb;
			arrMaint[2] = maint_mar;
			arrMaint[3] = maint_apr;
			arrMaint[4] = maint_may;
			arrMaint[5] = maint_jun;
			arrMaint[6] = maint_jul;
			arrMaint[7] = maint_agu;
			arrMaint[8] = maint_sep;
			arrMaint[9] = maint_oct;
			arrMaint[10] = maint_nov;
			arrMaint[11] = maint_dec;
			
			
			
			String remark = request.getParameter("remark");
			//plan
			PlanInfo planInfo = new PlanInfo();
			planInfo.setEquip_id(equip_id);
			planInfo.setPlan_id(Tools.getTimeStr());
//			planInfo.setPlan_type(plan_type);
			planInfo.setMaint_year(String.valueOf(Tools.getCurrentYear()));
			planInfo.setMaint_jan(maint_jan);
			planInfo.setMaint_feb(maint_feb);
			planInfo.setMaint_mar(maint_mar);
			planInfo.setMaint_apr(maint_apr);
			planInfo.setMaint_may(maint_may);
			planInfo.setMaint_jun(maint_jun);
			planInfo.setMaint_jul(maint_jul);
			planInfo.setMaint_agu(maint_agu);
			planInfo.setMaint_sep(maint_sep);
			planInfo.setMaint_oct(maint_oct);
			planInfo.setMaint_nov(maint_nov);
			planInfo.setMaint_dec(maint_dec);
			planInfo.setCreator(creator);
			planInfo.setCreated_date(new Date());
			planInfo.setRemark(remark);
			returns = planInfoService.addSinglePlan(planInfo);
			
//			//planned work order
//			List<WorkOrderInfo> workOrderInfos = new ArrayList<WorkOrderInfo>();
//			WorkOrderInfo workOrderInfo = null;
//			
//			//if there are preventive maintenance in those months.
//			for(int i=0;i<arrMaint.length;i++){
//				if(!arrMaint[i].equalsIgnoreCase(",,")){//no preventive maintenance month
//					workOrderInfo = new WorkOrderInfo();
//					workOrderInfo.setPlanStartMonth(Tools.getFirstDayOfMonth(i));
//					workOrderInfo.setWorkOrderId(Tools.getTimeStr());
//					workOrderInfo.setPlanId(planInfo.getPlan_id());
//					workOrderInfo.setWorkOrderType(Constants.WORK_ORDER_TYPE_PLAN);
//					workOrderInfo.setWorkOrderStatus(Constants.WORK_ORDER_STATUS_SUBMIT);
////					workOrderInfo.setCreator(creator);
//					workOrderInfo.setEquipId(equip_id);
//					workOrderInfo.setFaultDescription(Constants.PLAN_WO_DEFAULT_DESC);
////					workOrderInfo.setSafetyInvolved(safetyInvolved);
////					workOrderInfo.setShutdownFlag(shutdownFlag);
//					workOrderInfo.setCreatedate(Tools.getToday());
//					workOrderInfos.add(workOrderInfo);
//				}
//			}
//			workOrderInfoService.saveAll(workOrderInfos);
		} catch (Exception e) {
			LOGGER.error("savePlanInfo() method occures exception......Exception:"+e.getMessage());
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	//验证单个计划工单是否有完成的
	@RequestMapping(value = "/plan/validateSinglePlan", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String validateSinglePlan(HttpServletRequest request) {
		try {
			String plan_id = request.getParameter("plan_id");
			returns = planInfoService.validateSinglePlan(plan_id);
		} catch (Exception e) {
			LOGGER.error("validateSinglePlan() method occures exception......Exception:"+e.getMessage());
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	//根据计划编号，查询已完成的计划工单列表
	@RequestMapping(value = "/plan/getFinishedPWO", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getFinishedPWO(HttpServletRequest request) {
		try {
			String plan_id = request.getParameter("plan_id");
			returns = planInfoService.getFinishedPWO(plan_id);
		} catch (Exception e) {
			LOGGER.error("getFinishedPWO() method occures exception......Exception:"+e.getMessage());
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	//根据计划编号，查询不能修改计划工单月份的月份列表
	@RequestMapping(value = "/plan/getUnchangeablePWOMonth", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getUnchangeablePWOMonth(HttpServletRequest request) {
		try {
			String plan_id = request.getParameter("plan_id");
			returns = planInfoService.getUnchangeablePWOMonth(plan_id);
		} catch (Exception e) {
			LOGGER.error("getUnchangeablePWOMonth() method occures exception......Exception:"+e.getMessage());
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}	
	
	//根据计划编号，查询已完成的计划工单月份列表
	@RequestMapping(value = "/plan/getFinishedPWOMonth", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getFinishedPWOMonth(HttpServletRequest request) {
		try {
			String plan_id = request.getParameter("plan_id");
			returns = planInfoService.getFinishedPWOMonth(plan_id);
		} catch (Exception e) {
			LOGGER.error("getFinishedPWOMonth() method occures exception......Exception:"+e.getMessage());
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}	
	
	//根据计划编号，查询未完成的计划工单列表
	@RequestMapping(value = "/plan/getUnfinishedPWO", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getUnfinishedPWO(HttpServletRequest request) {
		try {
			String plan_id = request.getParameter("plan_id");
			returns = planInfoService.getUnfinishedPWO(plan_id);
		} catch (Exception e) {
			LOGGER.error("getUnfinishedPWO() method occures exception......Exception:"+e.getMessage());
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	//修改单个计划
	@RequestMapping(value = "/plan/modifyPlanInfo", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String modifyPlanInfo(HttpServletRequest request) {
		try {
			String id = request.getParameter("id");
			String maint_jan = request.getParameter("maint_jan");
			String maint_feb = request.getParameter("maint_feb");
			String maint_mar = request.getParameter("maint_mar");
			String maint_apr = request.getParameter("maint_apr");
			String maint_may = request.getParameter("maint_may");
			String maint_jun = request.getParameter("maint_jun");
			String maint_jul = request.getParameter("maint_jul");
			String maint_agu = request.getParameter("maint_agu");
			String maint_sep = request.getParameter("maint_sep");
			String maint_oct = request.getParameter("maint_oct");
			String maint_nov = request.getParameter("maint_nov");
			String maint_dec = request.getParameter("maint_dec");
			String remark = request.getParameter("remark");
			
			String updater = ((User)request.getSession().getAttribute(PMMSConstants.USER_SESSION_DATA)).getSso();
			
			PlanInfo planInfo = new PlanInfo();
//			planInfo.setId(Integer.parseInt(id));
			planInfo.setPlan_id(id);
			planInfo.setMaint_jan(maint_jan);
			planInfo.setMaint_feb(maint_feb);
			planInfo.setMaint_mar(maint_mar);
			planInfo.setMaint_apr(maint_apr);
			planInfo.setMaint_may(maint_may);
			planInfo.setMaint_jun(maint_jun);
			planInfo.setMaint_jul(maint_jul);
			planInfo.setMaint_agu(maint_agu);
			planInfo.setMaint_sep(maint_sep);
			planInfo.setMaint_oct(maint_oct);
			planInfo.setMaint_nov(maint_nov);
			planInfo.setMaint_dec(maint_dec);
			planInfo.setUpdater(updater);
			planInfo.setLast_updated_date(new Date());
			planInfo.setRemark(remark);
			
			returns = planInfoService.modifySinglePlan(planInfo);
		} catch (Exception e) {
			LOGGER.error("modifyPlanInfo() method occures exception......Exception:"+e.getMessage());
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	/**
	 * 
	 * @Author: Xun Jiang 
	 * @param request
	 * @return
	 * @Description:remove plan by planId
	 */
	//删除单个计划
	@RequestMapping(value = "/plan/removePlanInfo", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String removePlanInfo(HttpServletRequest request) {
		try {
			String planId = request.getParameter("planId");
			returns = planInfoService.removeSinglePlan(planId);
		} catch (Exception e) {
			LOGGER.error("removePlanInfo() method occures exception......Exception:"+e.getMessage());
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	/**
	 * 
	 * @Author: Xun Jiang 
	 * @param request
	 * @return
	 * @Description:validate plan to check if the plans exist or is empty
	 */
	@RequestMapping(value = "/plan/validatePlans", produces = "text/html;charset=utf-8")
	public  @ResponseBody
	String validatePlans(HttpServletRequest request) {
		try {
			String copyFrom = request.getParameter("copyFrom");
			String copyTo = request.getParameter("copyTo");
			returns = planInfoService.validatePlans(copyFrom, copyTo);
		} catch (Exception e) {
			LOGGER.error("validatePlans() method occures exception......Exception:"+e.getMessage());
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	/**
	 * 
	 * @Author: Xun Jiang 
	 * @param request
	 * @return
	 * @Description:generate maintenance plans for the plans of one year based on the plans of another year
	 */
	@RequestMapping(value = "/plan/generatePlans", produces = "text/html;charset=utf-8")
	public  @ResponseBody
	String generatePlans(HttpServletRequest request) {
		try {
			String copyFrom = request.getParameter("copyFrom");
			String copyTo = request.getParameter("copyTo");
			returns = planInfoService.generateWholeYearPlan(copyFrom,copyTo);
		} catch (Exception e) {
			LOGGER.error("generatePlans() method occures exception......Exception:"+e.getMessage());
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	//导出一年的保养计划到Excel
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/plan/exportYearPlan", produces = "text/html;charset=utf-8")
	public void exportYearPlan(HttpServletRequest request,HttpServletResponse response) {
		LOGGER.info("Export Preventive Plan of One Year to Local starting......");
		try {
			String maintYear = request.getParameter("maintYear");
			if(Tools.isBlank(maintYear)){
				maintYear = String.valueOf(Tools.getCurrentYear());
			}
			String equipType = request.getParameter("equipType");
			String sheetName = "";
			String fileName = "Preventive_Maint";
			returns = planInfoService.findPlan(maintYear, equipType);
			List<PlanInfo> list = (List<PlanInfo>)returns.getData().get("list");
			if(!CollectionUtils.isEmpty(list)){
				sheetName = list.get(0).getMaint_year()+"_Preventive_Maint";
			}else{
				sheetName = "Preventive_Maint";
			}
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet0 = wb.createSheet(sheetName);
			HSSFRow rowFirst = sheet0.createRow(0);//创建第一行说明
			HSSFRow row0 = sheet0.createRow(1);
			//第一行表格说明
			HSSFFont font0 = (HSSFFont) wb.createFont();
			HSSFFont font1 = (HSSFFont) wb.createFont();
			font0.setFontHeightInPoints((short)12);
			font0.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font0.setFontName("宋体");
			font1.setFontHeightInPoints((short)8);
			font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font1.setFontName("宋体");
			font1.setItalic(true);
			HSSFCellStyle csHead0 = (HSSFCellStyle) wb.createCellStyle();
			HSSFCellStyle csHead1 = (HSSFCellStyle) wb.createCellStyle();
			csHead0.setFont(font0);
			csHead1.setFont(font1);
			csHead0.setAlignment(CellStyle.ALIGN_CENTER);
			rowFirst.createCell(0).setCellValue("设备(设施）预防性维护保养PM计划");
			Cell cell = rowFirst.createCell(6);
			cell.setCellValue("Y：年度保养；Q：季度保养；M：月度保养");
			Tools.setCellStyleForEachCell(rowFirst, csHead0);
			cell.setCellStyle(csHead1);
			sheet0.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
			//创建表头
			HSSFFont headFont = (HSSFFont) wb.createFont();
			headFont.setFontHeightInPoints((short)11);
			headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			
			HSSFCellStyle csHead = (HSSFCellStyle) wb.createCellStyle();
			csHead.setFont(headFont);
			csHead.setAlignment(CellStyle.ALIGN_CENTER);
			row0.createCell(0).setCellValue("序号");
			row0.createCell(1).setCellValue("设备编号");
			row0.createCell(2).setCellValue("设备名称");
			row0.createCell(3).setCellValue("设备型号");
			row0.createCell(4).setCellValue("一月份");
			row0.createCell(5).setCellValue("二月份");
			row0.createCell(6).setCellValue("三月份");
			row0.createCell(7).setCellValue("四月份");
			row0.createCell(8).setCellValue("五月份");
			row0.createCell(9).setCellValue("六月份");
			row0.createCell(10).setCellValue("七月份");
			row0.createCell(11).setCellValue("八月份");
			row0.createCell(12).setCellValue("九月份");
			row0.createCell(13).setCellValue("十月份");
			row0.createCell(14).setCellValue("十一月份");
			row0.createCell(15).setCellValue("十二月份");
			row0.createCell(16).setCellValue("备注");
			Tools.setCellStyleForEachCell(row0, csHead);
			
			//创建数据行
			for(int i=0;i<list.size();i++){
				PlanInfo plan = list.get(i);
				Row row = sheet0.createRow(i+2);
				row.createCell(0).setCellValue(i+1);
				row.createCell(1).setCellValue(plan.getEquip_id());
				row.createCell(2).setCellValue(plan.getEquipmentName());
				row.createCell(3).setCellValue(plan.getEquipModel());
				row.createCell(4).setCellValue(plan.getMaint_jan());
				row.createCell(5).setCellValue(plan.getMaint_feb());
				row.createCell(6).setCellValue(plan.getMaint_mar());
				row.createCell(7).setCellValue(plan.getMaint_apr());
				row.createCell(8).setCellValue(plan.getMaint_may());
				row.createCell(9).setCellValue(plan.getMaint_jun());
				row.createCell(10).setCellValue(plan.getMaint_jul());
				row.createCell(11).setCellValue(plan.getMaint_agu());
				row.createCell(12).setCellValue(plan.getMaint_sep());
				row.createCell(13).setCellValue(plan.getMaint_oct());
				row.createCell(14).setCellValue(plan.getMaint_nov());
				row.createCell(15).setCellValue(plan.getMaint_dec());
				row.createCell(16).setCellValue(plan.getRemark());
			}
			
			Tools.commonExport(response,wb,fileName);
		} catch (Exception e) {
			LOGGER.error("exportYearPlan() method occures exception......Exception:"+e.getMessage());
			returns = Tools.getExceptionControllerRetruns(e);
		}
		LOGGER.info("Export Preventive Plan of One Year to Local ending......");
	}
	
	//导出本月的保养计划到Excel
	@RequestMapping(value = "/plan/exportCurrMonthPlan", produces = "text/html;charset=utf-8")
	public void exportCurrMonthPlan(HttpServletRequest request,HttpServletResponse response) {
		LOGGER.info("Export Preventive Plan of Current Month to Local starting......");
		try {
			String sheetName = "";
			String fileName = "Preventive_Maint_Month";
			returns = planInfoService.findPlanForCurrMonth();
			List<PlanMaintInfo> list = (List<PlanMaintInfo>)returns.getData().get("list_month");
			if(!CollectionUtils.isEmpty(list)){
				sheetName = list.get(0).getPlanMonth()+"_Preventive_Maint_Month";
			}else{
				sheetName = "Preventive_Maint_Month";
			}
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet0 = wb.createSheet(sheetName);
			HSSFRow rowFirst = sheet0.createRow(0);//创建第一行说明
			HSSFRow row0 = sheet0.createRow(1);
			//第一行表格说明
			HSSFFont font0 = (HSSFFont) wb.createFont();
			font0.setFontHeightInPoints((short)12);
			font0.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font0.setFontName("宋体");
			HSSFCellStyle csHead0 = (HSSFCellStyle) wb.createCellStyle();
			csHead0.setFont(font0);
			csHead0.setAlignment(CellStyle.ALIGN_CENTER);
			rowFirst.createCell(0).setCellValue("设备(设施）预防性维护保养PM计划--本月计划");
			Tools.setCellStyleForEachCell(rowFirst, csHead0);
			sheet0.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
			//创建表头
			HSSFFont headFont = (HSSFFont) wb.createFont();
			headFont.setFontHeightInPoints((short)11);
			headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			
			HSSFCellStyle csHead = (HSSFCellStyle) wb.createCellStyle();
			csHead.setFont(headFont);
			csHead.setAlignment(CellStyle.ALIGN_CENTER);
			row0.createCell(0).setCellValue("序号");
			row0.createCell(1).setCellValue("是否完成");
			row0.createCell(2).setCellValue("设备名称");
			row0.createCell(3).setCellValue("设备型号");
			row0.createCell(4).setCellValue("设备编号");
			row0.createCell(5).setCellValue("负责人");
			row0.createCell(6).setCellValue("计划保养月份");
			row0.createCell(7).setCellValue("保养开始时间");
			row0.createCell(8).setCellValue("保养结束时间");
			Tools.setCellStyleForEachCell(row0, csHead);
			
			//创建数据行
			for(int i=0;i<list.size();i++){
				PlanMaintInfo plan = list.get(i);
				String status = plan.getWoStatus();
				if(status.equalsIgnoreCase("1")){
					status = Constants.WORK_ORDER_STATUS_SUBMIT_DESC;
				}else if(status.equalsIgnoreCase("2")){
					status = Constants.WORK_ORDER_STATUS_MAINT_DESC;
				}else if(status.equalsIgnoreCase("3")){
					status = Constants.WORK_ORDER_STATUS_ClOSED_DESC;
				}
				
				Row row = sheet0.createRow(i+2);
				row.createCell(0).setCellValue(i+1);
				row.createCell(1).setCellValue(status);
				row.createCell(2).setCellValue(plan.getEquipName());
				row.createCell(3).setCellValue(plan.getEquipModel());
				row.createCell(4).setCellValue(plan.getEquipId());
				row.createCell(5).setCellValue(plan.getLastname()+" "+plan.getFirstname());
				row.createCell(6).setCellValue(plan.getPlan_start_month());
				row.createCell(7).setCellValue(plan.getMaint_start_date());
				row.createCell(8).setCellValue(plan.getMaint_end_date());
			}
			
			Tools.commonExport(response,wb,fileName);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("exportCurrMonthPlan() method occures exception......Exception:"+e.getMessage());
			returns = Tools.getExceptionControllerRetruns(e);
		}
		LOGGER.info("Export Preventive Plan of Current Month to Local ending......");
	}	
}
