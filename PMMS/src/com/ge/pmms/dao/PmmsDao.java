package com.ge.pmms.dao;

import java.util.List;

import com.ge.pmms.base.BaseDao;
import com.ge.pmms.po.ReminderInfo;
import com.ge.pmms.po.User;
import com.ge.pmms.po.WorkOrderInfo;


/**
 * @author iGATE
 *
 */
public interface PmmsDao extends BaseDao<User> {
	
	/**
	 * @param user
	 * @return List<User>
	 */
	List<User> validUser(User user);

	/**
	 * @return List<WorkOrderInfo>
	 */
	List<WorkOrderInfo> getWorkOrderInfoDetails();
	
	/**
	 * @return List<ReminderInfo>
	 */
	List<ReminderInfo> getReminderInfoDetails();
	
	/**
	 * @return List<User>
	 */
	List<User> getUserInfo();

	
	/**
	 * 
	 * @Author: iGATE 
	 * @param sso
	 * @return
	 * @Description:
	 */
	User getUserInfoById(String sso);
	 
	 
	/**
	 * @param user
	 * @return String
	 */
	String addNewUser(User user);

	/**
	 * @param user
	 * @return String
	 */
	String updateUser(User user);

	/**
	 * @param user
	 * @return String
	 */
	String deleteUser(User user);

	/**
	 * @return List<String>
	 */
	List<String> getRoleNames();
	
	/**
	 * update user password
	 * @Author: iGATE 
	 * @param sso
	 * @param pwd
	 * @return
	 * @Description:
	 */
	int updatePwd(String sso,String pwd);
	
}
