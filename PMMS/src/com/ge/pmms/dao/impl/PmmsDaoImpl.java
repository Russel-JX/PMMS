package com.ge.pmms.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.ge.pmms.base.impl.BaseDaoImpl;
import com.ge.pmms.dao.PmmsDao;
import com.ge.pmms.po.ReminderInfo;
import com.ge.pmms.po.User;
import com.ge.pmms.po.WorkOrderInfo;


/**
 * @author iGATE
 *
 */
@Repository("pmmsDao")
public class PmmsDaoImpl extends BaseDaoImpl<User> implements PmmsDao {
	
	@Override
	public List<User> validUser(User user) {

		try {
			StringBuffer sbQuery = new StringBuffer();

			sbQuery.append("from User"
					+ " where SSO=:sso and PASSWORD=:pwd");
			Query query = getSession().createQuery(
					sbQuery.toString());
			query.setParameter("sso", user.getSso());
			query.setParameter("pwd", user.getPassword());

			@SuppressWarnings("unchecked")
			List<User> list1 = query.list();

			return list1;
		}
		catch (Exception e) {
			logger.error("fetch user info failed", e);
		}

		return null;

	}

	@Override
	public List<User> getUserInfo() {

		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("from User");
		Query query = getSession().createQuery(sbQuery.toString());

		@SuppressWarnings("unchecked")
		List<User> list1 = query.list();

		return list1;
	}
	
	
  public User getUserInfoById(String sso) {

    StringBuffer sbQuery = new StringBuffer();
    sbQuery.append("from User where id=?");
    
    return findOneByHql("from User where id=?",new Object[]{Integer.parseInt(sso)});
  }

	@Override
	public List<WorkOrderInfo> getWorkOrderInfoDetails() {

		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("from workOrderInfo");
		Query query = getSession().createQuery(sbQuery.toString());

		@SuppressWarnings("unchecked")
		List<WorkOrderInfo> list1 = query.list();

		return list1;
	}

	@Override
	public List<ReminderInfo> getReminderInfoDetails() {

		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("from ReminderInfo"
				+ " where Status=:status order by Created_Date");
		Query query = getSession().createQuery(sbQuery.toString());
		query.setParameter("status", "pending");

		@SuppressWarnings("unchecked")
		List<ReminderInfo> list1 = query.list();

		return list1;
	}

	@Override
	public String addNewUser(User user) {
		
		try {
			getHiberanteTemplate().save(user);
			return "1";
		}
		catch (Exception e) {
			logger.error("Exception in addNewUser()"
					+ " of PmmsDaoImpl and the exception is ", e);
		}
		return "0";
	}

	@Override
	public String updateUser(User user) {
		
		try {
			User user2 = getHiberanteTemplate().get(
					User.class, user.getId());

			user2.setFirstName(user.getFirstName());
			user2.setLastName(user.getLastName());
			user2.setRole(user.getRole());
			user2.setContactNo(user.getContactNo());
			user2.setEmailId(user.getEmailId());
			user2.setPassword(user.getPassword());

			getHiberanteTemplate().saveOrUpdate(user2);

			return "1";
		}
		catch (Exception e) {
			logger.error("Exception in updateUser()"
					+ " of PmmsDaoImpl and the exception is ", e);
		}
		return "0";
	}

	@Override
	public String deleteUser(User user) {
		
		try {
			User user2 = getHiberanteTemplate().get(
					User.class, user.getId());

			getHiberanteTemplate().delete(user2);

			return "1";
		}
		catch (Exception e) {
			logger.error("Exception in deleteUser()"
					+ " of PmmsDaoImpl and the exception is ", e);
		}
		return "0";
	}

	@Override
	public List<String> getRoleNames() {
		
		try {
			StringBuffer sbQuery = new StringBuffer();
			
			sbQuery.append("select name from Role");
			Query query = getSession().createQuery(
					sbQuery.toString());
			
			@SuppressWarnings("unchecked")
			List<String> roleNameList = query.list();
			
			if (roleNameList.size() != 0) {
				return roleNameList;
			}
		}
		catch (Exception e) {
			logger.error("Exception in getRoleNames()"
					+ " of PmmsDaoImpl and the exception is ", e);
		}
		
		return null;
	}
	
	public int updatePwd(String sso,String pwd) {
    String sql = "update users set PASSWORD=? where SSO=?";
    return executeBySql(sql, new Object[]{pwd,sso});
  }
	
}
