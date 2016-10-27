package com.ge.pmms.utils;

import java.util.HashMap;
import java.util.Map;


public class Constants {
	
	public static String LOCALE = "zh_CN";
	
	public static final String MESSAGE_TYPE_INFO = "info";
	public static final String MESSAGE_TYPE_WARNING = "warning";
	public static final String MESSAGE_TYPE_ERROR = "error";
	
	public static final String ERR_COMMON_SYS_ERROR = "10000";
	public static final String ERR_COMMON_DB_ERROR = "10001";
	public static final String ERR_COMMON_BAD_ACCESS = "10002";
	public static final String ERR_COMMON_DB_NO_RESULTS = "10003";
	public static final String ERR_COMMON_DB_NOT_EXISTS = "10004";
	public static final String ERR_COMMON_DB_REFERENCED = "10005";
	
	public static final String ERR_ORDER_NOT_EXISTS = "20000";
	
	public static final int HOURS = 60*60*1000;
	public static final int DAYS = 60*60*1000*24;
	public static final int DECIMAL_SCALE2 = 2;
	public static final int DECIMAL_SCALE4 = 4;
	public static final int DAYOFYR_LEAP = 366;
	public static final int DAYOFYR_NON_LEAP = 365;
	public static final int FEB_LEAP = 29;
	public static final int FEB_NON_LEAP = 28;
	
	
	
public static final Map<String,String> SYS_CONFIG_MAP = new HashMap<String,String>();
  
	//系统参数配置
	//文档上传存放路径
	public static final String EQUIP_DOC_UPLOAD_PATH = "1001";
	
	//设备每天工作时长
	public static final String EQUIP_WORK_TIME = "1002";
	//计划工单收件人
	public static final String FW_EMAIL_RECEIVER = "1003";
	//系统是否发送邮件提醒。1：提醒，0：不提醒
	public static final String FLAG_MAIL_REMINDER = "1004";
	//系统是否发送短信提醒。1：提醒，0：不提醒
	public static final String FLAG_MSG_REMINDER = "1005";
	//计划工单邮件提醒内容模板
	public static final String FW_EMAIL_FORMAT = "1007";
	//计划工单短信提醒内容模板
	public static final String FW_MSG_FORMAT = "1008";
	//间接物料安全库存管理收件人
	public static final String IDM_EMAIL_RECEIVER = "1006";
	//备件安全库存邮件接收人
	public static final String SPM_EMAIL_RECEIVER = "1009";
	//故障维修工单创建短信提醒
	public static final String WO_CREATED_MSG_RECEIVER = "1010";
	//维修团队的SSO,具有维修权限，开始和结束维修工单时验证
	public static final String MAINTANENCE_TEAM = "1011";
	
	/**
	 * 是否发送提醒
	 */
	public static final String FLAG_REMIND = "1";
	
	/*
	 * 设备类型
	 */
	public static final String EQUIP_TYPE_MANUFACTURE = "1";
	public static final String EQUIP_TYPE_SPECIAL = "2";
	public static final String EQUIP_TYPE_FACILITY = "3";
	
	public static final String DATE_PATTEN = "yyyy-MM-dd";
	public static final String DATE_PATTEN_SEC = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_PATTEN_MONTH = "yyyy-MM";
	
	/*
	 * module 定义
	 */
	public static final String MODULE_WO = "1";//工单
	public static final String MODULE_PM = "2";//计划
	public static final String MODULE_SPW = "3";//备件
	public static final String MODULE_AR = "4";//报表
	public static final String MODULE_EF = "5";//设备/设施
	public static final String MODULE_IMS = "6";//间接物料
	public static final String MODULE_URM = "7";//用户/权限
	
	/*
	 * reminder 定义
	 */
	public static final String REMINDER_YES = "1";//提醒
	public static final String REMINDER_NO = "2";//不提醒
	
	//保养内容图表说明
	public static final String MAINTITEMHELP = "方法代码: 1（清洁）, 2（检查）, 3（补充调整）, 4（修理）, 5（更换）, 6（润滑）";
	
	
	/**
	 * 设备文档类型
	 */
	//全厂
	public static final String EQUIPDOC_ENTIRE = "1";
	//设备型号
	public static final String EQUIPDOC_EQMODEL = "2";
	//设备编号
	public static final String EQUIPDOC_EQNO = "3";

