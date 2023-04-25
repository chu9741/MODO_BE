package com.example.modo_be.book.exception;

import java.util.HashMap;
import java.util.Map;

public class BookNotFoundException extends RuntimeException {
    Map<String, String> validation = new HashMap<>();
    private final int statusCode=400;

    public int statusCode(){
        return this.statusCode;
    };

    public BookNotFoundException(String message){
        super(message);
    }

    public void addValidation(String fieldName, String errorMessage){
        validation.put(fieldName,errorMessage);
    }
}
