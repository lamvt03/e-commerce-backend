package com.ecommerce.order;

import com.ecommerce.cart.Cart;
import com.ecommerce.cart.CartRepository;
import com.ecommerce.cart.product.CProduct;
import com.ecommerce.cart.product.CProductRepository;
import com.ecommerce.order.model.Order;
import com.ecommerce.order.model.OrderDTO;
import com.ecommerce.order.model.OrderMethod;
import com.ecommerce.order.model.OrderStatus;
import com.ecommerce.order.paymentIntent.OrderPaymentIntent;
import com.ecommerce.order.paymentIntent.OrderPaymentIntentRepository;
import com.ecommerce.order.product.OProductMapper;
import com.ecommerce.order.product.OProductRepository;
import com.ecommerce.order.request.OrderCreateRequest;
import com.ecommerce.product.ProductRepository;
import com.ecommerce.product.ProductService;
import com.ecommerce.product.model.Product;
import com.ecommerce.util.PaginationService;
import com.ecommerce.util.model.PaginationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private final OProductRepository oProductRepository;
    private final OProductMapper oProductMapper;

    private final OrderPaymentIntentRepository orderPaymentIntentRepository;

    private final CartRepository cartRepository;
    private final CProductRepository cProductRepository;

    private final PaginationService paginationService;

    public OrderDTO createOrder(Long userId, OrderCreateRequest request) {
        if(request.method() != OrderMethod.CASH_ON_DELIVERY.getCode())
            throw new RuntimeException("Unsupported payment method");

        Cart cart = cartRepository.findByUser_Id(userId)
                .orElseThrow();
        double finalAmount = request.couponApplied() ? cart.getTotalAfterDiscount() : cart.getTotal();

        OrderPaymentIntent paymentIntent = orderPaymentIntentRepository.save(
                OrderPaymentIntent.builder()
                        .method(OrderMethod.CASH_ON_DELIVERY)
                        .amount(finalAmount)
                        .currency("USD")
                        .build()
        );
        Order order = orderRepository.save(
                Order.builder()
                        .status(OrderStatus.PROCESSING)
                        .paymentIntentId(paymentIntent.getId())
                        .userId(userId)
                        .build()
        );

        List<CProduct> products = cProductRepository.findAllByCart_Id(cart.getId());
        products.forEach(cProduct -> {
                oProductRepository.save(oProductMapper.toEntity(cProduct, order.getId()));

                Product product = productRepository.findById(cProduct.getProductId())
                        .orElseThrow();
                product.setQuantity(product.getQuantity() - cProduct.getQuantity());
                product.setSold(product.getSold() + cProduct.getQuantity());
                productRepository.save(product);
            }
        );

        return orderMapper.toDto(order);
    }

    public List<OrderDTO> getOrders(Long userId, PaginationDTO pagination) {
        Pageable pageable = paginationService.getPageable(pagination);
        return orderRepository.findByUserId(userId, pageable).stream()
                .map(orderMapper::toDto)
                .toList();
    }
}
