package com.ecommerce.cart.product;


import com.ecommerce.cart.Cart;
import com.ecommerce.product.model.Product;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "cart_product")
public class CProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private Long colorId;

    private int quantity;

    private double price;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @CreatedDate
    private LocalDateTime createdAt;
}
