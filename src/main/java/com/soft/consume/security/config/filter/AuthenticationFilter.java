package com.soft.consume.security.config.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soft.consume.security.service.UserService;
import com.soft.consume.security.ui.model.LoginRequestModel;
import com.soft.consume.security.ui.model.UserDTO;
import com.soft.consume.security.util.TokenProvider;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private UserService usersService;
	private Environment environment;
	private TokenProvider jwtUtil;
	
	public AuthenticationFilter(UserService usersService, 
			Environment environment, 
			AuthenticationManager authenticationManager,
			TokenProvider jwtUtil) {
		this.usersService = usersService;
		this.environment = environment;
		super.setAuthenticationManager(authenticationManager);
		this.jwtUtil= jwtUtil;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			  
            LoginRequestModel creds = new ObjectMapper()
                    .readValue(request.getInputStream(), LoginRequestModel.class);
            
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
	}
	
	@Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
    	String email = ((User) auth.getPrincipal()).getUsername();
    	UserDTO userDetails = usersService.findUserByEmail(email);
    	
        String token = jwtUtil.generateToken(auth);
        
        res.addHeader("token", "Bearer "+token);
        res.addHeader("userId", userDetails.getUserId());
    }
}