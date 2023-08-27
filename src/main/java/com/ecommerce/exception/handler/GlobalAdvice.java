package com.ecommerce.exception.handler;

import com.ecommerce.exception.DuplicateResourceException;
import com.ibm.icu.impl.locale.XCldrStub;
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
        Map<String, String> errMap = createErrorMap(
                ex.getMessage(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase()
        );
        return errMap;
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<String, String> handleBadCredentialsException(BadCredentialsException ex){
        Map<String, String> errMap = createErrorMap(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
        return errMap;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    Map<String, String> handleRuntimeException(Exception ex){
        Map<String, String> errMap = createErrorMap(
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
        );
        return errMap;
    }
    private Map<String, String> createErrorMap(
            String message,
            int code,
            String status
    ){
        return Map.ofEntries(
                Map.entry("msg", message),
                Map.entry("code", String.valueOf(code)),
                Map.entry("status", status)
        );
    }
}
