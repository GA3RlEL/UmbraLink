package com.umbra.umbralink.helper;

import com.umbra.umbralink.model.Conversation;
import com.umbra.umbralink.model.Message;
import com.umbra.umbralink.model.UserEntity;
import com.umbra.umbralink.model.enums.MessageState;
import com.umbra.umbralink.model.enums.MessageType;
import com.umbra.umbralink.repository.ConversationRepository;
import com.umbra.umbralink.repository.MessageRepository;
import com.umbra.umbralink.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cglib.core.Local;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, ConversationRepository conversationRepository, MessageRepository messageRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (conversationRepository.findAll().isEmpty() && messageRepository.findAll().isEmpty() && userRepository.findAll().isEmpty()) {
            UserEntity user1 = new UserEntity();
            user1.setUsername("john_doe");
            user1.setEmail("test1@test.com");
            user1.setPassword(passwordEncoder.encode("dupa"));
            user1.setLastSeen(LocalDateTime.now());
            user1.setCreatedAt(LocalDateTime.now());

            UserEntity user2 = new UserEntity();
            user2.setUsername("jane_smith");
            user2.setEmail("test2test.com");
            user2.setPassword(passwordEncoder.encode("dupa"));
            user2.setLastSeen(LocalDateTime.now());
            user2.setCreatedAt(LocalDateTime.now());

            userRepository.saveAll(List.of(user1, user2));


            Conversation conversation = new Conversation();
            conversation.setSender(user1);
            conversation.setRecipient(user2);
            conversation.setCreatedAt(LocalDateTime.now());

            conversationRepository.save(conversation);

            Message message1 = new Message();
            message1.setContent("Hello, Jane!");
            message1.setState(MessageState.SENT);
            message1.setType(MessageType.TEXT);
            message1.setConversation(conversation);
            message1.setSenderId(user1.getId().toString());
            message1.setReceiverId(user2.getId().toString());
            message1.setCreatedAt(LocalDateTime.now());

            Message message2 = new Message();
            message2.setContent("Hi, John! How are you?");
            message2.setState(MessageState.SENT);
            message2.setType(MessageType.TEXT);
            message2.setConversation(conversation);
            message2.setSenderId(user2.getId().toString());
            message2.setReceiverId(user1.getId().toString());
            message2.setCreatedAt(LocalDateTime.now());

            messageRepository.saveAll(List.of(message1, message2));
        }
    }
}
