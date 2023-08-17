package com.ecommerce.product.repository;

import com.ecommerce.product.model.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
//    Rating findByUserId(Long userId);
}
