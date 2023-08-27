package com.ecommerce.cart;

import org.springframework.stereotype.Service;

@Service
public class CartMapper {

    public CartDTO toDto(Cart entity){
        return new CartDTO(
                entity.getProducts(),
                entity.getTotal(),
                entity.getLastModifiedBy(),
                entity.getLastModifiedAt(),
                entity.getTotalAfterDiscount()
        );
    }
}
