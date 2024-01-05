package com.ecommerce.user;

import com.ecommerce.user.model.UserRole;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
