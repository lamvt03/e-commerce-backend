package com.ecommerce.product.color;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record PColorDTO(
        Long id,
        @NotBlank(message = "name is mandatory")
        String name,

        String code,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime lastModifiedAt,
        String lastModifiedBy
) {
}
