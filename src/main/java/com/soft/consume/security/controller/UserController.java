package com.soft.consume.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soft.consume.security.service.UserService;
import com.soft.consume.security.ui.model.UserDTO;

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
	
	@PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
	@GetMapping("/useroradminping")
    public String userOrAdminPing(){
        return "Any User Or admin Can Read This";
    }
	
	@PreAuthorize("hasRole('USER')")
    @GetMapping("/userping")
    public String userPing(){
        return "Any User Can Read This";
    }
	
	@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/adminping")
    public String adminPing(){
        return "Any Admin Can Read This";
    }
	
	@PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/superadminping")
    public String superAdminPing(){
        return "Any SuperAdmin Can Read This";
    }
	
	 @GetMapping("/openping")
	    public String openPing(){
	        return "Any Role Can Read This";
	    }
	

}
