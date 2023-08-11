package com.ecommerce.user.controller;

import com.ecommerce.user.model.*;
import com.ecommerce.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @PostMapping("change-password")
    public UserDTO changePassword(
            @AuthenticationPrincipal User authenticatedUser,
            @RequestBody UserPasswordChange userPasswordChange
    ){
        return userService.changePassword(authenticatedUser, userPasswordChange);
    }
    @PostMapping("forgot-password")
    public String forgotPassword(@RequestBody UserPasswordForgot userPasswordForgot){
        return userService.handleForgotPassword(userPasswordForgot.email());
    }
    @PostMapping("reset-password")
    public String resetPassword(
            @RequestBody UserPasswordReset userPasswordReset
    ){
        return userService.handleResetPassword(userPasswordReset);
    }

}
