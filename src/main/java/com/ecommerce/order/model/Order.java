package com.ecommerce.order.model;

import com.ecommerce.common.AbstractEntity;
import com.ecommerce.order.paymentIntent.OrderPaymentIntent;
import com.ecommerce.order.product.OProduct;
import com.ecommerce.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private OrderStatus status;

    private Long paymentIntentId;

    private Long userId;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    protected LocalDateTime createdAt;
}
