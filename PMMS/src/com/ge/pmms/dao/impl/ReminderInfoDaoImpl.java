package com.ge.pmms.dao.impl;

import org.springframework.stereotype.Repository;

import com.ge.pmms.base.PmmsException;
import com.ge.pmms.base.impl.BaseDaoImpl;
import com.ge.pmms.dao.ReminderInfoDao;
import com.ge.pmms.po.ReminderInfo;

@Repository
public class ReminderInfoDaoImpl extends BaseDaoImpl<ReminderInfo> implements ReminderInfoDao {

	public ReminderInfo insertReminder(ReminderInfo reminderInfo)throws PmmsException{
		try{
			save(reminderInfo);
		}catch(Exception e){
			e.printStackTrace();
		}
		return reminderInfo;
	}
}
