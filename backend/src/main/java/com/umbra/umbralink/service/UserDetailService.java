package com.umbra.umbralink.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.umbra.umbralink.model.UserEntity;
import com.umbra.umbralink.repository.UserRepository;

@Service
public class UserDetailService implements UserDetailsService {

  private UserRepository userRepository;

  public UserDetailService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<UserEntity> userOptional = userRepository.findByUsername(username);
    if (userOptional.isPresent()) {
      UserEntity user = userOptional.get();
      return User.builder()
          .username(username)
          .password(user.getPassword())
          .roles(getRoles(user))
          .build();
    } else {
      throw new UsernameNotFoundException(username);
    }
  }

  private String getRoles(UserEntity user) {
    return "";
  }

}
