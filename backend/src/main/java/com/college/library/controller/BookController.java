package com.college.library.controller;

import com.college.library.dto.BookRequestDto;
import com.college.library.dto.BookResponseDto;
import com.college.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponseDto createBook(@Valid @RequestBody BookRequestDto request) {
        return bookService.createBook(request);
    }

    @GetMapping
    public List<BookResponseDto> getAllBooks(@RequestParam(required = false) String q) {
        return q == null ? bookService.getAllBooks() : bookService.searchBooks(q);
    }

    @GetMapping("/{id}")
    public BookResponseDto getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/search")
    public List<BookResponseDto> searchBooks(@RequestParam(defaultValue = "") String query) {
        return bookService.searchBooks(query);
    }

    @PutMapping("/{id}")
    public BookResponseDto updateBook(@PathVariable Long id, @Valid @RequestBody BookRequestDto request) {
        return bookService.updateBook(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @PutMapping("/{id}/issue")
    public BookResponseDto issueBook(@PathVariable Long id) {
        return bookService.issueBook(id);
    }

    @PutMapping("/{id}/return")
    public BookResponseDto returnBook(@PathVariable Long id) {
        return bookService.returnBook(id);
    }

    @PutMapping("/{id}/reserve")
    public BookResponseDto reserveBook(@PathVariable Long id) {
        return bookService.reserveBook(id);
    }

    @GetMapping("/{id}/availability")
    public BookResponseDto checkAvailability(@PathVariable Long id) {
        return bookService.checkAvailability(id);
    }
}
