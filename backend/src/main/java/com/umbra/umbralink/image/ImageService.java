package com.umbra.umbralink.image;

import com.umbra.umbralink.cloudinary.CloudinaryService;
import com.umbra.umbralink.user.UserEntity;
import com.umbra.umbralink.user.UserRepository;
import com.umbra.umbralink.security.jwt.JwtService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final CloudinaryService cloudinaryService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public ImageService(ImageRepository imageRepository, CloudinaryService cloudinaryService, JwtService jwtService, UserRepository userRepository) {
        this.imageRepository = imageRepository;
        this.cloudinaryService = cloudinaryService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }


    public Image saveImage(MultipartFile file, String token) {
        Long userId = jwtService.extractId(token.substring(7));
        UserEntity user = userRepository.findById(userId).orElseThrow(()->new UsernameNotFoundException("User not " +
                "found"));


        String photoUrl = cloudinaryService.saveImage(file);
        String[] photoString = photoUrl.split("/");
        String publicId = photoString[photoString.length-1].split("\\.")[0];

        Image image = new Image();
        image.setUrl(photoUrl);
        image.setPublicId(publicId);
        image.setUser(user);

        return imageRepository.save(image);
    }
}
