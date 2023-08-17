package com.ecommerce.user.model;

import com.ecommerce.product.model.dto.ProductDTO;

import java.util.Set;

public record UserDTO (
        Long encodeId,
        String firstName,
        String lastName,
        String email,
        String mobile,
        Set<ProductDTO> wishlist
){ }
