//package com.ecommerce.blog.service;
//
//import com.ecommerce.blog.BlogController.Blog;
//import com.ecommerce.blog.BlogDTO;
//import org.springframework.stereotype.Service;
//
//@Service
//public class BlogMapper {
//
//    public Blog toEntity(BlogDTO dto){
//        Blog blog = new Blog();
//        blog.setTitle(dto.title());
//        blog.setDescription(dto.description());
//        blog.setCategory(dto.category());
//        return blog;
//    }
//    public Blog toEntity(Blog oldEntity, BlogDTO dto){
//        oldEntity.setTitle(dto.title());
//        oldEntity.setDescription(dto.description());
//        oldEntity.setCategory(dto.category());
//        return oldEntity;
//    }
//
//    public BlogDTO toDTO(Blog blog){
//        return new BlogDTO(
//                blog.getTitle(),
//                blog.getDescription(),
//                blog.getCategory(),
//                blog.getModifiedAt()
//        );
//    }
//}
