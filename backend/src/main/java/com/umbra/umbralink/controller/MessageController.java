package com.umbra.umbralink.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.umbra.umbralink.model.Message;
import com.umbra.umbralink.service.MessageService;

@RestController
@RequestMapping("/messages")
public class MessageController {

  private MessageService messageService;

  public MessageController(MessageService messageService) {
    this.messageService = messageService;
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
