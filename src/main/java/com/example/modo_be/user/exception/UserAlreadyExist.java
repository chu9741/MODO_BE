package com.example.modo_be.user.exception;

import java.util.Map;

public class UserAlreadyExist extends UserException{

    private static final String message = "User is already exists.";
    @Override
    public Map<String, String> getValidation() {
        return super.getValidation();
    }

    @Override
    public int statusCode() {
        return 409;
    }

    public UserAlreadyExist() {
        super(message);
    }

    @Override
    public void addValidation(String fieldName, String errorMessage) {
        super.addValidation(fieldName, errorMessage);
    }
}
