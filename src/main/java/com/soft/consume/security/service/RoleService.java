package com.soft.consume.security.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.soft.consume.security.data.entity.RoleEntity;
import com.soft.consume.security.data.repos.RoleRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleService {
	private RoleRepository roleRepo;

 
    public RoleEntity findRoleByName(String name) {
    	RoleEntity role = roleRepo.findByNameIgnoreCase(name).orElseGet(()->roleRepo.findByNameIgnoreCase("USER").get());
        return role;
    }
}
