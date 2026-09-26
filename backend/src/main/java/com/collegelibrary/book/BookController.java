package com.collegelibrary.book;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getBooks(@RequestParam(required = false) String search) {
        return bookService.findAll(search);
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBook(@Valid @RequestBody Book book) {
        return bookService.save(book);
    }

    @PutMapping("/{id}")
    public Book editBook(@PathVariable Long id, @Valid @RequestBody Book book) {
        Book existing = bookService.findById(id);
        book.setId(existing.getId());
        book.setIssuedCopies(existing.getIssuedCopies());
        return bookService.save(book);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        bookService.delete(id);
    }

    @PostMapping("/{id}/issue")
    public Book issueBook(@PathVariable Long id) {
        return bookService.issue(id);
    }

    @PostMapping("/{id}/return")
    public Book returnBook(@PathVariable Long id) {
        return bookService.returnBook(id);
    }
}
