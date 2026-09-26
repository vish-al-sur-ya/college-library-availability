package com.college.library.dto;

import com.college.library.entity.BookStatus;

public record BookResponseDto(
        Long id,
        String title,
        String author,
        String isbn,
        String category,
        String publisher,
        String edition,
        String shelfNumber,
        Integer totalCopies,
        Integer availableCopies,
        BookStatus status,
        boolean reserved
) {
}
