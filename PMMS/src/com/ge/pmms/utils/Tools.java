package com.ge.pmms.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.format.datetime.DateFormatter;

import com.ge.pmms.base.ControllerReturns;
import com.ge.pmms.base.MessageContent;
import com.ge.pmms.base.ServiceReturns;

public class Tools {
    
	/**
     * 返回页面配置
     * @Author: Xun Jiang 
     * @return Date
     * @throws ParseException
     * @Description:
     */
	public static String getPageConfig(String pageCode){
		Map<String,String> map = new HashMap<String,String>();
		map.put("100","故障工单");
		map.put("101","计划工单");
		map.put("102","计划保养");
		map.put("103","备件入库");
		map.put("104","备件出库");
		map.put("105","PM完成率");
		map.put("106","设备可利用率");
		map.put("107","平均维修间隔时间");
		map.put("108","平均维修时间");
		map.put("109","维修配件费用");
		map.put("110","维修配件使用数量");
		map.put("111","设备管理");
		map.put("112","文档管理");
		map.put("113","间接物料入库");
		map.put("114","间接物料出库");
		map.put("115","用户管理");
		map.put("116","角色管理");
		return map.get(pageCode);
	}
    
    /**
     * 返回当前系统时间(包含时分秒)
     * @Author: Mike 
     * @return Date
     * @throws ParseException
     * @Description:
     */
    public static Date getToday(){
    	Calendar CALENDAR=Calendar.getInstance();
    	return CALENDAR.getTime();
    }
	public static boolean isNull(Object obj) {
		return obj == null;
	}

	public static <T> T isNull(T obj, T defaultVal) {
		if (obj == null) {
			return defaultVal;
		}
		return obj;
	}

	public static String isNull(Object obj, String defaultVal) {
		if (obj == null) {
			return defaultVal;
		}
		return obj.toString();
	}

	public static boolean isBlank(String val) {
		if (isNull(val) || "".equals(val)) {
			return true;
		}
		return false;
	}

	public static String decode(String str, String charset) throws UnsupportedEncodingException  {
		return java.net.URLDecoder.decode(str, charset);
	}

	public static String decode(String str) throws UnsupportedEncodingException {
		return java.net.URLDecoder.decode(str, "UTF-8");
	}
	
	public static ControllerReturns getExceptionControllerRetruns(Exception e){
		ControllerReturns returns = new ControllerReturns();
		returns.setSuccess(false);
		returns.setMessageType("error");
		returns.setMessage(new MessageContent(Constants.ERR_COMMON_SYS_ERROR));
		return returns;
	}
	public static ServiceReturns getExceptionServiceReturns(Exception e){
		ServiceReturns returns = new ServiceReturns();
		returns.setSuccess(false);
		returns.setMessageType("error");
		returns.setMessage(new MessageContent(Constants.ERR_COMMON_SYS_ERROR));
		return returns;
	}
	
	public static String formatDate(Date date) {
		if (date == null) {
			return "";
		}
		DateFormatter format = new DateFormatter("yyyy-MM-dd HH:mm:ss");
		return format.print(date, Locale.getDefault());
	}
	
	public static String formatDate(Date date,String patten) {
		if (date == null) {
			return "";
		}
		DateFormatter format = new DateFormatter(patten);
		return format.print(date, Locale.getDefault());
	}

	public static Date parseToDate(String source) throws ParseException {
		return parseToDate(source,Constants.DATE_PATTEN);
	}
	
	public static Date parseToDate(Date source) throws ParseException {
		String tmp = formatDate(source,Constants.DATE_PATTEN);
		return parseToDate(tmp);
	}
	
