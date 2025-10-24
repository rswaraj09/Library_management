package com.example.library.model;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String isbn;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String author;
    
    private Integer publishedYear;
    private String category;
    private String academicYear;
    private String subject;
    
    @Column(nullable = false)
    private Integer copiesTotal;
    
    @Column(nullable = false)
    private Integer copiesAvailable;

    @PrePersist
    @PreUpdate
    private void ensureCopiesAvailable() {
        if (this.copiesAvailable == null) {
            if (this.copiesTotal != null) {
                this.copiesAvailable = this.copiesTotal;
            } else {
                this.copiesAvailable = 0;
            }
        }
    }

    // Constructors
    public Book() {}

    public Book(String isbn, String title, String author, Integer publishedYear, 
                String category, String academicYear, String subject, 
                Integer copiesTotal, Integer copiesAvailable) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publishedYear = publishedYear;
        this.category = category;
        this.academicYear = academicYear;
        this.subject = subject;
        this.copiesTotal = copiesTotal;
        this.copiesAvailable = copiesAvailable;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public Integer getPublishedYear() { return publishedYear; }
    public void setPublishedYear(Integer publishedYear) { this.publishedYear = publishedYear; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public Integer getCopiesTotal() { return copiesTotal; }
    public void setCopiesTotal(Integer copiesTotal) { this.copiesTotal = copiesTotal; }

    public Integer getCopiesAvailable() { return copiesAvailable; }
    public void setCopiesAvailable(Integer copiesAvailable) { this.copiesAvailable = copiesAvailable; }
}