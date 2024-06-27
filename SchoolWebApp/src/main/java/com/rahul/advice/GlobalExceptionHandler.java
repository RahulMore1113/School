package com.rahul.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ModelAndView exceptionHandler(Exception exception) {

		return new ModelAndView("error").addObject("errormsg", exception.getMessage());

	}

}
