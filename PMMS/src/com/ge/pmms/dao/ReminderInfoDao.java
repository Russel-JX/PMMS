package com.ge.pmms.dao;


import com.ge.pmms.base.BaseDao;
import com.ge.pmms.base.PmmsException;
import com.ge.pmms.po.ReminderInfo;

public interface ReminderInfoDao extends BaseDao<ReminderInfo> {
	
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
