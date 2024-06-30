package com.rahul.validations;

import java.util.Arrays;
import java.util.List;

import com.rahul.annotation.PasswordValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordStrengthValidator implements ConstraintValidator<PasswordValidator, String> {

	List<String> weakPassword;

	@Override
	public void initialize(PasswordValidator constraintAnnotation) {
		weakPassword = Arrays.asList("12345", "password", "qwerty");
	}

	@Override
	public boolean isValid(String passwordField, ConstraintValidatorContext context) {
		return passwordField != null && (!weakPassword.contains(passwordField));
	}

}
