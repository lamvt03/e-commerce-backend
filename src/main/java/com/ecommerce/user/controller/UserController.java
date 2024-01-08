package com.ecommerce.user.controller;

import com.ecommerce.product.ProductService;
import com.ecommerce.product.model.ProductDTO;
import com.ecommerce.product.model.request.AddWishlistRequest;
import com.ecommerce.product.model.request.RatingRequest;
import com.ecommerce.user.favorite.FavoriteDTO;
import com.ecommerce.user.favorite.FavoriteService;
import com.ecommerce.user.model.*;
import com.ecommerce.user.model.request.*;
import com.ecommerce.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<UserDTO> getUser(@AuthenticationPrincipal User user){
        UserDTO userDTO = userService.getUser(user.getId());
        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping("{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/password/change")
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
    @Secured("ROLE_USER")
    public ResponseEntity<FavoriteDTO> likeProduct(
            @AuthenticationPrincipal User user,
            @PathVariable Long productId
    ){
        FavoriteDTO favoriteDTO = favoriteService.likeProduct(user, productId);
        return ResponseEntity.ok(favoriteDTO);
    }

    @PostMapping("/rating")
    @Secured("ROLE_USER")
    public ResponseEntity<ProductDTO> ratingProduct(
            @AuthenticationPrincipal User user,
            @RequestBody RatingRequest request
    ){
        ProductDTO productDTO = productService.ratingProduct(user,request);
        return ResponseEntity.ok(productDTO);
    }

    @GetMapping("/favorite")
    @Secured("ROLE_USER")
    public ResponseEntity<List<ProductDTO>> getFavoriteProducts(
            @AuthenticationPrincipal User user
    ){
        List<ProductDTO> favoriteProducts = favoriteService.getFavoriteProducts(user.getId());
        return ResponseEntity.ok(favoriteProducts);
    }

    @GetMapping("/wishlist")
    @Secured("ROLE_USER")
    public ResponseEntity<Set<ProductDTO>> getWishlist(
            @AuthenticationPrincipal User user
    ){
        Set<ProductDTO> wishlist = userService.getWishlist(user.getId());
        return ResponseEntity.ok(wishlist);
    }

    @PutMapping("/wishlist")
    @Secured("ROLE_USER")
    public ResponseEntity<UserDTO> addToWishList(
            @AuthenticationPrincipal User user,
            @RequestBody AddWishlistRequest request
    ){
        UserDTO userDTO = productService.addToWishlist(user.getId(),request);
        return ResponseEntity.ok(userDTO);
    }
}
