package com.ecommerce.product.model.entity;

import com.ecommerce.common.AbstractEntity;
import com.ecommerce.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
@Table(name = "rating")
public class Rating extends AbstractEntity {

    private byte star;
    private String comment;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User postedBy;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
