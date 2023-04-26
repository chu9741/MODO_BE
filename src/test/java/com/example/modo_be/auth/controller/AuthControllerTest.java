package com.example.modo_be.auth.controller;

import com.example.modo_be.auth.request.SignInRequest;
import com.example.modo_be.auth.service.AuthService;
import com.example.modo_be.user.domain.User;
import com.example.modo_be.user.repository.UserRepository;
import com.example.modo_be.user.request.SignUpRequest;
import com.example.modo_be.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void beforeEach(){
        if(userRepository.count()==0){
//            userRepository.deleteAll();
            SignUpRequest signUpRequest = SignUpRequest.builder()
                    .id("chu9741").pw("chu970401")
                    .address("경기도 고양시").latitude("1").longitude("2")
                    .nickName("최현욱").phoneNum("010-1234-5678").build();
            userService.signUp(signUpRequest);

        }
    }


    @Test
    @DisplayName("로그인 - 올바른 케이스")
    void test1() throws Exception{
        //given
        SignInRequest signInRequest = SignInRequest.builder()
                .userId("chu9741").password("chu970401").build();
        //when
        User user = authService.validateUser(signInRequest);

        String json = objectMapper.writeValueAsString(signInRequest);


        //then
        mockMvc.perform(post("/signin")
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andDo(print());


        //expected

//        assertEquals(1L, userRepository.count());

    }

}