package com.example.modo_be.auth.exception;

import java.util.HashMap;
import java.util.Map;

public abstract class AuthException extends RuntimeException{

    Map<String, String> validation = new HashMap<>();

    public abstract int statusCode();

    public AuthException(String message){
        super(message);
    }

    public void addValidation(String fieldName, String errorMessage){
        validation.put(fieldName,errorMessage);
    }
    // 만료 는 Expired Jwt Exception 쓰면됨

    // JWT Exception = runtimeExc 상속


//    ExpiredJwtException : JWT를 생성할 때 지정한 유효기간 초과할 때.
//    UnsupportedJwtException : 예상하는 형식과 일치하지 않는 특정 형식이나 구성의 JWT일 때
//    MalformedJwtException : JWT가 올바르게 구성되지 않았을 때
//    SignatureException :  JWT의 기존 서명을 확인하지 못했을 때

}
