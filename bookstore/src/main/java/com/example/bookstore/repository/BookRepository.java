package com.example.bookstore.repository;

import com.example.bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findByActiveTrue(Pageable pageable);

    Page<Book> findByActiveTrueAndTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Book> findByActiveTrueAndAuthorContainingIgnoreCase(String author, Pageable pageable);

    Page<Book> findByActiveTrueAndTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(
            String title, String author, Pageable pageable
    );
}
