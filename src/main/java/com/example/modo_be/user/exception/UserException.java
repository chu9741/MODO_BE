package com.example.modo_be.user.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter

public abstract class UserException extends RuntimeException{

    Map<String, String> validation = new HashMap<>();

    public abstract int statusCode();

    public UserException(String message){
        super(message);
    }

    public void addValidation(String fieldName, String errorMessage){
        validation.put(fieldName,errorMessage);
    }

}
