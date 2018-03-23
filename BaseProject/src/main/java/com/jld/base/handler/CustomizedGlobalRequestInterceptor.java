/**
 * This class implements a HTTP Request interceptor used for logging requests information and future uses. All the 
 * incoming HTTP Request in the Application will go through this Interceptor.
 * See spring-mvc-servlet.xml file, the global interceptor beans definition, under <mvc:interceptors> label.
 * 
 * @author javier
 * @version 1.0
 * @since 1.0
 */

package com.jld.base.handler;

import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CustomizedGlobalRequestInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = Logger.getLogger(CustomizedGlobalRequestInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws ServletException {
		
		// Get current user:
		String loguedUser = "";
		Principal principal = request.getUserPrincipal();
        if(principal != null) {
        	loguedUser = principal.getName();
        }
		
        // Log information about the HTTP Request:
		logger.info("Request [" + request.getMethod() + "] "
				+ " - Source: "   + request.getRemoteHost() + ":" + request.getRemotePort() 
				+ " - Target: "   + request.getLocalAddr() + ":" + request.getLocalPort()
				+ " - Resource: " + request.getRequestURI()
				+ " - User: "     + loguedUser);
		
		
		return true;
	}

}
