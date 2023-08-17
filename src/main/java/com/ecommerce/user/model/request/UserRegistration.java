package com.ecommerce.user.model.request;

public record UserRegistration(
        String firstName,
        String lastName,
        String email,
        String mobile,
        String password
){

}
