package com.ecommerce.user;

import com.ecommerce.user.model.UserRole;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ecommerce.user.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    List<User> findByRoleAndIsEnable(UserRole role, boolean isEnable, Pageable pageable);
    List<User> findByRole(UserRole role, Pageable pageable);

    Optional<User> findByIdAndRole(Long id, UserRole role);

    @Query("SELECT u FROM User u WHERE " +
            "(UPPER(u.email) = UPPER(:keyword)) " +
            "OR (u.mobile = :keyword) " +
            "OR (UPPER(CONCAT(u.firstName, ' ', u.lastName)) LIKE CONCAT('%', UPPER(:keyword), '%')) ")
    List<User> findWithKeyword(@Param("keyword") String keyword, Pageable pageable);
}