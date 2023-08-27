package com.ecommerce.product.brand;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product-brand")
public class PBrandController {

    private final PBrandService pBrandService;
    @PostMapping
    public ResponseEntity<PBrandDTO> createProductBrand(
            @RequestBody PBrandDTO pBrandDTO
    ){
        return new ResponseEntity<>(
                pBrandService.createBrand(pBrandDTO),
                HttpStatus.CREATED
        );
    }
    @PutMapping("{id}")
    public ResponseEntity<PBrandDTO> updateProductBrand(
            @PathVariable Long id,
            @RequestBody PBrandDTO pBrandDTO
    ){
        return ResponseEntity.ok(
                pBrandService.updateProductBrand(id, pBrandDTO)
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProductBrand(
            @PathVariable Long id
    ){
        pBrandService.deleteProductBrand(id);
        return new ResponseEntity<>(
                HttpStatus.NO_CONTENT
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<PBrandDTO> getProductBrand(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(
                pBrandService.getProductBrand(id)
        );
    }
}
