package com.ecommerce.product.service;

import com.ecommerce.product.model.entity.Product;
import com.ecommerce.product.model.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductMapper {
    private final RatingMapper ratingMapper;
    public ProductDTO toDto(Product entity){
        return new ProductDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getSlug(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getQuantity(),
                entity.getCategory(),
                entity.getBrand(),
                entity.getColor(),
                entity.getLastModifiedAt(),
                entity.getRatingPoint(),
                entity.getRatings().stream()
                        .map(ratingMapper::toDto)
                        .collect(Collectors.toSet())
        );
    }
}
