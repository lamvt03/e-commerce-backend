package com.ecommerce.cart.model;

public record CartCoupon(
        String code,
        String name,
        String discount
) {
}
