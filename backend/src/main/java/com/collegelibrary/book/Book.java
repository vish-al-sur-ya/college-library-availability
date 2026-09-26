package com.collegelibrary.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class Book {
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private String isbn;

    private String category;
    private String description;
    private String shelfNumber;

    @Min(1)
    private int totalCopies;

    @Min(0)
    private int issuedCopies;

    public Book() {
    }

    public Book(String title, String author, String isbn, String category, String description, String shelfNumber, int totalCopies) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.description = description;
        this.shelfNumber = shelfNumber;
        this.totalCopies = totalCopies;
        this.issuedCopies = 0;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getShelfNumber() { return shelfNumber; }
    public void setShelfNumber(String shelfNumber) { this.shelfNumber = shelfNumber; }
    public int getTotalCopies() { return totalCopies; }
    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }
    public int getIssuedCopies() { return issuedCopies; }
    public void setIssuedCopies(int issuedCopies) { this.issuedCopies = issuedCopies; }
    public int getAvailableCopies() { return totalCopies - issuedCopies; }
}
