package com.ecommerce.cart;

import com.ecommerce.cart.cartProduct.CartProduct;

import java.time.LocalDateTime;
import java.util.Set;

public record CartDTO(
        Set<CartProduct> products,
        double total,
        String orderBy,
        LocalDateTime lastModifiedAt,
        double totalAfterDiscount
) {
}
