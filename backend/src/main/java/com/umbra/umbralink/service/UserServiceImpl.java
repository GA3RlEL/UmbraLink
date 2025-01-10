package com.umbra.umbralink.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.umbra.umbralink.dto.RegisterRequest;
import com.umbra.umbralink.model.Role;
import com.umbra.umbralink.model.UserEntity;
import com.umbra.umbralink.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;

  public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public List<UserEntity> getAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public UserEntity register(RegisterRequest registerRequest) {
    if (userRepository.existsByEmail(registerRequest.getEmail())) {
      throw new RuntimeException("User with email: " + registerRequest.getEmail() + " already exists");
    }
    UserEntity user = new UserEntity();
    user.setEmail(registerRequest.getEmail());
    user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
    user.setUsername(registerRequest.getUsername());

    return userRepository.save(user);
  }

}
