package com.umbra.umbralink.service;

import java.util.List;

import com.umbra.umbralink.dto.RegisterRequestDto;
import com.umbra.umbralink.dto.UserResponseDto;
import com.umbra.umbralink.model.UserEntity;

public interface UserService {
    List<UserEntity> getAllUsers();

    UserEntity findById(Long id);

    UserEntity register(RegisterRequestDto registerRequest);

    UserResponseDto findByToken(String token);

    UserEntity findByTokenUserEntity(String email);
}
