package com.ecommerce.order;

import com.ecommerce.order.model.OrderDTO;
import com.ecommerce.order.request.OrderCreateRequest;
import com.ecommerce.user.model.User;
import com.ecommerce.util.model.PaginationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(
            @AuthenticationPrincipal User user,
            @RequestBody OrderCreateRequest request
    ){
        OrderDTO orderDTO = orderService.createOrder(user.getId(), request);
        return ResponseEntity.ok(orderDTO);
    }

    @GetMapping
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
