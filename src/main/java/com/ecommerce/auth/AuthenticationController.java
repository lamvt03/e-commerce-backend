package com.ecommerce.auth;

import com.ecommerce.auth.model.AuthenticationRequest;
import com.ecommerce.auth.model.AuthenticationResponse;
import com.ecommerce.user.model.UserDTO;
import com.ecommerce.user.model.request.UserRegistration;
import com.ecommerce.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser (
            @Valid @RequestBody UserRegistration u
    ){
        UserDTO userDTO = userService.registerUser(u);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
           @Valid @RequestBody AuthenticationRequest request
    ){
        AuthenticationResponse resp = authenticationService.login(request);
        return ResponseEntity.ok(resp);
    }

}
