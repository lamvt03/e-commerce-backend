package com.ecommerce.product.rating;

import org.springframework.stereotype.Service;

@Service
public class RatingMapper {

    public RatingDTO toDto(Rating entity){
        return new RatingDTO(
                entity.getStar(),
                entity.getComment(),
                entity.getCreatedAt(),
                entity.getUser().getEmail(),
                entity.getUser().getFullname(),
                entity.getLastModifiedAt().isAfter(entity.getCreatedAt())
        );
    }
}
