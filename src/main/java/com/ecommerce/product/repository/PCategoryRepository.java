package com.ecommerce.product.repository;

import com.ecommerce.product.model.entity.PCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PCategoryRepository extends JpaRepository<PCategory, Long> {
    Optional<PCategory> findByIdAndIsDeletedFalse(Long id);
}
