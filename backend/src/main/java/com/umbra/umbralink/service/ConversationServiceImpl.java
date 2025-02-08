package com.umbra.umbralink.service;

import com.umbra.umbralink.dto.conversationData.ConversationDataDto;
import com.umbra.umbralink.dto.conversationData.ConversationMessageDto;
import com.umbra.umbralink.dto.conversationData.ConversationMessageSaveDto;
import com.umbra.umbralink.model.Conversation;
import com.umbra.umbralink.repository.ConversationRepository;
import com.umbra.umbralink.repository.UserRepository;
import com.umbra.umbralink.security.jwt.JwtService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public ConversationServiceImpl(ConversationRepository conversationRepository, JwtService jwtService, UserRepository userRepository) {
        this.conversationRepository = conversationRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public List<Conversation> getAllConversation() {
        return conversationRepository.findAll();
    }

    @Override
    public ConversationDataDto getConversationById(String token, int id) {
        Optional<Conversation> conversation = conversationRepository.findById(id);
        if (conversation.isPresent()) {
            Long senderId = jwtService.extractId(token.substring(7));
            if (userRepository.findById(senderId).isPresent()) {
                Conversation conversationFound = conversation.get();
                ConversationDataDto conversationData = new ConversationDataDto();
                conversationData.setConversationId(conversationFound.getId());
                conversationData.setReceiverId(Objects.equals(conversationFound.getUser1(), senderId) ? conversationFound.getUser2() :
                        conversationFound.getUser1());
                conversationData.setReceiverName(Objects.equals(conversationFound.getUser1(), senderId) ?
                        userRepository.findById(conversationFound.getUser2()).get().getUsername() :
                        userRepository.findById(conversationFound.getUser1()).get().getUsername());
                List<ConversationMessageDto> messages = new ArrayList<>();
                messages = conversationFound.getMessages().stream().map(mess -> {
                    ConversationMessageDto message = new ConversationMessageDto();
                    message.setContent(mess.getContent());
                    message.setSenderId(mess.getSender().getId());
                    message.setSentTime(mess.getCreatedAt());
                    message.setMessageId(mess.getId());
                    message.setConversationId(mess.getConversation().getId());
                    message.setState(mess.getState());
                    return message;
                }).toList();
                conversationData.setMessages(messages);
                return conversationData;
            } else {
                throw new UsernameNotFoundException("Username with Id: " + id + " was not found");
            }
        }
        throw new RuntimeException("There is no conversation with id: " + id);
    }

    @Override
    public Conversation saveConversation(ConversationMessageSaveDto dto) {
        System.out.println(dto);
        return null;
    }

    @Override
    public Long findConversation(Long user1, Long user2) {
        Optional<Conversation> conversation = conversationRepository.findConversation(user1,user2);
        if(conversation.isPresent()){
            return conversation.get().getId();
        }

        return null;
    }

}
