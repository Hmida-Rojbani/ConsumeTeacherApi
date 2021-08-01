package com.soft.consume.security.ui.model;



import java.util.Set;


import lombok.Data;

@Data
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String userId;
	private String username;
	private String email;
	private Set<String> roles;
	
	public JwtResponse(String accessToken, String id, String username, String email, Set<String> roles) {
		this.token = accessToken;
		this.userId = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
	}
	
}