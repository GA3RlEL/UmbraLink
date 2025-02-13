package com.umbra.umbralink.message;

import java.util.Optional;

import com.umbra.umbralink.dto.conversationData.ConversationMessageDto;
import com.umbra.umbralink.dto.conversationData.ConversationMessageSaveDto;
import com.umbra.umbralink.dto.conversationData.ReadMessageDto;
import com.umbra.umbralink.conversation.Conversation;
import com.umbra.umbralink.user.UserEntity;
import com.umbra.umbralink.enums.MessageState;
import com.umbra.umbralink.conversation.ConversationRepository;
import com.umbra.umbralink.user.UserRepository;
import org.springframework.stereotype.Service;

import com.umbra.umbralink.error.MessageNotFoundException;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    public MessageServiceImpl(MessageRepository messageRepository, ConversationRepository conversationRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
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
        return returnDto;

    }

    @Override
    public ReadMessageDto readMessage(Long messageId) {
        Optional<Message> oMessage = messageRepository.findById(messageId);
        if (oMessage.isPresent()) {
            Message message = oMessage.get();
            message.setState(MessageState.SEEN);

            Message savedMessage = messageRepository.save(message);

            ReadMessageDto returnDto = new ReadMessageDto();
            returnDto.setState(savedMessage.getState());
            returnDto.setConversationId(savedMessage.getConversation().getId());
            returnDto.setMessageId(savedMessage.getId());
            return returnDto;
        } else {
            throw new MessageNotFoundException("Message with ID: " + messageId + " does not exist in database");
        }
    }

}
