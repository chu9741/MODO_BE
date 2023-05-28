package com.example.modo_be.book.controller;

import com.example.modo_be.auth.request.SignInRequest;
import com.example.modo_be.auth.service.AuthService;
import com.example.modo_be.auth.service.TokenService;
import com.example.modo_be.book.config.NaverConfig;
import com.example.modo_be.book.repository.BookRepository;
import com.example.modo_be.book.request.NaverHeaderRequest;
import com.example.modo_be.book.request.PostBookRequest;
import com.example.modo_be.book.service.BookService;
import com.example.modo_be.user.domain.User;
import com.example.modo_be.user.repository.UserRepository;
import com.example.modo_be.user.request.SignUpRequest;
import com.example.modo_be.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.headers.HeaderDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.print.DocFlavor;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(RestDocumentationExtension.class)
public class BookControllerDocsTest {

    @BeforeEach
    public void before(WebApplicationContext ctx, RestDocumentationContextProvider restDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();

        if(userRepository.count()!=0){
            return;
        }
//        userRepository.deleteAll();

        SignUpRequest signUpRequest = SignUpRequest.builder()
                .userEmail("modo@gmail.com").password("modo1234")
                .address("경기도 수원시").latitude("37.541").longitude("126.986")
                .nickName("김모도").phoneNum("010-1234-5678").build();
        userService.signUp(signUpRequest);
        SignUpRequest signUpRequest1 = SignUpRequest.builder().userEmail("chu9741").password("chu970401")
                .address("경기도 고양시").latitude("1").longitude("2")
                .nickName("최현욱").phoneNum("010-1234-5678").build();
        userService.signUp(signUpRequest1);

    }


    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    NaverConfig naverConfig;

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Test
    @DisplayName("책 검색 - 네이버 검색 API ")
    public void test1() throws Exception {
        //given
        NaverHeaderRequest naverHeaderRequest = naverConfig.toNaverHeaderRequest();
        String booktTitle = "어린 왕자";

        String accessToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyRW1haWwiOiJjaHU5NzQxIiwidXNlck5pY2tOYW1lIjoi7LWc7ZiE7JqxIiwiaWF0IjoxNjg1MDU5NjMxLCJleHAiOjE2ODUyMzk2MzF9.ToZqXkijKRNZZkU1UTb8qgreYgOslvRuqmNn7GtZHo4";

        String json = objectMapper.writeValueAsString(naverHeaderRequest);

        FieldDescriptor[] book = new FieldDescriptor[]{
                fieldWithPath("title").type(JsonFieldType.STRING).description("책 제목"),
                fieldWithPath("link").type(JsonFieldType.STRING).description("책 링크"),
                fieldWithPath("image").type(JsonFieldType.STRING).description("이미지 url"),
                fieldWithPath("author").type(JsonFieldType.STRING).description("작가"),
                fieldWithPath("discount").type(JsonFieldType.STRING).description("가격"),
                fieldWithPath("publisher").type(JsonFieldType.STRING).description("출판사"),
                fieldWithPath("pubdate").type(JsonFieldType.STRING).description("출판 일자"),
                fieldWithPath("isbn").type(JsonFieldType.STRING).description("고유번호"),
                fieldWithPath("description").type(JsonFieldType.STRING).description("책 설명")
        };
        //expected

        mockMvc.perform(RestDocumentationRequestBuilders.get("/naver/books")
                        .contentType(APPLICATION_JSON)
                        .header("accessToken", accessToken)
                        .param("bookTitle", booktTitle)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("naver-books",
                        HeaderDocumentation.requestHeaders(HeaderDocumentation.headerWithName("accessToken").description("액세스 토큰")),
                        RequestDocumentation.requestParameters(RequestDocumentation.parameterWithName("bookTitle").description("책 제목")),
                        requestFields(
                                fieldWithPath("clientId").type(JsonFieldType.STRING).description("클라이언트 아이디"),
                                fieldWithPath("clientSecret").type(JsonFieldType.STRING).description("클라이언트 시크릿")
                        ),
                        responseFields(
                                fieldWithPath("[]").description("책 리스트"))
                                .andWithPrefix("[].", book)
                        )
                );
    }


