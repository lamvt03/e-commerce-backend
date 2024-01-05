package com.ecommerce.product.category;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record PCategoryDTO(
        Long id,
        @NotBlank(message = "name is mandatory")
        String name,
        String code,
        String link,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime lastModifiedAt,
        String lastModifiedBy
) {
}
