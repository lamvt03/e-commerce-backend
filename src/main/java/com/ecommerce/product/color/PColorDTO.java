package com.ecommerce.product.color;

import java.time.LocalDateTime;

public record PColorDTO(
        Long id,

        String name,

        String code,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime lastModifiedAt,
        String lastModifiedBy
) {
}
