package com.umbra.umbralink.controller;

import java.util.List;

import com.umbra.umbralink.dto.conversationData.ConversationMessageDto;
import com.umbra.umbralink.dto.conversationData.ConversationMessageSaveDto;
import com.umbra.umbralink.dto.conversationData.ReadMessageDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import com.umbra.umbralink.model.Message;
import com.umbra.umbralink.service.MessageService;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/topic")
    @SendTo("/topic")
    public ConversationMessageDto showMessage(ConversationMessageSaveDto dto) {
        return messageService.saveMessageToDb(dto);
    }

    @MessageMapping("/readMessage")
    @SendTo("/readMessage")
    public ReadMessageDto readMessage(ReadMessageDto dto) {
        return messageService.readMessage(dto.getMessageId());
    }


    @GetMapping
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/{id}")
    public Message getMessageById(@PathVariable Long id) {
        return messageService.getMessageById(id);
    }

    @PostMapping
    public Message createMessage(Message message) {
        return messageService.createMessage(message);
    }

    @DeleteMapping("/{id}")
    public void deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
    }

}
