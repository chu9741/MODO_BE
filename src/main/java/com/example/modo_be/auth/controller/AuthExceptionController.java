package com.example.modo_be.auth.controller;


import com.example.modo_be.auth.exception.WrongPassword;
import com.example.modo_be.common.response.ErrorResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AuthExceptionController {

    @ResponseBody
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> jwtExceptionHandler(JwtException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message(e.getMessage())
                .build();

        errorResponse.addValidation(e.getLocalizedMessage(), e.getCause().toString());
        return ResponseEntity.badRequest().body(errorResponse);
    }


    @ResponseBody
    @ExceptionHandler(WrongPassword.class)
    public ResponseEntity<ErrorResponse> wrongPasswordHandler(WrongPassword e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(String.valueOf(e.statusCode()))
                .message(e.getMessage())
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }
}
