package com.ecommerce.coupon.request;

public record CouponCreateRequest(
        String name,
        int lifeCycle,
        int discount,
        int quantity
) {
}
