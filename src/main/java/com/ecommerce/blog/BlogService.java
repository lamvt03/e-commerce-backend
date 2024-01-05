//package com.ecommerce.blog.service;
//
//import com.ecommerce.blog.BlogController;
//import com.ecommerce.blog.BlogRepository;
//import com.ecommerce.blog.BlogDTO;
//import com.ecommerce.common.PaginationDTO;
//import com.ecommerce.common.PaginationService;
//import com.ecommerce.exception.ResourceNotFoundException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@RequiredArgsConstructor
//@Service
//public class BlogService {
//
//    private final BlogRepository blogRepository;
//    private final BlogMapper blogMapper;
//    private final PaginationService paginationService;
//
//    public BlogDTO createBlog(BlogDTO blogDto){
//        BlogController.Blog blog =  blogMapper.toEntity(blogDto);
//        return Optional.of(blogRepository.save(blog))
//                .map(blogMapper::toDTO)
//                .orElseThrow();
//    }
//
//    private BlogController.Blog findBlogById(Long id){
//        return blogRepository.findByIdAndIsDeletedFalse(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Blog with id [%s] not found".formatted(id)));
//    }
//
//    public BlogDTO updateBlog(Long id, BlogDTO blogDto){
//        BlogController.Blog oldBlog = findBlogById(id);
//        BlogController.Blog updatedBlog = blogMapper.toEntity(oldBlog, blogDto);
//        return Optional.of(updatedBlog)
//                .map(b -> blogRepository.save(b))
//                .map(blogMapper::toDTO)
//                .orElseThrow();
//    }
//
//    public BlogDTO getBlogById(Long id) {
//        BlogController.Blog b = findBlogById(id);
//        return blogMapper.toDTO(b);
//    }
//
//    public void deleteBlogById(Long id) {
//        BlogController.Blog b = findBlogById(id);
//        b.setDeleted(true);
//        blogRepository.save(b);
//    }
//
//    public List<BlogDTO> getBlogs(PaginationDTO paginationDTO) {
//        Pageable pageable = paginationService.getPageable(paginationDTO);
//        return blogRepository.findByIsDeletedFalse(pageable).stream()
//                .map(blogMapper::toDTO)
//                .collect(Collectors.toList());
//    }
//}
