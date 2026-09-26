package com.college.library.service;

import com.college.library.dto.BookRequestDto;
import com.college.library.dto.BookResponseDto;

import java.util.List;

public interface BookService {
    BookResponseDto createBook(BookRequestDto request);

    List<BookResponseDto> getAllBooks();

    BookResponseDto getBookById(Long id);

    List<BookResponseDto> searchBooks(String query);

    BookResponseDto updateBook(Long id, BookRequestDto request);

    void deleteBook(Long id);

    BookResponseDto issueBook(Long id);

    BookResponseDto returnBook(Long id);

    BookResponseDto reserveBook(Long id);

    BookResponseDto checkAvailability(Long id);
}
