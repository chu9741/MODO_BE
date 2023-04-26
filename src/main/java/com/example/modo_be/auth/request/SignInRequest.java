package com.example.modo_be.auth.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class SignInRequest {

    private final String userId;
    private final String password;


    @Builder
    public SignInRequest(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
