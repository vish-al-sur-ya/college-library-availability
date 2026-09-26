package com.collegelibrary.order;

public class LibraryOrder {
    private Long id;
    private Long bookId;
    private String bookTitle;
    private String username;
    private String orderedAt;
    private String status;

    public LibraryOrder() {
    }

    public LibraryOrder(Long id, Long bookId, String bookTitle, String username, String orderedAt) {
        this.id = id;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.username = username;
        this.orderedAt = orderedAt;
        this.status = "ORDERED";
    }

    public Long getId() { return id; }
    public Long getBookId() { return bookId; }
    public String getBookTitle() { return bookTitle; }
    public String getUsername() { return username; }
    public String getOrderedAt() { return orderedAt; }
    public String getStatus() { return status; }
}
