package com.example.library.repository;

import com.example.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    List<Book> findByCategory(String category);
    
    List<Book> findByAcademicYear(String academicYear);
    
    @Query("SELECT b FROM Book b WHERE b.category = :category OR b.academicYear = :academicYear")
    List<Book> findByCategoryOrAcademicYear(@Param("category") String category, @Param("academicYear") String academicYear);
}