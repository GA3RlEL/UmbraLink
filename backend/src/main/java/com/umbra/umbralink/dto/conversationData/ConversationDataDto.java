package com.umbra.umbralink.dto.conversationData;

import lombok.Data;

import java.util.List;

@Data
public class ConversationDataDto {
    private List<ConversationMessageDto> messages;
    private int conversationId;
    private Long receiverId;
}