    @Test
    @DisplayName("책 등록 ")
    public void test2() throws Exception {
        //given
        PostBookRequest postBookRequest = PostBookRequest.builder()
                .bookTitle("어린 왕자").bookImageUrl("https://www.naver.com")
                .bookAuthor("생텍쥐베리").bookPrice(2000).bookDescription("어린 왕자 책입니다.")
                .build();

        String accessToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyRW1haWwiOiJjaHU5NzQxIiwidXNlck5pY2tOYW1lIjoi7LWc7ZiE7JqxIiwiaWF0IjoxNjg1MDU5NjMxLCJleHAiOjE2ODUyMzk2MzF9.ToZqXkijKRNZZkU1UTb8qgreYgOslvRuqmNn7GtZHo4";

//        bookService.postBook(postBookRequest, tokenService.getJwtSubject(accessToken));
        String json = objectMapper.writeValueAsString(postBookRequest);
        //expected

        mockMvc.perform(RestDocumentationRequestBuilders.post("/book/post")
                        .contentType(APPLICATION_JSON)
                        .header("accessToken",accessToken)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("book-post",
                        HeaderDocumentation.requestHeaders(HeaderDocumentation.headerWithName("accessToken").description("액세스 토큰")),
                        requestFields(
                                fieldWithPath("bookTitle").type(JsonFieldType.STRING).description("책 제목"),
                                fieldWithPath("bookImageUrl").type(JsonFieldType.STRING).description("책 이미지 url"),
                                fieldWithPath("bookAuthor").type(JsonFieldType.STRING).description("작가"),
                                fieldWithPath("bookPrice").type(JsonFieldType.NUMBER).description("책 가격"),
                                fieldWithPath("bookDescription").type(JsonFieldType.STRING).description("책 설명")
                        )
                ));
    }

