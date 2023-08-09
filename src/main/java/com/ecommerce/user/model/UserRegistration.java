package com.ecommerce.user.model;

public record UserRegistration(
        String firstName,
        String lastName,
        String email,
        String mobile,
        String password
){

}
