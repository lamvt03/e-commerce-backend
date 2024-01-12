package com.ecommerce.cart.model;

import com.ecommerce.cart.product.CProduct;
import com.ecommerce.cart.product.CProductDTO;

import java.time.LocalDateTime;
import java.util.Set;

public record CartDTO(
        Set<CProductDTO> products,

        CartCoupon coupon,
        double total,
        double totalAfterDiscount
) {
}
