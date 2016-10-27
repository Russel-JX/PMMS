package com.ge.pmms.qutz;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.pmms.dao.IdmInfoDao;
import com.ge.pmms.po.IdmInfo;
import com.ge.pmms.service.IdmInfoService;


@Component
public class IdmSafetyQtz {
	@Autowired
	private IdmInfoService idmInfoService;
	@Autowired
	private IdmInfoDao idmInfoDao;
	
	//@Scheduled(cron = "0 15 17 * * ?")   
	public void startRun() {
		try {
		//	System.out.println("idm quartz start");
			List<IdmInfo> idmInfoLst=idmInfoDao.getAllUnSaftyIdm();
			idmInfoService.emailSafetyIdmReminder(idmInfoLst);
		//	System.out.println("idm quartz end");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
