package com.ge.pmms.po;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
@Table(name="ROLE")
public class Role implements Serializable{

	/**
   * 
   */
  private static final long serialVersionUID = -8707360890265401046L;
  private int id;
	private String name;
	private String description;
	
	private List<RoleModule> roleModule;
	private List<RoleSubModule> roleSubModule;
	
	
	@Id
	@Column(name="RoleId")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
	@Column(name="RoleName")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	@Column(name="RoleDescription")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	@OneToMany(mappedBy="role")
	@LazyCollection(LazyCollectionOption.FALSE)
	public List<RoleModule> getRoleModule() {
		return roleModule;
	}
	public void setRoleModule(List<RoleModule> roleModule) {
		this.roleModule = roleModule;
	}
	
	
	
	@OneToMany(mappedBy="role")
	@LazyCollection(LazyCollectionOption.FALSE)
	public List<RoleSubModule> getRoleSubModule() {
		return roleSubModule;
	}
	public void setRoleSubModule(List<RoleSubModule> roleSubModule) {
		this.roleSubModule = roleSubModule;
	}
	
	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", description="
				+ description  + "]";
	}
		
}
