/**
 * ============================================================
 * File : StringFormatTest.java
 * Description : 
 * 
 * Package : com.ge.pmms.test
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Apr 1, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.pmms.test;


/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Apr 1, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
public class StringFormatTest {

	/**
	 * @Author: iGATE 
	 * @param args
	 * @Description:
	*/
	public static void main(String[] args) {
		System.out.println(System.getProperty("line.separator"));
		System.out.println(System.getProperty("java.version"));
		System.out.println(System.getProperty("os.name"));
		
		String ss = "abcd  %ss";
		String ss2 = String.format(ss, "11");
		System.out.println("==="+ss);
		System.out.println("==="+ss2);
	}
}
