package com.ecommerce.exception.handler;

import com.ecommerce.exception.DuplicateResourceException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalAdvice {

    @ExceptionHandler(DuplicateResourceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    Map<String, String> handleDuplicateResourceException(DuplicateResourceException ex){
        Map<String, String> errMap = new HashMap<>();
        errMap.put("msg", ex.getMessage());
        errMap.put("code", String.valueOf(HttpStatus.CONFLICT.value()));
        errMap.put("status", HttpStatus.CONFLICT.getReasonPhrase());
        return errMap;
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<String, String> handleBadCredentialsException(BadCredentialsException ex){
        Map<String, String> errMap = new HashMap<>();
        errMap.put("msg", ex.getMessage());
        errMap.put("code", String.valueOf(HttpStatus.BAD_REQUEST.value()));
        errMap.put("status", HttpStatus.BAD_REQUEST.getReasonPhrase());
        return errMap;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    Map<String, String> handleRuntimeException(Exception ex){
        Map<String, String> errMap = new HashMap<>();
        errMap.put("msg", ex.getMessage());
        errMap.put("code", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        errMap.put("status", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        return errMap;
    }
}
