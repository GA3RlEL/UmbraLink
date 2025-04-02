package com.umbra.umbralink.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.api.client.json.Json;
import com.umbra.umbralink.dto.conversationData.ConversationMessageDto;
import com.umbra.umbralink.dto.conversationData.ConversationMessageSaveDto;
import com.umbra.umbralink.dto.conversationData.ReadMessageDto;
import org.apache.coyote.Request;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public MessageController(MessageService messageService, SimpMessagingTemplate simpMessagingTemplate) {
        this.messageService = messageService;
        this.simpMessagingTemplate = simpMessagingTemplate;
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


    @GetMapping("/{id}")
    public Message getMessageById(@PathVariable Long id) {
        return messageService.getMessageById(id);
    }

    @PostMapping("/addPhotoMessage")
    public ConversationMessageDto addPhotoMessage(
            @RequestParam(value = "file",required = true)MultipartFile file,
            @RequestParam(value = "data", required = true) String data,
            @RequestHeader(value = "Authorization") String token
    ) throws JsonProcessingException {
        ConversationMessageDto dto = messageService.savePhotoMessage(file,data,token);
        simpMessagingTemplate.convertAndSend("/topic",dto);
        return null;
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
