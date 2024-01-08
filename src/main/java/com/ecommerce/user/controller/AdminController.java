package com.ecommerce.user.controller;

import com.ecommerce.product.ProductService;
import com.ecommerce.product.model.Product;
import com.ecommerce.product.model.ProductDTO;
import com.ecommerce.user.model.User;
import com.ecommerce.user.model.UserDTO;
import com.ecommerce.user.model.request.UserRegistration;
import com.ecommerce.user.service.UserService;
import com.ecommerce.util.model.PaginationDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<User> createAdmin (
            @Valid @RequestBody UserRegistration u
    ){
        User admin = userService.createAdmin(u);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(admin);
    }

    @PutMapping("/lock/{id}")
    public ResponseEntity<?> activateOrDeactivateAdmin(@PathVariable Long id){
        userService.activateOrDeactivateAdmin(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping
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

    @PutMapping("/lockUser/{userId}")
    public ResponseEntity<?> activateOrDeactivateUser(@PathVariable Long userId){
        userService.activateOrDeactivateUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/activateUsers")
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

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long userId){
        UserDTO userDTO = userService.getUser(userId);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/search")
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
}
