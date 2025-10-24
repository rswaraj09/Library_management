    // Generic handler for unsupported DELETE requests
package com.example.library.web;

// import com.example.library.model.Book;
// import com.example.library.service.BookService;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/books")
// public class BookController {
    
//     private final BookService bookService;
    
//     public BookController(BookService bookService) {
//         this.bookService = bookService;
//     }
    
//     @GetMapping
//     public List<Book> getAllBooks() {
//         return bookService.getAllBooks();
//     }
    
//     @GetMapping("/category/{category}")
//     public List<Book> getBooksByCategory(@PathVariable String category) {
//         return bookService.getBooksByCategory(category);
//     }
    
//     @GetMapping("/year/{academicYear}")
//     public List<Book> getBooksByAcademicYear(@PathVariable String academicYear) {
//         return bookService.getBooksByAcademicYear(academicYear);
//     }
    
//     @GetMapping("/{id}")
//     public ResponseEntity<Book> getBookById(@PathVariable Long id) {
//         Book book = bookService.getBookById(id);
//         if (book != null) {
//             return ResponseEntity.ok(book);
//         } else {
//             return ResponseEntity.notFound().build();
//         }
//     }
    
//     @PostMapping
//     public ResponseEntity<Book> createBook(@RequestBody Book book) {
//         try {
//             Book savedBook = bookService.saveBook(book);
//             return ResponseEntity.ok(savedBook);
//         } catch (Exception e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }
    
//     @PutMapping("/{id}")
//     public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
//         try {
//             book.setId(id);
//             Book updatedBook = bookService.updateBook(book);
//             return ResponseEntity.ok(updatedBook);
//         } catch (Exception e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }
    
//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
//         try {
//             bookService.deleteBook(id);
//             return ResponseEntity.noContent().build(); // 204 No Content on successful delete
//         } catch (Exception e) {
//             return ResponseEntity.notFound().build();
//         }
//     }


import org.springframework.http.HttpStatus;

import com.example.library.model.Book;
import com.example.library.service.BookService;
import com.example.library.exception.BookNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/category/{category}")
    public List<Book> getBooksByCategory(@PathVariable String category) {
        return bookService.getBooksByCategory(category);
    }

    @GetMapping("/year/{academicYear}")
    public List<Book> getBooksByAcademicYear(@PathVariable String academicYear) {
        return bookService.getBooksByAcademicYear(academicYear);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        try {
            Book savedBook = bookService.saveBook(book);
            URI location = new URI("/books/" + savedBook.getId());
            return ResponseEntity.created(location).body(savedBook);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        try {
            book.setId(id);
            Book updatedBook = bookService.updateBook(book);
            return ResponseEntity.ok(updatedBook);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.noContent().build(); // 204 No Content on successful delete
        } catch (BookNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 if book not found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 for other errors
        }
    }
}
