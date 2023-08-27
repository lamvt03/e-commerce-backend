package com.ecommerce.user.controller;

import com.ecommerce.cart.request.CartRequest;
import com.ecommerce.coupon.CouponApplyRequest;
import com.ecommerce.order.OrderCreateRequest;
import com.ecommerce.user.model.*;
import com.ecommerce.user.model.request.*;
import com.ecommerce.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("register")
    public UserDTO registerUser (@RequestBody UserRegistration u) {
        return userService.registerUser(u);
    }

    @GetMapping("role")
    public String role(){
        return "Role admin";
    }

    @GetMapping("{id}")
    public UserDTO getUser(@PathVariable Long id){
        UserDTO u = userService.getUser(id);
        return u;
    }

    @DeleteMapping("{id}")
    public UserDTO deleteUser(@PathVariable Long id){
        UserDTO u = userService.deleteUser(id);
        return u;
    }

    @PostMapping("change-password")
    public UserDTO changePassword(
            @AuthenticationPrincipal User authenticatedUser,
            @RequestBody UserPasswordChange userPasswordChange
    ){
        return userService.changePassword(authenticatedUser, userPasswordChange);
    }
    @PostMapping("forgot-password")
    public String forgotPassword(@RequestBody UserPasswordForgot userPasswordForgot){
        return userService.handleForgotPassword(userPasswordForgot.email());
    }
    @PostMapping("reset-password")
    public String resetPassword(
            @RequestBody UserPasswordReset userPasswordReset
    ){
        return userService.handleResetPassword(userPasswordReset);
    }

    @GetMapping("wishlist")
    public ResponseEntity<?> getWishlist(
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(
                userService.getWishlist(user.getId())
        );
    }

    @PostMapping("cart")
    public ResponseEntity<?> addCart(
            @AuthenticationPrincipal User user,
            @RequestBody CartRequest request
            ){

        return ResponseEntity.ok(
                userService.addCart(user.getId(), request)
        );
    }
    @GetMapping("cart")
    public ResponseEntity<?> getCart(
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(
                userService.getCart(user.getId())
        );
    }

    @PostMapping("/apply-coupon")
    public ResponseEntity<?> applyCoupon(
            @AuthenticationPrincipal User user,
            @RequestBody CouponApplyRequest request
            ){
        return ResponseEntity.ok(
                userService.applyCoupon(user.getId(), request)
        );
    }

    @PostMapping("order")
    public ResponseEntity<?> createOrder(
            @AuthenticationPrincipal User user,
            @RequestBody OrderCreateRequest request
    ){
        return ResponseEntity.ok(
                userService.createOrder(user.getId(), request)
        );
    }

    @GetMapping("orders")
    public ResponseEntity<?> getOrders(
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(
                userService.getOrders(user.getId())
        );
    }
}
