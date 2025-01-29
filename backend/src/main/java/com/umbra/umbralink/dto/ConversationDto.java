package com.umbra.umbralink.dto;

import com.umbra.umbralink.model.enums.MessageState;
import com.umbra.umbralink.model.enums.UserStatus;
import lombok.Data;

@Data
public class ConversationDto {
    private String otherUser;
    private Long otherUserId;
    private String lastMessage;
    private int conversationId;
    private MessageState state;
    private Boolean isLastMessageSender;
    private UserStatus status;
}
