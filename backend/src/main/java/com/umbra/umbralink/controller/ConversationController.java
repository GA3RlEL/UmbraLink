package com.umbra.umbralink.controller;

import com.umbra.umbralink.dto.conversationData.ConversationDataDto;
import com.umbra.umbralink.model.Conversation;
import com.umbra.umbralink.service.ConversationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PostMapping("/findConversation")
    public Long findConversation(@RequestBody Map<String, Long> body){
        Long user1 = body.get("user1");
        Long user2 = body.get("user2");
        System.out.println("User1: " + user1);
        System.out.println("User2: " + user2);
        return conversationService.findConversation(user1,user2);
    }

}
