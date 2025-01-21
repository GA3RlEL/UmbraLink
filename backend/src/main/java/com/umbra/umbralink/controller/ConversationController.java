package com.umbra.umbralink.controller;

import com.umbra.umbralink.dto.conversationData.ConversationDataDto;
import com.umbra.umbralink.model.Conversation;
import com.umbra.umbralink.service.ConversationService;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ConversationDataDto getConversationById(@RequestHeader("Authorization") String token, @PathVariable int id) {
        return conversationService.getConversationById(token, id);
    }

}
