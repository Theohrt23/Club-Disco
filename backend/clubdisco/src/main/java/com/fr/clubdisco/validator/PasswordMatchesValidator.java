package com.fr.clubdisco.validator;

import com.fr.clubdisco.dto.SignUpRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, SignUpRequest> {

	@Override
	public boolean isValid(final SignUpRequest user, final ConstraintValidatorContext context) {
		return user.getPassword().equals(user.getMatchingPassword());
	}

}
