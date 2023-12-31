package com.ecommerce.auth.model;

public record AuthenticationResponse(
        String accessToken,
        String refreshToken
){ }
