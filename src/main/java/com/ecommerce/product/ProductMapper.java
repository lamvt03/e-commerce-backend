package com.ecommerce.product;

import com.ecommerce.product.brand.PBrandRepository;
import com.ecommerce.product.category.PCategory;
import com.ecommerce.product.category.PCategoryRepository;
import com.ecommerce.product.color.PColor;
import com.ecommerce.product.color.PColorDTO;
import com.ecommerce.product.color.PColorMapper;
import com.ecommerce.product.color.PColorRepository;
import com.ecommerce.product.image.PImageMapper;
import com.ecommerce.product.model.Product;
import com.ecommerce.product.model.ProductDTO;
import com.ecommerce.product.rating.RatingMapper;
import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductMapper {
    private final RatingMapper ratingMapper;
    private final PImageMapper pImageMapper;
    private final PColorRepository pColorRepository;
    private final PCategoryRepository pCategoryRepository;
    private final PBrandRepository pBrandRepository;

    public ProductDTO toDto(Product entity){
        return new ProductDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getSlug(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getQuantity(),
                entity.getSold(),
                entity.getCategory().getName(),
                entity.getBrand().getName(),
                entity.getColors().stream()
                        .map(PColor::getId)
                        .collect(Collectors.toSet()),
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

    public Product toEntity(ProductDTO dto){
        Product entity = new Product();
        Slugify slugify = Slugify.builder()
                        .lowerCase(true)
                        .transliterator(true)
                        .build();

        entity.setTitle(dto.title());
        entity.setSlug(slugify.slugify(dto.title()));
        entity.setDescription(dto.description());
        entity.setQuantity(dto.quantity());
        entity.setPrice(dto.price());
        entity.setColors(
                pColorRepository.findAllById(dto.colorIds()).stream()
                        .collect(Collectors.toSet())
        );
        entity.setCategory(
                pCategoryRepository.findByNameIgnoreCase(dto.category())
        );
        entity.setBrand(
                pBrandRepository.findByNameIgnoreCase(dto.brand())
        );
        return entity;
    }
}