	public static Date parseToDate(String source,String patten) throws ParseException {
		if (isEmpty(source)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(patten);
		
		return sdf.parse(source);
	}
	/**
     * 返回指定日期所在月份的第一天
     * @Author: Xun Jiang 
     * @param date
     * @return Date
     * @throws ParseException
     * @Description:
     */
	public static Date getFirstDayByDate(Date date) {
		Calendar CALENDAR=Calendar.getInstance();
		CALENDAR.setTime(date);
		CALENDAR.set(CALENDAR.get(Calendar.YEAR), CALENDAR.get(Calendar.MONTH), 1,0,0,0);
		return CALENDAR.getTime();
	}
	/**
     * 返回指定日期所在月份的最后一天
     * @Author: Xun Jiang 
     * @param date
     * @return Date
     * @throws ParseException
     * @Description:
     */
	public static Date getLastDayByDate(Date date) {
		Calendar CALENDAR=Calendar.getInstance();
		CALENDAR.setTime(date);
		CALENDAR.set(CALENDAR.get(Calendar.YEAR), CALENDAR.get(Calendar.MONTH),  CALENDAR.getActualMaximum(Calendar.DAY_OF_MONTH),23,59,59);
		return CALENDAR.getTime();
	}
	/**
     * 返回指定日期所在年份的第一天
     * @Author: Xun Jiang 
     * @param date
     * @return Date
     * @throws ParseException
     * @Description:
     */
	public static Date getFirstDayofYearByDate(Date date) {
		Calendar CALENDAR=Calendar.getInstance();
		CALENDAR.setTime(date);
		CALENDAR.set(CALENDAR.get(Calendar.YEAR), 0, 1,0,0,0);
		return CALENDAR.getTime();
	}
	/**
     * 返回指定日期所在年份的最后一天
     * @Author: Xun Jiang 
     * @param date
     * @return Date
     * @throws ParseException
     * @Description:
     */
	public static Date getLastDayofYearByDate(Date date) {
		Calendar CALENDAR=Calendar.getInstance();
		CALENDAR.setTime(date);
		CALENDAR.set(CALENDAR.get(Calendar.YEAR), 11, 31,23,59,59);
		return CALENDAR.getTime();
	}
	/**
     * 返回指定日期所在月份的最后一天
     * @Author: Xun Jiang 
     * @param date
     * @return String
     * @throws ParseException
     * @Description:
     */
	public static String getLastDayofDate(Date date) {
		return formatDate(getLastDayByDate(date));
	}
	/**
     * 返回指定日期的月份
     * @Author: Xun Jiang 
     * @param date
     * @return int
     * @throws ParseException
     * @Description:
     */
	public static int getMonthByDate(Date date) {
		Calendar CALENDAR=Calendar.getInstance();
		CALENDAR.setTime(date);
		return CALENDAR.get(Calendar.MONTH);
	}
	/**??????????(有问题，还未测试)?????????
     * 返回指定日期的年份
     * @Author: Xun Jiang 
     * @param N/A
     * @return int
     * @throws ParseException
     * @Description:
     */
	public static int getYearByDate(Date date) {
		Calendar CALENDAR=Calendar.getInstance();
		CALENDAR.setTime(date);
		return CALENDAR.get(Calendar.YEAR);
	}
	/**
     * 返回当前月份
     * @Author: Xun Jiang 
     * @param N/A
     * @return int
     * @throws ParseException
     * @Description:
     */
	public static int getCurrentMonth() {
		Calendar CALENDAR=Calendar.getInstance();
		return CALENDAR.get(Calendar.MONTH);
	}
	/**
     * 返回当前年份
     * @Author: Xun Jiang 
     * @param N/A
     * @return int
     * @throws ParseException
     * @Description:
     */
	public static int getCurrentYear() {
		Calendar CALENDAR=Calendar.getInstance();
		return CALENDAR.get(Calendar.YEAR);
	}
	/**
     * 返回一个月的第一天
     * @Author: Xun Jiang 
     * @param mon  0到11
     * @return Date
     * @throws ParseException
     * @Description:
     */
	public static Date getFirstDayOfMonth(int mon) throws ParseException {
		Calendar cal = Calendar.getInstance();
		if(mon!=12){
			cal.set(cal.get(Calendar.YEAR), mon, 1);
		}else{
			cal.add(Calendar.YEAR, 1);
			cal.set(cal.get(Calendar.YEAR), mon, 1);
		}
		
		return parseToDate(cal.getTime());
	}
	/**
	 * 比较两个Date，返回相差的时间(小时)
	 * @param sDate	开始日期
	 * @param eDate 结束日期
	 * @return
	 */
	public static double getGapWithHour(Date sDate,Date eDate){
		return ((double)(eDate.getTime() - sDate.getTime()) / Constants.HOURS);
	}
	
	public static boolean isEmpty(Object obj) {
		return null == obj || "".equals(obj);
	}
	
	public static boolean isBlankRow(Row row) {
		return null == row || isEmptyCell(row.getCell(0))
				|| isEmptyCell(row.getCell(1));
	}

	public static String isEmptyCell(Cell cell, String defaultStr) {
		if (isEmptyCell(cell)) {
			return defaultStr;
		}
		//some value looks lie "'0556-7260190 ", need remove the comma
		String tmp = cell.toString();
		if (tmp.startsWith("'")){
		    tmp = tmp.substring(1, tmp.length());
		}
		return trimAllBlanks(tmp);
	}

	public static boolean isEmptyCell(Cell cell) {
		if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK
				|| !org.springframework.util.StringUtils.hasLength(cell.toString())) {
			return true;
		}
		return false;
	}

