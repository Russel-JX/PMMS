package com.ge.pmms.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ge.pmms.base.BaseService;
import com.ge.pmms.base.PmmsException;
import com.ge.pmms.base.ServiceReturns;
import com.ge.pmms.dao.PmmsDao;
import com.ge.pmms.po.ReminderInfo;
import com.ge.pmms.po.User;
import com.ge.pmms.po.WorkOrderInfo;
import com.ge.pmms.service.PmmsService;
import com.ge.pmms.service.WorkOrderInfoService;
import com.ge.pmms.utils.Tools;

/**
 * @author iGATE
 *
 */
@Service("pmmsService")
@Transactional
public class PmmsServiceImpl extends BaseService<User> implements PmmsService {

  @Autowired
  private PmmsDao pmmsDao;
  
  @Autowired
  private WorkOrderInfoService workOrderInfoService;

  @Override
  public List<User> validUser(User user) {
	  
    return pmmsDao.validUser(user);
  }

  public PmmsDao getPmmsDao() {
	return pmmsDao;
}
  
  public void setPmmsDao(PmmsDao pmmsDao) {
	this.pmmsDao = pmmsDao;
}
  
  public WorkOrderInfoService getWorkOrderInfoService() {
	  
	return workOrderInfoService;
}

  public void setWorkOrderInfoService(
		  WorkOrderInfoService workOrderInfoService) {
	  
	this.workOrderInfoService = workOrderInfoService;
}

  @Override
  public List<User> getUserInfo() {
	  
    return pmmsDao.getUserInfo();
  }

  @Override
  public List<WorkOrderInfo> getWorkOrderInfoDetails() {
    List<WorkOrderInfo> list = null;
    try {
      list = workOrderInfoService.getWOInProcess(
    		  Tools.getFirstDayByDate(new Date()),
    		  Tools.getLastDayByDate(new Date()));
    }
    catch (PmmsException e) {
      LOGGER.error("failed to get work orders.", e);
    }
    return list;
  }

  @Override
  public List<ReminderInfo> getReminderInfoDetails() {
	  
    return pmmsDao.getReminderInfoDetails();
  }

  @Override
  public String addNewUser(User user) {
	  
    return pmmsDao.addNewUser(user);
  }

  public String updateUser(User user) {
	  
    return pmmsDao.updateUser(user);
  }

  public String deleteUser(User user) {
	  
    return pmmsDao.deleteUser(user);
  }

  public List<String> getRoleNames() {
	  
    return pmmsDao.getRoleNames();
  }

  public User getUserInfoById(String sso) {
    return pmmsDao.getUserInfoById(sso);
  }
  
  
  public int updatePwd(String sso,String pwd) {
    serviceReturns = new ServiceReturns();
    int cnt = pmmsDao.updatePwd(sso, pwd);
    return cnt; 
  }
  
  
}
