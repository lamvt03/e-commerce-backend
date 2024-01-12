package com.ecommerce.product.brand;

import java.time.LocalDateTime;

public record PBrandDTO(
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
