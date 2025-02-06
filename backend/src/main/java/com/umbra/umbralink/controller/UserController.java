package com.umbra.umbralink.controller;

import com.umbra.umbralink.dto.UserResponseDto;
import com.umbra.umbralink.dto.findUsers.FindUsersDto;
import com.umbra.umbralink.model.UserEntity;
import com.umbra.umbralink.service.ConversationService;
import com.umbra.umbralink.service.UserService;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService, ConversationService conversationService) {
        this.userService = userService;
    }


    @GetMapping("/{id}")
    public UserEntity getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/me")
    public UserResponseDto findMe(@RequestHeader("Authorization") String token) {
        return userService.findByToken(token.substring(7));
    }

    @GetMapping("/findOther")
    public List<FindUsersDto> findOtherUsersByEmailOrUsername(@RequestParam String data)
    {
        data = data.replaceAll("\"", "");
        return userService.findUsers(data);
    }


}
