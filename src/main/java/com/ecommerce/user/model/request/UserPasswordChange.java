package com.ecommerce.user.model.request;

public record UserPasswordChange(
        String oldPassword,
        String newPassword

) {
}
