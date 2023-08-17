package com.ecommerce.product.rating;

import org.springframework.stereotype.Service;

@Service
public class RatingMapper {

    public RatingDTO toDto(Rating entity){
        return new RatingDTO(
                entity.getPostedBy().getEmail(),
                entity.getStar(),
                entity.getComment()
        );
    }
}
