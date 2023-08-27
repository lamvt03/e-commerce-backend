package com.ecommerce.product.color;

import java.time.LocalDateTime;

public record PColorDTO(
        String name,
        LocalDateTime lastModifiedAt
) {
}
