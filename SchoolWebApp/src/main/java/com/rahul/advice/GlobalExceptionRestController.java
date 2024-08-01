package com.rahul.advice;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rahul.model.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
@Order(1)
public class GlobalExceptionRestController extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatusCode statusCode, WebRequest request) {

        return ResponseEntity
                .badRequest()
                .body(new Response(statusCode.toString(), ex.getBindingResult().toString()));

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> exceptionHandler(Exception ex) {

        return ResponseEntity
                .internalServerError()
                .body(new Response("500", ex.getMessage()));

	}

}
