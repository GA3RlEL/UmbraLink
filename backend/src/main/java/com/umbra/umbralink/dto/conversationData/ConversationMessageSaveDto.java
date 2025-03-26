package com.umbra.umbralink.dto.conversationData;

import com.umbra.umbralink.enums.MessageType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class ConversationMessageSaveDto {
    private String content;
    private Long senderId;
    private int conversationId;
    private Long receiverId;
    private LocalDateTime sentTime;
    private MessageType messageType;
}
