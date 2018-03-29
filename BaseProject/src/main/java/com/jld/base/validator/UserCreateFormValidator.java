package com.jld.base.validator;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.jld.base.dao.UserDao;
import com.jld.base.dao.UsergroupDao;
import com.jld.base.form.UserCreateForm;

@Component
public class UserCreateFormValidator implements Validator {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UsergroupDao usergroupDao;
	
	private Pattern pattern;

	private static final String EMAIL_PATTERN = 
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public UserCreateFormValidator() {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}

	/*
	 * Which objects can be validated by this validator
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	public boolean supports(Class<?> paramClass) {
		return UserCreateForm.class.equals(paramClass);
	}

	public void validate(Object obj, Errors errors) {
		UserCreateForm form = (UserCreateForm) obj;
		
		// Validation of field 'UserName':
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "validation.username.isEmpty");
		if(userDao.countUserByField("username", form.getUsername()) > 0) {
			errors.rejectValue("username", "validation.username.alreadyExists");
		}
		
		// Validation of field 'Email':
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "validation.email.isEmpty");
		if(errors.getFieldErrorCount("email") == 0 && !pattern.matcher(form.getEmail()).matches()) {
			errors.rejectValue("email", "validation.email.noPattern");
		}
		else if(userDao.countUserByField("email", form.getEmail()) > 0) {
			errors.rejectValue("email", "validation.email.alreadyExists");
		}
		
		// Validation of field 'Grupo':
		if(form.getGroup() == null) {
			errors.rejectValue("group", "validation.group.isEmpty");
		}
		else {
			try {
				if(usergroupDao.findOne(form.getGroup()) == null) {
					errors.rejectValue("group", "validation.group.notValidValue");
				}				
			} catch(Exception e) {
				errors.rejectValue("group", "validation.group.notValidValue");
			}
		}
		
		// Validation of fields 'Password':
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password1", "validation.password1.isEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password2", "validation.password2.isEmpty");
		
		if(errors.getFieldErrorCount("password1") == 0 && errors.getFieldErrorCount("password2") == 0) {
			if(!form.getPassword1().equals(form.getPassword2())) {
				errors.rejectValue("password1", "validation.password.noMatch");
			}
		}
	}
	
	
}