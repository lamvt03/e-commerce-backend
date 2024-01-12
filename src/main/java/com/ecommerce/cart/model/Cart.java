package com.ecommerce.cart.model;

import com.ecommerce.user.model.User;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long couponId;
    private double total;
    private double totalAfterDiscount;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
