package com.soft.consume.security.ui.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	
	private String name;
	
	private String email;
	
	private String userId;
	
	private String password;

}
