package com.ecommerce.product.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PImageRepository extends JpaRepository<PImage, Long> {
    Optional<PImage> findByProduct_IdAndPublicId(Long productId, String publicKey);
    List<PImage> findAllByProduct_Id(Long productId);
    Optional<PImage> findFirstByProduct_Id(Long productId);
}
