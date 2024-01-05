package com.ecommerce.order;

import com.ecommerce.order.model.Order;
import com.ecommerce.order.model.OrderDTO;
import com.ecommerce.order.paymentIntent.OrderPaymentIntentRepository;
import com.ecommerce.order.product.OProduct;
import com.ecommerce.order.product.OProductMapper;
import com.ecommerce.order.product.OProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderMapper {
    private final OProductRepository oProductRepository;
    private final OProductMapper oProductMapper;

    private final OrderPaymentIntentRepository paymentIntentRepository;

    public OrderDTO toDto(Order entity){
        List<OProduct> oProducts = oProductRepository.findAllByOrderId(entity.getId());
        return new OrderDTO(
                entity.getId(),
                oProducts.stream()
                        .map(oProductMapper::toDto)
                        .collect(Collectors.toSet()),
                entity.getStatus().toString(),
                paymentIntentRepository.findFirstById(entity.getPaymentIntentId()),
                entity.getCreatedAt()
        );
    }
}
