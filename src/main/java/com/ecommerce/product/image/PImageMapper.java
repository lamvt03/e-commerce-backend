package com.ecommerce.product.image;

import org.springframework.stereotype.Service;

@Service
public class PImageMapper {

    public PImageDTO toDto(PImage entity){
        return new PImageDTO(
                entity.getUrl(),
                entity.getAssetId(),
                entity.getPublicId()
        );
    }
}
