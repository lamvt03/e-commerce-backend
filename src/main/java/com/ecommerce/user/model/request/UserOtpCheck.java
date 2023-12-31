package com.ecommerce.user.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserOtpCheck(
        @NotBlank(message = "email is mandatory")
        @Email(message = "email not valid")
        String email,

        @NotBlank(message = "email is mandatory")
        @Size(min = 6, max = 6, message = "otp length must be 6")
        String otp
) {
}
