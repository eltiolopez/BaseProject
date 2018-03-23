package com.jld.base.model.vo;

public class ModelAttribute {
	
	public ModelAttribute(String field, String value, String operation) {
		super();
		this.field = field;
		this.value = value;
		this.operation = operation;
	}

	private String field;
	
	private String value;
	
	private String operation;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
}
