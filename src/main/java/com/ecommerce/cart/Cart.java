package com.ecommerce.cart;

import com.ecommerce.cart.product.CProduct;
import com.ecommerce.common.AbstractEntity;
import com.ecommerce.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Builder
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private double total;
    private double totalAfterDiscount;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
