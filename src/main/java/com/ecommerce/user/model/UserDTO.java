package com.ecommerce.user.model;

import com.ecommerce.product.model.ProductDTO;

import java.util.Set;

public record UserDTO (
        Long id,
        String firstName,
        String lastName,
        String email,
        String mobile,
        Set<ProductDTO> wishlist
){ }
