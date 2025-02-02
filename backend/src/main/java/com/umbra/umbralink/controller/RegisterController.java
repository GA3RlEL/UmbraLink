package com.umbra.umbralink.controller;

import com.umbra.umbralink.dto.AuthResponseDto;
import com.umbra.umbralink.security.UserDetailService;
import com.umbra.umbralink.security.jwt.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.umbra.umbralink.dto.RegisterRequestDto;
import com.umbra.umbralink.model.UserEntity;
import com.umbra.umbralink.service.UserService;

@RestController
public class RegisterController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailService userDetailService;
    private final JwtService jwtService;

    public RegisterController(UserService userService, AuthenticationManager authenticationManager, UserDetailService userDetailService, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userDetailService = userDetailService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> registerUser(@RequestBody RegisterRequestDto register) {
        UserEntity registeredUser = userService.register(register);
        if(registeredUser != null) {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(register.getEmail(), register.getPassword()));
            if(authentication.isAuthenticated()) {
                UserDetails userDetails = userDetailService.loadUserByUsername(registeredUser.getEmail());
                return new ResponseEntity<>(new AuthResponseDto(jwtService.generateToken(userDetails)), HttpStatus.OK);
            }
        }
        throw new UsernameNotFoundException("Something went wrong with creating account");
    }

}
