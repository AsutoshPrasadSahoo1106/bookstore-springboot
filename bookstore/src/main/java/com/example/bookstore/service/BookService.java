package com.example.bookstore.service;

import com.example.bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    Book createBook(Book book);

    Book getBookById(Long id);

    Page<Book> getAllBooks(Pageable pageable);

    Page<Book> searchBooks(String title, String author, Pageable pageable);

    Book updateBook(Long id, Book book);

    void softDeleteBook(Long id);

    void hardDeleteBook(Long id);
}
