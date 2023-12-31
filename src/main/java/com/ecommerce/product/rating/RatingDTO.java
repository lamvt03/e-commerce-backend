package com.ecommerce.product.rating;

import java.time.LocalDateTime;

public record RatingDTO(
        byte star,
        String comment,
        LocalDateTime createdAt,
        String email,
        String name,
        Boolean isModified
) {
}
