package com.ge.pmms.dao;

import java.util.List;

import com.ge.pmms.base.BaseDao;
import com.ge.pmms.po.Role;
import com.ge.pmms.po.RoleModule;
import com.ge.pmms.po.RoleSubModule;

/**
 * @author iGATE
 *
 */
public interface RoleMgmtDao extends BaseDao<Role> {
	
	/**
	 * @return List<Role>
	 */
	List<Role> getRoleDetails();

	/**
	 * @param roleId
	 * @return List<RoleModule>
	 */
	List<RoleModule> getRoleModuleAccessDetails(int roleId);

	/**
	 * @param roleId
	 * @return List<RoleSubModule>
	 */
	List<RoleSubModule> getRoleSubModuleAccessDetails(int roleId);

	/**
	 * @param role
	 * @return String
	 */
	String addNewRole(Role role);

	/**
	 * @return int
	 */
	int getRoleId();

	/**
	 * @param role
	 * @return String
	 */
	String deleteRole(Role role);

	/**
	 * @param userRole
	 * @return List<Integer>
	 */
	List<Integer> getRoleId(String userRole);
	
}
