package com.ge.pmms.po;

import java.io.Serializable;

public class RoleModuleId implements Serializable{
	
	private int RoleId;
	private int ModuleId;
	
	@Override
	public int hashCode() {
		return this.RoleId+this.ModuleId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this){
			return true;
		}
		
		if(obj == null){
			return false;
		}
		
		if(obj.getClass() != this.getClass()){
			return false;
		}
		
		if(obj instanceof RoleModuleId){
			RoleModuleId other = (RoleModuleId) obj;
			return other.RoleId == this.RoleId && other.ModuleId == this.ModuleId;
		}
		return false;
	}

	public int getRoleId() {
		return RoleId;
	}

	public void setRoleId(int roleId) {
		RoleId = roleId;
	}

	public int getModuleId() {
		return ModuleId;
	}

	public void setModuleId(int moduleId) {
		ModuleId = moduleId;
	}

	
}
