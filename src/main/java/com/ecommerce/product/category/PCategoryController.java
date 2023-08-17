package com.ecommerce.product.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product-category")
public class PCategoryController {

    private final PCategoryService pCategoryService;
    @PostMapping
    public ResponseEntity<PCategoryDTO> createProductCategory(
            @RequestBody PCategoryDTO pCategoryDTO
    ){
        return ResponseEntity.ok(pCategoryService.createCategory(pCategoryDTO));
    }
    @PutMapping("{id}")
    public ResponseEntity<PCategoryDTO> updateProductCategory(
            @PathVariable Long id,
            @RequestBody PCategoryDTO pCategoryDTO
    ){
        return ResponseEntity.ok(pCategoryService.updateProductCategory(id, pCategoryDTO));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> updateProductCategory(
            @PathVariable Long id
    ){
        pCategoryService.deleteProductCategory(id);
        return ResponseEntity.ok("Product Category has been deleted");
    }

    @GetMapping("{id}")
    public ResponseEntity<PCategoryDTO> getProductCategory(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(pCategoryService.getProductCategory(id));
    }

    @PutMapping("restore/{id}")
    public ResponseEntity<PCategoryDTO> restoreProductCategory(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(pCategoryService.restoreCategory(id));
    }
}
