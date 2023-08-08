package com.ecommerce.user.model;

public record UserRegistration(
        Long id,
        String firstName,
        String lastName,
        String email,
        String mobile,
        String password
){

}
