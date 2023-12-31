package com.ecommerce.product;

import com.ecommerce.product.brand.PBrandRepository;
import com.ecommerce.product.category.PCategoryRepository;
import com.ecommerce.product.color.PColor;
import com.ecommerce.product.color.PColorRepository;
import com.ecommerce.product.image.PImageMapper;
import com.ecommerce.product.image.PImageRepository;
import com.ecommerce.product.model.Product;
import com.ecommerce.product.model.ProductDTO;
import com.ecommerce.product.model.request.ProductCreateRequest;
import com.ecommerce.product.rating.RatingMapper;
import com.ecommerce.product.rating.RatingRepository;
import com.ecommerce.user.favorite.FavoriteRepository;
import com.ecommerce.user.model.User;
import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductMapper {
    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;

    private final PImageRepository pImageRepository;
    private final PImageMapper pImageMapper;

    private final FavoriteRepository favoriteRepository;

    private final PColorRepository pColorRepository;
    private final PCategoryRepository pCategoryRepository;
    private final PBrandRepository pBrandRepository;
    private final Slugify slugify;

    public ProductDTO toDto(Product entity){
        AtomicReference<Boolean> isLiked = new AtomicReference<>(false);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user != null){
            favoriteRepository.findByUser_IdAndProduct_Id(user.getId(), entity.getId())
                    .ifPresent(f -> isLiked.set(f.getIsLiked()));
        }
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
                        .map(PColor::getName)
                        .collect(Collectors.toSet()),
                entity.getLastModifiedAt(),
                entity.getRatingPoint(),
                isLiked.get(),
                ratingRepository.findAllByProduct_Id(entity.getId()).stream()
                        .map(ratingMapper::toDto)
                        .collect(Collectors.toSet()),
                pImageRepository.findAllByProduct_Id(entity.getId()).stream()
                        .map(pImageMapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    public Product toEntity(ProductCreateRequest request){
        Product entity = new Product();

        entity.setTitle(request.title());
        entity.setSlug(slugify.slugify(request.title()));
        entity.setDescription(request.description());
        entity.setQuantity(request.quantity());
        entity.setPrice(request.price());
        entity.setSold(request.sold());
        entity.setColors(
                new HashSet<>(pColorRepository.findAllById(request.colorIds()))
        );
        entity.setCategory(
                pCategoryRepository.findById(request.categoryId()).orElseThrow()
        );
        entity.setBrand(
                pBrandRepository.findById(request.brandId()).orElseThrow()
        );
        return entity;
    }
    public Product toEntity(Product entity, ProductCreateRequest request){
        entity.setTitle(request.title());
        entity.setSlug(slugify.slugify(request.title()));
        entity.setDescription(request.description());
        entity.setQuantity(request.quantity());
        entity.setPrice(request.price());
        entity.setColors(
                new HashSet<>(pColorRepository.findAllById(request.colorIds()))
        );
        entity.setCategory(
                pCategoryRepository.findById(request.categoryId()).orElseThrow()
        );
        entity.setBrand(
                pBrandRepository.findById(request.brandId()).orElseThrow()
        );
        return entity;
    }
}
