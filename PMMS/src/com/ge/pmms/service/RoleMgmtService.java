package com.ge.pmms.service;

import java.util.List;

import com.ge.pmms.po.Role;
import com.ge.pmms.po.RoleVo;


/**
 * @author iGATE
 *
 */
public interface RoleMgmtService {

	/**
	 * @return List<RoleVo>
	 */
	List<RoleVo> getRoleDetails();
	
	/**
	 * @param roleId
	 * @return String
	 */
	String getRoleAccessInfo(int roleId);

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
	 * @return int
	 */
	int getRoleId(String userRole);

}
