package com.ecommerce.product.model.request;

import java.util.Set;

public record ProductCreateRequest(
        String title,
        String description,
        Long brandId,
        Long price,
        Integer quantity,
        Long categoryId,
        Integer sold,
        Set<Long> colorIds
) {
}
