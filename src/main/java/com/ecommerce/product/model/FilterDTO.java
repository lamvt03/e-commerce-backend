package com.ecommerce.product.model;

public record FilterDTO(
        String brand,
        String category,
        Double maxPrice,
        Double minPrice
) {
}
