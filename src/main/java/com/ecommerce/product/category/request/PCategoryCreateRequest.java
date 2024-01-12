package com.ecommerce.product.category.request;

import jakarta.validation.constraints.NotBlank;

public record PCategoryCreateRequest(
        @NotBlank(message = "name is mandatory")
        String name
) {
}
