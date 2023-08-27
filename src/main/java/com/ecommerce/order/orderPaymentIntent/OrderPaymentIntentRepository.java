package com.ecommerce.order.orderPaymentIntent;

import com.ecommerce.order.orderPaymentIntent.OrderPaymentIntent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPaymentIntentRepository extends JpaRepository<OrderPaymentIntent, Long> {
}
