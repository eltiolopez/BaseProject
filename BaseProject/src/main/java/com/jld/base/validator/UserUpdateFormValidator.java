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
import com.jld.base.model.User;

@Component
public class UserUpdateFormValidator implements Validator {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UsergroupDao usergroupDao;
	
	private Pattern pattern;

	private static final String EMAIL_PATTERN = 
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public UserUpdateFormValidator() {
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
		
		User user = userDao.findOne(form.getUserid());
		
		// Validation of field 'UserName':
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "validation.username.isEmpty");
		if(!user.getUsername().equals(form.getUsername())) {
			errors.rejectValue("email", "validation.username.notValidValue");
		}
		
		// Validation of field 'Email':
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "validation.email.isEmpty");
		if(!pattern.matcher(form.getEmail()).matches()) {
			errors.rejectValue("email", "validation.email.noPattern");
		}
		else if(!user.getEmail().equals(form.getEmail()) && userDao.countUserByField("email", form.getEmail()) > 0) {
			errors.rejectValue("email", "validation.email.alreadyExists");
		}
		
		// Validación del campo Grupo:
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
	}
	
	
}