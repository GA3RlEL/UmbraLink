package com.umbra.umbralink.image;

import com.umbra.umbralink.cloudinary.CloudinaryService;
import com.umbra.umbralink.user.UserEntity;
import com.umbra.umbralink.user.UserRepository;
import com.umbra.umbralink.security.jwt.JwtService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final CloudinaryService cloudinaryService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public ImageService(ImageRepository imageRepository, CloudinaryService cloudinaryService, JwtService jwtService,
            UserRepository userRepository, SimpMessagingTemplate simpMessagingTemplate) {
        this.imageRepository = imageRepository;
        this.cloudinaryService = cloudinaryService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public Image saveImage(MultipartFile file, String token) {
        Long userId = jwtService.extractId(token.substring(7));
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not " +
                "found"));

        if (user.getProfileImage() != null) {
            String publicId = user.getProfileImage().getPublicId();
            if (publicId != null && !publicId.isEmpty()) {
                cloudinaryService.deleteImage(publicId);
            }
            user.setProfileImage(null);
            userRepository.save(user);
        }

        String[] photoData = cloudinaryService.saveImage(file).split(";");
        String photoUrl = photoData[0];
        String publicId = photoData[1];

        Image image = new Image();
        image.setUrl(photoUrl);
        image.setPublicId(publicId);
        image.setUser(user);

        user.setProfileImage(image);

        Map<String, Object> message = new HashMap<>();
        message.put("userId", user.getId());
        message.put("imageUrl", photoUrl);
        simpMessagingTemplate.convertAndSend("/photoUpdate", message);

        return userRepository.save(user).getProfileImage();
    }

    public Image saveImageToConversation(MultipartFile file, String token) {
        Long userId = jwtService.extractId(token.substring(7));
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not " +
                "found"));

        String[] photoData = cloudinaryService.saveImage(file).split(";");
        String photoUrl = photoData[0];
        String publicId = photoData[1];

        Image image = new Image();
        image.setPhotoMessageUser(user);
        image.setUrl(photoUrl);
        image.setPublicId(publicId);
        return image;
    }
}
