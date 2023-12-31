package com.ecommerce.user.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistration(

        @NotBlank(message = "first name is mandatory")
        String firstName,

        @NotBlank(message = "last name is mandatory")
        String lastName,

        @NotBlank(message = "email is mandatory")
        @Email(message = "email not valid")
        String email,

        @NotBlank(message = "mobile is mandatory")
        String mobile,

        @NotBlank(message = "password is mandatory")
        @Size(min = 8, message = "password must have at least 8 characters")
        String password
){
}
