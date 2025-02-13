package com.umbra.umbralink.dto;

import com.umbra.umbralink.model.Conversation;
import com.umbra.umbralink.model.Message;
import com.umbra.umbralink.model.enums.UserStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private UserStatus status;
    private List<ConversationDto> conversations;
    private String imageUrl;
}

