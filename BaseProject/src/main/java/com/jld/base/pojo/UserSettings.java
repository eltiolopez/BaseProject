package com.jld.base.pojo;

import java.io.Serializable;

public class UserSettings implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String username;
	
	private int numResultsInPage;
	
	private String orderByCode;

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

	public String getOrderByCode() {
		return orderByCode;
	}

	public void setOrderByCode(String orderByCode) {
		this.orderByCode = orderByCode;
	}
}
