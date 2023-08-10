package com.ecommerce.product.model;

import com.ecommerce.common.AbstractEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
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
    private List<String> ratings = new ArrayList<>();
    private boolean isDeleted = false;

}
