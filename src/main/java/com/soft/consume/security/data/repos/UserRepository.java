package com.soft.consume.security.data.repos;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.soft.consume.security.data.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long>{

	Optional<UserEntity> findByEmail(String username);

}
