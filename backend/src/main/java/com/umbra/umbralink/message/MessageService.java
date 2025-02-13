package com.umbra.umbralink.message;

import com.umbra.umbralink.dto.conversationData.ConversationMessageDto;
import com.umbra.umbralink.dto.conversationData.ConversationMessageSaveDto;
import com.umbra.umbralink.dto.conversationData.ReadMessageDto;

public interface MessageService {

    Message getMessageById(Long id);

    Message createMessage(Message message);

    Message updateMessage(Long id, Message message);

    void deleteMessage(Long id);

    ConversationMessageDto saveMessageToDb(ConversationMessageSaveDto dto);

    ReadMessageDto readMessage(Long messageId);
}
