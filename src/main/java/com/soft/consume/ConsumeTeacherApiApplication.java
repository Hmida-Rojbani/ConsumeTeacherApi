package com.soft.consume;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.soft.consume.security.data.entity.RoleEntity;
import com.soft.consume.security.data.repos.RoleRepository;
import com.soft.consume.security.service.UserService;
import com.soft.consume.security.ui.model.UserDTO;

@SpringBootApplication
@EnableFeignClients
public class ConsumeTeacherApiApplication {
	// for faster testing
	@Autowired
    private RoleRepository roleRepos;
	@Autowired
    private UserService userService;

    @PostConstruct
    public void initRoles() {
        List<RoleEntity> users = Stream.of(
                new RoleEntity(1, "SUPER_ADMIN", "Can controle anything"),
                new RoleEntity(2, "ADMIN", "have some controle anything"),
                new RoleEntity(3, "USER", "Cannot controle anything")
        ).collect(Collectors.toList());
        roleRepos.saveAll(users);
        initUsers();

    }
    
    
    private void initUsers() {
        userService.saveUserToDB(new UserDTO("hmida", "hmida@gmail.com", "", "12345", 
        		new HashSet<>(Arrays.asList("SUPER_ADMIN","ADMIN"))));
        userService.saveUserToDB(new UserDTO("admin", "admin@gmail.com", "", "12345", 
        		new HashSet<>(Arrays.asList("ADMIN"))));
        userService.saveUserToDB(new UserDTO("user", "user@gmail.com", "", "12345", 
        		new HashSet<>(Arrays.asList("USER"))));
    }

	public static void main(String[] args) {
		SpringApplication.run(ConsumeTeacherApiApplication.class, args);
	}
	
	 @Bean
		public WebMvcConfigurer corsConfigurer() {
			return new WebMvcConfigurer() {
				@Override
				public void addCorsMappings(CorsRegistry registry) {
					registry.addMapping("/*").allowedHeaders("*").allowedOrigins("http://localhost:4200").allowedMethods("*")
					.allowCredentials(true);
				}
			};
		}

}
