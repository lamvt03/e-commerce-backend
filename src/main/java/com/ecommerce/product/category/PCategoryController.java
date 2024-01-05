package com.ecommerce.product.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/category")
public class PCategoryController {

    private final PCategoryService pCategoryService;
    @PostMapping
    public ResponseEntity<PCategoryDTO> createProductCategory(
            @RequestBody PCategoryDTO pCategoryDTO
    ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pCategoryService.createCategory(pCategoryDTO));
    }
    @PutMapping("{id}")
    public ResponseEntity<PCategoryDTO> updateProductCategory(
            @PathVariable Long id,
            @RequestBody PCategoryDTO pCategoryDTO
    ){
        return ResponseEntity.ok(pCategoryService.updateProductCategory(id, pCategoryDTO));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> updateProductCategory(
            @PathVariable Long id
    ){
        pCategoryService.deleteProductCategory(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("{id}")
    public ResponseEntity<PCategoryDTO> getProductCategory(
            @PathVariable Long id
    ){
        PCategoryDTO pCategoryDTO = pCategoryService.getProductCategory(id);
        return ResponseEntity.ok(pCategoryDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PCategoryDTO>> getAllProductCategories(){
        List<PCategoryDTO> pCategoryDTOs = pCategoryService.getAllProductCategories();
        return ResponseEntity.ok(pCategoryDTOs);
    }
}
