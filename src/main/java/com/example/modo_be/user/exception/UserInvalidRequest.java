package com.example.modo_be.user.exception;

public class UserInvalidRequest extends UserException {

    private static final String message = "User Information is invalid request.";

    @Override
    public int statusCode() {
        return 400;
    }

    public UserInvalidRequest(String fieldName, String errorMessage) {
        super(message);
        addValidation(fieldName,errorMessage);
    }
}
