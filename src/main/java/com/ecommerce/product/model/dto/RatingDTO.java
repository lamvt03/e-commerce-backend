package com.ecommerce.product.model.dto;

public record RatingDTO(
        String email,
        byte star,
        String comment
) {
}
