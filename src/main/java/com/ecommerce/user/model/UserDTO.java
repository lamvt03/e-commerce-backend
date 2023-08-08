package com.ecommerce.user.model;

public record UserDTO (
        Long id,
        String firstName,
        String lastName,
        String email,
        String mobile
){ }
