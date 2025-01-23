package com.umbra.umbralink.service;

import java.util.List;

import com.umbra.umbralink.dto.conversationData.ConversationMessageDto;
import com.umbra.umbralink.dto.conversationData.ConversationMessageSaveDto;
import com.umbra.umbralink.model.Message;

public interface MessageService {
    List<Message> getAllMessages();

    Message getMessageById(Long id);

    Message createMessage(Message message);

    Message updateMessage(Long id, Message message);

    void deleteMessage(Long id);

    ConversationMessageDto saveMessageToDb(ConversationMessageSaveDto dto);
}
