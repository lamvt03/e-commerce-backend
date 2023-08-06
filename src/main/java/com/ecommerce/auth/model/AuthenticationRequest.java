package com.ecommerce.auth.model;

public record AuthenticationRequest(
        String email,
        String password
){}
