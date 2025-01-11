package com.umbra.umbralink.controller;

import com.umbra.umbralink.model.UserEntity;
import com.umbra.umbralink.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserEntity findMe(@RequestHeader("Authorization") String token) {
        return userService.findByToken(token.substring(7));
    }

}
