package com.ecommerce.cart.request;

public record CartProductRequest(
        Long productId,
        int quantity,
        Long colorId
) {
}
