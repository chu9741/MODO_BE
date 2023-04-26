package com.example.modo_be.auth.exception;

public class WrongPassword extends AuthException {

    private static final String message = "Wrong password.";

    @Override
    public int statusCode() {
        return 400;
    }

    public WrongPassword() {
        super(message);
    }

    @Override
    public void addValidation(String fieldName, String errorMessage) {
        super.addValidation(fieldName, errorMessage);
    }
}
