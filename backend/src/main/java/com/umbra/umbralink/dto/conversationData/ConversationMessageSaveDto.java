package com.umbra.umbralink.dto.conversationData;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.umbra.umbralink.model.enums.MessageType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ConversationMessageSaveDto {
    private String content;
    private Long senderId;
    private int conversationId;
    private Long receiverId;
    private String sentTime;
    private MessageType messageType;
}
