package com.umbra.umbralink.user;

import java.util.List;

import com.umbra.umbralink.dto.auth.AuthResponseDto;
import com.umbra.umbralink.dto.auth.RegisterRequestDto;
import com.umbra.umbralink.dto.UserResponseDto;
import com.umbra.umbralink.dto.UserStatusDto;
import com.umbra.umbralink.dto.findUsers.FindUsersDto;
import com.umbra.umbralink.dto.updateUser.UpdatePasswordPayloadDto;
import com.umbra.umbralink.dto.updateUser.UpdateUsernameDto;
import com.umbra.umbralink.enums.UserStatus;

public interface UserService {
    UserEntity findById(Long id);

    UserEntity register(RegisterRequestDto registerRequest);

    UserResponseDto findByToken(String token);

    UserStatusDto changeUserStatus(Long userId, UserStatus userStatus);

    List<FindUsersDto> findUsers(String data);

    UpdateUsernameDto updateUsername(String newUsername, String token);

    AuthResponseDto updatePassword(String token, UpdatePasswordPayloadDto payload);
}
