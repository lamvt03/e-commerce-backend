package com.ecommerce.auth;

import com.ecommerce.auth.model.AuthenticationRequest;
import com.ecommerce.auth.model.AuthenticationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
           @Valid @RequestBody AuthenticationRequest request
    ){
        AuthenticationResponse resp = authenticationService.login(request);
        return ResponseEntity.ok(resp);
    }
}
