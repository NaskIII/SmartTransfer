package com.wayon.SmartTransfer.config.advice;

import com.wayon.SmartTransfer.dto.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class AuthenticationErrorAdvice {

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ErrorResponse handle(BadCredentialsException e) {
        return new ErrorResponse(e.getMessage());
    }
}
