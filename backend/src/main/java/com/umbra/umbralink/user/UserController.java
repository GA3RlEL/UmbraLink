package com.umbra.umbralink.user;

import com.umbra.umbralink.dto.UserResponseDto;
import com.umbra.umbralink.dto.auth.AuthResponseDto;
import com.umbra.umbralink.dto.findUsers.FindUsersDto;
import com.umbra.umbralink.conversation.ConversationService;
import com.umbra.umbralink.dto.updateUser.UpdatePasswordPayloadDto;
import com.umbra.umbralink.dto.updateUser.UpdateUsernameDto;
import com.umbra.umbralink.dto.updateUser.UpdateUsernamePayloadDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;

    public UserController(UserService userService, ConversationService conversationService, SimpMessagingTemplate messagingTemplate) {
        this.userService = userService;
        this.messagingTemplate = messagingTemplate;
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

    @PatchMapping("/update/username")
    public UpdateUsernameDto updateUsername(@RequestHeader("Authorization")String token,
                                            @RequestBody UpdateUsernamePayloadDto payload){
        UpdateUsernameDto dto = userService.updateUsername(payload.newUsername(),token);
        messagingTemplate.convertAndSend("/updateUsername",dto);
        return dto;
    }

    @PatchMapping("/update/password")
    public AuthResponseDto updatePassword(@RequestHeader("Authorization") String token,
                                          @RequestBody UpdatePasswordPayloadDto payload){
        return userService.updatePassword(token, payload);
    }

    @PostMapping("/restore/password")
    public boolean updatePassword(@RequestBody Map<String,String> data){
        String token = data.get("token");
        String newPassword = data.get("newPassword");
        return userService.updateRestorePassword(token,newPassword);
    }


}
