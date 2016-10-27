package com.ge.pmms.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ge.pmms.base.impl.BaseDaoImpl;
import com.ge.pmms.dao.RoleMgmtDao;
import com.ge.pmms.po.Role;
import com.ge.pmms.po.RoleModule;
import com.ge.pmms.po.RoleSubModule;

/**
 * @author iGATE
 *
 */
@Repository("roleMgmtDao")
public class RoleMgmtDaoImpl extends BaseDaoImpl<Role> implements RoleMgmtDao {
	
	@Override
	public List<Role> getRoleDetails() {

		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("from Role");
		Query query = getSession().createQuery(sbQuery.toString());

		@SuppressWarnings("unchecked")
		List<Role> list1 = query.list();

		return list1;
	}
	
	
	@Override
	public List<RoleModule> getRoleModuleAccessDetails(int roleId) {

		DetachedCriteria criteria =
				DetachedCriteria.forClass(RoleModule.class);
		criteria.add(Restrictions.eq("RoleId", roleId));
		
		@SuppressWarnings("unchecked")
		List<RoleModule> roleModules =
				getHiberanteTemplate().findByCriteria(criteria);
		
		return roleModules;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleSubModule> getRoleSubModuleAccessDetails(int roleId) {

		DetachedCriteria criteria =
				DetachedCriteria.forClass(RoleSubModule.class);
		criteria.add(Restrictions.eq("RoleId", roleId));
		
		List<RoleSubModule> roleSubModules =
				getHiberanteTemplate().findByCriteria(criteria);
		
		return roleSubModules;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public int getRoleId() {
		
		int roleId = 0;
		
		StringBuffer sbQuery = new StringBuffer();

		sbQuery.append("select max(id) from Role");
		Query query = getSession().createQuery(sbQuery.toString());
		
		List<Integer> roleIdList = query.list();
		
		if (roleIdList.size() == 0 || roleIdList.isEmpty()
				|| roleIdList.get(0) == null) {
			roleId = 1;
		}
		else {
			roleId = roleIdList.get(0) + 1;
		}
				
		return roleId;
	}
	

	@Override
	public String addNewRole(Role role) {
		
		try {
			getHiberanteTemplate().save(role);
			
			for (RoleModule roleModule :
				role.getRoleModule()) {
				getHiberanteTemplate().save(roleModule);
			}
			
			for (RoleSubModule roleSubModule :
				role.getRoleSubModule()) {
				getHiberanteTemplate().save(roleSubModule);
			}
			
			return "1";
		}
		catch (Exception e) {
			logger.error("Exception in addNewRole()"
					+ " of RoleMgmtDaoImpl and the exception is ", e);
		}
		
		return "0";
	}


	@Override
	public String deleteRole(Role role) {
		
		try {
			Role role2 = getHiberanteTemplate().get(Role.class, role.getId());
						
			for (RoleModule roleModule :
				role2.getRoleModule()) {
				getHiberanteTemplate().delete(roleModule);
			}
			
			for (RoleSubModule roleSubModule :
				role2.getRoleSubModule()) {
				getHiberanteTemplate().delete(roleSubModule);
			}
			
			getHiberanteTemplate().delete(role2);
			
			return "1";
		}
		catch (Exception e) {
			logger.error("Exception in deleteUser() of RoleMgmtDaoImpl and the exception is ", e);
		}
		return "0";
	}


	@Override
	public List<Integer> getRoleId(String userRole) {
		
		try {
			StringBuffer sbQuery = new StringBuffer();

			sbQuery.append("select id from Role"
					+ " where name=:roleName");
			Query query = getSession().createQuery(
					sbQuery.toString());
			query.setParameter("roleName", userRole);
			
			@SuppressWarnings("unchecked")
			List<Integer> list1 = query.list();
			
			return list1;
		}
		catch (Exception e) {
			logger.error("Exception in getRoleId()"
					+ " of RoleMgmtDaoImpl and the exception is ", e);
		}
		
		return null;
	}

}
