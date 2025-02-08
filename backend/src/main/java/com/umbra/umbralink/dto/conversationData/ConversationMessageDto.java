package com.umbra.umbralink.dto.conversationData;

import com.umbra.umbralink.model.enums.MessageState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConversationMessageDto {
    private Long messageId;
    private Long conversationId;
    private Long senderId;

    private String content;
    private LocalDateTime sentTime;
    private MessageState state;
}
