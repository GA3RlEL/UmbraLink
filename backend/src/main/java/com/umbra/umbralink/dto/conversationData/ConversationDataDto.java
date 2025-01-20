package com.umbra.umbralink.dto.conversationData;

import lombok.Data;

import java.util.List;

@Data
public class ConversationDataDto {
    List<ConversationMessageDto> messages;
}
