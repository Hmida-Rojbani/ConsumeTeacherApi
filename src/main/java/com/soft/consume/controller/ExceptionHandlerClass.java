package com.soft.consume.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class ExceptionHandlerClass {
	
	@ExceptionHandler(HttpClientErrorException.class)
	public String handleHttpClientErrorException(HttpClientErrorException e) {
		return e.getMessage();
	}

}
