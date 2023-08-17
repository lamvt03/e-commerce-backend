package com.ecommerce.product.model;

import com.ecommerce.common.AbstractEntity;
import com.ecommerce.product.rating.Rating;
import com.ecommerce.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "product")
public class Product extends AbstractEntity {

    private String title;
    private String slug;
    private String description;
    private double price;
    private int quantity;
    private String category;
    private String brand;
    private int sold;
    private List<String> images = new ArrayList<>();
    private String color;
    private float ratingPoint;

    @ManyToMany(mappedBy = "wishlist")
    @JsonIgnore
    private Set<User> buyers = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<Rating> ratings = new HashSet<>();
}
