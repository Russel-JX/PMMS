package com.ge.pmms.po;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="SUB_MODULE")
public class SubModule implements Serializable{

	/**
   * 
   */
  private static final long serialVersionUID = -395320622975029787L;
  private int id;
	private String name;
	
	private List<RoleSubModule> roleSubModule;
	
	
	@Id
	@GenericGenerator(name="pmmsIncrementer",strategy="increment")
	@GeneratedValue(generator="pmmsIncrementer")
	@Column(name="SubModuleId")
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	
	@Column(name="SubModuleName")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	@OneToMany(mappedBy="subModule")
	public List<RoleSubModule> getRoleSubModule() {
		return roleSubModule;
	}
	public void setRoleSubModule(List<RoleSubModule> roleSubModule) {
		this.roleSubModule = roleSubModule;
	}
	
	
}
