package com.jld.base.form;

import java.io.Serializable;

public class UserSettingsForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String username;
	
	private int numResultsInPage;
	
	private String orderBy;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getNumResultsInPage() {
		return numResultsInPage;
	}

	public void setNumResultsInPage(int numResultsInPage) {
		this.numResultsInPage = numResultsInPage;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

}