package com.ecommerce.order.orderProduct;

public record OrderProductDTO(
        Long productId,
        int count,
        String color
) {
}
