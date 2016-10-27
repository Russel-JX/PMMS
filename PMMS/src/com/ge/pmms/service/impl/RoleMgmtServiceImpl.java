package com.ge.pmms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ge.pmms.base.BaseService;
import com.ge.pmms.dao.RoleMgmtDao;
import com.ge.pmms.po.Role;
import com.ge.pmms.po.RoleModule;
import com.ge.pmms.po.RoleSubModule;
import com.ge.pmms.po.RoleVo;
import com.ge.pmms.service.RoleMgmtService;

/**
 * @author iGATE
 *
 */
@Service("roleMgmtService")
public class RoleMgmtServiceImpl extends BaseService<Role>
		implements RoleMgmtService {
	
	@Autowired
	private RoleMgmtDao roleMgmtDao;
	
	public RoleMgmtDao getRoleMgmtDao() {
		return roleMgmtDao;
	}

	public void setRoleMgmtDao(RoleMgmtDao roleMgmtDao) {
		this.roleMgmtDao = roleMgmtDao;
	}

	@Override
	public List<RoleVo> getRoleDetails() {
				
		List<Role> list = roleMgmtDao.getRoleDetails();
		List<RoleVo> rolelist = new ArrayList<RoleVo>();
		
		for (Role role : list) {
			rolelist.add(new RoleVo(role.getId(),
					role.getName(), role.getDescription()));
		}
		
		return rolelist;
	}
	

	@Override
	public String getRoleAccessInfo(int roleId) {
		
		List<RoleModule> roleModuleList =
				roleMgmtDao.getRoleModuleAccessDetails(roleId);
		
		List<RoleSubModule> roleSubModuleList =
				roleMgmtDao.getRoleSubModuleAccessDetails(roleId);
		
		StringBuffer str = new StringBuffer();
		
		if (roleModuleList != null &&
				roleModuleList.size() > 0) {
			for (RoleModule roleModule : roleModuleList) {
				str.append(roleModule.getModuleId()).append("==").
						append(roleModule.getEditable()).append("||");
			}
		}
		
		str.append("====");
		
		if (roleSubModuleList != null &&
				roleSubModuleList.size() > 0) {
			for (RoleSubModule roleSubMod :
						roleSubModuleList) {
				str.append(roleSubMod.getSubModuleId())
						.append(",");
			}
		}
		return str.toString();
	}


	@Override
	public int getRoleId() {
		
		return roleMgmtDao.getRoleId();
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED,
			readOnly = false)
	public String addNewRole(Role role) {
		
		return roleMgmtDao.addNewRole(role);
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED,
			readOnly = false)
	public String deleteRole(Role role) {
		
		return roleMgmtDao.deleteRole(role);
	}


	@Override
	public int getRoleId(String userRole) {
		
		int a = 0;
		List<Integer> roleList = roleMgmtDao.getRoleId(userRole);
		
		if (roleList.size() == 0 || roleList.isEmpty()) {
			a=0;
		}
		else {
			a = roleList.get(0);
		}
		return a;
	}
}

