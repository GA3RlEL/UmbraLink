package com.umbra.umbralink.controller;

import com.umbra.umbralink.model.Conversation;
import com.umbra.umbralink.model.UserEntity;
import com.umbra.umbralink.service.ConversationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/conversation")
public class ConversationController {
    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @GetMapping
    public List<Conversation> getAllConversation() {
        return conversationService.getAllConversation();
    }

}
