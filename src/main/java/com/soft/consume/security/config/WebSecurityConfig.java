package com.soft.consume.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.soft.consume.security.config.filter.JwtFilter;
import com.soft.consume.security.service.UserService;


@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private Environment environment;
	private UserService usersService;
	private UnauthorizedEntryPoint unauthorizedEntryPoint;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.cors().and().csrf().disable()
		.exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint).and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.authorizeRequests().antMatchers(environment.getProperty("auth.url.path")).permitAll()
		.antMatchers("/h2-console/**").permitAll()
		.anyRequest().authenticated();
		
		
		http.addFilterBefore(getJwtFilter(), UsernamePasswordAuthenticationFilter.class);
		http.headers().frameOptions().disable();
		
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usersService).passwordEncoder(usersService.getBCryptPasswordEncoder());
    }
	
	@Bean
	public JwtFilter getJwtFilter() {
		return new JwtFilter();
	}
	@Autowired
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	@Autowired
	public void setUsersService(UserService usersService) {
		this.usersService = usersService;
	}
	@Autowired
	public void setUnauthorizedEntryPoint(UnauthorizedEntryPoint unauthorizedEntryPoint) {
		this.unauthorizedEntryPoint = unauthorizedEntryPoint;
	}
	
	

}
