package com.ecommerce.order;

import com.ecommerce.order.orderPaymentIntent.OrderPaymentIntent;
import com.ecommerce.order.orderProduct.OrderProductDTO;

import java.util.Set;

public record OrderDTO(
        Set<OrderProductDTO> products,
        String status,
        OrderPaymentIntent orderPaymentIntent,
        String orderBy
) {
}
