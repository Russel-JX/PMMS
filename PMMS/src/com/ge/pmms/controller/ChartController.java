package com.ge.pmms.controller;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.ge.pmms.base.BaseController;
import com.ge.pmms.po.ChartDetailInfo;
import com.ge.pmms.po.ChartInfoParam;
import com.ge.pmms.po.PlanMaintInfo;
import com.ge.pmms.service.ChartInfoService;
import com.ge.pmms.utils.Constants;
import com.ge.pmms.utils.Tools;
@Scope("request")
@Controller
public class ChartController extends BaseController{
	@Autowired
	private ChartInfoService chartInfoService;
	
	/**
	 * 
	 * @Author: Xun Jiang 
	 * @param request
	 * @return
	 * @Description:forward to chartView.jsp
	 */
	@RequestMapping(value = "/chart/index", produces = "text/html;charset=utf-8")
	public String forwardToChartIndex(HttpServletRequest request) {
		return "chart/chartView";
	}
	//PM完成率页面
	@RequestMapping(value = "/chart/pm", produces = "text/html;charset=utf-8")
	public String forwardToPM(HttpServletRequest request) {
		return "chart/pm";
	}
	//设备可利用率页面
	@RequestMapping(value = "/chart/ea", produces = "text/html;charset=utf-8")
	public String forwardToEA(HttpServletRequest request) {
		return "chart/ea";
	}
	//平均维修间隔页面
	@RequestMapping(value = "/chart/mtbf", produces = "text/html;charset=utf-8")
	public String forwardToMTBF(HttpServletRequest request) {
		return "chart/mtbf";
	}
	//平均维修时间页面
	@RequestMapping(value = "/chart/mttr", produces = "text/html;charset=utf-8")
	public String forwardToMTTR(HttpServletRequest request) {
		return "chart/mttr";
	}
	//维修备件费用页面
	@RequestMapping(value = "/chart/mspc", produces = "text/html;charset=utf-8")
	public String forwardToMSPC(HttpServletRequest request) {
		return "chart/mspc";
	}
	//备件使用数量页面
	@RequestMapping(value = "/chart/mspqu", produces = "text/html;charset=utf-8")
	public String forwardToMSPQU(HttpServletRequest request) {
		return "chart/mspqu";
	}
	
	//报表table导出
	@RequestMapping(value = "/chart/exportDataToExcel", produces = "text/html;charset=utf-8")
	public void exportDataToExcel(HttpServletRequest request,HttpServletResponse response) {
		LOGGER.info("Export Chart Data to Excel starting......");
		String attrName = request.getParameter("attrName");
		LOGGER.info("...... attrName="+attrName+" ......");
		String sheetName = "Chart_Data";
		String fileName = "Chart_Data";
		int currYear = Tools.getCurrentYear();
		try {
			List<ChartDetailInfo> table_list = (List<ChartDetailInfo>)WebUtils.getSessionAttribute(request, attrName);
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
			rowFirst.createCell(0).setCellValue("报表与统计--"+attrName.substring(0,attrName.indexOf("_")));
			Tools.setCellStyleForEachCell(rowFirst, csHead0);
			sheet0.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
			//创建表头
			HSSFFont headFont = (HSSFFont) wb.createFont();
			headFont.setFontHeightInPoints((short)11);
			headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			HSSFCellStyle csHead = (HSSFCellStyle) wb.createCellStyle();
			csHead.setFont(headFont);
			csHead.setAlignment(CellStyle.ALIGN_CENTER);
			
			final String[] arrOne = new String[]{Constants.CHART_PM_ONE,Constants.CHART_EA_ONE,Constants.CHART_MTBF_ONE
					,Constants.CHART_MTTR_ONE,Constants.CHART_MSPC_ONE,Constants.CHART_MSPQU_ONE};
			final String[] arrFive = new String[]{Constants.CHART_PM_FIVE,Constants.CHART_EA_FIVE,Constants.CHART_MTBF_FIVE
					,Constants.CHART_MTTR_FIVE,Constants.CHART_MSPC_FIVE,Constants.CHART_MSPQU_FIVE};
			final String[] arrSingle = new String[]{Constants.CHART_PM_SINGLE,Constants.CHART_EA_SINGLE,Constants.CHART_MTBF_SINGLE
					,Constants.CHART_MTTR_SINGLE,Constants.CHART_MSPC_SINGLE,Constants.CHART_MSPQU_SINGLE};
			
			if(Arrays.asList(arrOne).contains(attrName)){
				row0.createCell(0).setCellValue(Constants.CHART_DEPT_TH);
				for(int i=0;i<Constants.PERIOD_MONTH;i++){
					row0.createCell(i+1).setCellValue(i+1+"月份");
				}
			}else if(Arrays.asList(arrFive).contains(attrName)){
				row0.createCell(0).setCellValue(Constants.CHART_DEPT_TH);
				for(int i=0;i<Constants.PERIOD_YEAR;i++){
					row0.createCell(i+1).setCellValue(currYear-4+i);
				}
			}else if(Arrays.asList(arrSingle).contains(attrName)){
				row0.createCell(0).setCellValue(Constants.CHART_SINGLE_TH);
				for(int i=0;i<Constants.PERIOD_MONTH;i++){
					row0.createCell(i+1).setCellValue(i+1+"月份");
				}
			}else{
				LOGGER.error("Export Chart Data to Excel Error:Excel type dismatch!......");
			}
			Tools.setCellStyleForEachCell(row0, csHead);
			//创建数据行
			if(!CollectionUtils.isEmpty(table_list)){
				for(int i=0;i<table_list.size();i++){
					ChartDetailInfo chartDetailInfo = table_list.get(i);
					Row row = sheet0.createRow(i+2);
					row.createCell(0).setCellValue(chartDetailInfo.getName());
					List<Double> data = chartDetailInfo.getData();
					if(!CollectionUtils.isEmpty(data)){
						for(int k=0;k<data.size();k++){
							row.createCell(k+1).setCellValue(data.get(k)==null?"":data.get(k).toString());
						}
					}
				}
			}
			Tools.commonExport(response,wb,fileName);
		} catch (Exception e) {
			LOGGER.error("exportDataToExcel() method occures exception......Exception:"+e.getMessage());
			returns = Tools.getExceptionControllerRetruns(e);
		}
		LOGGER.info("Export Chart Data to Excel ending......");
	}	
	
