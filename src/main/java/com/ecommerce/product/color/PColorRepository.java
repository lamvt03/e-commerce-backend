package com.ecommerce.product.color;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PColorRepository extends JpaRepository<PColor, Long> {
}
