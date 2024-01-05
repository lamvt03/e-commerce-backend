package com.ecommerce.order.model;

import com.ecommerce.order.paymentIntent.OrderPaymentIntent;
import com.ecommerce.order.product.OProductDTO;

import java.time.LocalDateTime;
import java.util.Set;

public record OrderDTO(
        Long id,
        Set<OProductDTO> products,
        String status,
        OrderPaymentIntent paymentIntent,
        LocalDateTime createdAt
) {
}
