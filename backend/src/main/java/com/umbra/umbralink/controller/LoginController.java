package com.umbra.umbralink.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.umbra.umbralink.dto.LoginRequestDto;
import com.umbra.umbralink.security.UserDetailService;
import com.umbra.umbralink.security.jwt.JwtService;

@RestController
public class LoginController {

  private JwtService jwtService;
  private UserDetailService userDetailService;
  private AuthenticationManager authenticationManager;

  public LoginController(JwtService jwtService, UserDetailService userDetailService,
      AuthenticationManager authenticationManager) {
    this.jwtService = jwtService;
    this.userDetailService = userDetailService;
    this.authenticationManager = authenticationManager;
  }

  @PostMapping("/login")
  public String login(@RequestBody LoginRequestDto loginRequest) {
    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
    if (authentication.isAuthenticated()) {
      UserDetails userDetails = userDetailService.loadUserByUsername(loginRequest.getEmail());
      return jwtService.generateToken(userDetails);
    } else {
      throw new UsernameNotFoundException(loginRequest.getEmail());
    }

  }

}
