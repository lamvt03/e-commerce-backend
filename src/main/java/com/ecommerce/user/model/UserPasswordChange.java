package com.ecommerce.user.model;

public record UserPasswordChange(
        String oldPassword,
        String newPassword

) {
}
