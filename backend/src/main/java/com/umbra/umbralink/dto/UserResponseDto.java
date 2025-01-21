package com.umbra.umbralink.dto;

import com.umbra.umbralink.model.Conversation;
import com.umbra.umbralink.model.Message;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private List<ConversationDto> conversations;
}

