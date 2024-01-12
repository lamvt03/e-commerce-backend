package com.ecommerce.exception;

public record ErrResponse(
        Object msg,
        Integer code,
        String status
) {
}
