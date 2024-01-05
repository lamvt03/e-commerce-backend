package com.ecommerce.user.controller;

import com.ecommerce.user.model.User;
import com.ecommerce.user.service.UserService;
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
    @PutMapping("activate/{userId}")
    public ResponseEntity<?> activateOrDeactivateUser(@PathVariable Long userId){
        userService.activateOrDeactivateUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/user/activate")
    public ResponseEntity<List<User>> getActivateUserList(){
        List<User> users = userService.getActivateUserList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/user/deactivate")
    public ResponseEntity<List<User>> getDeactivateUserList(){
        List<User> users = userService.getDeactivateUserList();
        return ResponseEntity.ok(users);
    }

}
