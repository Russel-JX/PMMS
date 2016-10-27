/**
 * ============================================================
 * File : DiffTimeTest.java
 * Description : 
 * 
 * Package : com.ge.pmms.test
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Mar 14, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.pmms.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Mar 14, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
public class DiffTimeTest {

	/**
	 * @Author: iGATE 
	 * @param args
	 * @Description: 计算时间差
	*/
	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
	    try  
	    {   
//	    	Date d1 = df.parse("2015-04-08 14:55:04.123");   
//	        Date d2 = df.parse("2015-04-12 13:51:40.440");
	    	
//	    	Date d1 = df.parse("2012-01-01 00:00:00.327");   
//	        Date d2 = df.parse("2012-12-31 23:59:33.167");
	        
	        Date d1 = df.parse("2014-12-31 14:11:34");   
	        Date d2 = df.parse("2014-12-31 23:59:59");
	        
//	        Date d1 = df.parse("2015-03-25 18:06:46.647");   
//	        Date d2 = df.parse("2015-04-08 10:23:52.773");
	    	
//	    	Date d1 = df.parse("2014-04-02 14:41:33.693");   
//	        Date d2 = df.parse("2014-12-31 23:59:33.167"); 
	    	
//	    	Date d1 = df.parse("2015-04-01 00:00:00.327");   
//	        Date d2 = df.parse("2015-04-08 15:33:22.237"); 
	        long diff = d1.getTime() - d2.getTime();   
	        double hours = (double)diff / (1000 * 60 * 60);  
	        double days = (double)diff / (1000 * 60 * 60*24);  
	        
	        System.out.println(diff+"=="+hours);
	        System.out.println(diff+"=="+days);
	        
	        
//	        double aa = 32399000;
//	        long cc = 32399000;
//	        double bb = 3600000;
//	        System.out.println(d1.getTime());
//	        System.out.println(aa/bb);
//	        System.out.println(cc/bb);
	    }   
	    catch (Exception e)   
	    {   
	    }  

	}
}
