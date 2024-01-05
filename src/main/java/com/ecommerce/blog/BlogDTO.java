package com.ecommerce.blog;

import java.time.LocalDateTime;

public record BlogDTO(
        String title,
        String description,
        String category,
        LocalDateTime lastModifiedAt
) {
}
