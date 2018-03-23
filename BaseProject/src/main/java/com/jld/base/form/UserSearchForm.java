package com.jld.base.form;

import java.io.Serializable;

import com.jld.base.pagination.PaginationForm;

public class UserSearchForm extends PaginationForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String username;
	
	private String password;
	
	private String email;
	
	private String name;
	
	private String surname1;
	
	private String surname2;
	
	private Integer group;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname1() {
		return surname1;
	}

	public void setSurname1(String surname1) {
		this.surname1 = surname1;
	}

	public String getSurname2() {
		return surname2;
	}

	public void setSurname2(String surname2) {
		this.surname2 = surname2;
	}

	public Integer getGroup() {
		return group;
	}

	public void setGroup(Integer group) {
		this.group = group;
	}
}
