package com.example.modo_be.user;


import com.example.modo_be.user.repository.UserRepository;
import com.example.modo_be.user.request.SignUpRequest;
import com.example.modo_be.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(RestDocumentationExtension.class)
public class UserControllerDocsTest {

    @BeforeEach
    public void before(WebApplicationContext ctx, RestDocumentationContextProvider restDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void deleteRepository(){
        userRepository.deleteAll();
    }


    @Test
    @DisplayName("유저 생성")
    public void test1() throws Exception {
        //given
        SignUpRequest signUpRequest =SignUpRequest.builder()
                .userEmail("modo@gmail.com").password("modo1234")
                .address("경기도 수원시").latitude("37.541").longitude("126.986")
                .nickName("김모도").phoneNum("010-1234-5678").build();

        String json = objectMapper.writeValueAsString(signUpRequest);

        //expected

        mockMvc.perform(RestDocumentationRequestBuilders.post("/signup")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("signup", requestFields(
                        fieldWithPath("userEmail").type(JsonFieldType.STRING).description("유저 ID"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                        fieldWithPath("address").type(JsonFieldType.STRING).description("주소"),
                        fieldWithPath("latitude").type(JsonFieldType.STRING).description("위도"),
                        fieldWithPath("longitude").type(JsonFieldType.STRING).description("경도"),
                        fieldWithPath("nickName").type(JsonFieldType.STRING).description("닉네임"),
                        fieldWithPath("phoneNum").type(JsonFieldType.STRING).description("휴대폰 번호"))
                        )
                );
    }

}
