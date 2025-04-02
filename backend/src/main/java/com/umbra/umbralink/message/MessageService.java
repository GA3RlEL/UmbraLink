package com.umbra.umbralink.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.umbra.umbralink.dto.conversationData.ConversationMessageDto;
import com.umbra.umbralink.dto.conversationData.ConversationMessageSaveDto;
import com.umbra.umbralink.dto.conversationData.ReadMessageDto;
import org.springframework.web.multipart.MultipartFile;

public interface MessageService {

    Message getMessageById(Long id);

    Message createMessage(Message message);

    Message updateMessage(Long id, Message message);

    void deleteMessage(Long id);

    ConversationMessageDto saveMessageToDb(ConversationMessageSaveDto dto);

    ReadMessageDto readMessage(Long messageId);

    ConversationMessageDto savePhotoMessage(MultipartFile file, String data, String token) throws JsonProcessingException;
}