	//某一年中，五大部门PM完成率
	@RequestMapping(value = "/chart/getPMWORate", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getPMWORate(HttpServletRequest request) {
		try {
			String year = request.getParameter("year");
			returns = chartInfoService.getPMWORate(year, null);
			WebUtils.setSessionAttribute(request,Constants.CHART_PM_ONE, returns.getData().get("list"));
		} catch (Exception e) {
			LOGGER.error("getPMWORate() method occures exception......Exception:"+e.getMessage());
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	//五年中，五大部门PM完成率
	@RequestMapping(value = "/chart/getPMWORateByYear", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getPMWORateByYear(HttpServletRequest request) {
		try {
			returns = chartInfoService.getPMWORateByYear();
			WebUtils.setSessionAttribute(request,Constants.CHART_PM_FIVE, returns.getData().get("list"));
		} catch (Exception e) {
		  LOGGER.error("faied to get dashboard!", e);
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}	
	
	//单个设备PM完成率
	@RequestMapping(value = "/chart/getSinglePMWORate", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getSinglePMWORate(HttpServletRequest request) {
		try {
			String equipId = request.getParameter("equipId");
			String year = request.getParameter("year");
			returns = chartInfoService.getSinglePMWORate(equipId, year);
			WebUtils.setSessionAttribute(request,Constants.CHART_PM_SINGLE, returns.getData().get("list"));
		} catch (Exception e) {
		  LOGGER.error("faied to get dashboard!", e);
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	//一年中，五大部门MTTR
	@RequestMapping(value = "/chart/getMTTRForDepts", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getMTTRForDepts(HttpServletRequest request) {
		try {
			String year = request.getParameter("year");
			returns = chartInfoService.getMTTRRateByYear(year);
			WebUtils.setSessionAttribute(request,Constants.CHART_MTTR_ONE, returns.getData().get("list"));
		} catch (Exception e) {
		  LOGGER.error("faied to get dashboard!", e);
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}	
	
	//一年中，单个设备MTTR
	@RequestMapping(value = "/chart/getMTTRForSingleEquip", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getMTTRForSingleEquip(HttpServletRequest request) {
		try {
			String equipId = request.getParameter("equipId");
			String year = request.getParameter("year");
			returns = chartInfoService.getSingleMTTRRateByYear(equipId, year);
			WebUtils.setSessionAttribute(request,Constants.CHART_MTTR_SINGLE, returns.getData().get("list"));
		} catch (Exception e) {
		  LOGGER.error("faied to get dashboard!", e);
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	//近5年中，五大部门的MTTR
	@RequestMapping(value = "/chart/getMTTRForYears", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getMTTRForYears(HttpServletRequest request) {
		try {
			String year = request.getParameter("year");
			returns = chartInfoService.getMTTRRateforYears();
			WebUtils.setSessionAttribute(request,Constants.CHART_MTTR_FIVE, returns.getData().get("list"));
		} catch (Exception e) {
		  LOGGER.error("faied to get dashboard!", e);
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}	
	
	//统计单个备件使用数量--指定 年份统计1-12月
	@RequestMapping(value = "/chart/getSpNumBySpId", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getSpNumBySpId(HttpServletRequest request) {
		try {
			String spId = request.getParameter("spId");
			String year = request.getParameter("year");
			ChartInfoParam  chartPara=new ChartInfoParam();
			chartPara.setSparePartId(spId);
			if(Tools.isEmpty(year)){
				Calendar a=Calendar.getInstance();
				String currYear=String.valueOf(a.get(Calendar.YEAR));
				chartPara.setYear(currYear);
			}else{
				chartPara.setYear(year);
			}
			
			returns = chartInfoService.getSpNumBySpId(chartPara);
			WebUtils.setSessionAttribute(request,Constants.CHART_MSPQU_SINGLE, returns.getData().get("list"));
		} catch (Exception e) {
		  LOGGER.error("faied to get dashboard!", e);
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	//统计备件使用数量---按部门统计1-12月份
	@RequestMapping(value = "/chart/getSpNumBydept", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getSpNumBydept(HttpServletRequest request) {
		try {
			String year = request.getParameter("year");
			ChartInfoParam  chartPara=new ChartInfoParam();
			if(Tools.isEmpty(year)){
				Calendar a=Calendar.getInstance();
				String currYear=String.valueOf(a.get(Calendar.YEAR));
				chartPara.setYear(currYear);
			}else{
				chartPara.setYear(year);
			}
			returns = chartInfoService.getSpNumBydept(chartPara);
			WebUtils.setSessionAttribute(request,Constants.CHART_MSPQU_ONE, returns.getData().get("list"));
		} catch (Exception e) {
		  LOGGER.error("faied to get dashboard!", e);
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	//统计备件使用数量---按年统计五大部门
	@RequestMapping(value = "/chart/getSpNumByYear", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getSpNumByYear(HttpServletRequest request) {
		try {
			//String spId = request.getParameter("spId");
			returns = chartInfoService.getSpNumByYear();
			WebUtils.setSessionAttribute(request,Constants.CHART_MSPQU_FIVE, returns.getData().get("list"));
		} catch (Exception e) {
		  LOGGER.error("faied to get dashboard!", e);
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	//统计备件费用---单个备件指定年份的1-12月份
	@RequestMapping(value = "/chart/getSpFeeBySpId", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getSpFeeBySpId(HttpServletRequest request) {
		try {
			String spId = request.getParameter("spId");
			String year = request.getParameter("year");
			ChartInfoParam  chartPara=new ChartInfoParam();
			chartPara.setSparePartId(spId);
			if(Tools.isEmpty(year)){
				Calendar a=Calendar.getInstance();
				String currYear=String.valueOf(a.get(Calendar.YEAR));
				chartPara.setYear(currYear);
			}else{
				chartPara.setYear(year);
			}
			returns = chartInfoService.getSpFeeBySpId(chartPara);
			WebUtils.setSessionAttribute(request,Constants.CHART_MSPC_SINGLE, returns.getData().get("list"));
		} catch (Exception e) {
		  LOGGER.error("faied to get dashboard!", e);
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	//统计备件费用---按部门统计1-12月份
	@RequestMapping(value = "/chart/getSpFeeByDept", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getSpFeeByDept(HttpServletRequest request) {
		try {
			String year = request.getParameter("year");
			ChartInfoParam  chartPara=new ChartInfoParam();
			if(Tools.isEmpty(year)){
				Calendar a=Calendar.getInstance();
				String currYear=String.valueOf(a.get(Calendar.YEAR));
				chartPara.setYear(currYear);
			}else{
				chartPara.setYear(year);
			}
			returns = chartInfoService.getSpFeeByDept(chartPara);
			WebUtils.setSessionAttribute(request,Constants.CHART_MSPC_ONE, returns.getData().get("list"));
		} catch (Exception e) {
		  LOGGER.error("faied to get dashboard!", e);
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	//统计备件费用---按年统计五大部门
	@RequestMapping(value = "/chart/getSpFeeByYear", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getSpFeeByYear(HttpServletRequest request) {
		try {
			returns = chartInfoService.getSpFeeByYear();
			WebUtils.setSessionAttribute(request,Constants.CHART_MSPC_FIVE, returns.getData().get("list"));
		} catch (Exception e) {
		  LOGGER.error("faied to get dashboard!", e);
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	//统计设备使用率，五大部门按年份统计,统计几年由PERIOD_YEAR指定
	@RequestMapping(value = "/chart/getEquipUsageRateByYr", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getEquipUsageByYr(HttpServletRequest request) {
		try {
			//String spId = request.getParameter("spId");
			returns = chartInfoService.getEquipUsageByYr();
			WebUtils.setSessionAttribute(request,Constants.CHART_EA_FIVE, returns.getData().get("list"));
		} catch (Exception e) {
			LOGGER.info(e);
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	//统计设备使用率，指定某一年12个月，按五大部门统计
	@RequestMapping(value = "/chart/getEquipUsageRateByMonth", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getEquipUsageByMonth(HttpServletRequest request) {
		try {
			//按五大部门统计，设备ID为空。
			String equipId = "";
			int year = 0;
			String strYear = request.getParameter("year");
			if(!Tools.isEmpty(strYear)){
				year = Integer.parseInt(request.getParameter("year").trim());				
			}else{
				year = Tools.getCurrentYear();
			}
			returns = chartInfoService.getEquipUsageByMonth(equipId, year);
			WebUtils.setSessionAttribute(request,Constants.CHART_EA_ONE, returns.getData().get("list"));
		} catch (Exception e) {
		  e.printStackTrace();
		  LOGGER.error("faied to get dashboard!", e);
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}	
	
	//统计设备使用率，指定某一年12个月，单个设备统计
	@RequestMapping(value = "/chart/getSingleEquipUsageRateByMonth", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getSingleEquipUsageByMonth(HttpServletRequest request) {
		try {
			//按单个设备查询
			String equipId = request.getParameter("equipId").trim();
			String yr = request.getParameter("year");
			int year = Tools.getCurrentYear();
			if(!StringUtils.isEmpty(yr)){
			  year = Integer.parseInt(yr);
			}
			
			returns = chartInfoService.getEquipUsageByMonth(equipId, year);
			WebUtils.setSessionAttribute(request,Constants.CHART_EA_SINGLE, returns.getData().get("list"));
		} catch (Exception e) {
		  LOGGER.error("faied to get dashboard!", e);
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	//某一年12个月份统计，单个设备平均维修间隔时间
	@RequestMapping(value = "/chart/getEquipMTBFByEqId", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getEquipMTBF(HttpServletRequest request) {
		try {
		  String equipId = request.getParameter("equipId");
      String selYr = request.getParameter("year");

      int year = Tools.getCurrentYear();
      if(!StringUtils.isEmpty(selYr)){
        year = Integer.parseInt(selYr);
      }
      
      returns = chartInfoService.getEquipMTBFByMonth(equipId,String.valueOf(year));
      		WebUtils.setSessionAttribute(request,Constants.CHART_MTBF_SINGLE, returns.getData().get("list"));
		} catch (Exception e) {
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	//多年份统计，五大区域，设备平均维修间隔时间
	@RequestMapping(value = "/chart/getEquipMTBFByYr", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getEquipMTBFByYr(HttpServletRequest request) {
		try {
			returns = chartInfoService.getEquipMTBFByYr();
			WebUtils.setSessionAttribute(request,Constants.CHART_MTBF_FIVE, returns.getData().get("list"));
		} catch (Exception e) {
			LOGGER.info(e);
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	////某一年12个月份统计，五大区域，设备平均维修间隔时间
	@RequestMapping(value = "/chart/getSignleYrEquipMTBF", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getSignleYrEquipMTBF(HttpServletRequest request) {
		try {
			String equipId = request.getParameter("equipId");
      String selYr = request.getParameter("year");

      int year = Tools.getCurrentYear();
      if(!StringUtils.isEmpty(selYr)){
        year = Integer.parseInt(selYr);
      }
      
			returns = chartInfoService.getEquipMTBFByMonth(equipId,String.valueOf(year));
			WebUtils.setSessionAttribute(request,Constants.CHART_MTBF_ONE, returns.getData().get("list"));
		} catch (Exception e) {
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
}
