package com.example.modo_be.auth.controller;


import com.example.modo_be.auth.exception.TokenEmptyException;
import com.example.modo_be.auth.exception.WrongPassword;
import com.example.modo_be.common.response.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AuthExceptionController {

    @ResponseBody
    @ExceptionHandler(TokenEmptyException.class)
    public ResponseEntity<ErrorResponse> jwtExceptionHandler(TokenEmptyException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message(e.getMessage())
                .build();

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

    @ResponseBody
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> expiredJwtExceptionHandler(ExpiredJwtException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message(e.getMessage())
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ResponseBody
    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<ErrorResponse> expiredJwtExceptionHandler(UnsupportedJwtException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message(e.getMessage())
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }
    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorResponse> expiredJwtExceptionHandler(MalformedJwtException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message(e.getMessage())
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponse> expiredJwtExceptionHandler(SignatureException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message(e.getMessage())
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    //    ExpiredJwtException : JWT를 생성할 때 지정한 유효기간 초과할 때.
    //    UnsupportedJwtException : 예상하는 형식과 일치하지 않는 특정 형식이나 구성의 JWT일 때
    //    MalformedJwtException : JWT가 올바르게 구성되지 않았을 때
    //    SignatureException :  JWT의 기존 서명을 확인하지 못했을 때
}
