package com.ecommerce.util;

public record FilterDTO(
        String brand,
        String category,
        Double maxPrice,
        Double minPrice
) {
}
