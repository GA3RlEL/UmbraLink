package com.umbra.umbralink.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.umbra.umbralink.dto.*;
import com.umbra.umbralink.dto.auth.AuthResponseDto;
import com.umbra.umbralink.dto.auth.RegisterRequestDto;
import com.umbra.umbralink.dto.findUsers.FindUsersDto;
import com.umbra.umbralink.conversation.Conversation;
import com.umbra.umbralink.dto.updateUser.UpdatePasswordPayloadDto;
import com.umbra.umbralink.dto.updateUser.UpdateUsernameDto;
import com.umbra.umbralink.enums.UserStatus;
import com.umbra.umbralink.conversation.ConversationRepository;
import com.umbra.umbralink.error.GeneralError;
import com.umbra.umbralink.message.Message;
import com.umbra.umbralink.security.UserDetailService;
import com.umbra.umbralink.security.jwt.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ConversationRepository conversationRepository;
    private final UserDetailService userDetailService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, ConversationRepository conversationRepository, UserDetailService userDetailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.conversationRepository = conversationRepository;
        this.userDetailService = userDetailService;
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
            userResponseDto.setId(userEntity.getId());
            userResponseDto.setUsername(userEntity.getUsername());
            userResponseDto.setEmail(userEntity.getEmail());
            userResponseDto.setStatus(userEntity.getStatus());
            if(userEntity.getProfileImage()!=null){
                userResponseDto.setImageUrl(userEntity.getProfileImage().getUrl());
            }else{
                userResponseDto.setImageUrl("");
            }

            List<ConversationDto> conversationDtos = conversations.stream().map(c -> {
                ConversationDto dto = new ConversationDto();

                if(!c.getMessages().isEmpty()){
                    Message lastMessage = c.getMessages().getLast();
                    dto.setLastMessage(lastMessage.getContent());
                    dto.setState(lastMessage.getState());
                    dto.setIsLastMessageSender(Objects.equals(lastMessage.getSender().getId(), userEntity.getId()));
                    dto.setLastMessageTimestamp(lastMessage.getCreatedAt());
                    dto.setLastMessageUpdateTimestamp(lastMessage.getUpdatedAt());
                }else{
                    dto.setLastMessage("");
                    dto.setState(null);
                    dto.setIsLastMessageSender(false);
                    dto.setLastMessageTimestamp(null);
                    dto.setLastMessageUpdateTimestamp(null);
                }
                UserEntity otherUser = Objects.equals(c.getUser1(),userEntity.getId()) ?
                        userRepository.findById(c.getUser2()).get() : userRepository.findById(c.getUser1()).get();
                dto.setConversationId(c.getId());
                dto.setOtherUser(otherUser.getUsername());
                dto.setOtherUserId(otherUser.getId());
                dto.setStatus(otherUser.getStatus());
                if (otherUser.getProfileImage() != null) {
                    dto.setImageUrl(otherUser.getProfileImage().getUrl());
                } else {
                    dto.setImageUrl("");
                }


                return dto;
            }).toList();
            userResponseDto.setConversations(conversationDtos);
            return userResponseDto;
        } else {
            throw new UsernameNotFoundException("" + id);
        }

    }

    @Override
    public UserStatusDto changeUserStatus(Long userId, UserStatus userStatus) {
      Optional<UserEntity> oUser = userRepository.findById(userId);
      if(oUser.isPresent())
      {
          UserEntity user = oUser.get();
          user.setStatus(userStatus);
          UserEntity savedUser = userRepository.save(user);
          UserStatusDto dto = new UserStatusDto();
          dto.setId(savedUser.getId());
          dto.setStatus(savedUser.getStatus());
          return dto;
      }else{
          throw new UsernameNotFoundException("User with id" + userId +" was not found in database");
      }
    }

    @Override
    public List<FindUsersDto> findUsers(String data) {
        List<UserEntity> usersFound = userRepository.findByEmailContainingIgnoreCaseOrUsernameContainingIgnoreCase(data,data);
        List<FindUsersDto> dto = new ArrayList<>();
        if(usersFound.isEmpty()){
            return dto;
        }
        usersFound.forEach(user->{
            FindUsersDto findUser = new FindUsersDto();
            findUser.setId(user.getId());
            findUser.setUsername(user.getUsername());
            if(user.getProfileImage()!=null){
                findUser.setImageUrl(user.getProfileImage().getUrl());
            }else{
                findUser.setImageUrl(null);
            }
            dto.add(findUser);
        });
        return dto;
    }

    @Override
    public UpdateUsernameDto updateUsername(String newUsername, String token) {
        Long id = jwtService.extractId(token.substring(7));
        UserEntity user = userRepository.findById(id).orElseThrow(()->new UsernameNotFoundException("User was not " +
                "found"));
        user.setUsername(newUsername);
        UserEntity savedUser = userRepository.save(user);
        UpdateUsernameDto dto = new UpdateUsernameDto();
        dto.setId(savedUser.getId());
        dto.setNewUsername(savedUser.getUsername());

        return dto;
    }

    @Override
    public AuthResponseDto updatePassword(String token, UpdatePasswordPayloadDto payload) {
        Long id = jwtService.extractId(token.substring(7));

        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User was not found"));

        if (!passwordEncoder.matches(payload.oldPassword(), user.getPassword())) {
            throw new GeneralError("Invalid old password");
        }

        user.setPassword(passwordEncoder.encode(payload.newPassword()));
        userRepository.save(user);

        UserDetails userDetails = userDetailService.loadUserByUsername(user.getEmail());
        String jwtToken = jwtService.generateToken(userDetails);

        return new AuthResponseDto(jwtToken);
    }
}
