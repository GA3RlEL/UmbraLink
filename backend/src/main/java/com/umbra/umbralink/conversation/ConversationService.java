package com.umbra.umbralink.conversation;

import com.umbra.umbralink.dto.ConversationDto;
import com.umbra.umbralink.dto.conversationData.ConversationDataDto;

public interface ConversationService {

    ConversationDataDto getConversationDataById(String token, int id);

    Long getOrCreateConversation(Long user1, Long user2);

    ConversationDto getConversationMetadata(String token, int id);
}
