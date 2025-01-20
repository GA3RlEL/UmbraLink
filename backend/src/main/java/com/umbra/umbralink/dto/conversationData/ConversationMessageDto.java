package com.umbra.umbralink.dto.conversationData;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConversationMessageDto {
    private String content;
    private Long senderId;
    private LocalDateTime sentTime;
}
