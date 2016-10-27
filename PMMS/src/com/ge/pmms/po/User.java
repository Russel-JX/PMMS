package com.ge.pmms.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;



@Entity
@Table(name="USERS")
public class User {
	
	private int id;
	private String sso;
	private String password;
	private String firstName;
	private String lastName;
	private String role;
	private String contactNo;
	private String emailId;
	
	public User() {
		super();
	}

		
	public User(int id, String sso, String password, String firstName,
			String lastName, String role, String contactNo, String emailId) {
		super();
		this.id = id;
		this.sso = sso;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.contactNo = contactNo;
		this.emailId = emailId;
	}

	
	@Id
	@GenericGenerator(name="pmmsIncrementer",strategy="increment")
	@GeneratedValue(generator="pmmsIncrementer")
	@Column(name="Id")
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}


	@Column(name="FirstName")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name="LastName")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name="ContactNo")
	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	@Column(name="EmailId")
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Column(name="Role")
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Column(name="SSO")	
	public String getSso() {
		return sso;
	}

	public void setSso(String sso) {
		this.sso = sso;
	}

	@Column(name="Password")
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
