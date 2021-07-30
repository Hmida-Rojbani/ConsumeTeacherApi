package com.soft.consume.security.data.repos;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.soft.consume.security.data.entity.RoleEntity;

public interface RoleRepository extends CrudRepository<RoleEntity, Long>{
	Optional<RoleEntity> findByNameIgnoreCase(String name);
}
