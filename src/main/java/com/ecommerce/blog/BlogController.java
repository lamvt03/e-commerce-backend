package com.ecommerce.blog;

import com.ecommerce.blog.model.Blog;
import com.ecommerce.blog.model.BlogDTO;
import com.ecommerce.blog.service.BlogService;
import com.ecommerce.common.PaginationDTO;
import com.ecommerce.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/blog")
public class BlogController {

    private final BlogService blogService;

    @PostMapping
    public ResponseEntity<BlogDTO> createBlog(
            @RequestBody BlogDTO blog
    ){
        return  ResponseEntity.ok(blogService.createBlog(blog));
    }

    @PutMapping("{id}")
    public ResponseEntity<BlogDTO> updateBlog(
            @PathVariable Long id,
            @RequestBody BlogDTO blogDto
    ){
        return ResponseEntity.ok(blogService.updateBlog(id, blogDto));
    }
    @GetMapping("{id}")
    public ResponseEntity<BlogDTO> getBlogById(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(blogService.getBlogById(id));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBlogById(
            @PathVariable Long id
    ){
        blogService.deleteBlogById(id);
        return ResponseEntity.ok("Blog with id [%s] have been deleted".formatted(id));
    }

    @GetMapping
    public ResponseEntity<List<BlogDTO>> getBlogs(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "desc", name = "sort") String sortDirection,
            @RequestParam(defaultValue = "modifiedAt") String sortBy
    ){
        PaginationDTO paginationDTO = new PaginationDTO(page, limit, sortDirection, sortBy);
        return ResponseEntity.ok(blogService.getBlogs(paginationDTO));
    }

//    @PutMapping("like/{id}")
//    public ResponseEntity<?> likeBlog(
//            @PathVariable Long id,
//            @AuthenticationPrincipal User u
//        ){
//        return ResponseEntity.ok("");
//    }
}
