package com.example.bookstore.service.impl;

import com.example.bookstore.entity.Book;
import com.example.bookstore.exception.ResourceNotFoundException;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .filter(Book::isActive)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    @Override
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findByActiveTrue(pageable);
    }

    @Override
    public Page<Book> searchBooks(String title, String author, Pageable pageable) {
        String t = title == null ? "" : title;
        String a = author == null ? "" : author;

        if (!t.isEmpty() && !a.isEmpty()) {
            return bookRepository.findByActiveTrueAndTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(t, a, pageable);
        } else if (!t.isEmpty()) {
            return bookRepository.findByActiveTrueAndTitleContainingIgnoreCase(t, pageable);
        } else if (!a.isEmpty()) {
            return bookRepository.findByActiveTrueAndAuthorContainingIgnoreCase(a, pageable);
        }
        return bookRepository.findByActiveTrue(pageable);
    }

    @Override
    public Book updateBook(Long id, Book updatedBook) {
        Book existing = getBookById(id);

        existing.setTitle(updatedBook.getTitle());
        existing.setAuthor(updatedBook.getAuthor());
        existing.setPrice(updatedBook.getPrice());
        existing.setPublishedDate(updatedBook.getPublishedDate());

        return bookRepository.save(existing);
    }

    @Override
    public void softDeleteBook(Long id) {
        Book existing = getBookById(id);
        existing.setActive(false);
        bookRepository.save(existing);
    }

    @Override
    public void hardDeleteBook(Long id) {
        if (!bookRepository.existsById(id))
            throw new ResourceNotFoundException("Book not found with id: " + id);

        bookRepository.deleteById(id);
    }
}
