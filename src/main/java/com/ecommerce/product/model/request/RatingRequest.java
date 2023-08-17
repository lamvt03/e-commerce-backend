package com.ecommerce.product.model.request;

public record RatingRequest(
        byte star,
        String comment,
        Long productId
) {
}