	public static String isEmptyNumberCell(Cell cell, String defaultStr) {
		if (isEmptyCell(cell)) {
			return defaultStr;
		}
		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			return String.valueOf((long) cell.getNumericCellValue());
		}
		return cell.toString().trim();
	}

	public static String trimAllBlanks(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
	
	//获取以时间为序列的字符串
	public static String getTimeStr() {
		String tempStr = formatDate(getToday());
		tempStr = tempStr.substring(0, 4)+tempStr.substring(5, 7)+tempStr.substring(8, 10)+tempStr.substring(11, 13)+tempStr.substring(14, 16)+tempStr.substring(17, 19);
		//return  tempStr+System.nanoTime();
		return  tempStr+generateRandom();
	}
	//获取四位随机数
	private synchronized static String generateRandom(){
		return String.valueOf((int)(Math.random()*9000+1000));
	}
	/*
	public static String getTimeStr() {
		return ((Long)System.nanoTime())+generate();
	}
	
	private synchronized static String generate() {
		 
        int n = (int) (Math.random() * 300) + 300;
        return n + "";
    }
	*/
	public static String getParameters(JSONObject jsonObjParameters,
			String parameterName)  {
		Object object;
		boolean status=jsonObjParameters.containsKey(parameterName);
		if (status) {
			object = jsonObjParameters.get(parameterName);
			if (null == object) {
				object = "";
			}
		}
		else {
			object ="";
		}
		return object.toString();
	}

	
	/**
	 * 返回包含年月日的分割后的数组
	 * @param source 日期字符串
	 * @return
	 * @throws ParseException 
	 */
	public static int[] splitDate(String source) throws ParseException{
		int[] tmp = new int[3];
		if(!StringUtils.isEmpty(source)){
			Date date = parseToDate(source);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			tmp[0] = cal.get(Calendar.YEAR);
			tmp[1] = cal.get(Calendar.MONTH)+1;
			tmp[2] = cal.get(Calendar.DAY_OF_MONTH);
		}
		return tmp;
	}
	
	
	/**
	 * 计算间隔的小时数
	 * @param startDt
	 * @param endDt
	 * @return
	 * @throws ParseException
	 */
	public static long getHrsGap(String startDt, String endDt) throws ParseException {
		long gaps = 0L;
		Date stD = Tools.parseToDate(startDt, Constants.DATE_PATTEN_SEC);
		Date edD = Tools.parseToDate(endDt, Constants.DATE_PATTEN_SEC);
		gaps = ((edD.getTime() - stD.getTime()) / Constants.HOURS)+24;

		return gaps;
	}
	
	/**
	 * 计算间隔的天数
	 * @param startDt
	 * @param endDt
	 * @return
	 * @throws ParseException
	 */
	public static long getDaysGap(String startDt, String endDt) throws ParseException {
		long gaps = 0L;
		Date stD = Tools.parseToDate(startDt, Constants.DATE_PATTEN_SEC);
		Date edD = Tools.parseToDate(endDt, Constants.DATE_PATTEN_SEC);
		gaps = ((edD.getTime() - stD.getTime()) / Constants.DAYS)+1;

		return gaps;
	}
	
	/**
	 * 计算从月初到指定日期的间隔天数
	 * @Author: iGATE 
	 * @param time
	 * @return
	 * @throws ParseException
	 * @Description:
	 */
	public static long getDaysGapFromFirst(String endDt) throws ParseException {
		Date date = Tools.parseToDate(endDt, Constants.DATE_PATTEN_SEC);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		
		cal.clear(Calendar.HOUR_OF_DAY);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		
		Date startDt = cal.getTime();
		return getDaysGap(formatDate(startDt),endDt);
	}
	
	
	
	/**
	 * 计算从指定日期到月末的间隔天数
	 * @Author: iGATE 
	 * @param time
	 * @return
	 * @throws ParseException
	 * @Description:
	 */
	public static long getDaysGapToEnd(String startDt) throws ParseException {
		Date date = Tools.parseToDate(startDt, Constants.DATE_PATTEN_SEC);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int finalDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		cal.set(Calendar.DAY_OF_MONTH, finalDay);
		
		cal.clear(Calendar.HOUR_OF_DAY);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		
		Date endDt = cal.getTime();
		return getDaysGap(startDt,formatDate(endDt));
	}
	
	/**
	 * 是否闰年
	 * @param year
	 * @return
	 */
	public static boolean  isLeapYr(int year){
		return (year % 4 == 0 && year % 100 != 0 || year % 400 == 0);
	}
	
	/**
	 * 获取年份的总天数
	 * @param year
	 * @return
	 */
	public static int getTotalDaysOfYr(int year){
		if(isLeapYr(year)){
			return Constants.DAYOFYR_LEAP;
		}else{
			return Constants.DAYOFYR_NON_LEAP;
		}
	}
	
	/**
	 * 获取月份的最后一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getFinalDayofMonth(int year,int month){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	
	/**
	 * 获取月份的总共的小时数
	 * @param year
	 * @param month
	 * @return
	 */
	public static long getTotalHrsofMonth(int year,int month){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		int days =  cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		return days*24;
	}
	
	/**
	 * 获取年份的总共的小时数
	 * @param year
	 * @param month
	 * @return
	 */
	public static long getTotalHrsofYear(int year){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		int days =  cal.getActualMaximum(Calendar.DAY_OF_YEAR);
		return days*24;
	}
	
	
	/**
	 * 返回年份的最末日期
	 * @param year
	 * @return
	 */
	public static String finalDateStrOfYr(int year){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		return formatDate(cal.getTime(),Constants.DATE_PATTEN_SEC);
	}
	
	
	/**
   * 返回年份的最初日期
   * @param year
   * @return
   */
  public static String firstDateStrOfYr(int year){
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.YEAR, year);
    cal.set(Calendar.MONTH, Calendar.JANUARY);
    cal.set(Calendar.DAY_OF_MONTH, 1);
    return formatDate(cal.getTime(),Constants.DATE_PATTEN_SEC);
  }
  
  /**
   * 返回年份的最初日期
   * @param year
   * @return
   */
  public static Date firstDateStrOfYrDate(int year){
    Calendar cal = Calendar.getInstance();
    cal.set(year, 0, 1,0,0,0);
    return cal.getTime();
  }
  
  /**
   * 返回年份的最末日期
   * @param year
   * @return
   */
  public static Date finalDateStrOfYrDate(int year){
	Calendar cal = Calendar.getInstance();
	cal.set(year, 11, cal.getActualMaximum(Calendar.DATE),23,59,59);
    return cal.getTime();
  }
	
	/**
	 * 拼接开始时间字符串
	 * @Author: iGATE 
	 * @param year
	 * @return
	 * @Description:
	 */
	public static String conactStartDt(int year){
		String str = "";
		if(0 != year && String.valueOf(year).length() == 4){
			str =  year + "-1-1 00:00:00.0";
		}
		return str;
	}
	
	/**
	 * 拼接结束时间字符串
	 * @Author: iGATE 
	 * @param year
	 * @return
	 * @Description:
	 */
	public static String conactEndDt(int year){
		String str = "";
		if(0 != year && String.valueOf(year).length() == 4){
			str = year +"-12-31 23:59:59.999";
		}
		return str;
	}
	
	
	/**
	 * 保留两位小数
	 * @Author: iGATE 
	 * @param value
	 * @return
	 * @Description:
	 */
	public static double roundDecimal2(double value){
		BigDecimal bd = new BigDecimal(value);
		return bd.setScale(Constants.DECIMAL_SCALE2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
   * 保留两位小数
   * @Author: iGATE 
   * @param value
   * @return
   * @Description:
   */
  public static double roundDecimal4(double value){
    BigDecimal bd = new BigDecimal(value);
    return bd.setScale(Constants.DECIMAL_SCALE4, BigDecimal.ROUND_HALF_UP).doubleValue();
  }
	
	
	public static String getPinYinAbbreviation(String SourceStr){
	  return PinYin2Abbreviation.cn2py(SourceStr);
	}
	
	/*
	public static void main(String[] args){
		//System.out.println("today ==== ");
//		String tmp = "2015-03-04 18:04:16.863";
//		String tmp1 = "2015-03-08 21:04:16.123";
//		System.out.println(getFinalDayofMonth(2002,2)+"=="+finalDayOfYr(2015));
//		System.out.println(getCurrentYear());
		//-------------------------------
		System.out.println(generateRandom());
		System.out.println(getTimeStr());
		//System.out.println((int)(Math.random()*9000+1000));
	}
*/
	public static void setCellStyleForEachCell(HSSFRow row, HSSFCellStyle style) {
		if (row != null) {
			Iterator<Cell> itr = row.cellIterator();
			while (itr.hasNext()) {
				itr.next().setCellStyle(style);
			}
		}
	}
	
	/**
	 * 获取指定时间前一天的日期
	 * @Author: Mike 
	 * @param date
	 * @return
	 * @Description:
	 */
	public static Date getLastDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return cal.getTime();
	}
	/**
	 * 获取指定日期后推两年的日期
	 * @Author: Mike 
	 * @param date
	 * @return
	 * @Description:
	 */
	public static Date getLastTwoYrDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, -2);
		return cal.getTime();
	}
	
	public static String replaceByRegx(String source,String regx,String replaceMent){
	  StringBuffer sb = new StringBuffer();
	  if(!StringUtils.isEmpty(source) && !StringUtils.isEmpty(regx)){
	    Pattern p = Pattern.compile(regx);
	    Matcher matcher = p.matcher(source);
	   
	    while (matcher.find()){
	      matcher.appendReplacement(sb, replaceMent);
	    }
	    
	    matcher.appendTail(sb);
	  }
	  return sb.toString();
	}
	
	public static String md5Encript(String s) {
    char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
        'F' };
    try {
      byte[] btInput = s.getBytes();
      //获得MD5摘要算法的 MessageDigest 对象  
      MessageDigest mdInst = MessageDigest.getInstance("MD5");
      //使用指定的字节更新摘要  
      mdInst.update(btInput);
      //获得密文  
      byte[] md = mdInst.digest();
      //把密文转换成十六进制的字符串形式  
      int j = md.length;
      char str[] = new char[j * 2];
      int k = 0;
      for (int i = 0; i < j; i++) {
        byte byte0 = md[i];
        str[k++] = hexDigits[byte0 >>> 4 & 0xf];
        str[k++] = hexDigits[byte0 & 0xf];
      }
      return new String(str);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
	
	
	public static void main(String[] args){
	  String tmp = "LD3T-11.5M/9M";
	  System.out.println("dddddddddd = "+Tools.md5Encript("123"));
	}
	
	
	//导出Excel公共方法
	public static void commonExport(HttpServletResponse response, HSSFWorkbook hwb,String fileName) throws IOException{
		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		hwb.write(outByteStream);
		byte[] outArray = outByteStream.toByteArray();
		response.setContentType("application/ms-excel;charset=UTF-8");
		response.setContentLength(outArray.length);
		response.setHeader("Expires", "0"); //设置本页面不要被浏览器缓存
		Calendar cal = Calendar.getInstance();
		response.setHeader("Content-Disposition", 
				"attachment; filename="
				+fileName+"_"
				+ (cal.get(Calendar.MONTH)+1) + ""
				+ cal.get(Calendar.DATE) +""
				+ cal.get(Calendar.YEAR) + ""
				+ cal.get(Calendar.HOUR_OF_DAY)
				+""+""
				+ cal.get(Calendar.MINUTE) +""
				+ cal.get(Calendar.SECOND) + ".xls");
		OutputStream outStream = response.getOutputStream();
		outStream.write(outArray);
		outStream.flush();
		outStream.close();
		outByteStream.close();		
	}
}
