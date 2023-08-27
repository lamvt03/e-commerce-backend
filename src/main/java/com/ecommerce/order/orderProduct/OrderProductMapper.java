package com.ecommerce.order.orderProduct;

import org.springframework.stereotype.Service;

@Service
public class OrderProductMapper {

    public OrderProductDTO toDto(OrderProduct entity){
        return new OrderProductDTO(
                entity.getProduct().getId(),
                entity.getCount(),
                entity.getColor()
        );
    }
}
