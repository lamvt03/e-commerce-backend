package com.ecommerce.order.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OProductRepository extends JpaRepository<OProduct, Long> {
    List<OProduct> findAllByOrderId(Long orderId);
}
