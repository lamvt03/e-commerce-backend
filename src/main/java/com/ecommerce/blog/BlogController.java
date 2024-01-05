//package com.ecommerce.blog;
//
//import com.ecommerce.blog.service.BlogService;
//import com.ecommerce.common.AbstractEntity;
//import com.ecommerce.common.PaginationDTO;
//import com.ecommerce.user.model.User;
//import com.ecommerce.util.model.PaginationDTO;
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/api/blog")
//public class BlogController {
//
//    private final BlogService blogService;
//
//    @PostMapping
//    public ResponseEntity<BlogDTO> createBlog(
//            @RequestBody BlogDTO blog
//    ){
//        return  ResponseEntity.ok(blogService.createBlog(blog));
//    }
//
//    @PutMapping("{id}")
//    public ResponseEntity<BlogDTO> updateBlog(
//            @PathVariable Long id,
//            @RequestBody BlogDTO blogDto
//    ){
//        return ResponseEntity.ok(blogService.updateBlog(id, blogDto));
//    }
//    @GetMapping("{id}")
//    public ResponseEntity<BlogDTO> getBlogById(
//            @PathVariable Long id
//    ){
//        return ResponseEntity.ok(blogService.getBlogById(id));
//    }
//    @DeleteMapping("{id}")
//    public ResponseEntity<?> deleteBlogById(
//            @PathVariable Long id
//    ){
//        blogService.deleteBlogById(id);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT)
//                .build();
//    }
//
//    @GetMapping
//    public ResponseEntity<List<BlogDTO>> getBlogs(
//            @RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "10") int limit,
//            @RequestParam(defaultValue = "desc", name = "sort") String sortDirection,
//            @RequestParam(defaultValue = "modifiedAt") String sortBy
//    ){
//        PaginationDTO paginationDTO = new PaginationDTO(page, limit, sortDirection, sortBy);
//        return ResponseEntity.ok(blogService.getBlogs(paginationDTO));
//    }
//
////    @PutMapping("like/{id}")
////    public ResponseEntity<?> likeBlog(
////            @PathVariable Long id,
////            @AuthenticationPrincipal User u
////        ){
////        return ResponseEntity.ok("");
////    }
//
//}
