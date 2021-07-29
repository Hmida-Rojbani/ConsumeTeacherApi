package com.soft.consume.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.soft.consume.service.ConsumeService;
import com.soft.consume.service.models.TeacherResponse;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ConsumeTeacherController {
	
	private ConsumeService service;

	@GetMapping("id/{code}")
	public TeacherResponse executeConsumeById(@PathVariable("code") long id) {
		return service.consumeGetTeacherById(id);
	}
	
	
}
