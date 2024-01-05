package com.ecommerce.product.brand;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record PBrandDTO(
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
