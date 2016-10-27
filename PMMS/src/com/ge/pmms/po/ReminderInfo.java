package com.ge.pmms.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Reminder_Info")
public class ReminderInfo
{
	private int id;
	private String titleInfo;
	private String moduleId;
	private String status;
	private String receiver;
	private Date createdDate;
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="Title_Info")
	public String getTitleInfo() {
		return titleInfo;
	}
	public void setTitleInfo(String titleInfo) {
		this.titleInfo = titleInfo;
	}
	
	@Column(name="Module_Id")
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	
	@Column(name="Status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name="Receiver")
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	@Column(name="Created_Date")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public ReminderInfo(int id, String titleInfo, String moduleId,
			String status, String receiver, Date createdDate) {
		super();
		this.id = id;
		this.titleInfo = titleInfo;
		this.moduleId = moduleId;
		this.status = status;
		this.receiver = receiver;
		this.createdDate = createdDate;
	}

	public ReminderInfo() {
		super();
	}
	@Override
	public String toString() {
		return "ReminderInfo [id=" + id + ", titleInfo=" + titleInfo
				+ ", moduleId=" + moduleId + ", status=" + status
				+ ", receiver=" + receiver + ", createdDate=" + createdDate + "]";
	}
	
	
}
