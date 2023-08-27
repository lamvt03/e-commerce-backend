package com.ecommerce.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ecommerce.product.image.PImage;
import com.ecommerce.product.model.Product;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class ImageService {

    private final Cloudinary cloudinary;

    public List<PImage> uploadProductImages(MultipartFile[] images){
        List<PImage> imagesList = new ArrayList<>();
        for(MultipartFile image: images){
            PImage imageEntity = uploadImageToCloudinary(image);
            imagesList.add(imageEntity);
        }
        return imagesList;
    }
    
    private PImage uploadImageToCloudinary(MultipartFile image){
        if(!image.isEmpty() && isImage(image)){
            byte[] imageBytes = getResizedAndFormattedImageBytes(image);
            try{
                Map uploadResult = cloudinary.uploader()
                        .upload(imageBytes, ObjectUtils.emptyMap());

                return new PImage(
                        uploadResult.get("url").toString(),
                        uploadResult.get("asset_id").toString(),
                        uploadResult.get("public_id").toString()
                    );
            }catch (IOException ex) {}
        }
        else
            throw new RuntimeException("Unsupported file format");
        return null;
    }
    private boolean isImage(MultipartFile file) {
        return file.getContentType() != null && file.getContentType().startsWith("image");
    }
    private byte[] getResizedAndFormattedImageBytes(MultipartFile image){
        ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
        try{
            Thumbnails.of(new ByteArrayInputStream(image.getBytes()))
                    .size(300, 300)
                    .outputFormat("jpg")
                    .outputQuality(0.9)
                    .toOutputStream(resultStream);
        }catch (IOException ex){}
        return resultStream.toByteArray();
    }

    public void deleteProductImage(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
