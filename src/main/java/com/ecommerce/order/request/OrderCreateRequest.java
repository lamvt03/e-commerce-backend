package com.ecommerce.order.request;

public record OrderCreateRequest(
        int method,
        boolean couponApplied
) {
}
