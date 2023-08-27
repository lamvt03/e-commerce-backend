package com.ecommerce.product.brand;

import java.time.LocalDateTime;

public record PBrandDTO(
        String name,
        LocalDateTime lastModifiedAt
) {
}