	/**
	 * 工单模块常量
	 */
	//工单类型--故障维修工单
	public static final String WORK_ORDER_TYPE_FALUT = "1";
	//工单类型--计划保养工单
	public static final String WORK_ORDER_TYPE_PLAN = "2";
	//未关闭状态的工单
	public static final String ORDER_FLAG_NOT_CLOSED = "1";
	//关闭状态的工单
	public static final String ORDER_FLAG_CLOSED = "2";
	
	//工单状态---刚提交
	public static final String WORK_ORDER_STATUS_SUBMIT="1";
	//工单状态---维修中
	public static final String WORK_ORDER_STATUS_MAINT="2";
	//工单状态---已关闭
	public static final String WORK_ORDER_STATUS_ClOSED="3";
	
	//工单状态描述---刚提交
	public static final String WORK_ORDER_STATUS_SUBMIT_DESC="未开始";
	//工单状态态描述---维修中
	public static final String WORK_ORDER_STATUS_MAINT_DESC="维修中";
	//工单状态态描述---已关闭
	public static final String WORK_ORDER_STATUS_ClOSED_DESC="关闭";
	
	//故障维修工单
	public static final String FAULT_WO="故障维修工单";
	//计划保养工单
	public static final String PLAN_WO="计划保养工单";
	
	//故障维修默认描述
	public static final String FAULT_WO_DEFAULT_DESC="故障维修工单";
	//计划保养默认描述
	public static final String PLAN_WO_DEFAULT_DESC="计划保养工单";
	
	//是否涉及安全隐患描述
	public static final boolean SAFETY_INVOLVED = true;
	public static final boolean NOT_SAFETY_INVOLVED = false;
	public static final String SAFETY_INVOLVED_DESC = "是";
	public static final String NOT_SAFETY_INVOLVED_DESC = "否";
	
	//是否需停机
	public static final boolean SHUTDOWN = true;
	public static final boolean NOT_SHUTDOWN = false;
	public static final String SHUTDOWN_DESC = "是";
	public static final String NOT_SHUTDOWN_DESC = "否";
	
	/**
	 * 备件模块
	 */
	//备件出入库操作---入库
	public static final String TRANS_TYPE_IN = "1";
	//备件出入库操作---出库
	public static final String TRANS_TYPE_OUT = "2";
	
	//备件出入库操作---入库描述
	public static final String TRANS_TYPE_IN_DESC = "入库";
	//备件出入库操作---出库描述
	public static final String TRANS_TYPE_OUT_DESC = "出库";
	/**
	 * 复制整年计划提示
	 */
	//来源年份没有计划
	public static final String FROM_YEAR_NO_PLAN = "1";
	//工单类型--计划保养工单
	public static final String TO_YEAR_HAD_PLAN = "2";
	
	/**
	 * 五大区域编号和中文名
	 */
	
	//变压器制造部
	public static final String DEPT_TRANSFORMER = "dept0001";
	public static final String DEPT_TRANSFORMER_NM = "变压器制造部";
	//成套设备制部
	public static final String DEPT_OUTFIT = "dept0002";
	public static final String DEPT_OUTFIT_NM = "成套设备制部";
	//机械加工部
	public static final String DEPT_MACHINING = "dept0004";
	public static final String DEPT_MACHINING_NM = "机械加工部";	
	//开关制造部
	public static final String DEPT_SWITCH = "dept0005";
	public static final String DEPT_SWITCH_NM = "开关制造部";	
	//其他部门
	public static final String DEPT_OTHER = "dept0006";
	public static final String DEPT_OTHER_NM = "其他部门";
	//总计
	public static final String DEPT_AVG = "dept000x";
	public static final String DEPT_AVG_NM = "总计";	
	
	/**
	 * 报表与统计
	 * 统计周期常亮
	 */
	//按月份统计
	public static final int PERIOD_MONTH = 12;
	//按年统计
	public static final int PERIOD_YEAR = 5;
	
