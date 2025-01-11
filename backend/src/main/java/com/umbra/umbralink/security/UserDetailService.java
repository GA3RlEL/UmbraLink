package com.umbra.umbralink.security;

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
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<UserEntity> userOptional = userRepository.findByEmail(email);
    if (userOptional.isPresent()) {
      UserEntity user = userOptional.get();
      return User.builder()
          .username(user.getEmail())
          .password(user.getPassword())
          .roles(getRoles(user))
          .build();
    } else {
      throw new UsernameNotFoundException(email);
    }
  }

  private String[] getRoles(UserEntity user) {
    if (user.getRole().isEmpty()) {
      return new String[] { "USER" };
    } else {
      return user.getRole().split(",");
    }
  }

}
