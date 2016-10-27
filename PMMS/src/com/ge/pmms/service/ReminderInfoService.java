package com.ge.pmms.service;


import java.util.List;

import com.ge.pmms.base.PmmsException;
import com.ge.pmms.po.ReminderInfo;
import com.ge.pmms.po.WorkOrderInfo;


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
public interface ReminderInfoService{
	
	/**
	 * insert record into reminder table
	 * @Author: Xun Jiang 
	 * @param reminderInfo
	 * @return
	 * 	ReminderInfo
	 * @throws PmmsException
	 */
	public ReminderInfo insertReminder(ReminderInfo reminderInfo)throws PmmsException;
	
}
