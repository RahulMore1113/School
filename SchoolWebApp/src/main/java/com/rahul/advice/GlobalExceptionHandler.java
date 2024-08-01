package com.rahul.advice;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(annotations = Controller.class)
@Order(2)
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ModelAndView exceptionHandler(Exception exception) {

		return new ModelAndView("error").addObject("errormsg", exception.getMessage());

	}

}
