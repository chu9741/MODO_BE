package com.example.modo_be.book.request;

import com.example.modo_be.book.domain.Book;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@RequiredArgsConstructor
public class PostBookRequest {

    @NotBlank(message = "제목은 반드시 입력해야합니다.")
    private final String bookTitle;
    private final String bookImageUrl;
    @NotBlank(message = "작가명은 반드시 입력해야합니다.")
    private final String bookAuthor;
    private final int bookPrice;
    private final String bookDescription;


    public Book toEntity(){
        return Book.builder().bookTitle(bookTitle).bookImageUrl(bookImageUrl)
                .bookAuthor(bookAuthor).bookDescription(bookDescription)
                .bookPrice(bookPrice).build();
    }

}
