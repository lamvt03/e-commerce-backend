package com.ecommerce.order;

public record OrderCreateRequest(
        boolean COD,
        boolean couponApplied
) {
}
