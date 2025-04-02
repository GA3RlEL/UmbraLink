package com.umbra.umbralink.message;

import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.umbra.umbralink.dto.conversationData.ConversationMessageDto;
import com.umbra.umbralink.dto.conversationData.ConversationMessageSaveDto;
import com.umbra.umbralink.dto.conversationData.ReadMessageDto;
import com.umbra.umbralink.conversation.Conversation;
import com.umbra.umbralink.image.Image;
import com.umbra.umbralink.image.ImageRepository;
import com.umbra.umbralink.image.ImageService;
import com.umbra.umbralink.user.UserEntity;
import com.umbra.umbralink.enums.MessageState;
import com.umbra.umbralink.conversation.ConversationRepository;
import com.umbra.umbralink.user.UserRepository;
import org.springframework.stereotype.Service;

import com.umbra.umbralink.error.MessageNotFoundException;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final ImageRepository imageRepository;

    public MessageServiceImpl(MessageRepository messageRepository, ConversationRepository conversationRepository, UserRepository userRepository, ImageService imageService, ImageRepository imageRepository) {
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
        this.imageService = imageService;
        this.imageRepository = imageRepository;
    }

    @Override
    public Message getMessageById(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new MessageNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public Message createMessage(Message message) {
        return messageRepository.save(message);
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

    @Override
    public ConversationMessageDto saveMessageToDb(ConversationMessageSaveDto dto) {
        Message message = new Message();
        Optional<Conversation> conversation = conversationRepository.findById(dto.getConversationId());
        Optional<UserEntity> sender = userRepository.findById(dto.getSenderId());
        Optional<UserEntity> receiver = userRepository.findById(dto.getReceiverId());
        if (conversation.isPresent() && sender.isPresent() && receiver.isPresent()) {
            message.setConversation(conversation.get());
            message.setSender(sender.get());
            message.setReceiver(receiver.get());
            message.setContent(dto.getContent());
            message.setCreatedAt(dto.getSentTime());
            message.setType(dto.getMessageType());
        }
        Message savedMessage = messageRepository.save(message);
        ConversationMessageDto returnDto = new ConversationMessageDto();
        returnDto.setContent(savedMessage.getContent());
        returnDto.setSenderId(savedMessage.getSender().getId());
        returnDto.setMessageId(savedMessage.getId());
        returnDto.setSentTime(message.getCreatedAt());
        returnDto.setConversationId(message.getConversation().getId());
        returnDto.setState(savedMessage.getState());
        returnDto.setReceiverId(savedMessage.getReceiver().getId());
        returnDto.setType(savedMessage.getType());
        return returnDto;

    }

    @Override
    public ReadMessageDto readMessage(Long messageId) {
        Message message =
                messageRepository.findById(messageId).orElseThrow(()->new MessageNotFoundException("Message was not " +
                        "found"));

            message.setState(MessageState.SEEN);
            Message savedMessage = messageRepository.save(message);
            ReadMessageDto returnDto = new ReadMessageDto();
            returnDto.setState(savedMessage.getState());
            returnDto.setConversationId(savedMessage.getConversation().getId());
            returnDto.setMessageId(savedMessage.getId());
            returnDto.setUpdateTime(savedMessage.getUpdatedAt());
            return returnDto;

    }

    @Override
    public ConversationMessageDto savePhotoMessage(MultipartFile file, String data, String token) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        ConversationMessageSaveDto dto = objectMapper.readValue(data,ConversationMessageSaveDto.class);

        Image image = imageService.saveImageToConversation(file,token);
        Message message = new Message();
        Optional<Conversation> conversation = conversationRepository.findById(dto.getConversationId());
        Optional<UserEntity> sender = userRepository.findById(dto.getSenderId());
        Optional<UserEntity> receiver = userRepository.findById(dto.getReceiverId());
        if (conversation.isPresent() && sender.isPresent() && receiver.isPresent()) {
            message.setConversation(conversation.get());
            message.setSender(sender.get());
            message.setReceiver(receiver.get());
            message.setContent(dto.getContent());
            message.setCreatedAt(dto.getSentTime());
            message.setImage(image);
            message.setType(dto.getMessageType());
            image.setMessage(message);
        }
        Message savedMessage = messageRepository.save(message);
        ConversationMessageDto returnDto = new ConversationMessageDto();
        returnDto.setContent(savedMessage.getContent());
        returnDto.setSenderId(savedMessage.getSender().getId());
        returnDto.setMessageId(savedMessage.getId());
        returnDto.setSentTime(message.getCreatedAt());
        returnDto.setConversationId(message.getConversation().getId());
        returnDto.setState(savedMessage.getState());
        returnDto.setReceiverId(savedMessage.getReceiver().getId());
        returnDto.setPhotoUrl(savedMessage.getImage().getUrl());
        returnDto.setType(savedMessage.getType());
        return returnDto;
    }

}
