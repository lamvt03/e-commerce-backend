package com.ecommerce.product.color;

import com.ecommerce.exception.ErrResponse;
import com.ecommerce.product.color.request.PColorCreateRequest;
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
@RequestMapping("/api/product/color")
@Tag(name = "Product Color Controller")
public class PColorController {

    private final PColorService pColorService;

    @PostMapping
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "bearAuth")
    @Operation(
            summary = "Create new product color",
            description = "This endpoint is specifically for administrators to create new product color"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201", description = "Create successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PColorDTO.class),
                                    examples = {
                                            @ExampleObject(value = "{\"id\":3,\"name\":\"Space Gray\",\"code\":\"space-gray\",\"createdAt\":\"2024-01-11T22:06:02.5551558\",\"createdBy\":\"votruonglam2109@gmail.com\",\"lastModifiedAt\":\"2024-01-11T22:06:02.5551558\",\"lastModifiedBy\":\"votruonglam2109@gmail.com\"}")
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
                                            @ExampleObject(value = "{\"msg\":\"The product color with name [Black] already exists\",\"code\":409,\"status\":\"Conflict\"}")
                                    }
                            )
                    })
    })
    public ResponseEntity<PColorDTO> createProductColor(
            @Valid @RequestBody PColorCreateRequest request
    ){
        return new ResponseEntity<>(
                pColorService.createColor(request),
                HttpStatus.CREATED
        );
    }

    @PutMapping("{id}")
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "bearAuth")
    @Operation(
            summary = "Update product color by its id",
            description = "This endpoint is specifically for administrators to update product color by its id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Update successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PColorDTO.class),
                                    examples = {
                                            @ExampleObject(value = "{\"id\":3,\"name\":\"Space Gray\",\"code\":\"space-gray\",\"createdAt\":\"2024-01-11T22:06:02.5551558\",\"createdBy\":\"votruonglam2109@gmail.com\",\"lastModifiedAt\":\"2024-01-11T22:06:02.5551558\",\"lastModifiedBy\":\"votruonglam2109@gmail.com\"}")
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
                                            @ExampleObject(value = "{\"msg\":\"The product color with id [1] not found\",\"code\":404,\"status\":\"Not found\"}")
                                    }
                            )
                    })
    })
    public ResponseEntity<PColorDTO> updateProductColor(
            @PathVariable Long id,
            @Valid @RequestBody PColorCreateRequest request
    ){
        return ResponseEntity.ok(
                pColorService.updateProductColor(id, request)
        );
    }

    @DeleteMapping("{id}")
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "bearAuth")
    @Operation(
            summary = "Delete product color by its id",
            description = "This endpoint is specifically for administrators to delete product color by its id"
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
                                            @ExampleObject(value = "{\"msg\":\"The product color with id [1] not found\",\"code\":404,\"status\":\"Not found\"}")
                                    }
                            )
                    })
    })
    public ResponseEntity<Void> deleteProductColor(
            @PathVariable Long id
    ){
        pColorService.deleteProductColor(id);
        return new ResponseEntity<>(
                HttpStatus.NO_CONTENT
        );
    }

    @GetMapping("{id}")
    @Operation(
            summary = "Retrieve product color information by its id",
            description = "This endpoint is used to retrieve product color information by its id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Retrieve successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PColorDTO.class),
                                    examples = {
                                            @ExampleObject(value = "{\"id\":1,\"name\":\"Black\",\"code\":\"black\",\"createdAt\":\"2024-01-11T19:37:43.5273479\",\"createdBy\":\"votruonglam2109@gmail.com\",\"lastModifiedAt\":\"2024-01-11T19:37:43.5273479\",\"lastModifiedBy\":\"votruonglam2109@gmail.com\"}")
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
                                            @ExampleObject(value = "{\"msg\":\"The product color with id [1] not found\",\"code\":404,\"status\":\"Not found\"}")
                                    }
                            )
                    })
    })
    public ResponseEntity<PColorDTO> getProductColor(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(
                pColorService.getProductCategory(id)
        );
    }

    @GetMapping("/all")
    @Operation(
            summary = "Retrieve all product colors",
            description = "This endpoint is used to retrieve all product colors"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Retrieve successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PColorDTO.class)),
                                    examples = {
                                            @ExampleObject(value = "[{\"id\":2,\"name\":\"White\",\"code\":\"white\",\"createdAt\":\"2024-01-11T19:43:15.671698\",\"createdBy\":\"votruonglam2109@gmail.com\",\"lastModifiedAt\":\"2024-01-11T19:43:15.671698\",\"lastModifiedBy\":\"votruonglam2109@gmail.com\"},{\"id\":1,\"name\":\"Black\",\"code\":\"black\",\"createdAt\":\"2024-01-11T19:37:43.527348\",\"createdBy\":\"votruonglam2109@gmail.com\",\"lastModifiedAt\":\"2024-01-11T19:37:43.527348\",\"lastModifiedBy\":\"votruonglam2109@gmail.com\"}]")
                                    }
                            )
                    })
    })
    public ResponseEntity<List<PColorDTO>> getAllProductColors(){
        return ResponseEntity.ok(
                pColorService.getAllProductCategories()
        );
    }
}
