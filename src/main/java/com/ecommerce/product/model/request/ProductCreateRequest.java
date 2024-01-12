package com.ecommerce.product.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Set;

public record ProductCreateRequest(
        @NotBlank(message = "title is mandatory")
        String title,

        @NotBlank(message = "description is mandatory")
        String description,

        @NotNull(message = "product brand id is mandatory")
        @Positive(message = "product brand id must be greater than zero")
        Long brandId,

        @NotNull(message = "price is mandatory")
        @Positive(message = "price must be greater than zero")
        Long price,

        @NotNull(message = "quantity is mandatory")
        @Positive(message = "quantity must be greater than zero")
        Integer quantity,

        @NotNull(message = "product category id is mandatory")
        @Positive(message = "product category id must be greater than zero")
        Long categoryId,

        @NotNull(message = "sold is mandatory")
        Integer sold,

        @NotEmpty(message = "color id array must contain at least 1 id")
        Set<Long> colorIds
) {
}
