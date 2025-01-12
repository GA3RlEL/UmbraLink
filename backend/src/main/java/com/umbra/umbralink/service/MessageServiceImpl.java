package com.umbra.umbralink.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.umbra.umbralink.error.MessageNotFoundException;
import com.umbra.umbralink.model.Message;
import com.umbra.umbralink.repository.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService {

    private MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }


    @Override
    public Message getMessageById(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new MessageNotFoundException("User with id " + id + " not found"));
        return message;
    }

    @Override
    public Message createMessage(Message message) {
        Message messageRes = messageRepository.save(message);
        return messageRes;
    }

    @Override
    public Message updateMessage(Long id, Message message) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateMessage'");
    }

    @Override
    public void deleteMessage(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new MessageNotFoundException("Message with id " + id + " not found"));
        messageRepository.delete(message);
    }

}
