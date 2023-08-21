package com.ecommerce.product;

import com.ecommerce.product.image.PImageMapper;
import com.ecommerce.product.model.Product;
import com.ecommerce.product.model.ProductDTO;
import com.ecommerce.product.rating.RatingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductMapper {
    private final RatingMapper ratingMapper;
    private final PImageMapper pImageMapper;
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
                        .collect(Collectors.toSet()),
                entity.getImages().stream()
                        .map(pImageMapper::toDto)
                        .collect(Collectors.toList())
        );
    }
}
