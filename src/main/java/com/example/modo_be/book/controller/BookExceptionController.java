package com.example.modo_be.book.controller;

import com.example.modo_be.book.exception.BookNotFoundException;
import com.example.modo_be.common.response.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
@RequiredArgsConstructor
public class BookExceptionController {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> commonBookRuntimeExceptionHandler(BookNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(String.valueOf(e.statusCode()))
                .message(e.getMessage())
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<ErrorResponse> jsonMappingExceptionHandler(JsonMappingException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message(e.getMessage())
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ErrorResponse> jsonMappingExceptionHandler(JsonProcessingException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message(e.getMessage())
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> nullPointerExceptionHandler(NullPointerException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message(e.getMessage())
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }
}