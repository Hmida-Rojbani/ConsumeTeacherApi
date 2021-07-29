package com.soft.consume.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.soft.consume.service.models.TeacherRequest;
import com.soft.consume.service.models.TeacherResponse;

@FeignClient(url = "http://localhost:8080/api/teachers", value = "http://localhost:8080/api/teachers")
public interface ConsumeByFeignService {
	
	@GetMapping("/id/{id}")
	public TeacherResponse getTeacherFromApi(@PathVariable("id") long id);
	
	@PostMapping()
	public TeacherResponse postTeacherToApi(@RequestBody TeacherRequest request);

}
