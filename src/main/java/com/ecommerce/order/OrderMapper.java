package com.ecommerce.order;

import com.ecommerce.order.orderProduct.OrderProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderMapper {
    private final OrderProductMapper orderProductMapper;

    public OrderDTO toDto(Order entity){
        return new OrderDTO(
                entity.getProducts().stream()
                        .map(orderProductMapper::toDto)
                        .collect(Collectors.toSet()),
                entity.getStatus().toString(),
                entity.getPaymentIntent(),
                entity.getLastModifiedBy()
        );
    }
}
