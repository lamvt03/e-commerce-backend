package com.ecommerce.cart.request;

public record CProductAddRequest(
        Long productId,
        int quantity,
        Long colorId
) {
}
