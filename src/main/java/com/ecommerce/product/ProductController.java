package com.ecommerce.product;

import com.cloudinary.Cloudinary;
import com.ecommerce.product.model.ProductDTO;
import com.ecommerce.product.model.request.AddToWishlistRequest;
import com.ecommerce.util.FilterDTO;
import com.ecommerce.util.PaginationDTO;
import com.ecommerce.product.model.Product;
import com.ecommerce.product.model.request.RatingRequest;
import com.ecommerce.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;
    private final Cloudinary cloudinary;

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(
            @RequestBody ProductDTO productDTO
    ){
        return ResponseEntity.ok(
                productService.createProduct(productDTO)
        );
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

    @PostMapping("upload/{id}")
    public ResponseEntity<ProductDTO> uploadImage(
            @RequestParam("image") MultipartFile[] images,
            @PathVariable Long id
            ){
        return ResponseEntity.ok(
                productService.uploadProductImages(id, images)
        );
    }
    @DeleteMapping("image/{publicId}")
    public ResponseEntity<?> deleteProductImage(
            @PathVariable String publicId
    ){
        productService.deleteProductImage(publicId);
        return new ResponseEntity<>(
                HttpStatus.NO_CONTENT
        );
    }
}
