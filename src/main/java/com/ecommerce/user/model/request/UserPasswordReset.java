package com.ecommerce.user.model.request;

public record UserPasswordReset(
        String otp,
        String email,
        String newPassword
) {
}
