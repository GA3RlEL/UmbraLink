package com.umbra.umbralink.dto.conversationData;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ConversationDataDto {
    private List<ConversationMessageDto> messages;
    private Long conversationId;
    private Long receiverId;
    private String receiverName;
}
