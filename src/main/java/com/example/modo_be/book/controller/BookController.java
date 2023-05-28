package com.example.modo_be.book.controller;


import com.example.modo_be.auth.service.TokenService;
import com.example.modo_be.book.request.NaverHeaderRequest;
import com.example.modo_be.book.request.PostBookRequest;
import com.example.modo_be.book.response.BookResponse;
import com.example.modo_be.book.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/book")
    public ResponseEntity bookList(@RequestHeader String accessToken){
        // 인증,,
        List<BookResponse> bookResponseList = bookService.getAllBooks();
        return ResponseEntity.ok().body(bookResponseList);
    }

    // 빌리기, 빌려준 책 목록, 빌린 책 목록, 반납
    @PostMapping("/book/borrow")
    public ResponseEntity borrowBook(@RequestHeader String accessToken, @RequestParam Long bookId){
        String userEmail = tokenService.getJwtSubject(accessToken);
        bookService.borrowBook(bookId,userEmail);

        return ResponseEntity.ok().body("Success.");
    }

    @GetMapping("/book/lend")
    public ResponseEntity lendBooks(@RequestHeader String accessToken){
        String userEmail = tokenService.getJwtSubject(accessToken);
        List<BookResponse> bookResponseList = bookService.lendBookList(userEmail);

        return ResponseEntity.ok().body(bookResponseList);
    }

    @GetMapping("/book/borrow")
    public ResponseEntity borrowBooks(@RequestHeader String accessToken){
        String userEmail = tokenService.getJwtSubject(accessToken);
        List<BookResponse> bookResponseList = bookService.borrowBookList(userEmail);

        return ResponseEntity.ok().body(bookResponseList);
    }

    @PostMapping("/book/return")
    public ResponseEntity returnBook(@RequestHeader String accessToken, @RequestParam Long bookId){
        String userEmail = tokenService.getJwtSubject(accessToken);
        bookService.returnBook(bookId);

        return ResponseEntity.ok().body("Success.");
    }

}
