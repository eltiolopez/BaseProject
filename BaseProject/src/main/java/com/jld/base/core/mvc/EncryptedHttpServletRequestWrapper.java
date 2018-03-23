package com.jld.base.core.mvc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.log4j.Logger;

import com.jld.base.core.utils.AESencrypter;

public class EncryptedHttpServletRequestWrapper extends HttpServletRequestWrapper {
	
	private static final Logger logger = Logger.getLogger(EncryptedHttpServletRequestWrapper.class);

	public EncryptedHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		
		// TODO Auto-generated constructor stub
	}
	
	public String getParameter(String name) {
    	
    	//Patrones utilizados para detectar parámetros que han sido cifrados con TAGLIB en las JSP    	
    	
    	Pattern p1 = Pattern.compile("([iI][dD]).*");	// "id"
    	Matcher m1 = p1.matcher(name);
    	
    	Pattern p2 = Pattern.compile("([a-zA-Z]*)([iI][dD])");	// "userId"
    	Matcher m2 = p2.matcher(name);
    	

    	if(m1.matches() || m2.matches()) {
    		
    		String encryptedParamValue = super.getParameter(name);
    		try {
    			if(encryptedParamValue != null) {
    				return AESencrypter.decrypt(encryptedParamValue);
    			}
    			else {
    				return null;
    			}
			} catch (Exception e) {
				logger.error("Error descifrando parámetro-request [" + name + "] con valor [" + encryptedParamValue + "]", e);
				return encryptedParamValue;
			}
    	}
    	else {
    		
    		return super.getParameter(name);
    	}

    }
    
	public String[] getParameterValues(String name) {

    	Pattern p1 = Pattern.compile("(id).*");	// "id"
    	Matcher m1 = p1.matcher(name);
    	
    	Pattern p2 = Pattern.compile("([a-zA-Z]*)([iI][dD])");	// "userId"
    	Matcher m2 = p2.matcher(name);   
		
    	if((m1.matches() || m2.matches())) {
    		
			String[] paramValues = super.getParameterValues(name);
			for(int i = 0; i < paramValues.length; i++) {
				
				try {
					paramValues[i] =  AESencrypter.decrypt(paramValues[i]);
				} catch (Exception e) {
					logger.error("Error descifrando parámetro-request [" + name + "] con valor [" + paramValues[i] + "]", e);
				}
			}
			
			return paramValues;
		}
		else {
			
			return super.getParameterValues(name);
		}
	}

}
