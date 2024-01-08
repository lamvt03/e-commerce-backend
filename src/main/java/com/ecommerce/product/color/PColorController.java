package com.ecommerce.product.color;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product/color")
public class PColorController {

    private final PColorService pColorService;

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<PColorDTO> createProductColor(
            @RequestBody PColorDTO pColorDTO
    ){
        return new ResponseEntity<>(
                pColorService.createColor(pColorDTO),
                HttpStatus.CREATED
        );
    }
    @PutMapping("{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<PColorDTO> updateProductColor(
            @PathVariable Long id,
            @RequestBody PColorDTO pColorDTO
    ){
        return ResponseEntity.ok(
                pColorService.updateProductColor(id, pColorDTO)
        );
    }

    @DeleteMapping("{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> deleteProductColor(
            @PathVariable Long id
    ){
        pColorService.deleteProductColor(id);
        return new ResponseEntity<>(
                HttpStatus.NO_CONTENT
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<PColorDTO> getProductColor(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(
                pColorService.getProductCategory(id)
        );
    }

    @GetMapping("/all")
    public ResponseEntity<List<PColorDTO>> getAllProductColors(){
        return ResponseEntity.ok(
                pColorService.getAllProductCategories()
        );
    }
}
