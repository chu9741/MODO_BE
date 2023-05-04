package com.example.modo_be.book.controller;


import com.example.modo_be.book.request.NaverHeaderRequest;
import com.example.modo_be.book.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/naver/books")
    public ResponseEntity naverBookList(@RequestParam String bookTitle,
                                        @RequestBody NaverHeaderRequest naverHeaderRequest) throws JsonProcessingException {

        return ResponseEntity.ok().body(bookService.getNaverBookList(naverHeaderRequest,bookTitle));

    }
}
