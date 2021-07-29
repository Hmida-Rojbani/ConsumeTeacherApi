package com.soft.consume;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ConsumeTeacherApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumeTeacherApiApplication.class, args);
	}

}
