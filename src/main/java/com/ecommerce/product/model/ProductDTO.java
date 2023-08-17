package com.ecommerce.product.model;

import com.ecommerce.product.rating.RatingDTO;

import java.time.LocalDateTime;
import java.util.Set;

public record ProductDTO(
        Long encodeId,
        String title,
        String slug,
        String description,
        double price,
        int quantity,
        String category,
        String brand,
        String color,
        LocalDateTime lastModifiedAt,
        float ratingPoint,
        Set<RatingDTO> ratings
) {
}
