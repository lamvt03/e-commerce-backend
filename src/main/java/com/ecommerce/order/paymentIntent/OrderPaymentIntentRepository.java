package com.ecommerce.order.paymentIntent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPaymentIntentRepository extends JpaRepository<OrderPaymentIntent, Long> {
    OrderPaymentIntent findFirstById(Long id);
}
