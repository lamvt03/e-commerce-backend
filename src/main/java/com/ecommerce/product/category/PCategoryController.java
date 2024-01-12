package com.ecommerce.product.category;

import com.ecommerce.exception.ErrResponse;
import com.ecommerce.product.category.request.PCategoryCreateRequest;
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
@RequestMapping("/api/product/category")
@Tag(name = "Product Category Controller")
public class PCategoryController {

    private final PCategoryService pCategoryService;

    @PostMapping
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "bearAuth")
    @Operation(
            summary = "Create new product category",
            description = "This endpoint is specifically for administrators to create new product category"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201", description = "Create successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PCategoryDTO.class),
                                    examples = {
                                            @ExampleObject(value = "{\"id\":1,\"name\":\"Smartphone\",\"code\":\"smartphone\",\"link\":\"/smartphone\",\"createdAt\":\"2024-01-11T19:19:50.550058\",\"createdBy\":\"votruonglam2109@gmail.com\",\"lastModifiedAt\":\"2024-01-11T19:19:50.550058\",\"lastModifiedBy\":\"votruonglam2109@gmail.com\"}")
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
                                            @ExampleObject(value = "{\"msg\":\"The product category with name [Smartphone] already exists\",\"code\":409,\"status\":\"Conflict\"}")
                                    }
                            )
                    })
    })
    public ResponseEntity<PCategoryDTO> createProductCategory(
            @Valid @RequestBody PCategoryCreateRequest request
    ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pCategoryService.createCategory(request));
    }

    @PutMapping("{id}")
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "bearAuth")
    @Operation(
            summary = "Update product category by its id",
            description = "This endpoint is specifically for administrators to update product category by its id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Update successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PCategoryDTO.class),
                                    examples = {
                                            @ExampleObject(value = "{\"id\":1,\"name\":\"Smartphone\",\"code\":\"smartphone\",\"link\":\"/smartphone\",\"createdAt\":\"2024-01-11T19:19:50.550058\",\"createdBy\":\"votruonglam2109@gmail.com\",\"lastModifiedAt\":\"2024-01-11T19:19:50.550058\",\"lastModifiedBy\":\"votruonglam2109@gmail.com\"}")
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
                                            @ExampleObject(value = "{\"msg\":\"The product category with id [1] not found\",\"code\":404,\"status\":\"Not found\"}")
                                    }
                            )
                    })
    })
    public ResponseEntity<PCategoryDTO> updateProductCategory(
            @PathVariable Long id,
            @Valid @RequestBody PCategoryCreateRequest request
    ){
        return ResponseEntity.ok(pCategoryService.updateProductCategory(id, request));
    }

    @DeleteMapping("{id}")
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "bearAuth")
    @Operation(
            summary = "Delete product category by its id",
            description = "This endpoint is specifically for administrators to delete product category by its id"
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
                                            @ExampleObject(value = "{\"msg\":\"The product category with id [1] not found\",\"code\":404,\"status\":\"Not found\"}")
                                    }
                            )
                    })
    })
    public ResponseEntity<Void> deleteProductCategory(
            @PathVariable Long id
    ){
        pCategoryService.deleteProductCategory(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("{id}")
    @Operation(
            summary = "Retrieve product category information by its id",
            description = "This endpoint is used to retrieve product category information by its id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Retrieve successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PCategoryDTO.class),
                                    examples = {
                                            @ExampleObject(value = "{\"id\":1,\"name\":\"Smartphone\",\"code\":\"smartphone\",\"link\":\"/smartphone\",\"createdAt\":\"2024-01-11T19:19:50.550058\",\"createdBy\":\"votruonglam2109@gmail.com\",\"lastModifiedAt\":\"2024-01-11T19:19:50.550058\",\"lastModifiedBy\":\"votruonglam2109@gmail.com\"}")
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
                                            @ExampleObject(value = "{\"msg\":\"The product category with id [1] not found\",\"code\":404,\"status\":\"Not found\"}")
                                    }
                            )
                    })
    })
    public ResponseEntity<PCategoryDTO> getProductCategory(
            @PathVariable Long id
    ){
        PCategoryDTO pCategoryDTO = pCategoryService.getProductCategory(id);
        return ResponseEntity.ok(pCategoryDTO);
    }

    @GetMapping("/all")
    @Operation(
            summary = "Retrieve all product categories",
            description = "This endpoint is used to retrieve all product categories"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Retrieve successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PCategoryDTO.class)),
                                    examples = {
                                            @ExampleObject(value = "[{\"id\":2,\"name\":\"Laptop\",\"code\":\"laptop\",\"link\":\"/laptop\",\"createdAt\":\"2024-01-11T19:27:49.544247\",\"createdBy\":\"votruonglam2109@gmail.com\",\"lastModifiedAt\":\"2024-01-11T19:27:49.544247\",\"lastModifiedBy\":\"votruonglam2109@gmail.com\"},{\"id\":1,\"name\":\"Smartphone\",\"code\":\"smartphone\",\"link\":\"/smartphone\",\"createdAt\":\"2024-01-11T19:19:50.550058\",\"createdBy\":\"votruonglam2109@gmail.com\",\"lastModifiedAt\":\"2024-01-11T19:19:50.550058\",\"lastModifiedBy\":\"votruonglam2109@gmail.com\"}]")
                                    }
                            )
                    })
    })
    public ResponseEntity<List<PCategoryDTO>> getAllProductCategories(){
        List<PCategoryDTO> pCategoryDTOs = pCategoryService.getAllProductCategories();
        return ResponseEntity.ok(pCategoryDTOs);
    }
}
