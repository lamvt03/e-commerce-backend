package com.ecommerce.exception.handler;

import com.ecommerce.exception.DuplicateResourceException;
import com.ecommerce.exception.ErrResponse;
import com.ecommerce.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalAdvice {

    @ExceptionHandler(DuplicateResourceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @Hidden
    ErrResponse handleDuplicateResourceException(DuplicateResourceException ex){
        return createErrorMap(
                ex.getMessage(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase()
        );
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @Hidden
    ErrResponse handleResourceNotFoundException(ResourceNotFoundException ex){
        return createErrorMap(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase()
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @Hidden
    ErrResponse handleAccessDeniedException(AccessDeniedException ex){
        return createErrorMap(
                "You have no any permissions",
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.getReasonPhrase()
        );
    }
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Hidden
    ErrResponse handleBadCredentialsException(BadCredentialsException ex){
        return createErrorMap(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Hidden
    ErrResponse  handleBindingException(BindException ex){
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return createErrorMap(
                errors,
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ApiResponse(
            responseCode = "500", description = "Something wrong",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrResponse.class),
                    examples = {
                            @ExampleObject(value = "{\"msg\":\"Something wrong\",\"code\":500,\"status\":\"Internal server error\"}")
                    }
            ))
    ErrResponse handleRuntimeException(Exception ex){
        return createErrorMap(
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Something wrong"
        );
    }
    private ErrResponse createErrorMap(
            Object message,
            Integer code,
            String status
    ){
        return new ErrResponse(
                message, code, status
        );
    }
}
