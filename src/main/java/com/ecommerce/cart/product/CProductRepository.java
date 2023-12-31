package com.ecommerce.cart.product;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CProductRepository extends JpaRepository<CProduct, Long> {

    Optional<CProduct> findByCart_IdAndProductIdAndColorIdOrderByCreatedAtDesc(Long cartId, Long productId, Long colorId);
    List<CProduct> findAllByCart_Id (Long cartId);
    List<CProduct> findAllByCart_Id (Long cartId, Pageable pageable);
}
