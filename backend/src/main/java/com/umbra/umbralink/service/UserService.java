package com.umbra.umbralink.service;

import java.util.List;

import com.umbra.umbralink.dto.RegisterRequestDto;
import com.umbra.umbralink.model.UserEntity;

public interface UserService {
    List<UserEntity> getAllUsers();

    UserEntity register(RegisterRequestDto registerRequest);

    UserEntity findByToken(String token);
}
