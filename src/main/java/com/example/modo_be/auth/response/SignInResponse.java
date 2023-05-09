package com.example.modo_be.auth.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignInResponse {

    private final String accessToken;
    private final String userEmail;

}
