package com.umbra.umbralink.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.umbra.umbralink.model.User;
import com.umbra.umbralink.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

}
