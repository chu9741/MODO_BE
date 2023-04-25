package com.example.modo_be.book.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BookResponse {

    private Long bookId;
    private String bookTitle;
    private String bookImageUrl;
    private String bookAuthor;
    private String bookBorrowUserEmail;
    private String bookDescription;
    private String createdAt;
    private int bookPrice;
}
