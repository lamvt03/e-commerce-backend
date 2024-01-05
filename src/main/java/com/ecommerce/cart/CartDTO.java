package com.ecommerce.cart;

import com.ecommerce.cart.product.CProduct;
import com.ecommerce.cart.product.CProductDTO;

import java.time.LocalDateTime;
import java.util.Set;

public record CartDTO(
        Set<CProductDTO> products,

        String couponCode,
        String couponName,
        double total,
        double totalAfterDiscount
) {
}
