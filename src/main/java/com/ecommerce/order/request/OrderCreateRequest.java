package com.ecommerce.order.request;

import java.util.List;

public record OrderCreateRequest(
        int method,
        boolean couponApplied
) {
}
