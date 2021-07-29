package com.soft.consume.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.soft.consume.service.ConsumeByFeignService;
import com.soft.consume.service.ConsumeService;
import com.soft.consume.service.models.TeacherRequest;
import com.soft.consume.service.models.TeacherResponse;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ConsumeTeacherController {
	
	private ConsumeService service;
	private ConsumeByFeignService feignService;

	@GetMapping("/id/{code}")
	public TeacherResponse executeConsumeById(@PathVariable("code") long id) {
		return service.consumeGetTeacherById(id);
	}
	
	@GetMapping("feign/id/{code}")
	public TeacherResponse executeFeignConsumeById(@PathVariable("code") long id) {
		return  feignService.getTeacherFromApi(id);
	}
	
	
	@PostMapping("/post")
	public TeacherResponse executeConsumePost(@RequestBody TeacherRequest teacherRequest) {
		return service.consumePostTeacher(teacherRequest);
	}
	
	@PostMapping("feign/post")
	public TeacherResponse executeFeignConsumePost(@RequestBody TeacherRequest teacherRequest) {
		return feignService.postTeacherToApi(teacherRequest);
	}
	
	
}
