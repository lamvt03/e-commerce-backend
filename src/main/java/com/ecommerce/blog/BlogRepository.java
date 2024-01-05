//package com.ecommerce.blog;
//
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface BlogRepository extends JpaRepository<BlogController.Blog, Long> {
//    Optional<BlogController.Blog> findByIdAndIsDeletedFalse(Long id);
//    List<BlogController.Blog> findByIsDeletedFalse(Pageable pageable);
//
//}
