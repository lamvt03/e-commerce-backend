package com.ecommerce.cart.cartProduct;

import com.ecommerce.cart.cartProduct.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    @Modifying
    @Query("delete from CartProduct cp where cp.id = :id")
    void deleteById(@Param("id") Long id);
}
