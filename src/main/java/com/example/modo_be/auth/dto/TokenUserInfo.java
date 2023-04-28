package com.example.modo_be.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenUserInfo {

    private final String userId;
    private final String userNickName;


    @Builder
    public TokenUserInfo(String userId, String userNickName) {
        this.userId = userId;
        this.userNickName = userNickName;
    }
}
