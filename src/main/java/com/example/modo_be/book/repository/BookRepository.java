package com.example.modo_be.book.repository;

import com.example.modo_be.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByBookTitleContainsIgnoreCase(String bookTitle);
}
