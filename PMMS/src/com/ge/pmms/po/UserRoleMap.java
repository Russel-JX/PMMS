
/**
 * ============================================================
 * File : UserRoleMap.java
 * Description : 
 * 	用户和角色关系实体类
 * 
 * Package : com.ge.pmms.po
 * Author : Xun Jiang
 * Last Edited By :
 * Version : 1.0
 * Created on : Feb 11, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.pmms.po;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity(name="userRoleMap")
@Table(name="USER_ROLE_MAP")
public class UserRoleMap {
	private Integer id;            				// ID
	private String user_id;         			// 用户编号
	private String role_id;         			// 角色编号
	private String is_active;       			// 是否激活
	private String creator;         			// 创建人编号
	private Date created_date;         			// 创建日期
	private String updater;         			// 修改人编号
	private Date last_updated_date;       		// 最后修改时间
	private String remark;         				// 备注
	
	@Id 
	@GeneratedValue(strategy=IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	/**
	 * @return the user_id
	 */
	public String getUser_id() {
		return user_id;
	}

	
	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	
	/**
	 * @return the role_id
	 */
	public String getRole_id() {
		return role_id;
	}

	
	/**
	 * @param role_id the role_id to set
	 */
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}

	
	/**
	 * @return the is_active
	 */
	public String getIs_active() {
		return is_active;
	}

	
	/**
	 * @param is_active the is_active to set
	 */
	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}

	
	/**
	 * @return the creator
	 */
	public String getCreator() {
		return creator;
	}

	
	/**
	 * @param creator the creator to set
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	
	/**
	 * @return the created_date
	 */
	public Date getCreated_date() {
		return created_date;
	}

	
	/**
	 * @param created_date the created_date to set
	 */
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	
	/**
	 * @return the updater
	 */
	public String getUpdater() {
		return updater;
	}

	
	/**
	 * @param updater the updater to set
	 */
	public void setUpdater(String updater) {
		this.updater = updater;
	}

	
	/**
	 * @return the last_updated_date
	 */
	public Date getLast_updated_date() {
		return last_updated_date;
	}

	
	/**
	 * @param last_updated_date the last_updated_date to set
	 */
	public void setLast_updated_date(Date last_updated_date) {
		this.last_updated_date = last_updated_date;
	}

	
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	
}
