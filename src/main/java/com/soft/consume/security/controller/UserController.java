package com.soft.consume.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.soft.consume.security.ui.model.UserDTO;

import com.soft.consume.security.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
	
	private UserService service;
	
	@PostMapping("/register")
	public ResponseEntity<String> saveUser(@RequestBody UserDTO userDTO){
			service.saveUserToDB(userDTO) ;
			return ResponseEntity.status(HttpStatus.CREATED)
									.body("User is added to DB");
		
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleExp(Exception e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body("Something Wrong");
	}

}
