package com.ecommerce.product.category;

import com.ecommerce.product.brand.PBrand;
import com.ecommerce.product.category.PCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PCategoryRepository extends JpaRepository<PCategory, Long> {
    List<PCategory> findAllByOrderByCreatedAtDesc();
}
