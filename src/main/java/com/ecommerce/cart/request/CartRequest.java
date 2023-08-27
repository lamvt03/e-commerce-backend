package com.ecommerce.cart.request;

import java.util.List;

public record CartRequest(
        List<CartItemRequest> cart
) {
}
