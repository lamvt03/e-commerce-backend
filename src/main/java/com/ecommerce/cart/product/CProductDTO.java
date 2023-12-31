package com.ecommerce.cart.product;

public record CProductDTO(
        Long id,
        String title,
        String color,
        Double price,
        Integer quantity,
        String poster
) {
}
