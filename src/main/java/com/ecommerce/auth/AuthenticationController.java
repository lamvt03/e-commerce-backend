package com.ecommerce.auth;

import com.ecommerce.auth.model.AuthenticationRequest;
import com.ecommerce.auth.model.AuthenticationResponse;
import com.ecommerce.exception.ErrResponse;
import com.ecommerce.user.model.request.UserRegistration;
import com.ecommerce.user.model.resp.UserRegistrationResp;
import com.ecommerce.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Auth Controller")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final UserService userService;

    @PostMapping("/register")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserRegistration.class),
                    examples = {
                            @ExampleObject(value = "{\"firstName\":\"Vo\",\"lastName\":\"Truong Lam\",\"email\":\"truonglam.113.147@gmail.com\",\"mobile\":\"0886338218\",\"password\":\"21092003@\"}")
                    }
            )
    )
    @Operation(
            summary = "Register a new user",
            description = "This endpoint registers a new user with the provided registration details."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201", description = "User successfully registered",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserRegistrationResp.class),
                                    examples = {
                                            @ExampleObject(value = "{\"firstName\":\"Vo\",\"lastName\":\"TruongLam\",\"email\":\"votruonglam2109@gmail.com\",\"mobile\":\"0886338217\"}")
                                    }
                            )
                    }),
            @ApiResponse(
                    responseCode = "409", description = "Duplicate resource",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"msg\":\"The user with email [truonglam.113.147@gmail.com] already exists\",\"code\":409,\"status\":\"Conflict\"}")
                                    }
                            )
                    }),
    })
    public ResponseEntity<UserRegistrationResp> registerUser (
            @Valid @RequestBody UserRegistration u
    ){
        UserRegistrationResp resp = userService.registerUser(u);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resp);
    }

    @PostMapping("/login")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AuthenticationRequest.class),
                    examples = {
                            @ExampleObject(value = "{\"email\":\"truonglam.113.147@gmail.com\",\"password\":\"21092003@\"}")
                    }
            )
    )
    @Operation(
            summary = "User login",
            description = "This endpoint authenticates a user based on the provided credentials."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Login successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthenticationResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"accessToken\":\"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0cnVvbmdsYW0uMTEzLjE0N0BnbWFpbC5jb20iLCJpYXQiOjE3MDQ5NjQxNDgsImV4cCI6MTcwNTA1MDU0OH0.BzJ3SFZ4_ETdYvMa-KRncEMyekemQmvZVwtKcr56xzc\",\"refreshToken\":\"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0cnVvbmdsYW0uMTEzLjE0N0BnbWFpbC5jb20iLCJpYXQiOjE3MDQ5NjQxNDgsImV4cCI6MTcwNTM5NjE0OH0.arJWqk46XSxd5EzbgRk8W3JZ7HK9MCNlpFqwCqCVBLc\"}")
                                    }
                            )
                    }),
            @ApiResponse(
                    responseCode = "400", description = "Login failure",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"msg\":\"Username or password wrong\",\"code\":400,\"status\":\"Bad request\"}")
                                    }
                            )
                    }),
    })
    public ResponseEntity<AuthenticationResponse> login(
            @Valid @RequestBody AuthenticationRequest request
    ){
        AuthenticationResponse resp = authenticationService.login(request);
        return ResponseEntity.ok(resp);
    }

}
