package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    
    private final BookRepository bookRepository;
    
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    public List<Book> getBooksByCategory(String category) {
        return bookRepository.findByCategory(category);
    }
    
    public List<Book> getBooksByAcademicYear(String academicYear) {
        return bookRepository.findByAcademicYear(academicYear);
    }
    
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }
    
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
    
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
    
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }
}