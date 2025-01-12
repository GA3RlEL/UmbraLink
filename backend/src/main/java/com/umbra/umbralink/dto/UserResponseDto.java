package com.umbra.umbralink.dto;

import com.umbra.umbralink.model.Conversation;
import com.umbra.umbralink.model.Message;
import com.umbra.umbralink.model.Photo;
import lombok.Data;

import java.util.List;

@Data
public class UserResponseDto {
    private String username;
    private Photo photo;
    private String email;
    private List<Conversation> conversations;
    private List<Message> messages;
}
