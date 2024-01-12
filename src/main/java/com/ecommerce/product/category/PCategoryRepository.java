package com.ecommerce.product.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PCategoryRepository extends JpaRepository<PCategory, Long> {
    List<PCategory> findAllByOrderByCreatedAtDesc();
}