    @Test
    @DisplayName("책 매물 목록")
    public void test3() throws Exception {
        //given
        String accessToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyRW1haWwiOiJjaHU5NzQxIiwidXNlck5pY2tOYW1lIjoi7LWc7ZiE7JqxIiwiaWF0IjoxNjg1MDU5NjMxLCJleHAiOjE2ODUyMzk2MzF9.ToZqXkijKRNZZkU1UTb8qgreYgOslvRuqmNn7GtZHo4";


        FieldDescriptor[] book = new FieldDescriptor[]{
                fieldWithPath("bookId").type(JsonFieldType.NUMBER).description("책 ID"),
                fieldWithPath("bookTitle").type(JsonFieldType.STRING).description("책 제목"),
                fieldWithPath("bookImageUrl").type(JsonFieldType.STRING).description("책 이미지 URL"),
                fieldWithPath("bookAuthor").type(JsonFieldType.STRING).description("작가"),
                fieldWithPath("bookBorrowUserEmail").type(JsonFieldType.STRING).description("빌린 사람 이메일"),
                fieldWithPath("bookDescription").type(JsonFieldType.STRING).description("책 설명"),
                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("등록 일자"),
                fieldWithPath("bookPrice").type(JsonFieldType.NUMBER).description("가격")
        };
        //expected

        mockMvc.perform(RestDocumentationRequestBuilders.get("/book")
                        .contentType(APPLICATION_JSON)
                        .header("accessToken",accessToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("books-get",
                        HeaderDocumentation.requestHeaders(HeaderDocumentation.headerWithName("accessToken").description("액세스 토큰")),
                        responseFields(
                                fieldWithPath("[]").description("책 리스트"))
                                .andWithPrefix("[].", book)
                ));
    }

    @Test
    @DisplayName("빌려준 책 목록")
    public void test4() throws Exception {
        //given
        String accessToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyRW1haWwiOiJjaHU5NzQxIiwidXNlck5pY2tOYW1lIjoi7LWc7ZiE7JqxIiwiaWF0IjoxNjg1MDU5NjMxLCJleHAiOjE2ODUyMzk2MzF9.ToZqXkijKRNZZkU1UTb8qgreYgOslvRuqmNn7GtZHo4";

        FieldDescriptor[] book = new FieldDescriptor[]{
                fieldWithPath("bookId").type(JsonFieldType.NUMBER).description("책 ID"),
                fieldWithPath("bookTitle").type(JsonFieldType.STRING).description("책 제목"),
                fieldWithPath("bookImageUrl").type(JsonFieldType.STRING).description("책 이미지 URL"),
                fieldWithPath("bookAuthor").type(JsonFieldType.STRING).description("작가"),
                fieldWithPath("bookBorrowUserEmail").type(JsonFieldType.STRING).description("빌린 사람 이메일"),
                fieldWithPath("bookDescription").type(JsonFieldType.STRING).description("책 설명"),
                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("등록 일자"),
                fieldWithPath("bookPrice").type(JsonFieldType.NUMBER).description("가격")
        };
        //expected

        mockMvc.perform(RestDocumentationRequestBuilders.get("/book/lend")
                        .contentType(APPLICATION_JSON)
                        .header("accessToken",accessToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("lend-books-get",
                        HeaderDocumentation.requestHeaders(HeaderDocumentation.headerWithName("accessToken").description("액세스 토큰")),
                        responseFields(
                                fieldWithPath("[]").description("책 리스트"))
                                .andWithPrefix("[].", book)
                ));
    }


    @Test
    @DisplayName("책 대여")
    public void test6() throws Exception {

        //given
        String accessToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyRW1haWwiOiJjaHU5NzQxIiwidXNlck5pY2tOYW1lIjoi7LWc7ZiE7JqxIiwiaWF0IjoxNjg1MDU5NjMxLCJleHAiOjE2ODUyMzk2MzF9.ToZqXkijKRNZZkU1UTb8qgreYgOslvRuqmNn7GtZHo4";
        Long bookId = bookRepository.findAll().get(0).getId();

//        bookService.borrowBook(bookId+1,tokenService.getJwtSubject(accessToken));
        //when
        mockMvc.perform(RestDocumentationRequestBuilders.post("/book/borrow")
                        .contentType(APPLICATION_JSON)
                        .header("accessToken", accessToken)
                        .param("bookId", String.valueOf(bookId)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("borrow-book-post",
                                HeaderDocumentation.requestHeaders(HeaderDocumentation.headerWithName("accessToken").description("액세스 토큰")),
                                RequestDocumentation.requestParameters(RequestDocumentation.parameterWithName("bookId").description("책 ID"))
                        )
                );
    }

    @Test
    @DisplayName("빌린 책 목록")
    public void test5() throws Exception {
        //given
        String accessToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyRW1haWwiOiJjaHU5NzQxIiwidXNlck5pY2tOYW1lIjoi7LWc7ZiE7JqxIiwiaWF0IjoxNjg1MDU5NjMxLCJleHAiOjE2ODUyMzk2MzF9.ToZqXkijKRNZZkU1UTb8qgreYgOslvRuqmNn7GtZHo4";

        FieldDescriptor[] book = new FieldDescriptor[]{
                fieldWithPath("bookId").type(JsonFieldType.NUMBER).description("책 ID"),
                fieldWithPath("bookTitle").type(JsonFieldType.STRING).description("책 제목"),
                fieldWithPath("bookImageUrl").type(JsonFieldType.STRING).description("책 이미지 URL"),
                fieldWithPath("bookAuthor").type(JsonFieldType.STRING).description("작가"),
                fieldWithPath("bookBorrowUserEmail").type(JsonFieldType.STRING).description("빌린 사람 이메일"),
                fieldWithPath("bookDescription").type(JsonFieldType.STRING).description("책 설명"),
                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("등록 일자"),
                fieldWithPath("bookPrice").type(JsonFieldType.NUMBER).description("가격")
        };
        //expected

        mockMvc.perform(RestDocumentationRequestBuilders.get("/book/borrow")
                        .contentType(APPLICATION_JSON)
                        .header("accessToken",accessToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("borrow-books-get",
                        HeaderDocumentation.requestHeaders(HeaderDocumentation.headerWithName("accessToken").description("액세스 토큰"))
//                        responseFields(
//                                fieldWithPath("[]").description("책 리스트"))
//                                .andWithPrefix("[].", book)
                ));
    }


    @Test
    @DisplayName("책 반납")
    public void test7() throws Exception {
        //given
        String accessToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyRW1haWwiOiJjaHU5NzQxIiwidXNlck5pY2tOYW1lIjoi7LWc7ZiE7JqxIiwiaWF0IjoxNjg1MDU5NjMxLCJleHAiOjE2ODUyMzk2MzF9.ToZqXkijKRNZZkU1UTb8qgreYgOslvRuqmNn7GtZHo4";
        Long bookId = bookRepository.findAll().get(0).getId();

        //when
        mockMvc.perform(RestDocumentationRequestBuilders.post("/book/return")
                        .contentType(APPLICATION_JSON)
                        .header("accessToken", accessToken)
                        .param("bookId", String.valueOf(bookId)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("return-book-post",
                                HeaderDocumentation.requestHeaders(HeaderDocumentation.headerWithName("accessToken").description("액세스 토큰")),
                                RequestDocumentation.requestParameters(RequestDocumentation.parameterWithName("bookId").description("책 ID"))
                        )
                );
    }

}
