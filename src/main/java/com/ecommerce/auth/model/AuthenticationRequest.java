package com.ecommerce.auth.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(
        @NotBlank(message = "email is mandatory")
        @Email(message = "email not valid")
        String email,

        @NotBlank(message = "password is mandatory")
        String password
){}
