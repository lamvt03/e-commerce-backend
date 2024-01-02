package com.ecommerce.coupon;

import java.time.LocalDateTime;

public record CouponDTO(
        Long id,
        String name,
        LocalDateTime expiryAt,
        int discount,
        LocalDateTime createdAt,
        LocalDateTime lastModifiedAt
) {
}
