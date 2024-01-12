package com.ecommerce.product.brand;

import com.ecommerce.exception.ErrResponse;
import com.ecommerce.product.brand.request.PBrandCreateRequest;
import com.ecommerce.product.model.ProductDTO;
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

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product/brand")
@Tag(name = "Product Brand Controller")
public class PBrandController {

    private final PBrandService pBrandService;

    @PostMapping
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "bearAuth")
    @Operation(
            summary = "Create new product brand",
            description = "This endpoint is specifically for administrators to create new product brand"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201", description = "Create successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PBrandDTO.class),
                                    examples = {
                                            @ExampleObject(value = "{\"id\":1,\"name\":\"Apple\",\"code\":\"apple\",\"link\":\"/apple\",\"createdAt\":\"2024-01-11T16:22:23.5061514\",\"createdBy\":\"votruonglam2109@gmail.com\",\"lastModifiedAt\":\"2024-01-11T16:22:23.5061514\",\"lastModifiedBy\":\"votruonglam2109@gmail.com\"}")
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
                    responseCode = "409", description = "Duplicate resource",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"msg\":\"The product brand with name [Apple] already exists\",\"code\":409,\"status\":\"Conflict\"}")
                                    }
                            )
                    })
    })
    public ResponseEntity<PBrandDTO> createProductBrand(
            @Valid @RequestBody PBrandCreateRequest request
    ){
        return new ResponseEntity<>(
                pBrandService.createBrand(request),
                HttpStatus.CREATED
        );
    }

    @PutMapping("{id}")
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "bearAuth")
    @Operation(
            summary = "Update product brand by its id",
            description = "This endpoint is specifically for administrators to update product brand by its id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Update successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PBrandDTO.class),
                                    examples = {
                                            @ExampleObject(value = "{\"id\":1,\"name\":\"Apple\",\"code\":\"apple\",\"link\":\"/apple\",\"createdAt\":\"2024-01-11T16:22:23.5061514\",\"createdBy\":\"votruonglam2109@gmail.com\",\"lastModifiedAt\":\"2024-01-11T16:22:23.5061514\",\"lastModifiedBy\":\"votruonglam2109@gmail.com\"}")
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
                                            @ExampleObject(value = "{\"msg\":\"The product brand with id [1] not found\",\"code\":404,\"status\":\"Not found\"}")
                                    }
                            )
                    })
    })
    public ResponseEntity<PBrandDTO> updateProductBrand(
            @PathVariable Long id,
            @Valid @RequestBody PBrandCreateRequest request
    ){
        return ResponseEntity.ok(
                pBrandService.updateProductBrand(id, request)
        );
    }

    @DeleteMapping("{id}")
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "bearAuth")
    @Operation(
            summary = "Delete product brand by its id",
            description = "This endpoint is specifically for administrators to delete product brand by its id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204", description = "Delete successfully"),
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
                                            @ExampleObject(value = "{\"msg\":\"The product brand with id [1] not found\",\"code\":404,\"status\":\"Not found\"}")
                                    }
                            )
                    })
    })
    public ResponseEntity<Void> deleteProductBrand(
            @PathVariable Long id
    ){
        pBrandService.deleteProductBrand(id);
        return new ResponseEntity<>(
                HttpStatus.NO_CONTENT
        );
    }

    @GetMapping("{id}")
    @Operation(
            summary = "Retrieve product brand information by its id",
            description = "This endpoint is used to retrieve product brand information by its id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Retrieve successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PBrandDTO.class),
                                    examples = {
                                            @ExampleObject(value = "{\"id\":1,\"name\":\"Apple\",\"code\":\"apple\",\"link\":\"/apple\",\"createdAt\":\"2024-01-11T16:22:23.5061514\",\"createdBy\":\"votruonglam2109@gmail.com\",\"lastModifiedAt\":\"2024-01-11T16:22:23.5061514\",\"lastModifiedBy\":\"votruonglam2109@gmail.com\"}")
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
                                            @ExampleObject(value = "{\"msg\":\"The product brand with id [1] not found\",\"code\":404,\"status\":\"Not found\"}")
                                    }
                            )
                    })
    })
    public ResponseEntity<PBrandDTO> getProductBrand(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(
                pBrandService.getProductBrand(id)
        );
    }

    @GetMapping("/all")
    @Operation(
            summary = "Retrieve all product brands",
            description = "This endpoint is used to retrieve all product brands"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Retrieve successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class)),
                                    examples = {
                                            @ExampleObject(value = "[{\"id\":2,\"name\":\"Lenovo\",\"code\":\"lenovo\",\"link\":\"/lenovo\",\"createdAt\":\"2024-01-11T17:06:48.591759\",\"createdBy\":\"votruonglam2109@gmail.com\",\"lastModifiedAt\":\"2024-01-11T17:06:48.591759\",\"lastModifiedBy\":\"votruonglam2109@gmail.com\"},{\"id\":1,\"name\":\"Apple\",\"code\":\"apple\",\"link\":\"/apple\",\"createdAt\":\"2024-01-11T16:22:23.506151\",\"createdBy\":\"votruonglam2109@gmail.com\",\"lastModifiedAt\":\"2024-01-11T16:22:23.506151\",\"lastModifiedBy\":\"votruonglam2109@gmail.com\"}]")
                                    }
                            )
                    })
    })
    public ResponseEntity<List<PBrandDTO>> getAllProductBrands(){
        return ResponseEntity.ok(
                pBrandService.getAllProductBrands()
        );
    }
}
