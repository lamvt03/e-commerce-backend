package com.ecommerce.user.controller;

import com.ecommerce.enquiry.EnquiryDTO;
import com.ecommerce.enquiry.EnquiryService;
import com.ecommerce.exception.ErrResponse;
import com.ecommerce.product.ProductService;
import com.ecommerce.product.model.ProductDTO;
import com.ecommerce.user.model.User;
import com.ecommerce.user.model.request.UserRegistration;
import com.ecommerce.user.service.UserService;
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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
@Secured("ROLE_ADMIN")
@Tag(name = "Admin Controller")
@SecurityRequirement(name = "bearAuth")
public class AdminController {

    private final UserService userService;
    private final ProductService productService;
    private final EnquiryService enquiryService;

    @PostMapping
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserRegistration.class),
                    examples = {
                            @ExampleObject(value = "{\"firstName\":\"Vo\",\"lastName\":\"Lam\",\"email\":\"votruonglam2109@gmail.com\",\"mobile\":\"0886338217\",\"password\":\"21092003@\"}")
                    }
            )
    )
    @Operation(
            summary = "Create a new admin",
            description = "This endpoint is specifically for administrators to create a new admin"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201", description = "Create admin successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = User.class),
                                    examples = {
                                            @ExampleObject(value = "{\"id\":1,\"firstName\":\"Vo\",\"lastName\":\"Lam\",\"email\":\"votruonglam2109@gmail.com\",\"mobile\":\"0886338217\",\"role\":\"ADMIN\",\"createdAt\":\"2024-01-11T17:48:10.221256\",\"username\":\"votruonglam2109@gmail.com\",\"credentialsNonExpired\":true,\"accountNonExpired\":true,\"fullname\":\"Vo Lam\",\"enable\":true,\"nonLocked\":true}")
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
                    responseCode = "409", description = "Duplicate resource",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"msg\":\"The admin with email [votruonglam2109@gmail.com] already exists\",\"code\":409,\"status\":\"Conflict\"}")
                                    }
                            )
                    }),
    })
    public ResponseEntity<User> createAdmin (
            @Valid @RequestBody UserRegistration u
    ){
        User admin = userService.createAdmin(u);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(admin);
    }

    @GetMapping
    @Operation(
            summary = "Retrieve admin list",
            description = "This endpoint is specifically for administrators to retrieve admin list"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Retrieve admins successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = User.class)),
                                    examples = {
                                            @ExampleObject(value = "[{\"id\":1,\"firstName\":\"Vo\",\"lastName\":\"Lam\",\"email\":\"votruonglam2109@gmail.com\",\"mobile\":\"0886338217\",\"role\":\"ADMIN\",\"createdAt\":\"2024-01-11T17:48:10.221256\",\"username\":\"votruonglam2109@gmail.com\",\"credentialsNonExpired\":true,\"accountNonExpired\":true,\"fullname\":\"Vo Lam\",\"enable\":true,\"nonLocked\":true}]")
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
    public ResponseEntity<List<User>> getAdmins(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "desc", name = "sort") String sortDirection,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ){
        PaginationDTO paginationDTO = new PaginationDTO(page, limit, sortDirection, sortBy);
        List<User> admins = userService.getAdmins(paginationDTO);
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/activateUsers")
    @Operation(
            summary = "Retrieve activate user list",
            description = "This endpoint is specifically for administrators to retrieve activate user list"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Retrieve successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = User.class),
                                    examples = {
                                            @ExampleObject(value = "[{\"id\":3,\"firstName\":\"Trum\",\"lastName\":\"Bui Chu\",\"email\":\"trumbuichu.113.147@gmail.com\",\"mobile\":\"0886338219\",\"role\":\"USER\",\"createdAt\":\"2024-01-12T16:50:17.183635\",\"username\":\"trumbuichu.113.147@gmail.com\",\"credentialsNonExpired\":true,\"accountNonExpired\":true,\"fullname\":\"Trum Bui Chu\",\"enable\":true,\"nonLocked\":true},{\"id\":2,\"firstName\":\"Vo\",\"lastName\":\"Truong Lam\",\"email\":\"truonglam.113.147@gmail.com\",\"mobile\":\"0886338218\",\"role\":\"USER\",\"createdAt\":\"2024-01-11T17:58:15.304258\",\"username\":\"truonglam.113.147@gmail.com\",\"credentialsNonExpired\":true,\"accountNonExpired\":true,\"fullname\":\"Vo Truong Lam\",\"enable\":true,\"nonLocked\":true}]")
                                    }
                            )
                    }),
            @ApiResponse(
                    responseCode = "401", description = "Unauthorized",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = User.class)),
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
    public ResponseEntity<List<User>> getActivateUserList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "desc", name = "sort") String sortDirection,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ){
        PaginationDTO paginationDTO = new PaginationDTO(page, limit, sortDirection, sortBy);
        List<User> users = userService.getActivateUserList(paginationDTO);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/deactivateUsers")
    @Operation(
            summary = "Retrieve deactivate user list",
            description = "This endpoint is specifically for administrators to retrieve deactivate user list"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Retrieve successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = User.class)),
                                    examples = {
                                            @ExampleObject(value = "[{\"id\":3,\"firstName\":\"Trum\",\"lastName\":\"Bui Chu\",\"email\":\"trumbuichu.113.147@gmail.com\",\"mobile\":\"0886338219\",\"role\":\"USER\",\"createdAt\":\"2024-01-12T16:50:17.183635\",\"username\":\"trumbuichu.113.147@gmail.com\",\"credentialsNonExpired\":true,\"accountNonExpired\":true,\"fullname\":\"Trum Bui Chu\",\"enable\":false,\"nonLocked\":true}]")
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
    public ResponseEntity<List<User>> getDeactivateUserList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "desc", name = "sort") String sortDirection,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ){
        PaginationDTO paginationDTO = new PaginationDTO(page, limit, sortDirection, sortBy);
        List<User> users = userService.getDeactivateUserList(paginationDTO);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/user/{id}")
    @Operation(
            summary = "Retrieve user by his/her id",
            description = "This endpoint is specifically for administrators to retrieve user by his/her id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Retrieve successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = User.class),
                                    examples = {
                                            @ExampleObject(value = "{\"id\":3,\"firstName\":\"Trum\",\"lastName\":\"Bui Chu\",\"email\":\"trumbuichu.113.147@gmail.com\",\"mobile\":\"0886338219\",\"role\":\"USER\",\"createdAt\":\"2024-01-12T16:50:17.183635\",\"username\":\"trumbuichu.113.147@gmail.com\",\"credentialsNonExpired\":true,\"accountNonExpired\":true,\"fullname\":\"Trum Bui Chu\",\"enable\":true,\"nonLocked\":true}")
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
    public ResponseEntity<User> getUser(@PathVariable Long id){
        User user = userService.searchUser(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/search")
    @Operation(
            summary = "Search users by email, phone or full name",
            description = "This endpoint is specifically for administrators to search users by email, phone or full name"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Retrieve successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = User.class)),
                                    examples = {
                                            @ExampleObject(value = "[{\"id\":2,\"firstName\":\"Vo\",\"lastName\":\"TruongLam\",\"email\":\"truonglam.113.147@gmail.com\",\"mobile\":\"0886338218\",\"role\":\"USER\",\"createdAt\":\"2024-01-11T17:58:15.304258\",\"username\":\"truonglam.113.147@gmail.com\",\"credentialsNonExpired\":true,\"accountNonExpired\":true,\"fullname\":\"Vo Truong Lam\",\"enable\":true,\"nonLocked\":true}]")
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
    public ResponseEntity<List<User>> searchUser(
            @RequestParam(name = "q") String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(defaultValue = "desc", name = "sort") String sortDirection,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ){
        PaginationDTO paginationDTO = new PaginationDTO(page, limit, sortDirection, sortBy);
        List<User> users = userService.searchUsers(keyword, paginationDTO);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/deletedProducts")
    @Operation(
            summary = "Retrieve deleted product list",
            description = "This endpoint is specifically for administrators to retrieve deleted product list"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Retrieve successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class)),
                                    examples = {
                                            @ExampleObject(value = "[{\"id\":2,\"title\":\"iPhone SE 5G\",\"slug\":\"iphone-se-5g\",\"description\":\"Tracfone Apple iPhone SE 5G (3rdGeneration), 64GB, Black - Prepaid Smartphone (Locked)\",\"price\":189.0,\"quantity\":8,\"sold\":0,\"category\":\"Smartphone\",\"brand\":\"Apple\",\"colors\":[\"Black\"],\"lastModifiedAt\":\"2024-01-11T22:29:58.013168\",\"ratingPoint\":0.0,\"isLiked\":false,\"ratings\":[],\"images\":[]},{\"id\":1,\"title\":\"iPhone X\",\"slug\":\"iphone-x\",\"description\":\"Apple iPhone X, Fully Unlocked 5.8, 256GB - Space Gray, Black, White\",\"price\":1104.0,\"quantity\":10,\"sold\":2,\"category\":\"Smartphone\",\"brand\":\"Apple\",\"colors\":[\"White\",\"SpaceGray\"],\"lastModifiedAt\":\"2024-01-12T15:24:15.548203\",\"ratingPoint\":5.0,\"isLiked\":false,\"ratings\":[{\"star\":5,\"comment\":\"excellent\",\"createdAt\":\"2024-01-11T23:37:49.617247\",\"email\":\"truonglam.113.147@gmail.com\",\"name\":\"VoTruongLam\",\"isModified\":false}],\"images\":[{\"url\":\"http://res.cloudinary.com/ds2gtlowx/image/upload/v1704988095/guzrsgqurybqvsj1xogm.jpg\",\"assetId\":\"7a17b89188eaad6015f15bb094cdea36\",\"publicId\":\"guzrsgqurybqvsj1xogm\"}]}]")
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
    public ResponseEntity<List<ProductDTO>> getDeletedProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "desc", name = "sort") String sortDirection,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ){
        PaginationDTO paginationDTO = new PaginationDTO(page, limit, sortDirection, sortBy);
        List<ProductDTO> products = productService.getDeletedProducts(paginationDTO);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/publicProducts")
    @Operation(
            summary = "Retrieve public product list",
            description = "This endpoint is specifically for administrators to retrieve public product list"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Retrieve successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class)),
                                    examples = {
                                            @ExampleObject(value = "{\"id\":1,\"title\":\"iPhone X\",\"slug\":\"iphone-x\",\"description\":\"Apple iPhone X, Fully Unlocked 5.8, 256GB - Space Gray, Black, White\",\"price\":1104.0,\"quantity\":10,\"sold\":2,\"category\":\"Smartphone\",\"brand\":\"Apple\",\"colors\":[\"White\",\"SpaceGray\"],\"lastModifiedAt\":\"2024-01-12T15:24:15.548203\",\"ratingPoint\":5.0,\"isLiked\":false,\"ratings\":[{\"star\":5,\"comment\":\"excellent\",\"createdAt\":\"2024-01-11T23:37:49.617247\",\"email\":\"truonglam.113.147@gmail.com\",\"name\":\"VoTruongLam\",\"isModified\":false}],\"images\":[{\"url\":\"http://res.cloudinary.com/ds2gtlowx/image/upload/v1704988095/guzrsgqurybqvsj1xogm.jpg\",\"assetId\":\"7a17b89188eaad6015f15bb094cdea36\",\"publicId\":\"guzrsgqurybqvsj1xogm\"}]}")
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
    public ResponseEntity<List<ProductDTO>> getPublicProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "desc", name = "sort") String sortDirection,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ){
        PaginationDTO paginationDTO = new PaginationDTO(page, limit, sortDirection, sortBy);
        List<ProductDTO> products = productService.getPublicProducts(paginationDTO);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/enquiries")
    @Operation(
            summary = "Retrieve enquiry list",
            description = "This endpoint is specifically for administrators to retrieve enquiry list"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Retrieve successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = EnquiryDTO.class)),
                                    examples = {
                                            @ExampleObject(value = "[{\"name\":\"Vo Truong Lam\",\"email\":\"truonglam.113.147@gmail.com\",\"mobile\":\"0886338218\",\"comment\":\"This website has a beautiful and flexible appearance\",\"createdAt\":\"2024-01-12T00:53:55.1621267\"}]")
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
    public ResponseEntity<List<EnquiryDTO>> getEnquiries(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "desc", name = "sort") String sortDirection,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ){
        PaginationDTO paginationDTO = new PaginationDTO(page, limit, sortDirection, sortBy);
        List<EnquiryDTO> enquiries = enquiryService.findEnquiries(paginationDTO);
        return ResponseEntity.ok(enquiries);
    }
}
