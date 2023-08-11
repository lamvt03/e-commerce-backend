package com.ecommerce.user.model;

public record UserPasswordReset(
        String otp,
        String email,
        String newPassword
) {
}
