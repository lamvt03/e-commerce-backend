package com.ecommerce.cart;

import com.ecommerce.cart.model.CartDTO;
import com.ecommerce.cart.request.CProductAddRequest;
import com.ecommerce.cart.request.CProductDeleteRequest;
import com.ecommerce.coupon.request.CouponApplyRequest;
import com.ecommerce.exception.ErrResponse;
import com.ecommerce.user.model.User;
import com.ecommerce.util.model.PaginationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart")
@Secured("ROLE_USER")
@SecurityRequirement(name = "bearAuth")
@Tag(name = "Cart Controller")
public class CartController {

    private final CartService cartService;

    @PostMapping
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CProductAddRequest.class),
                    examples = {
                            @ExampleObject(value = "{\"productId\":1,\"quantity\":1,\"colorId\":2}")
                    }
            )
    )
    @Operation(
            summary = "Add a product to cart",
            description = "This endpoint is specifically for authenticated user to add a product to his/her cart"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Adding successfully ",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CartDTO.class),
                                    examples = {
                                            @ExampleObject(value = "{\"products\":[{\"id\":1,\"productId\":1,\"title\":\"iPhone X\",\"description\":\"Apple iPhone X, Fully Unlocked 5.8, 256GB - Space Gray, Black, White\",\"color\":\"White\",\"price\":1104.0,\"quantity\":1,\"poster\":\"http://res.cloudinary.com/ds2gtlowx/image/upload/v1704988095/guzrsgqurybqvsj1xogm.jpg\"}],\"coupon\":{},\"total\":1104.0,\"totalAfterDiscount\":1104.0}")
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
    public ResponseEntity<CartDTO> addProductCart(
            @AuthenticationPrincipal User user,
            @RequestBody CProductAddRequest request
    ){
        CartDTO cartDTO = cartService.addCart(user.getId(), request);
        return ResponseEntity.ok(cartDTO);
    }

    @GetMapping
    @Operation(
            summary = "Retrieve user cart",
            description = "This endpoint is specifically for authenticated user to retrieve his/her cart"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Retrieve successfully ",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CartDTO.class),
                                    examples = {
                                            @ExampleObject(value = "{\"products\":[{\"id\":2,\"productId\":1,\"title\":\"iPhoneX\",\"description\":\"Apple iPhone X, Fully Unlocked 5.8, 256GB - Space Gray, Black, White\",\"color\":\"Black\",\"price\":1104.0,\"quantity\":2,\"poster\":\"http://res.cloudinary.com/ds2gtlowx/image/upload/v1704988095/guzrsgqurybqvsj1xogm.jpg\"},{\"id\":1,\"productId\":1,\"title\":\"iPhone X\",\"description\":\"Apple iPhone X, Fully Unlocked 5.8, 256GB - Space Gray, Black, White\",\"color\":\"White\",\"price\":1104.0,\"quantity\":1,\"poster\":\"http://res.cloudinary.com/ds2gtlowx/image/upload/v1704988095/guzrsgqurybqvsj1xogm.jpg\"}],\"coupon\":{},\"total\":3312.0,\"totalAfterDiscount\":3312.0}")
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
    public ResponseEntity<CartDTO> getCart(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int limit,
        @RequestParam(defaultValue = "desc", name = "sort") String sortDirection,
        @RequestParam(defaultValue = "createdAt") String sortBy
    ){
        PaginationDTO paginationDTO = new PaginationDTO(page, limit, sortDirection, sortBy);
        CartDTO cartDTO = cartService.getCart(user.getId(), paginationDTO);
        return ResponseEntity.ok(cartDTO);
    }


    @DeleteMapping("/product")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CProductDeleteRequest.class),
                    examples = {
                            @ExampleObject(value = "{\"ids\":[1]}")
                    }
            )
    )
    @Operation(
            summary = "Delete products from user cart",
            description = "This endpoint is specifically for authenticated user to delete products from his/her cart"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Delete successfully ",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CartDTO.class),
                                    examples = {
                                            @ExampleObject(value = "{\"products\":[{\"id\":2,\"productId\":1,\"title\":\"iPhone X\",\"description\":\"Apple iPhone X, Fully Unlocked 5.8, 256GB - Space Gray, Black, White\",\"color\":\"Black\",\"price\":1104.0,\"quantity\":2,\"poster\":\"http://res.cloudinary.com/ds2gtlowx/image/upload/v1704988095/guzrsgqurybqvsj1xogm.jpg\"}],\"coupon\": {},\"total\":2208.0,\"totalAfterDiscount\":2208.0}")
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
    public ResponseEntity<CartDTO> deleteCartProduct(
            @AuthenticationPrincipal User user,
            @RequestBody CProductDeleteRequest request
    ){
        CartDTO cartDTO = cartService.deleteCartProduct(user.getId(), request);
        return ResponseEntity.ok(cartDTO);
    }

    @PostMapping("/apply")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CouponApplyRequest.class),
                    examples = {
                            @ExampleObject(value = "{\"code\":\"TmOIEiLOJmgk\"}")
                    }
            )
    )
    @Operation(
            summary = "Apply a coupon to cart",
            description = "This endpoint is specifically for authenticated user to add available coupon code to his/her cart"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Apply successfully ",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CartDTO.class),
                                    examples = {
                                            @ExampleObject(value = "{\"products\":[{\"id\":2,\"productId\":1,\"title\":\"iPhone X\",\"description\":\"Apple iPhone X, Fully Unlocked 5.8, 256GB - Space Gray, Black, White\",\"color\":\"Black\",\"price\":1104.0,\"quantity\":2,\"poster\":\"http://res.cloudinary.com/ds2gtlowx/image/upload/v1704988095/guzrsgqurybqvsj1xogm.jpg\"}],\"coupon\":{\"code\":\"TmOIEiLOJmgk\",\"name\":\"BLACK_FRIDAY\",\"discount\":\"10%\"},\"total\":2208.0,\"totalAfterDiscount\":1987.2}")
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
                                            @ExampleObject(value = "{\"msg\":\"Your cart has been applied coupon already\",\"code\":400,\"status\":\"Bad Request\"}"),
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
    public ResponseEntity<CartDTO> applyCoupon(
            @AuthenticationPrincipal User user,
            @RequestBody CouponApplyRequest request
            ){
        CartDTO cartDTO = cartService.applyCoupon(user.getId(), request);
        return ResponseEntity.ok(cartDTO);
    }
}
