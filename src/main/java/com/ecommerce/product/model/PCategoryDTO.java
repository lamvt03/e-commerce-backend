package com.ecommerce.product.model;

import java.time.LocalDateTime;

public record PCategoryDTO(
        String title,
        LocalDateTime lastModifiedAt
) {
}
