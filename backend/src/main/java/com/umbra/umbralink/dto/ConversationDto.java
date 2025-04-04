package com.umbra.umbralink.dto;

import com.umbra.umbralink.enums.MessageState;
import com.umbra.umbralink.enums.MessageType;
import com.umbra.umbralink.enums.UserStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConversationDto {
    private String otherUser;
    private Long otherUserId;
    private String lastMessage;
    private MessageType lastMessageType;
    private LocalDateTime lastMessageTimestamp;
    private LocalDateTime lastMessageUpdateTimestamp;
    private Long conversationId;
    private MessageState state;
    private Boolean isLastMessageSender;
    private UserStatus status;
    private String imageUrl;
}
