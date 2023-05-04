package com.example.modo_be.book.controller;

import com.example.modo_be.book.config.NaverConfig;
import com.example.modo_be.book.request.NaverHeaderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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



    @Test
    @DisplayName("책 검색 - 네이버 검색 API 사용")
    public void test1() throws Exception{
        //given
        NaverHeaderRequest naverHeaderRequest = naverConfig.toNaverHeaderRequest();
        String booktTitle = "어린 왕자";

        String json = objectMapper.writeValueAsString(naverHeaderRequest);
        //expected
        mockMvc.perform(get("/naver/books")
                        .contentType(APPLICATION_JSON)
                        .param("bookTitle", booktTitle)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }


}