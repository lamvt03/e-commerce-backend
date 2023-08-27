package com.ecommerce.product.category;

import java.time.LocalDateTime;

public record PCategoryDTO(
        String name,
        LocalDateTime lastModifiedAt
) {
}
