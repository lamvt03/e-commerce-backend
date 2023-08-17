package com.ecommerce.product.rating;

public record RatingDTO(
        String email,
        byte star,
        String comment
) {
}
