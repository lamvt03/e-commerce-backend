package com.ecommerce.user.favorite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Optional<Favorite> findByUser_IdAndProduct_Id(Long userId, Long productId);

    List<Favorite> findAllByIsLikedTrueAndUser_Id(Long userId);
}
