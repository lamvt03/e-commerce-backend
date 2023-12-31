package com.ecommerce.product.model;

import com.ecommerce.common.AbstractEntity;
import com.ecommerce.product.brand.PBrand;
import com.ecommerce.product.category.PCategory;
import com.ecommerce.product.color.PColor;
import com.ecommerce.product.image.PImage;
import com.ecommerce.product.rating.Rating;
import com.ecommerce.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashSet;
import java.util.Set;

@Setter @Getter
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

    @ManyToOne
    @JoinColumn(name = "p_category_id")
    private PCategory category;

    @ManyToOne
    @JoinColumn(name = "p_brand_id")
    private PBrand brand;

    private int sold;
    private float ratingPoint;

    @OneToMany
    @JoinColumn(name = "product_id")
    private Set<PColor> colors = new HashSet<>();

}
