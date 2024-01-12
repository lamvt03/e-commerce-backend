package com.ecommerce.order;

import com.ecommerce.exception.ErrResponse;
import com.ecommerce.order.model.OrderDTO;
import com.ecommerce.order.request.OrderCreateRequest;
import com.ecommerce.user.model.User;
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
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
@SecurityRequirement(name = "bearAuth")
@Secured("ROLE_USER")
@Tag(name = "Order Controller")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OrderCreateRequest.class),
                    examples = {
                            @ExampleObject(value = "{\"method\":1,\"couponApplied\":true}")
                    }
            )
    )
    @Operation(
            summary = "Create order from user cart",
            description = "This endpoint is specifically for authenticated user to create order from his/her cart"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Create successfully ",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrderDTO.class),
                                    examples = {
                                            @ExampleObject(value = "{\"id\":1,\"products\":[{\"id\":1,\"productId\":1,\"title\":\"iPhone X\",\"color\":\"Black\",\"price\":1104.0,\"quantity\":2,\"poster\":\"http://res.cloudinary.com/ds2gtlowx/image/upload/v1704988095/guzrsgqurybqvsj1xogm.jpg\"}],\"status\":\"Processing\",\"paymentIntent\":{\"id\":1,\"method\":\"CASH_ON_DELIVERY\",\"amount\":1987.2,\"currency\":\"USD\",\"createdAt\":\"2024-01-12T15:24:15.3782039\"},\"createdAt\":\"2024-01-12T15:24:15.4782043\"}")
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
    public ResponseEntity<OrderDTO> createOrder(
            @AuthenticationPrincipal User user,
            @RequestBody OrderCreateRequest request
    ){
        OrderDTO orderDTO = orderService.createOrder(user.getId(), request);
        return ResponseEntity.ok(orderDTO);
    }

    @GetMapping
    @Operation(
            summary = "Retrieve user orders",
            description = "This endpoint is specifically for authenticated user to retrieve his/her orders"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Create successfully ",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = OrderDTO.class)),
                                    examples = {
                                            @ExampleObject(value = "[{\"id\":1,\"products\":[{\"id\":1,\"productId\":1,\"title\":\"iPhone X\",\"color\":\"Black\",\"price\":1104.0,\"quantity\":2,\"poster\":\"http://res.cloudinary.com/ds2gtlowx/image/upload/v1704988095/guzrsgqurybqvsj1xogm.jpg\"}],\"status\":\"Processing\",\"paymentIntent\":{\"id\":1,\"method\":\"CASH_ON_DELIVERY\",\"amount\":1987.2,\"currency\":\"USD\",\"createdAt\":\"2024-01-12T15:24:15.3782039\"},\"createdAt\":\"2024-01-12T15:24:15.4782043\"}]")
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
    public ResponseEntity<List<OrderDTO>> getOrders(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(defaultValue = "desc", name = "sort") String sortDirection,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @AuthenticationPrincipal User user
    ){
        PaginationDTO paginationDTO = new PaginationDTO(page, limit, sortDirection, sortBy);
        List<OrderDTO> orders = orderService.getOrders(user.getId(), paginationDTO);
        return ResponseEntity.ok(orders);
    }
}
