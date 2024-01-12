package com.ecommerce.cart.request;

import java.util.List;

public record CProductDeleteRequest(
        List<Long> ids
) {
}
