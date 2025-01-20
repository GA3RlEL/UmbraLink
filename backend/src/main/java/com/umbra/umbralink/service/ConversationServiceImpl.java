package com.umbra.umbralink.service;

import com.umbra.umbralink.dto.conversationData.ConversationDataDto;
import com.umbra.umbralink.dto.conversationData.ConversationMessageDto;
import com.umbra.umbralink.model.Conversation;
import com.umbra.umbralink.repository.ConversationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;

    public ConversationServiceImpl(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    @Override
    public List<Conversation> getAllConversation() {
        return conversationRepository.findAll();
    }

    @Override
    public ConversationDataDto getConversationById(int id) {
        Optional<Conversation> conversation = conversationRepository.findById(id);
        if (conversation.isPresent()) {
            Conversation conversationFound = conversation.get();
            ConversationDataDto conversationData = new ConversationDataDto();
            List<ConversationMessageDto> messages = new ArrayList<>();
            messages = conversationFound.getMessages().stream().map(mess -> {
                ConversationMessageDto message = new ConversationMessageDto();
                message.setContent(mess.getContent());
                message.setSenderId(mess.getSender().getId());
                message.setSentTime(mess.getCreatedAt());
                return message;
            }).toList();
            conversationData.setMessages(messages);
            return conversationData;
        }
        throw new RuntimeException("There is no conversation with id: " + id);
    }

}
