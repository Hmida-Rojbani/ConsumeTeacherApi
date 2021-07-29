package com.soft.consume.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.soft.consume.service.models.TeacherResponse;

import lombok.AllArgsConstructor;

@Service

public class ConsumeService {
	
	private RestTemplate restTemplate;

	public ConsumeService(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}

	@Value("${api.url}")
	private String URL;
	
	public TeacherResponse consumeGetTeacherById(long id) {
		String url = URL + "/id/"+id;
		return restTemplate.getForObject(url, TeacherResponse.class);
	}

}
