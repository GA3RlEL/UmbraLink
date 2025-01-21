package com.umbra.umbralink.service;

import com.umbra.umbralink.dto.conversationData.ConversationDataDto;
import com.umbra.umbralink.dto.conversationData.ConversationMessageSaveDto;
import com.umbra.umbralink.model.Conversation;

import java.util.List;

public interface ConversationService {
    List<Conversation> getAllConversation();

    ConversationDataDto getConversationById(String token, int id);

    Conversation saveConversation(ConversationMessageSaveDto dto);

}
