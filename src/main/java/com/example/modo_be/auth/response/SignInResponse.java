package com.example.modo_be.auth.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
@Builder
@Setter
public class SignInResponse {

    private final String accessToken;
    private final String userEmail;
    private final String naverClientId;
    private final String naverClientSecret;

}
