package com.umbra.umbralink.image;

import com.umbra.umbralink.cloudinary.CloudinaryService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
public class ImageController {


    private final ImageService imageService;

    public ImageController(CloudinaryService cloudinaryService, ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/save")
    public Image saveImage(@RequestParam("file") MultipartFile file, @RequestHeader("Authorization") String token){
        return imageService.saveImage(file, token);
    }
}
