package com.example.library.config;

import com.example.library.auth.UserRepository;
import com.example.library.auth.UserService;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private final UserService userService;
    private final BookRepository bookRepository;
    
    public DataInitializer(UserService userService, BookRepository bookRepository) {
        this.userService = userService;
        this.bookRepository = bookRepository;
    }
    
    @Override
    public void run(String... args) throws Exception {
        // Create sample users
        try {
            userService.register("admin", "password123", "Administrator", "admin@library.com", "librarian");
            userService.register("librarian1", "password123", "John Librarian", "john@library.com", "librarian");
            userService.register("student1", "password123", "Jane Student", "jane@student.com", "student");
        } catch (Exception e) {
            // Users might already exist
        }
        
        // Create sample books
        if (bookRepository.count() == 0) {
            Book book1 = new Book("978-0134685991", "Effective Java", "Joshua Bloch", 2018, 
                                "textbook", "3rd-year", "Computer Science", 3, 3);
            bookRepository.save(book1);
            
            Book book2 = new Book("978-0132350884", "Clean Code", "Robert C. Martin", 2008, 
                                "textbook", "2nd-year", "Software Engineering", 2, 2);
            bookRepository.save(book2);
            
            Book book3 = new Book("978-0201633610", "Design Patterns", "Gang of Four", 1994, 
                                "reference", "4th-year", "Software Design", 1, 1);
            bookRepository.save(book3);
            
            Book book4 = new Book("978-0262033848", "Introduction to Algorithms", "Thomas H. Cormen", 2009, 
                                "textbook", "1st-year", "Computer Science", 4, 4);
            bookRepository.save(book4);
            
            Book book5 = new Book("978-0078022159", "Database System Concepts", "Abraham Silberschatz", 2019, 
                                "textbook", "2nd-year", "Database Systems", 2, 2);
            bookRepository.save(book5);
        }
    }
}


