package com.umbra.umbralink.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.umbra.umbralink.dto.ConversationDto;
import com.umbra.umbralink.dto.UserResponseDto;
import com.umbra.umbralink.model.Conversation;
import com.umbra.umbralink.repository.ConversationRepository;
import com.umbra.umbralink.security.jwt.JwtService;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.umbra.umbralink.dto.RegisterRequestDto;

import com.umbra.umbralink.model.UserEntity;
import com.umbra.umbralink.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ConversationRepository conversationRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, ConversationRepository conversationRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.conversationRepository = conversationRepository;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity findById(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

    @Override
    public UserEntity register(RegisterRequestDto registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("User with email: " + registerRequest.getEmail() + " already exists");
        }
        UserEntity user = new UserEntity();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setUsername(registerRequest.getUsername());

        return userRepository.save(user);
    }


    @Override
    public UserResponseDto findByToken(String token) {
        Long id = jwtService.extractId(token);
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            List<Conversation> conversations = conversationRepository.findAll().stream().filter(conversation ->
                    conversation.getUser1().equals(userEntity.getId()) || conversation.getUser2().equals(userEntity.getId())
            ).toList();
            UserResponseDto userResponseDto = new UserResponseDto();
            userResponseDto.setUsername(userEntity.getUsername());
            userResponseDto.setEmail(userEntity.getEmail());
            List<ConversationDto> conversationDtos = conversations.stream().map(c -> {
                ConversationDto dto = new ConversationDto();
                dto.setLastMessage(c.getMessages().get(0).getContent());
                dto.setConversationId(c.getId());
                dto.setOtherUser(Objects.equals(c.getUser1(), userEntity.getId()) ?
                        userRepository.findById(c.getUser2()).get().getUsername() :
                        userRepository.findById(c.getUser1()).get().getUsername());
                return dto;
            }).toList();
            userResponseDto.setConversations(conversationDtos);
            return userResponseDto;
        } else {
            throw new UsernameNotFoundException("" + id);
        }

    }

    @Override
    public UserEntity findByTokenUserEntity(String token) {
        Long id = jwtService.extractId(token);
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isPresent()) {
            return userEntity.get();
        } else {
            throw new UsernameNotFoundException(token);
        }
    }


}
