package com.ecommerce.cart.request;

public record CartItemRequest(
        Long productId,
        int count,
        String color
) {
}
