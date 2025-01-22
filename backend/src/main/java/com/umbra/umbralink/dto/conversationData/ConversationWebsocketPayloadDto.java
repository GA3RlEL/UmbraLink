package com.umbra.umbralink.dto.conversationData;

import lombok.Data;

@Data
public class ConversationWebsocketPayloadDto {

    private String operation;
    private ConversationMessageSaveDto dto;
}
