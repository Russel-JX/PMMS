/**
 * ============================================================
 * File : Other.java
 * Description : 
 * 
 * Package : com.ge.pmms.test
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Jan 27, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.pmms.test;

import java.util.Calendar;


public class Other {
	public static void main(String[] args) {
		int aa = Calendar.YEAR;
//		System.out.println(aa);
		
		Calendar cal = Calendar.getInstance();
		int bb = cal.get(Calendar.YEAR);
		
		//返回当前月份
		System.out.println("current month；"+cal.get(Calendar.MONTH));
		
		//设置当前年当前月的第一天日期
		cal.set(cal.get(Calendar.YEAR), Calendar.MONTH-1, 1);//Calendar.MONTH是阿拉伯的1,2,3.但通过get方法获取的时候g从0开始。
		System.out.println(cal.get(Calendar.YEAR)+"=="+cal.getTime()+" month="+(cal.get(Calendar.MONTH)+1));
		
		//后一年
		cal.add(Calendar.YEAR, 1);
		cal.set(cal.get(cal.YEAR), 3, 1);
		System.out.println(cal.get(Calendar.YEAR)+"=="+cal.getTime()+" month="+(cal.get(Calendar.MONTH)+1));
		
//		String cc = ",,";//Y,Q,M   Y,,M   ,Q,M
//		String cc2 = "Y,Q,M";//Y,Q,M   Y,,M   ,Q,M
//		String cc3 = "Y,,M";//Y,Q,M   Y,,M   ,Q,M
//		String cc4 = ",Q,M";//Y,Q,M   Y,,M   ,Q,M
//		String cc5 = "Y,,";
//		String cc6 = ",Q,";
//		String cc7 = ",,M";
//		
//		String[] dd = cc.split(",");
//		String[] dd2 = cc2.split(",");
//		String[] dd3 = cc3.split(",");
//		String[] dd4 = cc4.split(",");
//		String[] dd5 = cc5.split(",");
//		String[] dd6 = cc6.split(",");
//		String[] dd7 = cc7.split(",");
		
		
		
		
		
		Double nn=90.0;
		Double mm=null;
//		System.out.println(Double.isNaN(mm.doubleValue()));
		if(mm==null){
			mm = 0.0;
		}
		String aaa = null;
		String bbb = new String();
		String ccc = "";
		ccc = null;
		System.out.println("==----=="+(nn+mm)+"\t==="+Double.isNaN(mm)+"(((((((((((((((("+(null==null)+")))"+(aaa==null)+"&&&"+(bbb==null)+"^^^"+(ccc==null));
		
		Object[] arr = new Object[2];
		arr[0] = "abc";
		arr[1] = 123;
		System.out.println("***"+arr[0]);
//		for(int i-0;){
//			
//		}
		
		Double[] cc = new Double[12];
		System.out.println(cc[0]+"=="+cc[1]);
		
	}
	
	//去除数组中的空元素
//	public static String[] getPlanType(String[] arr) {
//		for(int i=0;i<arr.length;i++){
//			if(arr[i]==""){
//				for(){
//					
//				}
//			}
//		}
//		return arr;
//	}
	
}
