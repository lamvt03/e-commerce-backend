package com.ecommerce.cart.product;

public record CProductDTO(
        Long id,
        Long productId,
        String title,
        String color,
        Double price,
        Integer quantity,
        String poster
) {
}
