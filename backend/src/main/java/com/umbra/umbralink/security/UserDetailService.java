package com.umbra.umbralink.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.umbra.umbralink.user.UserEntity;
import com.umbra.umbralink.user.UserRepository;

@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
            return User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .roles(getRoles(user))
                    .build();
    }

    private String[] getRoles(UserEntity user) {
        if (user.getRole().isEmpty()) {
            return new String[]{"USER"};
        } else {
            return user.getRole().split(",");
        }
    }

}
