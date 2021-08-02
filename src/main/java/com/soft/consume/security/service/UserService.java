package com.soft.consume.security.service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.soft.consume.security.data.entity.UserEntity;
import com.soft.consume.security.data.repos.UserRepository;
import com.soft.consume.security.ui.model.JwtResponse;
import com.soft.consume.security.ui.model.LoginRequest;
import com.soft.consume.security.ui.model.MessageResponse;
import com.soft.consume.security.ui.model.UserDTO;
import com.soft.consume.security.util.TokenProvider;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Service
@AllArgsConstructor
@Getter
public class UserService implements UserDetailsService {

	private UserRepository userRepository;
	private RoleService roleService;
	private ModelMapper mapper;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private AuthenticationManager authenticationManager;
	private TokenProvider jwtUtils;

	public ResponseEntity<?> saveUserToDB(UserDTO userDTO) {

		if (userRepository.existsByEmail(userDTO.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account

		UserEntity userEntity = mapper.map(userDTO, UserEntity.class);
		userEntity.setHashedPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
		userEntity.setUserId(UUID.randomUUID().toString());
		// change to Set Role
		if(userDTO.getRoles()== null) {
			userDTO.setRoles(new HashSet<>());
			userDTO.getRoles().add("USER");
		}
		userEntity.setRoles(userDTO.getRoles().stream().map(roleName -> roleService.findRoleByName(roleName))
				.collect(Collectors.toSet()));
		userRepository.save(userEntity);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	public ResponseEntity<?> getUserFromDB(LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateToken(authentication);
		
		String email = ((User) authentication.getPrincipal()).getUsername();
    	UserDTO userDetails = findUserByEmail(email);
    	
    	return ResponseEntity.ok(new JwtResponse(jwt, 
				 userDetails.getUserId(), 
				 userDetails.getName(), 
				 userDetails.getEmail(), 
				 userDetails.getRoles()));
		
		}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(username)
				.orElseThrow(() -> new NoSuchElementException("Invalid username or password."));
		return new User(userEntity.getEmail(), userEntity.getHashedPassword(), getAuthority(userEntity));
	}

	public UserDTO findUserByEmail(String email) {
		UserEntity user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Email not found"));

		UserDTO userDTO = mapper.map(user, UserDTO.class);
		userDTO.setRoles(user.getRoles()
								.stream()
								.map(role->role.getName())
								.collect(Collectors.toSet()));
		return userDTO;
	}

	private Set<SimpleGrantedAuthority> getAuthority(UserEntity user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
		});
		return authorities;
	}

}
