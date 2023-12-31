package com.ecommerce.product;

import com.ecommerce.product.model.ProductDTO;
import com.ecommerce.product.model.request.AddWishlistRequest;
import com.ecommerce.product.model.request.ProductCreateRequest;
import com.ecommerce.util.model.FilterDTO;
import com.ecommerce.util.model.PaginationDTO;
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

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(
            @RequestBody ProductCreateRequest request
    ){
        ProductDTO productDTO = productService.createProduct(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productDTO);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDTO> getProductById(
            @PathVariable Long id
    ){
        ProductDTO productDTO = productService.getProductById(id);
        return ResponseEntity.ok(productDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProductById(
            @PathVariable Long id
    ){
        productService.deleteProductById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
    @PutMapping("{id}")
    public ResponseEntity<ProductDTO> updateProductById(
            @PathVariable Long id,
            @RequestBody ProductCreateRequest request){
        ProductDTO productDTO = productService.updateProductById(id, request);
        return ResponseEntity.ok(productDTO);
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
            @RequestParam(defaultValue = "createdAt") String sortBy
            ){
        FilterDTO filterDTO = new FilterDTO(brand, category, maxPrice, minPrice);
        PaginationDTO paginationDTO = new PaginationDTO(page, limit, sortDirection, sortBy);
        List<ProductDTO> products = productService.filterProducts(filterDTO, paginationDTO);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> getAllProducts (){
        List<ProductDTO> productDTOS = productService.findAllProducts();
        return ResponseEntity.ok(productDTOS);
    }

    @PutMapping("wishlist")
    public ResponseEntity<?> addToWishList(
            @AuthenticationPrincipal User user,
            @RequestBody AddWishlistRequest request
    ){
        return ResponseEntity.ok(
                productService.addToWishlist(user.getId(),request)
        );
    }

    @PostMapping("/rating")
    public ResponseEntity<ProductDTO> ratingProduct(
            @AuthenticationPrincipal User user,
            @RequestBody RatingRequest request
    ){
        ProductDTO productDTO = productService.ratingProduct(user,request);
        return ResponseEntity.ok(productDTO);
    }

    @PostMapping("upload/{id}")
    public ResponseEntity<ProductDTO> uploadImage(
            @RequestParam("image") MultipartFile[] images,
            @PathVariable Long id
            ){
        ProductDTO productDTO = productService.uploadProductImages(id, images);
        return ResponseEntity.ok(
                productDTO
        );
    }
    @DeleteMapping("/{prodId}/image/{publicId}")
    public ResponseEntity<?> deleteProductImage(
            @PathVariable Long prodId,
            @PathVariable String publicId
    ){
        productService.deleteProductImage(prodId, publicId);
        return new ResponseEntity<>(
                HttpStatus.NO_CONTENT
        );
    }
}
