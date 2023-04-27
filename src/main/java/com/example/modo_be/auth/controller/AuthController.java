package com.example.modo_be.auth.controller;


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

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;

    private final UserService userService;

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest){

        //validation : Id => pw
        User user = authService.validateUser(signInRequest);

        // token generate
        String accessToken = tokenService.createToken(user);

        return ResponseEntity.ok().body(new SignInResponse(accessToken));

    }
        //token생성 -> token validation을 모든 controller에 담는다..? -> Args Resolver로 일괄 처리,,?

}