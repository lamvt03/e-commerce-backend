package com.ecommerce.product.color.request;

import jakarta.validation.constraints.NotBlank;

public record PColorCreateRequest(
        @NotBlank(message = "name is mandatory")
        String name
) {
}
