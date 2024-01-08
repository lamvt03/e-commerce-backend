package com.ecommerce.cart.request;

import java.util.List;

public record DeleteCProductRequest(
        List<Long> ids
) {
}
