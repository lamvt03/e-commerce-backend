package com.ecommerce.order.product;

public record OProductDTO(
        Long id,
        Long productId,
        String title,
        String color,
        Double price,
        Integer quantity,
        String poster
) {
}
