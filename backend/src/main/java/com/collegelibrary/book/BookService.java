package com.collegelibrary.book;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll(String search) {
        return search == null || search.isBlank() ? bookRepository.findAll() : bookRepository.search(search.trim());
    }

    public Book findById(Long id) {
        Book book = bookRepository.findById(id);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
        return book;
    }

    public Book save(Book book) {
        if (book.getIssuedCopies() > book.getTotalCopies()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Issued copies cannot exceed total copies");
        }
        return bookRepository.save(book);
    }

    public void delete(Long id) {
        findById(id);
        bookRepository.deleteById(id);
    }

    public Book issue(Long id) {
        Book book = findById(id);
        if (book.getAvailableCopies() <= 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No copies are currently available");
        }
        book.setIssuedCopies(book.getIssuedCopies() + 1);
        return bookRepository.save(book);
    }

    public Book returnBook(Long id) {
        Book book = findById(id);
        if (book.getIssuedCopies() <= 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No issued copies to return");
        }
        book.setIssuedCopies(book.getIssuedCopies() - 1);
        return bookRepository.save(book);
    }
}
