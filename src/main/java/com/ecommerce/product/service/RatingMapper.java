package com.ecommerce.product.service;

import com.ecommerce.product.model.dto.RatingDTO;
import com.ecommerce.product.model.entity.Rating;
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
