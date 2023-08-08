package com.ecommerce.user.controller;

import com.ecommerce.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;
    @PutMapping("lock/{userId}")
    public ResponseEntity<String> lockUser(@PathVariable Long userId){
        String msg = userService.lockUserWithId(userId);
        return ResponseEntity.ok(msg);
    }
    @PutMapping("unlock/{userId}")
    public ResponseEntity<String> unlockUser(@PathVariable Long userId){
        String msg = userService.unlockUserWithId(userId);
        return ResponseEntity.ok(msg);
    }
}
