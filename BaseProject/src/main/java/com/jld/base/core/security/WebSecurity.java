package com.jld.base.core.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * This class was created to extend the SpringSecurity pattern functionality when the URL comes 
 * with path variables. Needed Spring Security version 4.0.1 or higher.
 * @author javier
 */

@Component
public class WebSecurity {

	public boolean checkLoggedInUser(Authentication authentication, String userName) {
		if(StringUtils.isNotBlank(userName)) {
			if(userName.equals(authentication.getName())) {
				return true;
			}
		}
		return false;
	}
}
