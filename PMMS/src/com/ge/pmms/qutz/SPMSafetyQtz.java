/**
 * ============================================================
 * File : SPMSafetyQtz.java
 * Description : 
 * 
 * Package : com.ge.pmms.qutz
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

package com.ge.pmms.qutz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.pmms.dao.SparePartInfoDao;
import com.ge.pmms.po.SparePartInfo;
import com.ge.pmms.service.SparePartInfoService;


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
@Component
public class SPMSafetyQtz {
	@Autowired
	private SparePartInfoService sparePartInfoService;
	@Autowired
	private SparePartInfoDao sparePartInfoDao;
	private static SparePartInfo sparePartInfo = new SparePartInfo();

	//@Scheduled(cron = "0 0/1 * * * ?")  每隔一分钟        	
	public void spReminder(){
		try{
			System.out.println("Excute spReminder() method start..............");
			sparePartInfo.setSafetyFlag(true);
			List<SparePartInfo> spList = sparePartInfoDao.getSparePartInfo(sparePartInfo);
			sparePartInfoService.emailSafetySPReminder(spList);
			System.out.println("Excute spReminder() method start..............");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
