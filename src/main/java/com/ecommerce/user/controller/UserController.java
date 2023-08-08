package com.ecommerce.user.controller;

import com.ecommerce.user.model.User;
import com.ecommerce.user.model.UserDTO;
import com.ecommerce.user.model.UserRegistration;
import com.ecommerce.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("register")
    public UserDTO registerUser (@RequestBody UserRegistration u) {
        return userService.registerUser(u);
    }

    @GetMapping("role")
    public String role(){
        return "Role admin";
    }

    @GetMapping("{id}")
    public UserDTO getUser(@PathVariable Long id){
        UserDTO u = userService.getUser(id);
        return u;
    }

    @DeleteMapping("{id}")
    public UserDTO deleteUser(@PathVariable Long id){
        UserDTO u = userService.deleteUser(id);
        return u;
    }


}
