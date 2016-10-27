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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ge.pmms.base.BaseService;
import com.ge.pmms.base.PmmsException;
import com.ge.pmms.dao.ReminderInfoDao;
import com.ge.pmms.po.ReminderInfo;
import com.ge.pmms.service.ReminderInfoService;
//import java.util.Calendar;


/*******************************************************************************
 *
* @Author 		: Xun Jiang
 * @Version 	: 1.0
 * @Date Created: April 20, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
@Service
@Transactional
public class ReminderInfoServiceImpl extends BaseService<ReminderInfo> implements ReminderInfoService {
	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Autowired
	private ReminderInfoDao reminderInfoDao;
	
	public ReminderInfo insertReminder(ReminderInfo reminderInfo)throws PmmsException{
		return reminderInfoDao.insertReminder(reminderInfo);
	}
	
}
