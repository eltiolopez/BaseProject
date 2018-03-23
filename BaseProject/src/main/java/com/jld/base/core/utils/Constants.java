package com.jld.base.core.utils;

import org.springframework.beans.factory.annotation.Value;

public class Constants {

	public static final String MODEL_OPERATION_EQUAL	= "=";	
	public static final String MODEL_OPERATION_GREATER	= ">";
	public static final String MODEL_OPERATION_LITTLE	= "<";
	public static final String MODEL_OPERATION_LIKE		= "LIKE";
	public static final String MODEL_ORDER_ASC			= "ASC";
	public static final String MODEL_ORDER_DES			= "DESC";
	
	@Value("${fileuploading.destination}")
	public static String UPLOAD_LOCATION;
}
