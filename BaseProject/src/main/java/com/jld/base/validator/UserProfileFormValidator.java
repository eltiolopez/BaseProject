package com.jld.base.validator;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.jld.base.dao.UserDao;
import com.jld.base.form.UserProfileForm;
import com.jld.base.model.User;

@Component
public class UserProfileFormValidator implements Validator {
	
	@Autowired
	private UserDao userDao;
	
	private Pattern pattern;

	private static final String EMAIL_PATTERN = 
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public UserProfileFormValidator() {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}

	/*
	 * Which objects can be validated by this validator
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	public boolean supports(Class<?> paramClass) {
		return UserProfileForm.class.equals(paramClass);
	}

	public void validate(Object obj, Errors errors) {
		UserProfileForm form = (UserProfileForm) obj;
		
		User user = userDao.findUserByField("username", form.getUsername());
		
		// Validation of field 'UserName':
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "validation.username.isEmpty");
		if(!user.getUsername().equals(form.getUsername())) {
			errors.rejectValue("email", "validation.username.notValidValue");
		}
		
		// Validation of field 'Email':
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "validation.email.isEmpty");
		if(errors.getFieldErrorCount("email") == 0) {
			if(!pattern.matcher(form.getEmail()).matches()) {
				errors.rejectValue("email", "validation.email.noPattern");
			}
			else if(!user.getEmail().equals(form.getEmail()) && userDao.countUserByField("email", form.getEmail()) > 0) {
				errors.rejectValue("email", "validation.email.alreadyExists");
			}
		}
		
		// Validation of field 'Image':
		if(!StringUtils.isEmpty(form.getImageUrl())) {		
			String[] tokens = form.getImageUrl().split("\\.(?=[^\\.]+$)");
			if(StringUtils.isEmpty(tokens[0]) || StringUtils.isEmpty(tokens[1])) {
				errors.reject("imageUrl", "validation.picture.invalidFormat");
			}
		}
	}
	
	
}