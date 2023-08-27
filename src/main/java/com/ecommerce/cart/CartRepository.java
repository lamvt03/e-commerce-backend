package com.ecommerce.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Modifying
    @Query("delete from Cart c where c.id = :id")
    void deleteById(@Param("id") Long id);
    Optional<Cart> findByUser_Id(Long id);
    void deleteByUser_Id(Long id);
}
