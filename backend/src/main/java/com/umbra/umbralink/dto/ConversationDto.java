package com.umbra.umbralink.dto;

import lombok.Data;

@Data
public class ConversationDto {
    private String otherUser;
    private String lastMessage;
    private int conversationId;
}
