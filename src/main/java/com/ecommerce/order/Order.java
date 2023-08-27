package com.ecommerce.order;

import com.ecommerce.common.AbstractEntity;
import com.ecommerce.order.orderPaymentIntent.OrderPaymentIntent;
import com.ecommerce.order.orderProduct.OrderProduct;
import com.ecommerce.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "_order")
public class Order extends AbstractEntity {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Set<OrderProduct> products = new HashSet<>();

    private OrderStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private OrderPaymentIntent paymentIntent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User orderBy;

}
