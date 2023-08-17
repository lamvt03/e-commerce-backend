package com.ecommerce.product.controller;

import com.ecommerce.product.model.request.AddToWishlistRequest;
import com.ecommerce.product.model.dto.FilterDTO;
import com.ecommerce.common.PaginationDTO;
import com.ecommerce.product.model.entity.Product;
import com.ecommerce.product.model.request.RatingRequest;
import com.ecommerce.product.service.ProductService;
import com.ecommerce.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product p){
        return ResponseEntity.ok(productService.createProduct(p));
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.deleteProductById(id));
    }
    @PutMapping("{id}")
    public ResponseEntity<Product> updateProductById(
            @PathVariable Long id,
            @RequestBody Product p){
        return ResponseEntity.ok(productService.updateProductById(id, p));
    }

    @GetMapping("filter")
    public ResponseEntity<?> getFilteredProducts(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "desc", name = "sort") String sortDirection,
            @RequestParam(defaultValue = "modifiedAt") String sortBy

            ){
        FilterDTO filterDTO = new FilterDTO(brand, category, maxPrice, minPrice);
        PaginationDTO paginationDTO = new PaginationDTO(page, limit, sortDirection, sortBy);
        List<Product> filteredProducts = productService.filterProducts(filterDTO, paginationDTO);
        return ResponseEntity.ok(filteredProducts);
    }

    @GetMapping("all")
    public List<Product> getAllProducts (){
        return productService.findAllProducts();
    }

    @PutMapping("wishlist")
    public ResponseEntity<?> addToWishList(
            @AuthenticationPrincipal User user,
            @RequestBody AddToWishlistRequest request
    ){
        return ResponseEntity.ok(productService.addToWishlist(user.getId(),request));
    }

    @PostMapping("rating")
    public ResponseEntity<?> ratingProduct(
            @AuthenticationPrincipal User user,
            @RequestBody RatingRequest request
    ){
        return ResponseEntity.ok(productService.ratingProduct(user.getId(),request));
    }
}
