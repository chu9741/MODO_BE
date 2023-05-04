package com.example.modo_be.auth.service;

import com.example.modo_be.auth.dto.TokenUserInfo;
import com.example.modo_be.auth.request.SignInRequest;
import com.example.modo_be.user.repository.UserRepository;
import com.example.modo_be.user.request.SignUpRequest;
import com.example.modo_be.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class AuthServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @BeforeEach
    void beforeEach(){
        userRepository.deleteAll();
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .userEmail("chu9741").password("chu970401")
                .address("경기도 고양시").latitude("1").longitude("2")
                .nickName("최현욱").phoneNum("010-1234-5678").build();

        userService.signUp(signUpRequest);
  }

    @Test
    @DisplayName("로그인 - 올바른 케이스")
    void test1(){


        //when
        SignInRequest signInRequest = SignInRequest.builder()
                .userEmail("chu9741").password("chu970401").build();
        TokenUserInfo tokenUserInfo = authService.validateUser(signInRequest);

        //then
        assertEquals("chu9741",tokenUserInfo.getUserEmail());

    }


}