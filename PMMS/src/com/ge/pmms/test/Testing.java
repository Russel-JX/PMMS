/**
 * ============================================================
 * File : Testing.java
 * Description : 
 * 
 * Package : com.ge.pmms.test
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Mar 30, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.pmms.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ge.pmms.utils.Tools;


/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Mar 30, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 谁再删我杀了谁，妈蛋
 * @History		:
 *
 ******************************************************************************/
public class Testing {

	/**
	 * @Author: iGATE 
	 * @param args
	 * @Description:
	*/
	public static void main(String[] args) {
		//今天
		Date currentDate = Tools.getToday();
		//昨天
		Date lastDate = Tools.getLastDate(currentDate);
		//从昨天往前推两年的日期
		Date lastTwoYrDate = Tools.getLastTwoYrDate(lastDate);
//		System.out.println("今天"+Tools.formatDate(currentDate));
//		System.out.println("昨天"+Tools.formatDate(lastDate));
//		System.out.println("穿越到两年前的昨天"+Tools.formatDate(lastTwoYrDate));
		
		Map<String , Double> map = new HashMap<String, Double>();
		System.out.println(map.get("1"));
	}
}