	/**
	 * 报表与统计
	 * 报表类型定义
	 */
	//保养计划完成率
	public static final String CHART_PM_ONE = "PM_ONE";//一年中五大部门
	public static final String CHART_PM_FIVE = "PM_FIVE";//五年中部门
	public static final String CHART_PM_SINGLE = "PM_SINGLE";//单个设备
	//设备可利用率
	public static final String CHART_EA_ONE = "EA_ONE";
	public static final String CHART_EA_FIVE = "EA_FIVE";
	public static final String CHART_EA_SINGLE = "EA_SINGLE";
	//平均维修间隔时间
	public static final String CHART_MTBF_ONE= "MTBF_ONE";
	public static final String CHART_MTBF_FIVE= "MTBF_FIVE";
	public static final String CHART_MTBF_SINGLE= "MTBF_SINGLE";
	//平均维修时间
	public static final String CHART_MTTR_ONE = "MTTR_ONE";
	public static final String CHART_MTTR_FIVE = "MTTR_FIVE";
	public static final String CHART_MTTR_SINGLE = "MTTR_SINGLE";
	//维修配件费用
	public static final String CHART_MSPC_ONE = "MSPC_ONE";
	public static final String CHART_MSPC_FIVE = "MSPC_FIVE";
	public static final String CHART_MSPC_SINGLE = "MSPC_SINGLE";
	//维修配件使用数量
	public static final String CHART_MSPQU_ONE = "MSPQU_ONE";
	public static final String CHART_MSPQU_FIVE = "MSPQU_FIVE";
	public static final String CHART_MSPQU_SINGLE = "MSPQU_SINGLE";
	
	/**
	 * 报表与统计
	 * 表头
	 */
	//部门
	public static final String CHART_DEPT_TH = "区域";
	//单个设备
	public static final String CHART_SINGLE_TH = "设备/备件编号";
//	//单个备件
//	public static final String CHART_SP_TH = "备件编号";
	
	/**
	 * 短信发送配置
	 */
	//串口名
	public static final String MSG_PORT = "COM5";
	//网关
	public static final String MSG_GATE_WAY = "SMS";
	
	/**
	 * 首页展示快捷链接和工单列表配置
	 */
	//展示快捷链接
	public static final String SHOW_SHORT_LINK = "Y";
	//不展示快捷链接
	public static final String HIDE_SHORT_LINK = "N";
	//展示工单列表
	public static final String SHOW_WO = "Y";
	//不展示工单列表
	public static final String HIDE_WO = "N";
	
	/**
	 * 页面是否可访问
	 * 大于1：可访问；
	 * 否则不能访问
	 */
	//展示快捷链接
	public static final String PAGE_ROLE_NO = "1";
	//不展示快捷链接
	public static final String PAGE_ROLE_YES = "3";
	
	/**
	 * 邮件常量配置
	 */
	//邮件服务器域名
	public static final String MAIL_HOST_URL = "ae.ge.com";
	
	/**
	 * 访问页面控制配置
	 */
	//页面数组配置
	public static final String[] PAGE_DEFINITION = {"100","101","102","103","104","105","106","107","108","109","110","111","112","113","114","115","116"};
	/*//故障工单页面
	public static final String PAGE_OWO = "100";
	//计划工单页面
	public static final String PAGE_PWO = "101";
	//计划保养
	public static final String PAGE_PM = "102";
	//备件入库
	public static final String PAGE_SP_IN = "103";
	//备件出库
	public static final String PAGE_SP_OUT = "104";
	//PM完成率
	public static final String PAGE_PMFF = "105";
	//设备可利用率
	public static final String PAGE_EA = "106";
	//平均维修间隔时间
	public static final String PAGE_MTBF = "107";
	//平均维修时间
	public static final String PAGE_MTTR = "108";
	//维修配件费用
	public static final String PAGE_MSPC = "109";
	//维修配件使用数量
	public static final String PAGE_MSPN = "110";
	//设备管理
	public static final String PAGE_EM = "111";
	//文档管理
	public static final String PAGE_DM = "112";
	//间接物料入库
	public static final String PAGE_IDM_IN = "113";
	//间接物料出库
	public static final String PAGE_IDM_OUT = "114";
	//用户管理
	public static final String PAGE_UM = "115";
	//角色管理
	public static final String PAGE_RM = "116";*/
	
	
	
	public static final String INVALID_WRDS = "[\\\\/:*?\"<>|]+";
	
	//安全库存
	public static final double Z = 1.65;      //安全系数
	public static final int TIME_SAPN = 24;   //时间跨度，默认24个月
}
