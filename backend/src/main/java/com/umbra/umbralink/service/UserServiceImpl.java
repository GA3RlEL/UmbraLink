package com.umbra.umbralink.service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import com.umbra.umbralink.dto.UserResponseDto;
import com.umbra.umbralink.model.Photo;
import com.umbra.umbralink.security.jwt.JwtService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.umbra.umbralink.dto.RegisterRequestDto;

import com.umbra.umbralink.model.UserEntity;
import com.umbra.umbralink.repository.UserRepository;

import javax.imageio.ImageIO;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
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

        if (registerRequest.getPhoto() != null) {
            String base64String = registerRequest.getPhoto();
            if (base64String.contains(",")) {
                try {
                    byte[] decodedPhoto = Base64.getDecoder().decode(base64String.split(",")[1]);
                    decodedPhoto = resizeImage(decodedPhoto, 200, 200);
                    Photo photo = new Photo();
                    photo.setUser(user);
                    photo.setPhoto(decodedPhoto);
                    user.setPhoto(photo);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return userRepository.save(user);
    }

    private byte[] resizeImage(byte[] decodedPhoto, int width, int height) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(decodedPhoto);
        BufferedImage originalImage = ImageIO.read(bais);

        Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "jpg", baos);
        baos.flush();
        return baos.toByteArray();
    }

    @Override
    public UserResponseDto findByToken(String token) {
        Long id = jwtService.extractId(token);
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            UserResponseDto userResponseDto = new UserResponseDto();
            userResponseDto.setUsername(userEntity.getUsername());
            userResponseDto.setConversations(userEntity.getConversations());
            userResponseDto.setPhoto(userEntity.getPhoto());
            userResponseDto.setEmail(userEntity.getEmail());
            userResponseDto.setMessages(userEntity.getMessages());

            return userResponseDto;
        } else {
            throw new UsernameNotFoundException("" + id);
        }

    }


}
