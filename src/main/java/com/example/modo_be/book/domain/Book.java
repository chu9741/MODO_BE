package com.example.modo_be.book.domain;


import com.example.modo_be.book.response.BookResponse;
import com.example.modo_be.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    private String bookTitle;
    private String bookImageUrl;
    private String bookAuthor;
    private String bookDescription;
    private int bookPrice;
    private String bookAddress;

    private String bookBorrowUserEmail;

    private boolean isLoaned;


    @CreatedDate
    private LocalDateTime createdAt;

    public void addUser(User user){
        user.getBooks().add(this);
        this.setBookAddress(user.getUserAddress());
        this.setUser(user);
    }

    public BookResponse toBookResponse(){
        return BookResponse.builder()
                .bookId(this.id)
                .bookTitle(this.bookTitle)
                .bookAuthor(this.bookAuthor)
                .bookDescription(this.bookDescription)
                .bookPrice(this.bookPrice)
                .createdAt(String.valueOf(this.createdAt))
                .bookBorrowUserEmail(this.bookBorrowUserEmail==null? "":this.bookBorrowUserEmail)
                .bookImageUrl(this.bookImageUrl).build();
    }

    @Builder
    public Book(Long id, User user, String bookTitle, String bookImageUrl, String bookAuthor, String bookDescription, int bookPrice, String bookAddress, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.bookTitle = bookTitle;
        this.bookImageUrl = bookImageUrl;
        this.bookAuthor = bookAuthor;
        this.bookDescription = bookDescription;
        this.bookPrice = bookPrice;
        this.bookAddress = bookAddress;
        this.createdAt = createdAt;
        this.isLoaned=false;
    }
}
