package com.ecommerce.product.model;

import com.ecommerce.product.color.PColorDTO;
import com.ecommerce.product.image.PImageDTO;
import com.ecommerce.product.rating.RatingDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public record ProductDTO(
        Long id,
        String title,
        String slug,
        String description,
        double price,
        int quantity,
        int sold,
        String category,
        String brand,
        Set<String> colors,
        LocalDateTime lastModifiedAt,
        float ratingPoint,
        Boolean isLiked,
        Set<RatingDTO> ratings,
        List<PImageDTO> images
) {
}
