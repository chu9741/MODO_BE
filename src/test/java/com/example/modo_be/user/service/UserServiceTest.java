package com.example.modo_be.user.service;

import com.example.modo_be.user.domain.User;
import com.example.modo_be.user.repository.UserRepository;
import com.example.modo_be.user.request.SignUpRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @BeforeEach
    void beforeEach(){
        userRepository.deleteAll();
    }


    @Test
    @DisplayName("유저 회원가입 - 올바른 케이스")
    public void test1(){
        //given
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .id("chu9741").pw("chu970401")
                .address("경기도 고양시").latitude("1").longitude("2")
                .nickName("최현욱").phoneNum("010-1234-5678").build();

        //when
        userService.signUp(signUpRequest);

        //then
        assertEquals(userRepository.count(),1);
    }


    @Test
    @DisplayName("비밀번호 암호화 테스트")
    void test2(){
        //given
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .id("chu9741").pw("chu970401")
                .address("경기도 고양시").latitude("1").longitude("2")
                .nickName("최현욱").phoneNum("010-1234-5678").build();


        //when
        User user = signUpRequest.toEntity(signUpRequest.getPw());

        //then

        assertNotEquals(signUpRequest.getPw(),user.getUserPw());


    }




}