package com.ecommerce.cart;

import com.ecommerce.cart.request.CartProductRequest;
import com.ecommerce.coupon.request.CouponApplyRequest;
import com.ecommerce.user.model.User;
import com.ecommerce.util.model.PaginationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    @PostMapping
    public ResponseEntity<CartDTO> addProductCart(
            @AuthenticationPrincipal User user,
            @RequestBody CartProductRequest request
    ){
        CartDTO cartDTO = cartService.addCart(user.getId(), request);
        return ResponseEntity.ok(cartDTO);
    }
    @GetMapping
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

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<CartDTO> deleteCartProduct(
            @AuthenticationPrincipal User user,
            @PathVariable Long productId

    ){
        CartDTO cartDTO = cartService.deleteCartProduct(user.getId(), productId);
        return ResponseEntity.ok(cartDTO);
    }
    @PostMapping("/apply")
    public ResponseEntity<CartDTO> applyCoupon(
            @AuthenticationPrincipal User user,
            @RequestBody CouponApplyRequest request
            ){
        CartDTO cartDTO = cartService.applyCoupon(user.getId(), request);
        return ResponseEntity.ok(cartDTO);
    }
}
