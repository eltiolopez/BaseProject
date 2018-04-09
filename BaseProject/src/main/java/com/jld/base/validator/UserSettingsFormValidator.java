package com.jld.base.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.jld.base.form.UserSettingsForm;

@Component
public class UserSettingsFormValidator implements Validator {

	/*
	 * Which objects can be validated by this validator
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	public boolean supports(Class<?> paramClass) {
		return UserSettingsForm.class.equals(paramClass);
	}

	public void validate(Object obj, Errors errors) {
		UserSettingsForm form = (UserSettingsForm) obj;
		
		// Validation of field 'numResultsInPage':
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numResultsInPage", "validation.numpage.isEmpty");
		if(errors.getFieldErrorCount("numResultsInPage") == 0) {
			int num = form.getNumResultsInPage();
			if(num != 10 && num != 40 && num != 100) {
				errors.rejectValue("numResultsInPage", "validation.numpage.notValidValue");
			}
		}
		
		// Validation of field 'orderBy':
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "orderBy", "validation.orderby.isEmpty");
		if(errors.getFieldErrorCount("orderBy") == 0) {
			String order = form.getOrderBy();
			if(order != "DEFAULT" && order != "NAME" && order != "DATE") {
				errors.rejectValue("orderBy", "validation.orderBy.notValidValue");
			}
		}
	}
	
	
}