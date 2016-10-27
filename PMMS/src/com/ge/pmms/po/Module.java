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
@Table(name="MODULE")
public class Module implements Serializable{
	
	/**
   * 
   */
  private static final long serialVersionUID = 2387211205804774545L;
  private int id;
	private String name;
		
	private List<RoleModule> roleModlue;
	
	
	
	@Id
	@GenericGenerator(name="pmmsIncrementer",strategy="increment")
	@GeneratedValue(generator="pmmsIncrementer")
	@Column(name="ModuleId")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
	@Column(name="ModuleName")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	@OneToMany(mappedBy="module")
	public List<RoleModule> getRoleModlue() {
		return roleModlue;
	}
	public void setRoleModlue(List<RoleModule> roleModlue) {
		this.roleModlue = roleModlue;
	}
	
	//private Set<SubModule> subModules;
	
	//private RoleModule roleModlue;
	
	//@OneToMany(fetch=FetchType.EAGER, cascade = javax.persistence.CascadeType.ALL)
		/*@OneToMany(cascade = javax.persistence.CascadeType.ALL)
		@JoinColumn(name="ModuleId")
		public Set<SubModule> getSubModules() {
			return subModules;
		}
		public void setSubModules(Set<SubModule> subModules) {
			this.subModules = subModules;
		}*/
	
	/*public RoleModule getRoleModlue() {
		return roleModlue;
	}
	public void setRoleModlue(RoleModule roleModlue) {
		this.roleModlue = roleModlue;
	}*/
	
}
