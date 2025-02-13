package com.umbra.umbralink.controller;

import com.umbra.umbralink.cloudinary.CloudinaryService;
import com.umbra.umbralink.model.Image;
import com.umbra.umbralink.service.ImageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
public class ImageController {


    private final ImageService imageService;

    public ImageController(CloudinaryService cloudinaryService, ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public Image saveImage(@RequestParam("file") MultipartFile file, @RequestHeader("Authorization") String token){
        return imageService.saveImage(file, token);
    }
}
