package com.example.modo_be.book.controller;


import com.example.modo_be.auth.service.TokenService;
import com.example.modo_be.book.request.NaverHeaderRequest;
import com.example.modo_be.book.request.PostBookRequest;
import com.example.modo_be.book.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final TokenService tokenService;

    @GetMapping("/naver/books")
    public ResponseEntity naverBookList(@RequestHeader String accessToken ,@RequestParam String bookTitle,
                                        @RequestBody NaverHeaderRequest naverHeaderRequest) throws JsonProcessingException {

        String userEmail = tokenService.getJwtSubject(accessToken);

        return ResponseEntity.ok().body(bookService.getNaverBookList(naverHeaderRequest,bookTitle));
    }

    @PostMapping("/book/post")
    public ResponseEntity postBook(@RequestHeader String accessToken, @Valid @RequestBody PostBookRequest postBookRequest){
        bookService.postBook(postBookRequest,tokenService.getJwtSubject(accessToken));

        return ResponseEntity.ok().body("Success");
    }

}
