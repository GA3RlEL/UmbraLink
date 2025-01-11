package com.umbra.umbralink.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.umbra.umbralink.dto.RegisterRequestDto;
import com.umbra.umbralink.model.UserEntity;
import com.umbra.umbralink.service.UserService;

@RestController
public class RegisterController {
  private UserService userService;

  public RegisterController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public UserEntity registerUser(@RequestBody RegisterRequestDto register) {
    return userService.register(register);
  }

}
