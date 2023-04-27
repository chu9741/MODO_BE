package com.example.modo_be.user.controller;

import com.example.modo_be.user.exception.UserAlreadyExist;
import com.example.modo_be.user.exception.UserException;
import com.example.modo_be.user.exception.UserInvalidRequest;
import com.example.modo_be.user.exception.UserNotFound;
import com.example.modo_be.common.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class UserExceptionController {

    @ResponseBody
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> commonUserExceptionHandler(UserException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(String.valueOf(e.statusCode()))
                .message(e.getMessage()).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ResponseBody
    @ExceptionHandler(UserInvalidRequest.class)
    public ResponseEntity<ErrorResponse> userInvalidRequestHandler(UserInvalidRequest e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(String.valueOf(e.statusCode()))
                .message(e.getMessage())
                .build();
        errorResponse.addValidation(e.getValidation().keySet().toString(), e.getValidation().values().toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ResponseBody
    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<ErrorResponse> userNotFoundHandler(UserNotFound e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(String.valueOf(e.statusCode()))
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ResponseBody
    @ExceptionHandler(UserAlreadyExist.class)
    public ResponseEntity<ErrorResponse> userAlreadyExistHandler(UserAlreadyExist e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(String.valueOf(e.statusCode()))
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> argumentNotValidException(MethodArgumentNotValidException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message(e.getBindingResult().getAllErrors().get(0).getDefaultMessage())
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> noArgumentSendException(HttpMessageNotReadableException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message("Request body is missing.")
                .build();


        return ResponseEntity.badRequest().body(errorResponse);
    }



}
