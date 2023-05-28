package com.example.modo_be.user.controller;

import com.example.modo_be.book.repository.BookRepository;
import com.example.modo_be.user.repository.UserRepository;
import com.example.modo_be.user.request.SignUpRequest;
import com.example.modo_be.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;

    @BeforeEach
    void deleteRepository(){
        userRepository.deleteAll();
        bookRepository.deleteAll();
    }


    @Test
    @DisplayName("유저 생성")
    public void test1() throws Exception {
        //given
        SignUpRequest signUpRequest =SignUpRequest.builder()
                .userEmail("chu9741").password("chu970401")
                .address("경기도 고양시").latitude("1").longitude("2")
                .nickName("최현욱").phoneNum("010-1234-5678").build();

        String json = objectMapper.writeValueAsString(signUpRequest);

        //expected

        mockMvc.perform(post("/signup")
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

}