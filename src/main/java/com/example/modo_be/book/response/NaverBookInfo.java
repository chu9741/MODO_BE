package com.example.modo_be.book.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NaverBookInfo {
    private String title;
    private String link;
    private String image;
    private String author;
    private String discount;
    private String publisher;
    private String pubdate;
    private String isbn;
    private String description;

    @Builder
    public NaverBookInfo(String title, String link, String image, String author, String discount, String publisher, String pubdate, String isbn, String description) {
        this.title = title;
        this.link = link;
        this.image = image;
        this.author = author;
        this.discount = discount;
        this.publisher = publisher;
        this.pubdate = pubdate;
        this.isbn = isbn;
        this.description = description;
    }

}
