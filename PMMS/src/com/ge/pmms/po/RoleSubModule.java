package com.ge.pmms.po;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ROLE_SUB_MODULE_MAPPING")
public class RoleSubModule implements Serializable
{
	
	//private int id;
	
	/**
   * 
   */
  private static final long serialVersionUID = -7499478159707089247L;

  @Id
	private int RoleId;

	@Id
	private int SubModuleId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="roleId", updatable = false, insertable = false)
	private Role role;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="subModuleId", updatable = false, insertable = false)
	private SubModule subModule;

	
	
	
	/*public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}*/
	
	@Override
	public int hashCode() {
		return this.getRoleId()+this.getSubModuleId();
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
		RoleSubModule other = (RoleSubModule) obj;
		return this.getRoleId() == other.getRoleId() && this.getSubModuleId() == other.getSubModuleId();
	}
	
	
	
	

	public Role getRole() {
		return role;
	}
	public int getRoleId() {
		return RoleId;
	}
	public void setRoleId(int roleId) {
		RoleId = roleId;
	}
	public int getSubModuleId() {
		return SubModuleId;
	}
	public void setSubModuleId(int subModuleId) {
		SubModuleId = subModuleId;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	

	public SubModule getSubModule() {
		return subModule;
	}
	public void setSubModule(SubModule subModule) {
		this.subModule = subModule;
	}
	
	
}
