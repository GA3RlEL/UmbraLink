package com.umbra.umbralink.service;

import java.util.List;
import java.util.Optional;

import com.umbra.umbralink.dto.UserResponseDto;
import com.umbra.umbralink.security.jwt.JwtService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.umbra.umbralink.dto.RegisterRequestDto;

import com.umbra.umbralink.model.UserEntity;
import com.umbra.umbralink.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity findById(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

    @Override
    public UserEntity register(RegisterRequestDto registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("User with email: " + registerRequest.getEmail() + " already exists");
        }
        UserEntity user = new UserEntity();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setUsername(registerRequest.getUsername());

        return userRepository.save(user);
    }


    @Override
    public UserResponseDto findByToken(String token) {
        Long id = jwtService.extractId(token);
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            UserResponseDto userResponseDto = new UserResponseDto();
            userResponseDto.setUsername(userEntity.getUsername());
            userResponseDto.setEmail(userEntity.getEmail());

            return userResponseDto;
        } else {
            throw new UsernameNotFoundException("" + id);
        }

    }

    @Override
    public UserEntity findByTokenUserEntity(String token) {
        Long id = jwtService.extractId(token);
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isPresent()) {
            return userEntity.get();
        } else {
            throw new UsernameNotFoundException(token);
        }
    }


}
