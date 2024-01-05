package com.ecommerce.user.controller;

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

    @PutMapping("/lock-user/{userId}")
    public ResponseEntity<?> activateOrDeactivateUser(@PathVariable Long userId){
        userService.activateOrDeactivateUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/activate-users")
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

    @GetMapping("/deactivate-users")
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

}
