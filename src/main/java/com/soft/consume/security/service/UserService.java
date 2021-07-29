package com.soft.consume.security.service;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.soft.consume.security.data.entity.UserEntity;
import com.soft.consume.security.data.repos.UserRepository;
import com.soft.consume.security.ui.model.UserDTO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService{
	
	private UserRepository userRepository;
	private ModelMapper mapper;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public UserDTO saveUserToDB(UserDTO userDTO) {
		UserEntity userEntity = mapper.map(userDTO, UserEntity.class);
		userEntity.setHashedPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
		userEntity.setUserId(UUID.randomUUID().toString());
		return mapper.map(userRepository.save(userEntity),UserDTO.class);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(username)
				.orElseThrow(()-> new NoSuchElementException("no user with this username"));
		return new User(userEntity.getEmail(), userEntity.getHashedPassword(), true, true, true, true, new ArrayList<>());
	}

	public UserDTO findUserByEmail(String email) {
		UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email not found"));

		return mapper.map(user, UserDTO.class);
	}

}
