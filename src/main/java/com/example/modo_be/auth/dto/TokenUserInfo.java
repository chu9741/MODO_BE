package com.example.modo_be.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenUserInfo {

    private final String userEmail;
    private final String userNickName;


    @Builder
    public TokenUserInfo(String userEmail, String userNickName) {
        this.userEmail = userEmail;
        this.userNickName = userNickName;
    }
}
