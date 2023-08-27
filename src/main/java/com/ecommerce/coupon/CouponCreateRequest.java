package com.ecommerce.coupon;

public record CouponCreateRequest(
        String name,
        int lifeCycle,
        int discount
) {
}
