package com.ecommerce.order.orderPaymentIntent;

import com.ecommerce.common.AbstractEntity;
import com.ecommerce.order.OrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "order_payment_intent")
public class OrderPaymentIntent extends AbstractEntity {

    private String method;
    private double amount;
    private OrderStatus status;
    private String currency;

}
