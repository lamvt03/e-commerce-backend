package com.ecommerce.blog;

import com.ecommerce.blog.model.Blog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    Optional<Blog> findByIdAndIsDeletedFalse(Long id);
    List<Blog> findByIsDeletedFalse(Pageable pageable);

}
