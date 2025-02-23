package com.umbra.umbralink.conversation;

import com.umbra.umbralink.dto.ConversationDto;
import com.umbra.umbralink.dto.conversationData.ConversationDataDto;
import com.umbra.umbralink.dto.conversationData.ConversationMessageDto;
import com.umbra.umbralink.error.NotFoundError;
import com.umbra.umbralink.error.UnauthorizedConversationAccessException;
import com.umbra.umbralink.message.Message;
import com.umbra.umbralink.user.UserEntity;
import com.umbra.umbralink.user.UserRepository;
import com.umbra.umbralink.security.jwt.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public ConversationDataDto getConversationDataById(String token, int id) {
        Long senderId = jwtService.extractId(token.substring(7));

        UserEntity sender = userRepository.findById(senderId)
                .orElseThrow(() -> new NotFoundError("User with ID: " + senderId + " was not found"));

        Conversation conversation = conversationRepository.findById(id)
                .orElseThrow(() -> new NotFoundError("Conversation does not exist"));

        // validation
        if(!Objects.equals(conversation.getUser1(),senderId) && !Objects.equals(conversation.getUser2(), senderId)){
            throw new UnauthorizedConversationAccessException("You are not participant of this conversation!");
        }

        Long receiverId = Objects.equals(conversation.getUser1(), senderId) ? conversation.getUser2() : conversation.getUser1();

        UserEntity receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new UsernameNotFoundException("User with ID: " + receiverId + " was not found"));

        List<ConversationMessageDto> messages = conversation.getMessages().stream()
                .map(m -> new ConversationMessageDto(
                        m.getId(),
                        m.getConversation().getId(),
                        m.getSender().getId(),
                        m.getReceiver().getId(),
                        m.getContent(),
                        m.getUpdatedAt(),
                        m.getCreatedAt(),
                        m.getState()
                )).toList();

        return new ConversationDataDto(
                messages,
                conversation.getId(),
                receiverId,
                receiver.getUsername(),
                receiver.getProfileImage() != null ? receiver.getProfileImage().getUrl() :""
        );
    }


    @Override
    public Long getOrCreateConversation(Long user1, Long user2) {
        Optional<Conversation> conversation = conversationRepository.findConversation(user1,user2);
        if(conversation.isPresent()){
            return conversation.get().getId();
        }else{
            Conversation newConversation = new Conversation();
            newConversation.setUser1(user1);
            newConversation.setUser2(user2);
            Conversation savedConversation= conversationRepository.save(newConversation);
            return savedConversation.getId();
        }
    }

    @Override
    public ConversationDto getConversationMetadata(String token, int id) {
        Long userId = jwtService.extractId(token.substring(7));

        Conversation conversation =
                conversationRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND
                        ,"Conversation not found"));

        Long otherUserId = conversation.getUser1().equals(userId)?conversation.getUser2():conversation.getUser1();

        UserEntity otherUser =
                userRepository.findById(otherUserId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND
                        ,"Other user not found"));

        ConversationDto dto = new ConversationDto();
        dto.setOtherUser(otherUser.getUsername());
        dto.setConversationId(conversation.getId());
        dto.setOtherUserId(otherUserId);
        dto.setStatus(otherUser.getStatus());

        if(!conversation.getMessages().isEmpty()){
            Message lastMessage = conversation.getMessages().getLast();
            dto.setLastMessage(lastMessage.getContent());
            dto.setIsLastMessageSender(Objects.equals(lastMessage.getSender().getId(), userId));
            dto.setState(lastMessage.getState());
        }else{
            dto.setLastMessage(null);
            dto.setLastMessage("");
            dto.setIsLastMessageSender(false);
        }

        return dto;
    }

}
