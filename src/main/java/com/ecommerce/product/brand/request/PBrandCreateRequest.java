package com.ecommerce.product.brand.request;

import jakarta.validation.constraints.NotBlank;

public record PBrandCreateRequest(
        @NotBlank(message = "name is mandatory")
        String name
) {
}
