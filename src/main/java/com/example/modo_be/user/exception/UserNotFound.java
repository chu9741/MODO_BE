package com.example.modo_be.user.exception;

public class UserNotFound extends UserException{

    private static final String message  = "User Not Found.";

    @Override
    public int statusCode() {
        return 404;
    }


    public UserNotFound(){
        super(message);
    }
    public UserNotFound(String message) {
        super(message);
    }


}
