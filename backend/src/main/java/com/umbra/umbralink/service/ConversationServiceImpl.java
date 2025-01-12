package com.umbra.umbralink.service;

import com.umbra.umbralink.model.Conversation;
import com.umbra.umbralink.repository.ConversationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
