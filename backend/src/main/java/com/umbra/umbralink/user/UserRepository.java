package com.umbra.umbralink.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByUsername(String username);

  Optional<UserEntity> findByEmail(String email);

  Boolean existsByEmail(String email);

  List<UserEntity> findByEmailContainingIgnoreCaseOrUsernameContainingIgnoreCase(String email, String username);
}
