package com.jld.base.core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.UrlPathHelper;

public class BaseProjectDispatcherServlet extends DispatcherServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2022731212651037973L;

	private static final UrlPathHelper urlPathHelper = new UrlPathHelper();

	@Override
	protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (logger.isDebugEnabled()) {
			String requestUri = urlPathHelper.getRequestUri(request);
			logger.debug("DispatcherServlet with name '" + getServletName() + "' processing " + request.getMethod() + " request for [" + requestUri + "]");
		}

		if (request.getMethod().equalsIgnoreCase("GET")) {
			// Solo se espera recibir parámetros cifrados en las peticiones GET
			EncryptedHttpServletRequestWrapper encryptedRequest = new EncryptedHttpServletRequestWrapper(request);
			super.doService(encryptedRequest, response);
		} else
			super.doService(request, response);

	}

}
