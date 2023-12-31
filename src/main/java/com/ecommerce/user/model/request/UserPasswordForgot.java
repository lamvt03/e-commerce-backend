package com.ecommerce.user.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserPasswordForgot (
        @NotBlank(message = "email is mandatory")
        @Email(message = "email not valid")
        String email
){
}
