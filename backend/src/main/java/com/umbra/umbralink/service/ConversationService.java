package com.umbra.umbralink.service;

import com.umbra.umbralink.dto.ConversationDto;
import com.umbra.umbralink.dto.conversationData.ConversationDataDto;
import com.umbra.umbralink.dto.conversationData.ConversationMessageSaveDto;
import com.umbra.umbralink.model.Conversation;

import java.util.List;
import java.util.Optional;

public interface ConversationService {

    ConversationDataDto getConversationDataById(String token, int id);

    Long getOrCreateConversation(Long user1, Long user2);

    ConversationDto getConversationMetadata(String token, int id);
}
