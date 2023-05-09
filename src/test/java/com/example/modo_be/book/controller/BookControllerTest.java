package com.example.modo_be.book.controller;

import com.example.modo_be.auth.service.TokenService;
import com.example.modo_be.book.config.NaverConfig;
import com.example.modo_be.book.repository.BookRepository;
import com.example.modo_be.book.request.NaverHeaderRequest;
import com.example.modo_be.book.request.PostBookRequest;
import com.example.modo_be.user.domain.User;
import com.example.modo_be.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest
class BookControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    NaverConfig naverConfig;

    @Autowired
    TokenService tokenService;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;


    @BeforeEach
    public void beforeEach(){
        bookRepository.deleteAll();
        userRepository.deleteAll();
        userRepository.save(User.builder().userEmail("chu9741").userPw("chu970401")
                .userAddress("경기도 고양시").userLatitude("1").userLongitude("2")
                .userNickName("최현욱").userPhoneNum("010-1234-5678").build());

    }

    @Test
    @DisplayName("책 검색 - 네이버 검색 API 사용")
    public void test1() throws Exception{
        //given
        NaverHeaderRequest naverHeaderRequest = naverConfig.toNaverHeaderRequest();
        String booktTitle = "어린 왕자";

        String accessToken="Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyRW1haWwiOiJjaHU5NzQxIiwidXNlck5pY2tOYW1lIjoi7LWc7ZiE7JqxIiwiaWF0IjoxNjgzNTU3NDMxLCJleHAiOjE2ODM1NzU0MzF9.k9s265yi57Sz_P-axFb1_VHUL9zzR8ooGNIArzWFSl0";

        String json = objectMapper.writeValueAsString(naverHeaderRequest);
        //expected
        mockMvc.perform(get("/naver/books")
                        .contentType(APPLICATION_JSON)
                        .param("bookTitle", booktTitle)
                        .header("accessToken", accessToken)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("책 등록")
    public void test2() throws Exception {
        //given
        PostBookRequest postBookRequest = PostBookRequest.builder()
                .bookTitle("어린 왕자").bookImageUrl("abc")
                .bookAuthor("생텍쥐베리").bookPrice(2000).bookDescription("어린 왕자")
                .build();

        String accessToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyRW1haWwiOiJjaHU5NzQxIiwidXNlck5pY2tOYW1lIjoi7LWc7ZiE7JqxIiwiaWF0IjoxNjgzNTU3NDMxLCJleHAiOjE2ODM1NzU0MzF9.k9s265yi57Sz_P-axFb1_VHUL9zzR8ooGNIArzWFSl0";
        String json = objectMapper.writeValueAsString(postBookRequest);

        //expected

        mockMvc.perform(post("/book/post").contentType(APPLICATION_JSON)
                .header("accessToken",accessToken)
                .content(json))
                .andExpect(status().isOk())
                .andDo(print());


    }


}