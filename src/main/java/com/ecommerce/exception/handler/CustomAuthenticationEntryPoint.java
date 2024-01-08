package com.ecommerce.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        String msg = "";
        if(authHeader == null || authHeader.isBlank())
            msg += "You haven't attach jwt token";
        else
            msg += "Your token not valid";

        Map<String, String> errMap = Map.of(
                "code", String.valueOf(HttpStatus.UNAUTHORIZED.value()),
                "status", HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                "msg", msg
        );

        // return for client
        PrintWriter out = response.getWriter();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        out.print(new ObjectMapper().writeValueAsString(errMap));
        out.flush();
    }
}
