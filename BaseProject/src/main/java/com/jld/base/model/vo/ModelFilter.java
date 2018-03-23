package com.jld.base.model.vo;

import java.util.ArrayList;
import java.util.List;

public class ModelFilter {

	private List<ModelAttribute> attributes;
	
	private String orderByField;
	
	private String orderByValue;
	
	private int firstResult;
	
	private int maxResults;

	public ModelFilter() {
		super();
		this.attributes = new ArrayList<ModelAttribute>();
	}

	public List<ModelAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<ModelAttribute> attributes) {
		this.attributes = attributes;
	}

	public String getOrderByField() {
		return orderByField;
	}

	public void setOrderByField(String orderByField) {
		this.orderByField = orderByField;
	}

	public String getOrderByValue() {
		return orderByValue;
	}

	public void setOrderByValue(String orderByValue) {
		this.orderByValue = orderByValue;
	}
	
	public void setOrderBy(String field, String value) {
		this.orderByField = field;
		this.orderByValue = value;
	}

	public int getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}
}
