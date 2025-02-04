package com.umbra.umbralink.repository;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.umbra.umbralink.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByUsername(String username);

  Optional<UserEntity> findByEmail(String email);

  Boolean existsByEmail(String email);

  List<UserEntity> findByEmailContainingIgnoreCaseOrUsernameContainingIgnoreCase(String email, String username);
}
