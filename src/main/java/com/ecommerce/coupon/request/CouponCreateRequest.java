package com.ecommerce.coupon.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CouponCreateRequest(

        @NotBlank(message = "name is mandatory")
        String name,

        @NotNull(message = "life cycle is mandatory")
        @Positive(message = "life cycle must be greater than zero")
        Integer lifeCycle,

        @NotNull(message = "discount is mandatory")
        @Positive(message = "discount must be greater than zero")
        Integer discount,

        @NotNull(message = "quantity is mandatory")
        @Positive(message = "quantity must be greater than zero")
        Integer quantity
) {
}
