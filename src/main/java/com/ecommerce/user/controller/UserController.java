package com.ecommerce.user.controller;

import com.ecommerce.product.model.ProductDTO;
import com.ecommerce.user.favorite.FavoriteDTO;
import com.ecommerce.user.favorite.FavoriteService;
import com.ecommerce.user.model.*;
import com.ecommerce.user.model.request.*;
import com.ecommerce.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final FavoriteService favoriteService;

    @PostMapping("/register")
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

    @PostMapping("/like/{productId}")
    public ResponseEntity<FavoriteDTO> likeProduct(
            @AuthenticationPrincipal User user,
            @PathVariable Long productId
    ){
        FavoriteDTO favoriteDTO = favoriteService.likeProduct(user, productId);
        return ResponseEntity.ok(favoriteDTO);
    }

    @GetMapping("/favorite")
    public ResponseEntity<List<ProductDTO>> getFavoriteProducts(
            @AuthenticationPrincipal User user
    ){
        List<ProductDTO> favoriteProducts = favoriteService.getFavoriteProducts(user.getId());
        return ResponseEntity.ok(favoriteProducts);
    }

    @GetMapping("/wishlist")
    public ResponseEntity<Set<ProductDTO>> getWishlist(
            @AuthenticationPrincipal User user
    ){
        Set<ProductDTO> wishlist = userService.getWishlist(user.getId());
        return ResponseEntity.ok(wishlist);
    }
}
