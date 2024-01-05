package com.ecommerce.product.brand;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PBrandRepository extends JpaRepository<PBrand, Long> {
    List<PBrand> findAllByOrderByCreatedAtDesc();
}
