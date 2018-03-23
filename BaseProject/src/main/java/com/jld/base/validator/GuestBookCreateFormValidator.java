package com.jld.base.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jld.base.form.GuestBookCreateForm;

@Component
public class GuestBookCreateFormValidator implements Validator {
	
	public GuestBookCreateFormValidator() {

	}

	/*
	 * Which objects can be validated by this validator
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	public boolean supports(Class<?> paramClass) {
		return GuestBookCreateForm.class.equals(paramClass);
	}

	public void validate(Object obj, Errors errors) {
		
		// Validation of field 'Message':
		Object messageValue = errors.getFieldValue("message");
		if(messageValue == null || String.valueOf(messageValue).length() == 0) {
			errors.rejectValue("message", "guestbook.validation.message.isEmpty");
		}
		else {
			if(String.valueOf(messageValue).length() > 255) {
				errors.rejectValue("message", "guestbook.validation.message.isTooLong");
			}
		}
	}
	
	
}