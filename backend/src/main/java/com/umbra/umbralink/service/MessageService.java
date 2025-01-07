package com.umbra.umbralink.service;

import java.util.List;

import com.umbra.umbralink.model.Message;

public interface MessageService {
  public List<Message> getAllMessages();

  public Message getMessageById(Long id);

  public Message createMessage(Message message);

  public Message updateMessage(Long id, Message message);

  public void deleteMessage(Long id);

}
