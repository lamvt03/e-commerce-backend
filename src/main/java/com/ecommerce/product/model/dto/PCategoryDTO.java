package com.ecommerce.product.model.dto;

import java.time.LocalDateTime;

public record PCategoryDTO(
        String title,
        LocalDateTime lastModifiedAt
) {
}
