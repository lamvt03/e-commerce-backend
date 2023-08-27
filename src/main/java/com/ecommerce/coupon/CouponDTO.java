package com.ecommerce.coupon;

import java.time.LocalDateTime;

public record CouponDTO(
        String name,
        LocalDateTime expiryAt,
        int discount,
        LocalDateTime createdAt
) {
}
