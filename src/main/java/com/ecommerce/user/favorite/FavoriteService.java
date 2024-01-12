package com.ecommerce.user.favorite;

import com.ecommerce.product.ProductMapper;
import com.ecommerce.product.ProductRepository;
import com.ecommerce.product.model.Product;
import com.ecommerce.product.model.ProductDTO;
import com.ecommerce.user.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public FavoriteDTO likeProduct(User user, Long productId) {
        favoriteRepository.findByUser_IdAndProduct_Id(user.getId(), productId)
                .ifPresentOrElse(
                        f -> {
                            f.setIsLiked(!f.getIsLiked());
                            favoriteRepository.save(f);
                        },
                        () -> {
                            Product p = productRepository.findById(productId).orElseThrow();
                            favoriteRepository.save(
                                    Favorite.builder()
                                            .isLiked(Boolean.TRUE)
                                            .user(user)
                                            .product(p)
                                            .build()
                            );
                        }
                );
        Favorite favorite = favoriteRepository.findByUser_IdAndProduct_Id(user.getId(), productId)
                .orElseThrow();
        return new FavoriteDTO(
                favorite.getIsLiked(),
                favorite.getLastModifiedAt(),
                productId
        );
    }

    public List<ProductDTO> getFavoriteProducts(Long userId) {
        return favoriteRepository.findAllByIsLikedTrueAndUser_Id(userId).stream()
                .map(Favorite::getProduct)
                .map(productMapper::toDto)
                .toList();
    }
}
