package com.ecommerce.product.category;

import java.time.LocalDateTime;

public record PCategoryDTO(
        Long id,
        String name,
        String code,
        String link,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime lastModifiedAt,
        String lastModifiedBy
) {
}
