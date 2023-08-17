package com.ecommerce.product.model.dto;

public record FilterDTO(
        String brand,
        String category,
        Double maxPrice,
        Double minPrice
) {
}
