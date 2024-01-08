package com.ecommerce.product.brand;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product/brand")
public class PBrandController {

    private final PBrandService pBrandService;

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<PBrandDTO> createProductBrand(
            @Valid @RequestBody PBrandDTO pBrandDTO
    ){
        return new ResponseEntity<>(
                pBrandService.createBrand(pBrandDTO),
                HttpStatus.CREATED
        );
    }
    @PutMapping("{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<PBrandDTO> updateProductBrand(
            @PathVariable Long id,
            @Valid @RequestBody PBrandDTO pBrandDTO
    ){
        return ResponseEntity.ok(
                pBrandService.updateProductBrand(id, pBrandDTO)
        );
    }

    @DeleteMapping("{id}")
    @Secured("ROLE_ADMIN")
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

    @GetMapping("/all")
    public ResponseEntity<List<PBrandDTO>> getAllProductBrands(){
        return ResponseEntity.ok(
                pBrandService.getAllProductBrands()
        );
    }
}
