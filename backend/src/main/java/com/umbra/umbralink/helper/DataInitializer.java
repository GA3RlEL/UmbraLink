package com.umbra.umbralink.helper;

import com.umbra.umbralink.conversation.Conversation;
import com.umbra.umbralink.enums.MessageType;
import com.umbra.umbralink.message.Message;
import com.umbra.umbralink.user.UserEntity;
import com.umbra.umbralink.conversation.ConversationRepository;
import com.umbra.umbralink.message.MessageRepository;
import com.umbra.umbralink.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
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
            user1.setEmail("test@test.com");
            user1.setPassword(passwordEncoder.encode("dupa"));
            user1.setUsername("test");

            UserEntity user2 = new UserEntity();
            user2.setEmail("test1@test.com");
            user2.setPassword(passwordEncoder.encode("dupa"));
            user2.setUsername("test1");

            UserEntity user3 = new UserEntity();
            user3.setEmail("test2@test.com");
            user3.setPassword(passwordEncoder.encode("dupa"));
            user3.setUsername("test2");

            userRepository.saveAll(List.of(user1, user2, user3));

            Conversation conversation = new Conversation();
            conversation.setUser1(user1.getId());
            conversation.setUser2(user2.getId());

            Conversation conversation2 = new Conversation();
            conversation2.setUser1(user3.getId());
            conversation2.setUser2(user1.getId());

            conversationRepository.saveAll(List.of(conversation, conversation2));

            Message message1 = new Message();
            message1.setSender(user1);
            message1.setReceiver(user2);
            message1.setConversation(conversation);
            message1.setContent("Hi");
            message1.setCreatedAt(LocalDateTime.now().minusDays(3));
            message1.setType(MessageType.TEXT);

            Message message2 = new Message();
            message2.setSender(user2);
            message2.setReceiver(user1);
            message2.setConversation(conversation);
            message2.setContent("Hello");
            message2.setCreatedAt(LocalDateTime.now().minusDays(2));
            message2.setType(MessageType.TEXT);

            Message message3 = new Message();
            message3.setSender(user1);
            message3.setReceiver(user2);
            message3.setConversation(conversation);
            message3.setContent("How are you?");
            message3.setCreatedAt(LocalDateTime.now().minusHours(5));
            message3.setType(MessageType.TEXT);

            Message message4 = new Message();
            message4.setSender(user3);
            message4.setReceiver(user1);
            message4.setConversation(conversation2);
            message4.setContent("Hello from user3");
            message4.setCreatedAt(LocalDateTime.now().minusDays(1));
            message4.setType(MessageType.TEXT);

            messageRepository.saveAll(List.of(message1, message2, message3, message4));


        }
    }
}
