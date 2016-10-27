package com.ge.pmms.service;

import java.util.List;

import com.ge.pmms.po.ReminderInfo;
import com.ge.pmms.po.User;
import com.ge.pmms.po.WorkOrderInfo;

/**
 * @author iGATE
 *
 */
public interface PmmsService {

  /**
   * @param user
   * @return List<User>
   */
  public List<User> validUser(User user);

  /**
   * @return List<User>
   */
  public List<User> getUserInfo();

  /**
   * 
   * @Author: iGATE 
   * @param sso
   * @return
   * @Description:
   */
  public User getUserInfoById(String sso);

  /**
   * @return List<WorkOrderInfo>
   */
  public List<WorkOrderInfo> getWorkOrderInfoDetails();

  /**
   * @return List<ReminderInfo>
   */
  public List<ReminderInfo> getReminderInfoDetails();

  /**
   * @param user
   * @return String
   */
  public String addNewUser(User user);

  /**
   * @param user
   * @return String
   */
  public String updateUser(User user);

  /**
   * @param user
   * @return String
   */
  public String deleteUser(User user);

  /**
   * @return List<String>
   */
  public List<String> getRoleNames();
  
  public int updatePwd(String sso,String pwd);
}
