package com.ecommerce.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    @PostMapping
    User createUser (@RequestBody User u){
        return new User();
    }
    @PostMapping("/register")
    User registerUser (@RequestBody User u) {
        return userService.registerUser(u);
    }
    @GetMapping
    String getUser(){
        return "Xac thuc thanh cong";
    }

    @GetMapping("/role")
    String role(){
        return "Role admin";
    }
}
