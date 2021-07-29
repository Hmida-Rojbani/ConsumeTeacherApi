package com.soft.consume.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.soft.consume.security.service.UserService;

import lombok.AllArgsConstructor;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private Environment environment;
	private UserService usersService;
	private BCryptPasswordEncoder Bcryptencoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/api/users/register").permitAll()
		.and().authorizeRequests().antMatchers("/h2-console/**").permitAll();
		
		http.authorizeRequests().antMatchers("/**")
		.hasIpAddress(environment.getProperty("gateway.ip"))
		.and()
		.addFilter(getAuthenticationFilter());
		
		http.headers().frameOptions().disable();
		
	}
	
	private AuthenticationFilter getAuthenticationFilter() throws Exception
	{
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(usersService, environment, authenticationManager());
        authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
		return authenticationFilter;
	}
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usersService).passwordEncoder(Bcryptencoder);
    }

}
