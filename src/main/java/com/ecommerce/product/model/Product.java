package com.ecommerce.product.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue
    private Long id;

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
