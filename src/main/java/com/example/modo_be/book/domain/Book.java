package com.example.modo_be.book.domain;


import com.example.modo_be.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Setter
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

    @CreatedDate
    private LocalDateTime createdAt;

    public void addUser(User user){
        user.getBooks().add(this);
        this.setUser(user);
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
    }
}
