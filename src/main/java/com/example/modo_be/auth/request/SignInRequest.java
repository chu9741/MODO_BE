package com.example.modo_be.auth.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
public class SignInRequest {

    @NotBlank(message = "ID는 반드시 입력해야 합니다.")
    private final String userId;

    @NotBlank(message = "비밀번호는 반드시 입력해야 합니다.")
    private final String password;


    @Builder
    public SignInRequest(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
