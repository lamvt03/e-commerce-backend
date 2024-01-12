package com.ecommerce.coupon;

import com.ecommerce.coupon.request.CouponCreateRequest;
import com.ecommerce.exception.ErrResponse;
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
@RequestMapping("/api/coupon")
@Tag(name = "Coupon Controller")
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "bearAuth")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CouponCreateRequest.class),
                    examples = {
                            @ExampleObject(value = "{\"name\":\"BLACK_FRIDAY\",\"lifeCycle\":1,\"discount\":10,\"quantity\":100}")
                    }
            )
    )
    @Operation(
            summary = "Create new coupon",
            description = "This endpoint is specifically for administrators to create new coupon"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201", description = "Create successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CouponDTO.class),
                                    examples = {
                                            @ExampleObject(value = "{\"id\":1,\"name\":\"BLACK_FRIDAY\",\"code\":\"GKUBGNtrSuoa\",\"quantity\":100,\"expiryAt\":\"2024-01-12T00:00:00\",\"discount\":10,\"createdAt\":\"2024-01-11T20:03:04.0187211\",\"lastModifiedAt\":\"2024-01-11T20:03:04.0187211\"}")
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
                    responseCode = "400", description = "Bad Request",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"msg\":{\"quantity\":\"quantity must be greater than zero\",\"name\":\"nameismandatory\",\"discount\":\"discount must be greater than zero\",\"lifeCycle\":\"life cycle must be greater than zero\"},\"code\":400,\"status\":\"BadRequest\"}")
                                    }
                            )
                    }),
    })
    public ResponseEntity<CouponDTO> createCoupon(
            @Valid @RequestBody CouponCreateRequest request
    ){
        CouponDTO couponDTO = couponService.createCoupon(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(couponDTO);
    }

    @GetMapping("{id}")
    @Operation(
            summary = "Retrieve coupon information by its id",
            description = "This endpoint is used to retrieve coupon information by its id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Retrieve successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CouponDTO.class),
                                    examples = {
                                            @ExampleObject(value = "{\"id\":1,\"name\":\"BLACK_FRIDAY\",\"code\":\"GKUBGNtrSuoa\",\"quantity\":100,\"expiryAt\":\"2024-01-12T00:00:00\",\"discount\":10,\"createdAt\":\"2024-01-11T20:03:04.0187211\",\"lastModifiedAt\":\"2024-01-11T20:03:04.0187211\"}")
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
                                            @ExampleObject(value = "{\"msg\":\"The coupon with id [1] not found\",\"code\":404,\"status\":\"Not Found\"}")
                                    }
                            )
                    })
    })
    public ResponseEntity<CouponDTO> getCoupon(
            @PathVariable Long id
    ){
        CouponDTO couponDTO = couponService.getCouponById(id);
        return ResponseEntity.ok(couponDTO);
    }

    @PutMapping("{id}")
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "bearAuth")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CouponCreateRequest.class),
                    examples = {
                            @ExampleObject(value = "{\"name\":\"BLACK_FRIDAY\",\"lifeCycle\":1,\"discount\":10,\"quantity\":100}")
                    }
            )
    )
    @Operation(
            summary = "Update coupon by its id",
            description = "This endpoint is specifically for administrators to update coupon by its id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Update successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CouponDTO.class),
                                    examples = {
                                            @ExampleObject(value = "{\"id\":1,\"name\":\"BLACK_FRIDAY\",\"code\":\"GKUBGNtrSuoa\",\"quantity\":100,\"expiryAt\":\"2024-01-12T00:00:00\",\"discount\":10,\"createdAt\":\"2024-01-11T20:03:04.0187211\",\"lastModifiedAt\":\"2024-01-11T20:03:04.0187211\"}")
                                    }
                            )
                    }),
            @ApiResponse(
                    responseCode = "400", description = "Bad Request",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"msg\":{\"quantity\":\"quantity must be greater than zero\",\"name\":\"nameismandatory\",\"discount\":\"discount must be greater than zero\",\"lifeCycle\":\"life cycle must be greater than zero\"},\"code\":400,\"status\":\"BadRequest\"}")
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
    public ResponseEntity<CouponDTO> updateCoupon(
            @PathVariable Long id,
            @RequestBody CouponCreateRequest request
    ){
        CouponDTO couponDTO = couponService.updateCoupon(id, request);
        return ResponseEntity.ok(couponDTO);
    }

    @DeleteMapping("{id}")
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "bearAuth")
    @Operation(
            summary = "Delete coupon by its id",
            description = "This endpoint is specifically for administrators to delete coupon by its id"
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
    public ResponseEntity<Void> deleteCoupon(
            @PathVariable Long id
    ){
        couponService.deleteCoupon(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/all")
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "bearAuth")
    @Operation(
            summary = "Retrieve all product coupons",
            description = "This endpoint is specifically for administrators to retrieve all product coupons"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Retrieve successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = CouponDTO.class)),
                                    examples = {
                                            @ExampleObject(value = "[{\"id\":1,\"name\":\"BLACK_FRIDAY\",\"code\":\"GKUBGNtrSuoa\",\"quantity\":100,\"expiryAt\":\"2024-01-12T00:00:00\",\"discount\":10,\"createdAt\":\"2024-01-11T20:03:04.018721\",\"lastModifiedAt\":\"2024-01-11T20:03:04.018721\"}]")
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
    public ResponseEntity<List<CouponDTO>> getAllCoupons(){
        List<CouponDTO> couponDTOS = couponService.getAllCoupons();
        return ResponseEntity.ok(couponDTOS);
    }
}
