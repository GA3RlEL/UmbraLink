package com.umbra.umbralink.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umbra.umbralink.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByUsername(String username);

  Optional<UserEntity> findByEmail(String email);

  Boolean existsByEmail(String email);
}
