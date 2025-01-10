package com.umbra.umbralink.controller;

import org.springframework.web.bind.annotation.RestController;

import com.umbra.umbralink.jwt.JwtService;
import com.umbra.umbralink.security.UserDetailService;

@RestController
public class LoginController {

  private JwtService jwtService;
  private UserDetailService userDetailService;

  public LoginController(JwtService jwtService, UserDetailService userDetailService) {
    this.jwtService = jwtService;
    this.userDetailService = userDetailService;
  }

}
