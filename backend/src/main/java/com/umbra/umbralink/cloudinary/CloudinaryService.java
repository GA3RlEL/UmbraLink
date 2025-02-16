package com.umbra.umbralink.cloudinary;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String saveImage(MultipartFile file){
        try{
            Map result =  cloudinary.uploader().upload(file.getBytes(),new HashMap<>());
            return result.get("secure_url") + ";"+result.get("public_id");
        }catch (Exception e){
            throw new RuntimeException("Upload filed: " + e.getMessage());
        }
    }

    public void deleteImage(String publicId){
        try{
            Map result = cloudinary.uploader().destroy(publicId,new HashMap<>());
            if(!"ok".equals(result.get("result"))){
                throw new RuntimeException("Failed to delete image from Cloudinary");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error occur during deletion of image: " + e.getMessage());
        }
    }
}
