package com.soft.consume.security.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

 
import com.soft.consume.security.service.UserService;
import com.soft.consume.security.ui.model.LoginRequest;
import com.soft.consume.security.ui.model.UserDTO;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
public class AuthController {
	
	private UserService service;
	
	@PostMapping("/signup")
	public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO){
			return service.saveUserToDB(userDTO) ;	
		
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
			return service.getUserFromDB(loginRequest);	
		
	}
	

}
