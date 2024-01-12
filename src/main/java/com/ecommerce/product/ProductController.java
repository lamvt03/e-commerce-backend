package com.ecommerce.product;

import com.ecommerce.exception.ErrResponse;
import com.ecommerce.product.color.PColorDTO;
import com.ecommerce.product.model.ProductDTO;
import com.ecommerce.product.model.request.ProductCreateRequest;
import com.ecommerce.util.model.FilterDTO;
import com.ecommerce.util.model.PaginationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product")
@Tag(name = "Product Controller")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "bearAuth")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProductCreateRequest.class),
                    examples = {
                            @ExampleObject(value = "{\"title\":\"iPhone X\",\"description\":\"Apple iPhone X, Fully Unlocked 5.8, 256GB - Space Gray, Black, White\",\"brandId\":1,\"price\":1104,\"quantity\":12,\"categoryId\":1,\"sold\":0,\"colorIds\":[1,2,3]}")
                    }
            )
    )
    @Operation(
            summary = "Create new product",
            description = "This endpoint is specifically for administrators to create new product"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201", description = "Create successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDTO.class),
                                    examples = {
                                            @ExampleObject(value = "{\"id\":1,\"title\":\"iPhone X\",\"slug\":\"iphone-x\",\"description\":\"Apple iPhoneX, Fully Unlocked 5.8, 256GB - SpaceGray, Black, White\",\"price\":1104.0,\"quantity\":12,\"sold\":0,\"category\":\"Smartphone\",\"brand\":\"Apple\",\"colors\":[\"White\",\"Space Gray\",\"Black\"],\"lastModifiedAt\":\"2024-01-11T22:08:58.8322345\",\"ratingPoint\":0.0,\"isLiked\":false,\"ratings\":[],\"images\":[]}")
                                    }
                            )
                    }),
            @ApiResponse(
                    responseCode = "401", description = "Unauthorized",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(name = "Forgot attaching token",value = "{\"msg\":\"You haven't attach jwt token\",\"code\":401,\"status\":\"Unauthorized\"}"),
                                            @ExampleObject(name = "Token not valid",value = "{\"msg\":\"Your token not valid\",\"code\":401,\"status\":\"Unauthorized\"}")
                                    }
                            )
                    }),
            @ApiResponse(
                    responseCode = "403", description = "Forbidden",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"msg\":\"You have no any permissions\",\"code\":403,\"status\":\"Forbidden\"}"),
                                    }
                            )
                    })
    })
    public ResponseEntity<ProductDTO> createProduct(
            @Valid @RequestBody ProductCreateRequest request
    ){
        ProductDTO productDTO = productService.createProduct(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productDTO);
    }

    @GetMapping("{id}")
    @Operation(
            summary = "Retrieve product information by its id",
            description = "This endpoint is used to retrieve product information by its id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Retrieve successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDTO.class),
                                    examples = {
                                            @ExampleObject(value = "{\"title\":\"iPhone X\",\"description\":\"Apple iPhone X, Fully Unlocked 5.8, 256GB - Space Gray, Black, White\",\"brandId\":1,\"price\":1104,\"quantity\":12,\"categoryId\":1,\"sold\":0,\"colorIds\":[1,2,3]}")
                                    }
                            )
                    }),
            @ApiResponse(
                    responseCode = "404", description = "Resource not found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"msg\":\"The product with id [1] not found\",\"code\":404,\"status\":\"Not found\"}")
                                    }
                            )
                    })
    })
    public ResponseEntity<ProductDTO> getProductById(
            @PathVariable Long id
    ){
        ProductDTO productDTO = productService.getProductById(id);
        return ResponseEntity.ok(productDTO);
    }

    @DeleteMapping("{id}")
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "bearAuth")
    @Operation(
            summary = "Delete product by its id",
            description = "This endpoint is specifically for administrators to delete product by its id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Delete successfully"),
            @ApiResponse(
                    responseCode = "401", description = "Unauthorized",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(name = "Forgot attaching token",value = "{\"msg\":\"You haven't attach jwt token\",\"code\":401,\"status\":\"Unauthorized\"}"),
                                            @ExampleObject(name = "Token not valid",value = "{\"msg\":\"Your token not valid\",\"code\":401,\"status\":\"Unauthorized\"}")
                                    }
                            )
                    }),
            @ApiResponse(
                    responseCode = "403", description = "Forbidden",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"msg\":\"You have no any permissions\",\"code\":403,\"status\":\"Forbidden\"}"),
                                    }
                            )
                    }),
            @ApiResponse(
                    responseCode = "404", description = "Resource not found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"msg\":\"The product with id [1] not found\",\"code\":404,\"status\":\"Not found\"}")
                                    }
                            )
                    })
    })
    public ResponseEntity<Void> deleteProductById(
            @PathVariable Long id
    ){
        productService.deleteProductById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("{id}")
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "bearAuth")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProductCreateRequest.class),
                    examples = {
                            @ExampleObject(value = "{\"title\":\"iPhone X\",\"description\":\"Apple iPhone X, Fully Unlocked 5.8, 256GB - Space Gray, Black, White\",\"brandId\":1,\"price\":1104,\"quantity\":12,\"categoryId\":1,\"sold\":0,\"colorIds\":[1,2,3]}")
                    }
            )
    )
    @Operation(
            summary = "Update product by its id",
            description = "This endpoint is specifically for administrators to update product by its id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Update successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDTO.class),
                                    examples = {
                                            @ExampleObject(value = "{\"id\":1,\"title\":\"iPhone X\",\"slug\":\"iphone-x\",\"description\":\"Apple iPhoneX, Fully Unlocked 5.8, 256GB - SpaceGray, Black, White\",\"price\":1104.0,\"quantity\":12,\"sold\":0,\"category\":\"Smartphone\",\"brand\":\"Apple\",\"colors\":[\"White\",\"Space Gray\",\"Black\"],\"lastModifiedAt\":\"2024-01-11T22:08:58.8322345\",\"ratingPoint\":0.0,\"isLiked\":false,\"ratings\":[],\"images\":[]}")
                                    }
                            )
                    }),
            @ApiResponse(
                    responseCode = "401", description = "Unauthorized",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(name = "Forgot attaching token",value = "{\"msg\":\"You haven't attach jwt token\",\"code\":401,\"status\":\"Unauthorized\"}"),
                                            @ExampleObject(name = "Token not valid",value = "{\"msg\":\"Your token not valid\",\"code\":401,\"status\":\"Unauthorized\"}")
                                    }
                            )
                    }),
            @ApiResponse(
                    responseCode = "403", description = "Forbidden",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"msg\":\"You have no any permissions\",\"code\":403,\"status\":\"Forbidden\"}"),
                                    }
                            )
                    }),
            @ApiResponse(
                    responseCode = "404", description = "Resource not found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"msg\":\"The product with id [1] not found\",\"code\":404,\"status\":\"Not found\"}")
                                    }
                            )
                    })
    })
    public ResponseEntity<ProductDTO> updateProductById(
            @PathVariable Long id,
            @RequestBody ProductCreateRequest request){
        ProductDTO productDTO = productService.updateProductById(id, request);
        return ResponseEntity.ok(productDTO);
    }

    @PutMapping("/restore/{id}")
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "bearAuth")
    @Operation(
            summary = "Restore product by its id",
            description = "This endpoint is specifically for administrators to restore product by its id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Restore successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PColorDTO.class),
                                    examples = {
                                            @ExampleObject(value = "{\"id\":1,\"title\":\"iPhone X\",\"slug\":\"iphone-x\",\"description\":\"Apple iPhoneX, Fully Unlocked 5.8, 256GB - SpaceGray, Black, White\",\"price\":1104.0,\"quantity\":12,\"sold\":0,\"category\":\"Smartphone\",\"brand\":\"Apple\",\"colors\":[\"White\",\"Space Gray\",\"Black\"],\"lastModifiedAt\":\"2024-01-11T22:08:58.8322345\",\"ratingPoint\":0.0,\"isLiked\":false,\"ratings\":[],\"images\":[]}")
                                    }
                            )
                    }),
            @ApiResponse(
                    responseCode = "401", description = "Unauthorized",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(name = "Forgot attaching token",value = "{\"msg\":\"You haven't attach jwt token\",\"code\":401,\"status\":\"Unauthorized\"}"),
                                            @ExampleObject(name = "Token not valid",value = "{\"msg\":\"Your token not valid\",\"code\":401,\"status\":\"Unauthorized\"}")
                                    }
                            )
                    }),
            @ApiResponse(
                    responseCode = "403", description = "Forbidden",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"msg\":\"You have no any permissions\",\"code\":403,\"status\":\"Forbidden\"}"),
                                    }
                            )
                    }),
            @ApiResponse(
                    responseCode = "404", description = "Resource not found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"msg\":\"The product with id [1] not exists\",\"code\":404,\"status\":\"Not found\"}")
                                    }
                            )
                    })
    })
    public ResponseEntity<ProductDTO> restoreProductById(@PathVariable Long id){
        ProductDTO productDTO = productService.restoreProductById(id);
        return ResponseEntity.ok(productDTO);
    }

    @GetMapping("/filter")
    @Operation(
            summary = "Product filter and pagination",
            description = "This endpoint is used to filter products by category, brand, max price or min price and pagination"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Retrieve successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class)),
                                    examples = {
                                            @ExampleObject(value = "[{\"id\":2,\"title\":\"iPhone SE 5G\",\"slug\":\"iphone-se-5g\",\"description\":\"Tracfone Apple iPhone SE 5G (3rdGeneration), 64GB, Black - Prepaid Smartphone (Locked)\",\"price\":189.0,\"quantity\":8,\"sold\":0,\"category\":\"Smartphone\",\"brand\":\"Apple\",\"colors\":[\"Black\"],\"lastModifiedAt\":\"2024-01-11T22:29:58.013168\",\"ratingPoint\":0.0,\"isLiked\":false,\"ratings\":[],\"images\":[]},{\"id\":1,\"title\":\"iPhoneX\",\"slug\":\"iphone-x\",\"description\":\"Apple iPhone X, Fully Unlocked 5.8, 256GB-SpaceGray, Black, White\",\"price\":1104.0,\"quantity\":12,\"sold\":0,\"category\":\"Smartphone\",\"brand\":\"Apple\",\"colors\":[\"White\",\"Space Gray\"],\"lastModifiedAt\":\"2024-01-11T22:08:58.832235\",\"ratingPoint\":0.0,\"isLiked\":false,\"ratings\":[],\"images\":[]}]")
                                    }
                            )
                    })
    })
    public ResponseEntity<List<ProductDTO>> getFilteredProducts(
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

    @GetMapping
    @Operation(
            summary = "Product pagination",
            description = "This endpoint is used to retrieve products by page and limit"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Retrieve successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class)),
                                    examples = {
                                            @ExampleObject(value = "[{\"id\":2,\"title\":\"iPhone SE 5G\",\"slug\":\"iphone-se-5g\",\"description\":\"Tracfone Apple iPhone SE 5G (3rdGeneration), 64GB, Black - Prepaid Smartphone (Locked)\",\"price\":189.0,\"quantity\":8,\"sold\":0,\"category\":\"Smartphone\",\"brand\":\"Apple\",\"colors\":[\"Black\"],\"lastModifiedAt\":\"2024-01-11T22:29:58.013168\",\"ratingPoint\":0.0,\"isLiked\":false,\"ratings\":[],\"images\":[]},{\"id\":1,\"title\":\"iPhoneX\",\"slug\":\"iphone-x\",\"description\":\"Apple iPhone X, Fully Unlocked 5.8, 256GB-SpaceGray, Black, White\",\"price\":1104.0,\"quantity\":12,\"sold\":0,\"category\":\"Smartphone\",\"brand\":\"Apple\",\"colors\":[\"White\",\"Space Gray\"],\"lastModifiedAt\":\"2024-01-11T22:08:58.832235\",\"ratingPoint\":0.0,\"isLiked\":false,\"ratings\":[],\"images\":[]}]")
                                    }
                            )
                    })
    })
    public ResponseEntity<List<ProductDTO>> getProducts (
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "desc", name = "sort") String sortDirection,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ){
        PaginationDTO paginationDTO = new PaginationDTO(page, limit, sortDirection, sortBy);
        List<ProductDTO> productDTOS = productService.findProducts(paginationDTO);
        return ResponseEntity.ok(productDTOS);
    }

    @GetMapping("/category/{categoryCode}")
    @Operation(
            summary = "Retrieve products by category code",
            description = "This endpoint is used to retrieve products by category code"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Retrieve successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class)),
                                    examples = {
                                            @ExampleObject(value = "[{\"id\":2,\"title\":\"iPhone SE 5G\",\"slug\":\"iphone-se-5g\",\"description\":\"Tracfone Apple iPhone SE 5G (3rdGeneration), 64GB, Black - Prepaid Smartphone (Locked)\",\"price\":189.0,\"quantity\":8,\"sold\":0,\"category\":\"Smartphone\",\"brand\":\"Apple\",\"colors\":[\"Black\"],\"lastModifiedAt\":\"2024-01-11T22:29:58.013168\",\"ratingPoint\":0.0,\"isLiked\":false,\"ratings\":[],\"images\":[]},{\"id\":1,\"title\":\"iPhoneX\",\"slug\":\"iphone-x\",\"description\":\"Apple iPhone X, Fully Unlocked 5.8, 256GB-SpaceGray, Black, White\",\"price\":1104.0,\"quantity\":12,\"sold\":0,\"category\":\"Smartphone\",\"brand\":\"Apple\",\"colors\":[\"White\",\"Space Gray\"],\"lastModifiedAt\":\"2024-01-11T22:08:58.832235\",\"ratingPoint\":0.0,\"isLiked\":false,\"ratings\":[],\"images\":[]}]")
                                    }
                            )
                    })
    })
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(
            @PathVariable(name = "categoryCode") String code,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "desc", name = "sort") String sortDirection,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ){
        PaginationDTO paginationDTO = new PaginationDTO(page, limit, sortDirection, sortBy);
        List<ProductDTO> productDTOS = productService.findProductByCategory(code, paginationDTO);
        return ResponseEntity.ok(productDTOS);
    }

    @GetMapping("/brand/{brandCode}")
    @Operation(
            summary = "Retrieve products by brand code",
            description = "This endpoint is used to retrieve products by brand code"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Retrieve successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class)),
                                    examples = {
                                            @ExampleObject(value = "[{\"id\":2,\"title\":\"iPhone SE 5G\",\"slug\":\"iphone-se-5g\",\"description\":\"Tracfone Apple iPhone SE 5G (3rdGeneration), 64GB, Black - Prepaid Smartphone (Locked)\",\"price\":189.0,\"quantity\":8,\"sold\":0,\"category\":\"Smartphone\",\"brand\":\"Apple\",\"colors\":[\"Black\"],\"lastModifiedAt\":\"2024-01-11T22:29:58.013168\",\"ratingPoint\":0.0,\"isLiked\":false,\"ratings\":[],\"images\":[]},{\"id\":1,\"title\":\"iPhoneX\",\"slug\":\"iphone-x\",\"description\":\"Apple iPhone X, Fully Unlocked 5.8, 256GB-SpaceGray, Black, White\",\"price\":1104.0,\"quantity\":12,\"sold\":0,\"category\":\"Smartphone\",\"brand\":\"Apple\",\"colors\":[\"White\",\"Space Gray\"],\"lastModifiedAt\":\"2024-01-11T22:08:58.832235\",\"ratingPoint\":0.0,\"isLiked\":false,\"ratings\":[],\"images\":[]}]")
                                    }
                            )
                    })
    })
    public ResponseEntity<List<ProductDTO>> getProductsByBrand(
            @PathVariable(name = "brandCode") String code,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "desc", name = "sort") String sortDirection,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ){
        PaginationDTO paginationDTO = new PaginationDTO(page, limit, sortDirection, sortBy);
        List<ProductDTO> productDTOS = productService.findProductByBrand(code, paginationDTO);
        return ResponseEntity.ok(productDTOS);
    }

    @GetMapping("/search")
    @Operation(
            summary = "Search products ",
            description = "This endpoint is used to retrieve products by provided keyword"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Retrieve successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class)),
                                    examples = {
                                            @ExampleObject(value = "[{\"id\":2,\"title\":\"iPhone SE 5G\",\"slug\":\"iphone-se-5g\",\"description\":\"Tracfone Apple iPhone SE 5G (3rdGeneration), 64GB, Black - Prepaid Smartphone (Locked)\",\"price\":189.0,\"quantity\":8,\"sold\":0,\"category\":\"Smartphone\",\"brand\":\"Apple\",\"colors\":[\"Black\"],\"lastModifiedAt\":\"2024-01-11T22:29:58.013168\",\"ratingPoint\":0.0,\"isLiked\":false,\"ratings\":[],\"images\":[]},{\"id\":1,\"title\":\"iPhoneX\",\"slug\":\"iphone-x\",\"description\":\"Apple iPhone X, Fully Unlocked 5.8, 256GB-SpaceGray, Black, White\",\"price\":1104.0,\"quantity\":12,\"sold\":0,\"category\":\"Smartphone\",\"brand\":\"Apple\",\"colors\":[\"White\",\"Space Gray\"],\"lastModifiedAt\":\"2024-01-11T22:08:58.832235\",\"ratingPoint\":0.0,\"isLiked\":false,\"ratings\":[],\"images\":[]}]")
                                    }
                            )
                    })
    })
    public ResponseEntity<List<ProductDTO>> searchProducts(
            @RequestParam("q") String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "desc", name = "sort") String sortDirection,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ){
        PaginationDTO paginationDTO = new PaginationDTO(page, limit, sortDirection, sortBy);
        List<ProductDTO> productDTOS = productService.searchProductByKeyword(keyword, paginationDTO);
        return ResponseEntity.ok(productDTOS);
    }


    @PostMapping("/{id}/uploadImages")
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "bearAuth")
    @Operation(
            summary = "Upload product images",
            description = "This endpoint is specifically for administrators to upload product images"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Upload successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDTO.class),
                                    examples = {
                                            @ExampleObject(value = "{\"id\":1,\"title\":\"iPhone X\",\"slug\":\"iphone-x\",\"description\":\"Apple iPhone X, Fully Unlocked 5.8, 256GB - Space Gray, Black, White\",\"price\":1104.0,\"quantity\":12,\"sold\":0,\"category\":\"Smartphone\",\"brand\":\"Apple\",\"colors\":[\"White\",\"Space Gray\"],\"lastModifiedAt\":\"2024-01-11T22:08:58.832235\",\"ratingPoint\":0.0,\"isLiked\":false,\"ratings\":[],\"images\":[{\"url\":\"http://res.cloudinary.com/ds2gtlowx/image/upload/v1704988095/guzrsgqurybqvsj1xogm.jpg\",\"assetId\":\"7a17b89188eaad6015f15bb094cdea36\",\"publicId\":\"guzrsgqurybqvsj1xogm\"}]}")
                                    }
                            )
                    }),
            @ApiResponse(
                    responseCode = "401", description = "Unauthorized",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(name = "Forgot attaching token",value = "{\"msg\":\"You haven't attach jwt token\",\"code\":401,\"status\":\"Unauthorized\"}"),
                                            @ExampleObject(name = "Token not valid",value = "{\"msg\":\"Your token not valid\",\"code\":401,\"status\":\"Unauthorized\"}")
                                    }
                            )
                    }),
            @ApiResponse(
                    responseCode = "403", description = "Forbidden",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"msg\":\"You have no any permissions\",\"code\":403,\"status\":\"Forbidden\"}"),
                                    }
                            )
                    }),
            @ApiResponse(
                    responseCode = "404", description = "Resource not found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"msg\":\"The product with id [1] not found\",\"code\":404,\"status\":\"Not found\"}")
                                    }
                            )
                    })
    })
    public ResponseEntity<ProductDTO> uploadImages(
            @RequestParam("image") MultipartFile[] images,
            @PathVariable Long id
            ){
        ProductDTO productDTO = productService.uploadProductImages(id, images);
        return ResponseEntity.ok(productDTO);
    }

    @DeleteMapping("/{id}/image/{imageId}")
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "bearAuth")
    @Operation(
            summary = "Delete a product image by its public id",
            description = "This endpoint is specifically for administrators to delete a product image by its public id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Delete successfully"),
            @ApiResponse(
                    responseCode = "401", description = "Unauthorized",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(name = "Forgot attaching token",value = "{\"msg\":\"You haven't attach jwt token\",\"code\":401,\"status\":\"Unauthorized\"}"),
                                            @ExampleObject(name = "Token not valid",value = "{\"msg\":\"Your token not valid\",\"code\":401,\"status\":\"Unauthorized\"}")
                                    }
                            )
                    }),
            @ApiResponse(
                    responseCode = "403", description = "Forbidden",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"msg\":\"You have no any permissions\",\"code\":403,\"status\":\"Forbidden\"}"),
                                    }
                            )
                    }),
            @ApiResponse(
                    responseCode = "404", description = "Resource not found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"msg\":\"The product with id [1] not found\",\"code\":404,\"status\":\"Not found\"}")
                                    }
                            )
                    })
    })
    public ResponseEntity<Void> deleteProductImage(
            @PathVariable(name = "id") Long prodId,
            @PathVariable(name = "imageId") String publicId
    ){
        productService.deleteProductImage(prodId, publicId);
        return new ResponseEntity<>(
                HttpStatus.NO_CONTENT
        );
    }
}
