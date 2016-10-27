package com.ge.pmms.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Role_Module_Mapping")
public class RoleModule implements Serializable
{
	/**
   * 
   */
  private static final long serialVersionUID = -8591271957483639617L;

  @Id
	private int RoleId;

	@Id
	private int ModuleId;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="roleId", updatable = false, insertable = false)
	private Role role;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="moduleId", updatable = false, insertable = false)
	private Module module;

	@Column(name="editable")
	private String editable;

	
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	

	
	public Module getModule() {
		return module;
	}
	public void setModule(Module module) {
		this.module = module;
	}
	

	
	public String getEditable() {
		return editable;
	}
	public void setEditable(String editable) {
		this.editable = editable;
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

	
	
	@Override
	public int hashCode() {
		return this.getRoleId()+this.getModuleId();
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

		RoleModule other = (RoleModule) obj;

		return this.getRoleId() == other.getRoleId() && this.getModuleId() == other.getModuleId();
	}
	
}
