package com.umbra.umbralink.service;

import java.util.List;

import com.umbra.umbralink.dto.auth.RegisterRequestDto;
import com.umbra.umbralink.dto.UserResponseDto;
import com.umbra.umbralink.dto.UserStatusDto;
import com.umbra.umbralink.dto.findUsers.FindUsersDto;
import com.umbra.umbralink.model.UserEntity;
import com.umbra.umbralink.model.enums.UserStatus;

public interface UserService {
    UserEntity findById(Long id);

    UserEntity register(RegisterRequestDto registerRequest);

    UserResponseDto findByToken(String token);

    UserStatusDto changeUserStatus(Long userId, UserStatus userStatus);

    List<FindUsersDto> findUsers(String data);
}
