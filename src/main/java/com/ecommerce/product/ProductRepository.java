package com.ecommerce.product;

import com.ecommerce.product.model.Product;
import com.ecommerce.util.model.FilterDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p JOIN p.category pc JOIN p.brand pb WHERE " +
            "(:#{#filter.brand} IS NULL OR UPPER(pb.name) = UPPER(:#{#filter.brand})) " +
            "AND (:#{#filter.category} IS NULL OR UPPER(pc.name) = UPPER(:#{#filter.category})) " +
            "AND (:#{#filter.minPrice} IS NULL OR p.price >= :#{#filter.minPrice}) " +
            "AND (:#{#filter.maxPrice} IS NULL OR p.price <= :#{#filter.maxPrice}) ")
    List<Product> findWithFilter(@Param("filter") FilterDTO filter, Pageable pageable);

    List<Product> findByCategory_Code(String code, Pageable pageable);

    List<Product> findByBrand_Code(String code, Pageable pageable);

    List<Product> findByIsDeleted(boolean isDeleted, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE " +
            "(UPPER(p.title) LIKE CONCAT('%', UPPER(:keyword), '%')) ")
    List<Product> findWithKeyword(@Param("keyword") String keyword, Pageable pageable);
}
