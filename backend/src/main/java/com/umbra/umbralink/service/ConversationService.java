package com.umbra.umbralink.service;

import com.umbra.umbralink.dto.conversationData.ConversationDataDto;
import com.umbra.umbralink.dto.conversationData.ConversationMessageSaveDto;
import com.umbra.umbralink.model.Conversation;

import java.util.List;
import java.util.Optional;

public interface ConversationService {
    List<Conversation> getAllConversation();

    ConversationDataDto getConversationById(String token, int id);

    Conversation saveConversation(ConversationMessageSaveDto dto);

    Long findConversation(Long user1, Long user2);
}
