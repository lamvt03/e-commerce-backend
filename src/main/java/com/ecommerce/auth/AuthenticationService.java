package com.ecommerce.auth;

import com.ecommerce.auth.model.AuthenticationRequest;
import com.ecommerce.auth.model.AuthenticationResponse;
import com.ecommerce.jwt.JwtUtil;
import com.ecommerce.user.UserRepository;
import com.ecommerce.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthenticationResponse login(AuthenticationRequest request){
        authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(
                    request.email(),
                    request.password()));

        User user = userRepository.findByEmail(request.email())
                .orElseThrow();
        return new AuthenticationResponse(
                jwtUtil.generateToken(user),
                jwtUtil.generateRefreshToken(user)
        );
    }
}
