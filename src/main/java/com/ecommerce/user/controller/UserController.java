package com.ecommerce.user.controller;

import com.ecommerce.exception.ErrResponse;
import com.ecommerce.product.ProductService;
import com.ecommerce.product.model.ProductDTO;
import com.ecommerce.product.model.request.AddWishlistRequest;
import com.ecommerce.product.model.request.RatingRequest;
import com.ecommerce.user.favorite.FavoriteDTO;
import com.ecommerce.user.favorite.FavoriteService;
import com.ecommerce.user.model.User;
import com.ecommerce.user.model.UserDTO;
import com.ecommerce.user.model.request.UserPasswordChange;
import com.ecommerce.user.model.request.UserPasswordForgot;
import com.ecommerce.user.model.request.UserPasswordReset;
import com.ecommerce.user.service.UserService;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
@Tag(name = "User Controller")
public class UserController {

    private final UserService userService;
    private final FavoriteService favoriteService;
    private final ProductService productService;

    @GetMapping("/profile")
    @SecurityRequirement(name = "bearAuth")
    @Operation(
            summary = "Retrieve user profile",
            description = "This endpoint retrieves user profile based on the authenticated user's credentials"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Retrieve the user profile successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class),
                                    examples = {
                                            @ExampleObject(value = "{\"firstName\":\"Vo\",\"lastName\":\"Truong Lam\",\"email\":\"truonglam.113.147@gmail.com\",\"mobile\":\"0886338218\",\"wishlist\":[]}")
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
                    })
    })
    public ResponseEntity<UserDTO> getUserProfile(@AuthenticationPrincipal User user){
        UserDTO userDTO = userService.getUser(user.getId());
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/restore/{id}")
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "bearAuth")
    @Operation(
            summary = "Restore a user by his/her id",
            description = "This endpoint is specifically for administrators to restore a user by his/her id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Restore successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class),
                                    examples = {
                                            @ExampleObject(value = "{\"firstName\":\"Vo\",\"lastName\":\"Truong Lam\",\"email\":\"truonglam.113.147@gmail.com\",\"mobile\":\"0886338218\",\"wishlist\":[]}")
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
                                            @ExampleObject(value = "{\"msg\":\"The user with id [1] not exists\",\"code\":404,\"status\":\"Not found\"}")
                                    }
                            )
                    })
    })
    public ResponseEntity<UserDTO> restoreUser(@PathVariable Long id){
        UserDTO user = userService.restoreUserById(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("{id}")
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "bearAuth")
    @Operation(
            summary = "Delete a user by his/her id",
            description = "This endpoint is specifically for administrators to delete a user by his/her id"
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
                                            @ExampleObject(value = "{\"msg\":\"The user with id [1] not exists\",\"code\":404,\"status\":\"Not found\"}")
                                    }
                            )
                    })
    })
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/password/change")
    @Secured("ROLE_USER")
    @SecurityRequirement(name = "bearAuth")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserPasswordChange.class),
                    examples = {
                            @ExampleObject(value = "{\"oldPassword\":\"21092003@\",\"newPassword\":\"Vtl@m2109\"}")
                    }
            )
    )
    @Operation(
            summary = "Change user password",
            description = "This endpoint is specifically for authenticated user to change his/her account password"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Change user password successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class),
                                    examples = {
                                            @ExampleObject(value = "{\"firstName\":\"Vo\",\"lastName\":\"Truong Lam\",\"email\":\"truonglam.113.147@gmail.com\",\"mobile\":\"0886338218\",\"wishlist\":[]}")
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
                    responseCode = "404", description = "Resource not found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"msg\":\"The user with id [1] not exists\",\"code\":404,\"status\":\"Not found\"}")
                                    }
                            )
                    })
    })
    public ResponseEntity<UserDTO> changePassword(
            @AuthenticationPrincipal User authenticatedUser,
            @Valid @RequestBody UserPasswordChange userPasswordChange
    ){
        UserDTO userDTO = userService.changePassword(authenticatedUser, userPasswordChange);
        return ResponseEntity.ok(userDTO);
    }


    @PostMapping("password/forgot")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserPasswordForgot.class),
                    examples = {
                            @ExampleObject(value = "{\"email\":\"truonglam.113.147@gmail.com\"}")
                    }
            )
    )
    @Operation(
            summary = "Forgot user password",
            description = "This endpoint is used to send otp code to user email"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Send otp to user email successfully"),
            @ApiResponse(
                    responseCode = "404", description = "Resource not found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"msg\":\"The user with email [truonglam.113.147@gmail.com] not exists\",\"code\":404,\"status\":\"Not found\"}")
                                    }
                            )
                    })
    })
    public ResponseEntity<Void> forgotPassword(
            @Valid @RequestBody UserPasswordForgot userPasswordForgot
    ){
        userService.handleForgotPassword(userPasswordForgot);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PostMapping("password/reset")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserPasswordReset.class),
                    examples = {
                            @ExampleObject(value = "{\"email\":\"truonglam.113.147@gmail.com\",\"otpCode\":\"658556\",\"newPassword\":\"Vtl@m2109\"}")
                    }
            )
    )
    @Operation(
            summary = "Reset user password",
            description = "This endpoint is used to reset user password when otp code is valid"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reset user password successfully"),
            @ApiResponse(
                    responseCode = "404", description = "Resource not found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"msg\":\"The user with email [truonglam.113.147@gmail.com] not exists\",\"code\":404,\"status\":\"Not found\"}")
                                    }
                            )
                    }),
            @ApiResponse(
                    responseCode = "400", description = "OTP code not valid",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"msg\":\"OTP code not valid\",\"code\":400,\"status\":\"Bad Request\"}")
                                    }
                            )
                    })
    })
    public ResponseEntity<Void> resetPassword(
            @Valid @RequestBody UserPasswordReset userPasswordReset
    ){
        userService.handleResetPassword(userPasswordReset);
        return ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .build();
    }

    @PostMapping("/like/{productId}")
    @Secured("ROLE_USER")
    @SecurityRequirement(name = "bearAuth")
    @Operation(
            summary = "Like a product",
            description = "This endpoint is specifically for authenticated user to like a product"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = FavoriteDTO.class),
                                    examples = {
                                            @ExampleObject(value = "{\"isLiked\":true,\"lastModifiedAt\":\"2024-01-11T23:25:50.4948606\",\"productId\":1}")
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
                    responseCode = "404", description = "Resource not found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"msg\":\"The product with id [1] not exists\",\"code\":404,\"status\":\"Not found\"}")
                                    }
                            )
                    })
    })
    public ResponseEntity<FavoriteDTO> likeProduct(
            @AuthenticationPrincipal User user,
            @PathVariable Long productId
    ){
        FavoriteDTO favoriteDTO = favoriteService.likeProduct(user, productId);
        return ResponseEntity.ok(favoriteDTO);
    }

    @PostMapping("/rating")
    @Secured("ROLE_USER")
    @SecurityRequirement(name = "bearAuth")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RatingRequest.class),
                    examples = {
                            @ExampleObject(value = "{\"star\":5,\"comment\":\"excellent\",\"productId\":1}")
                    }
            )
    )
    @Operation(
            summary = "Rating a product",
            description = "This endpoint is specifically for authenticated user to rating a product"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDTO.class),
                                    examples = {
                                            @ExampleObject(value = "{\"id\":1,\"title\":\"iPhone X\",\"slug\":\"iphone-x\",\"description\":\"Apple iPhone X, Fully Unlocked 5.8, 256GB - Space Gray, Black, White\",\"price\":1104.0,\"quantity\":12,\"sold\":0,\"category\":\"Smartphone\",\"brand\":\"Apple\",\"colors\":[\"White\",\"Space Gray\"],\"lastModifiedAt\":\"2024-01-11T23:37:49.6442481\",\"ratingPoint\":5.0,\"isLiked\":true,\"ratings\":[{\"star\":5,\"comment\":\"excellent\",\"createdAt\":\"2024-01-11T23:37:49.6172474\",\"email\":\"truonglam.113.147@gmail.com\",\"name\":\"VoTruongLam\",\"isModified\":false}],\"images\":[{\"url\":\"http://res.cloudinary.com/ds2gtlowx/image/upload/v1704988095/guzrsgqurybqvsj1xogm.jpg\",\"assetId\":\"7a17b89188eaad6015f15bb094cdea36\",\"publicId\":\"guzrsgqurybqvsj1xogm\"}]}")
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
                    responseCode = "404", description = "Resource not found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"msg\":\"The product with id [1] not exists\",\"code\":404,\"status\":\"Not found\"}")
                                    }
                            )
                    })
    })
    public ResponseEntity<ProductDTO> ratingProduct(
            @AuthenticationPrincipal User user,
            @RequestBody RatingRequest request
    ){
        ProductDTO productDTO = productService.ratingProduct(user,request);
        return ResponseEntity.ok(productDTO);
    }

    @GetMapping("/favorite")
    @Secured("ROLE_USER")
    @SecurityRequirement(name = "bearAuth")
    @Operation(
            summary = "Retrieve favorite list",
            description = "This endpoint is specifically for authenticated user to retrieve favorite product list"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class)),
                                    examples = {
                                            @ExampleObject(value = "[{\"id\":1,\"title\":\"iPhone X\",\"slug\":\"iphone-x\",\"description\":\"Apple iPhone X,Fully Unlocked 5.8, 256GB - Space Gray, Black, White\",\"price\":1104.0,\"quantity\":12,\"sold\":0,\"category\":\"Smartphone\",\"brand\":\"Apple\",\"colors\":[\"White\",\"Space Gray\"],\"lastModifiedAt\":\"2024-01-11T23:37:49.679257\",\"ratingPoint\":5.0,\"isLiked\":true,\"ratings\":[{\"star\":5,\"comment\":\"excellent\",\"createdAt\":\"2024-01-11T23:37:49.617247\",\"email\":\"truonglam.113.147@gmail.com\",\"name\":\"VoTruongLam\",\"isModified\":false}],\"images\":[{\"url\":\"http://res.cloudinary.com/ds2gtlowx/image/upload/v1704988095/guzrsgqurybqvsj1xogm.jpg\",\"assetId\":\"7a17b89188eaad6015f15bb094cdea36\",\"publicId\":\"guzrsgqurybqvsj1xogm\"}]},{\"id\":2,\"title\":\"iPhone SE 5G\",\"slug\":\"iphone-se-5g\",\"description\":\"Tracfone Apple iPhone SE 5G (3rdGeneration), 64GB, Black - Prepaid Smartphone (Locked)\",\"price\":189.0,\"quantity\":8,\"sold\":0,\"category\":\"Smartphone\",\"brand\":\"Apple\",\"colors\":[\"Black\"],\"lastModifiedAt\":\"2024-01-11T22:29:58.013168\",\"ratingPoint\":0.0,\"isLiked\":true,\"ratings\":[],\"images\":[]}]")
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
                    })
    })
    public ResponseEntity<List<ProductDTO>> getFavoriteProducts(
            @AuthenticationPrincipal User user
    ){
        List<ProductDTO> favoriteProducts = favoriteService.getFavoriteProducts(user.getId());
        return ResponseEntity.ok(favoriteProducts);
    }

    @GetMapping("/wishlist")
    @Secured("ROLE_USER")
    @SecurityRequirement(name = "bearAuth")
    @Operation(
            summary = "Retrieve wish list",
            description = "This endpoint is specifically for authenticated user to retrieve wish list"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class)),
                                    examples = {
                                            @ExampleObject(value = "[{\"id\":1,\"title\":\"iPhone X\",\"slug\":\"iphone-x\",\"description\":\"Apple iPhoneX ,Fully Unlocked 5.8, 256GB - Space Gray, Black, White\",\"price\":1104.0,\"quantity\":12,\"sold\":0,\"category\":\"Smartphone\",\"brand\":\"Apple\",\"colors\":[\"White\",\"Space Gray\"],\"lastModifiedAt\":\"2024-01-11T23:37:49.679257\",\"ratingPoint\":5.0,\"isLiked\":true,\"ratings\":[{\"star\":5,\"comment\":\"excellent\",\"createdAt\":\"2024-01-11T23:37:49.617247\",\"email\":\"truonglam.113.147@gmail.com\",\"name\":\"VoTruongLam\",\"isModified\":false}],\"images\":[{\"url\":\"http://res.cloudinary.com/ds2gtlowx/image/upload/v1704988095/guzrsgqurybqvsj1xogm.jpg\",\"assetId\":\"7a17b89188eaad6015f15bb094cdea36\",\"publicId\":\"guzrsgqurybqvsj1xogm\"}]}]")
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
                    })
    })
    public ResponseEntity<Set<ProductDTO>> getWishlist(
            @AuthenticationPrincipal User user
    ){
        Set<ProductDTO> wishlist = userService.getWishlist(user.getId());
        return ResponseEntity.ok(wishlist);
    }

    @PutMapping("/wishlist")
    @Secured("ROLE_USER")
    @SecurityRequirement(name = "bearAuth")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AddWishlistRequest.class),
                    examples = {
                            @ExampleObject(value = "{\"productId\":1}")
                    }
            )
    )
    @Operation(
            summary = "Adding to wish list",
            description = "This endpoint is specifically for authenticated user to add to product wish list"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UserDTO.class)),
                                    examples = {
                                            @ExampleObject(value = "{\"firstName\":\"Vo\",\"lastName\":\"Truong Lam\",\"email\":\"truonglam.113.147@gmail.com\",\"mobile\":\"0886338218\",\"wishlist\":[{\"id\":1,\"title\":\"iPhone X\",\"slug\":\"iphone-x\",\"description\":\"Apple iPhone X, Fully Unlocked 5.8, 256GB - Space Gray, Black, White\",\"price\":1104.0,\"quantity\":12,\"sold\":0,\"category\":\"Smartphone\",\"brand\":\"Apple\",\"colors\":[\"White\",\"Space Gray\"],\"lastModifiedAt\":\"2024-01-11T23:37:49.679257\",\"ratingPoint\":5.0,\"isLiked\":true,\"ratings\":[{\"star\":5,\"comment\":\"excellent\",\"createdAt\":\"2024-01-11T23:37:49.617247\",\"email\":\"truonglam.113.147@gmail.com\",\"name\":\"VoTruongLam\",\"isModified\":false}],\"images\":[{\"url\":\"http://res.cloudinary.com/ds2gtlowx/image/upload/v1704988095/guzrsgqurybqvsj1xogm.jpg\",\"assetId\":\"7a17b89188eaad6015f15bb094cdea36\",\"publicId\":\"guzrsgqurybqvsj1xogm\"}]}]}")
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
                    responseCode = "404", description = "Resource not found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"msg\":\"The product with id [1] not exists\",\"code\":404,\"status\":\"Not found\"}")
                                    }
                            )
                    })
    })
    public ResponseEntity<UserDTO> addToWishList(
            @AuthenticationPrincipal User user,
            @RequestBody AddWishlistRequest request
    ){
        UserDTO userDTO = productService.addToWishlist(user.getId(),request);
        return ResponseEntity.ok(userDTO);
    }
}
