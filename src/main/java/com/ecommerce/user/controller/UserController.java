package com.ecommerce.user.controller;

import com.ecommerce.cart.request.CartRequest;
import com.ecommerce.coupon.CouponApplyRequest;
import com.ecommerce.order.OrderCreateRequest;
import com.ecommerce.user.model.*;
import com.ecommerce.user.model.request.*;
import com.ecommerce.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("register")
    public ResponseEntity<UserDTO> registerUser (
           @Valid @RequestBody UserRegistration u
    ){
        UserDTO userDTO = userService.registerUser(u);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userDTO);
    }

    @GetMapping("role")
    public String role(){
        return "Role admin";
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id){
        UserDTO userDTO = userService.getUser(id);
        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PatchMapping("/password")
    public ResponseEntity<UserDTO> changePassword(
            @AuthenticationPrincipal User authenticatedUser,
            @Valid @RequestBody UserPasswordChange userPasswordChange
    ){
        UserDTO userDTO = userService.changePassword(authenticatedUser, userPasswordChange);
        return ResponseEntity.ok(userDTO);
    }
    @PostMapping("password/forgot")
    public ResponseEntity<?> forgotPassword(
            @Valid @RequestBody UserPasswordForgot userPasswordForgot
    ){
        userService.handleForgotPassword(userPasswordForgot);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
    @PostMapping("password/reset")
    public ResponseEntity<?> resetPassword(
            @Valid @RequestBody UserPasswordReset userPasswordReset
    ){
        userService.handleResetPassword(userPasswordReset);
        return ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .build();
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
