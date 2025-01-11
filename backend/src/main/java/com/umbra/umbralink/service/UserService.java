package com.umbra.umbralink.service;

import java.util.List;

import com.umbra.umbralink.dto.RegisterRequestDto;
import com.umbra.umbralink.model.UserEntity;

public interface UserService {
  public List<UserEntity> getAllUsers();

  public UserEntity register(RegisterRequestDto registerRequest);

}
