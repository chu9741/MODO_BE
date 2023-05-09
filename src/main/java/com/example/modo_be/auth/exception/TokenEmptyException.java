package com.example.modo_be.auth.exception;

public class TokenEmptyException extends AuthException{

    private static final String message = "토큰을 찾을 수 없습니다.";

    @Override
    public int statusCode() {
        return 404;
    }

    public TokenEmptyException() {
        super(message);
    }

    @Override
    public void addValidation(String fieldName, String errorMessage) {
        super.addValidation(fieldName, errorMessage);
    }
}
