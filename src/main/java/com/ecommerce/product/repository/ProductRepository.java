package com.ecommerce.product.repository;

import com.ecommerce.product.model.dto.FilterDTO;
import com.ecommerce.product.model.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE " +
            "(:#{#filter.brand} IS NULL OR p.brand = :#{#filter.brand}) " +
            "AND (:#{#filter.category} IS NULL OR p.category = :#{#filter.category}) " +
            "AND (:#{#filter.minPrice} IS NULL OR p.price >= :#{#filter.minPrice}) " +
            "AND (:#{#filter.maxPrice} IS NULL OR p.price <= :#{#filter.maxPrice})")
    List<Product> findWithFilter(@Param("filter") FilterDTO filter, Pageable pageable);
}
