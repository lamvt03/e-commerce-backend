package com.ecommerce.util.model;

public record FilterDTO(
        String brand,
        String category,
        Double maxPrice,
        Double minPrice
) {
}
