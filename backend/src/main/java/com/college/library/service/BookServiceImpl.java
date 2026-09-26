package com.college.library.service;

import com.college.library.dto.BookRequestDto;
import com.college.library.dto.BookResponseDto;
import com.college.library.entity.Book;
import com.college.library.entity.BookStatus;
import com.college.library.exception.BadRequestException;
import com.college.library.exception.ResourceNotFoundException;
import com.college.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookResponseDto createBook(BookRequestDto request) {
        validateCopies(request.totalCopies(), request.availableCopies());
        Book book = mapToEntity(request);
        updateStatus(book);
        return mapToResponse(bookRepository.save(book));
    }

    @Override
    public List<BookResponseDto> getAllBooks() {
        return bookRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Override
    public BookResponseDto getBookById(Long id) {
        return mapToResponse(findBook(id));
    }

    @Override
    public List<BookResponseDto> searchBooks(String query) {
        String safeQuery = query == null ? "" : query.trim();
        if (safeQuery.isEmpty()) {
            return getAllBooks();
        }
        return bookRepository
                .findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrIsbnContainingIgnoreCaseOrCategoryContainingIgnoreCase(
                        safeQuery, safeQuery, safeQuery, safeQuery)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public BookResponseDto updateBook(Long id, BookRequestDto request) {
        validateCopies(request.totalCopies(), request.availableCopies());
        Book book = findBook(id);
        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setIsbn(request.isbn());
        book.setCategory(request.category());
        book.setPublisher(request.publisher());
        book.setEdition(request.edition());
        book.setShelfNumber(request.shelfNumber());
        book.setTotalCopies(request.totalCopies());
        book.setAvailableCopies(request.availableCopies());
        if (request.availableCopies() > request.totalCopies()) {
            throw new BadRequestException("Available copies cannot exceed total copies.");
        }
        if (book.getAvailableCopies() > 0) {
            book.setReserved(false);
        }
        updateStatus(book);
        return mapToResponse(bookRepository.save(book));
    }

    @Override
    public void deleteBook(Long id) {
        Book book = findBook(id);
        bookRepository.delete(book);
    }

    @Override
    public BookResponseDto issueBook(Long id) {
        Book book = findBook(id);
        if (book.isReserved()) {
            throw new BadRequestException("Book is reserved and cannot be issued.");
        }
        if (book.getAvailableCopies() <= 0) {
            throw new BadRequestException("No available copies left to issue.");
        }
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        updateStatus(book);
        return mapToResponse(bookRepository.save(book));
    }

    @Override
    public BookResponseDto returnBook(Long id) {
        Book book = findBook(id);
        if (book.getAvailableCopies() >= book.getTotalCopies()) {
            throw new BadRequestException("All copies are already in library.");
        }
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        if (book.getAvailableCopies() > 0) {
            book.setReserved(false);
        }
        updateStatus(book);
        return mapToResponse(bookRepository.save(book));
    }

    @Override
    public BookResponseDto reserveBook(Long id) {
        Book book = findBook(id);
        book.setReserved(true);
        updateStatus(book);
        return mapToResponse(bookRepository.save(book));
    }

    @Override
    public BookResponseDto checkAvailability(Long id) {
        Book book = findBook(id);
        updateStatus(book);
        return mapToResponse(bookRepository.save(book));
    }

    private Book findBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + " not found."));
    }

    private void validateCopies(Integer totalCopies, Integer availableCopies) {
        if (totalCopies == null || availableCopies == null) {
            throw new BadRequestException("Total and available copies are required.");
        }
        if (availableCopies > totalCopies) {
            throw new BadRequestException("Available copies cannot exceed total copies.");
        }
    }

    private Book mapToEntity(BookRequestDto request) {
        Book book = new Book();
        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setIsbn(request.isbn());
        book.setCategory(request.category());
        book.setPublisher(request.publisher());
        book.setEdition(request.edition());
        book.setShelfNumber(request.shelfNumber());
        book.setTotalCopies(request.totalCopies());
        book.setAvailableCopies(request.availableCopies());
        book.setReserved(false);
        book.setStatus(BookStatus.AVAILABLE);
        return book;
    }

    private BookResponseDto mapToResponse(Book book) {
        return new BookResponseDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getCategory(),
                book.getPublisher(),
                book.getEdition(),
                book.getShelfNumber(),
                book.getTotalCopies(),
                book.getAvailableCopies(),
                book.getStatus(),
                book.isReserved()
        );
    }

    private void updateStatus(Book book) {
        if (book.isReserved()) {
            book.setStatus(BookStatus.RESERVED);
        } else if (book.getAvailableCopies() > 0) {
            book.setStatus(BookStatus.AVAILABLE);
        } else {
            book.setStatus(BookStatus.ISSUED);
        }
    }
}
