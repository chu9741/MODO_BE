package com.example.modo_be.auth.controller;


import com.example.modo_be.auth.dto.TokenUserInfo;
import com.example.modo_be.auth.request.SignInRequest;
import com.example.modo_be.auth.response.SignInResponse;
import com.example.modo_be.auth.service.AuthService;
import com.example.modo_be.auth.service.TokenService;
import com.example.modo_be.user.domain.User;
import com.example.modo_be.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;

    private final UserService userService;

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody @Valid SignInRequest signInRequest){

        TokenUserInfo tokenUserInfo = authService.validateUser(signInRequest);

        String accessToken = tokenService.createToken(tokenUserInfo);
        SignInResponse signInResponse =  authService.setSignInResponseInfo(tokenUserInfo,accessToken);

        return ResponseEntity.ok().body(signInResponse);

    }
}
