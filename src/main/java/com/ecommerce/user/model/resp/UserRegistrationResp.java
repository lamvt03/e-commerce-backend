package com.ecommerce.user.model.resp;

public record UserRegistrationResp(
        String firstName,
        String lastName,
        String email,
        String mobile
) {
}
