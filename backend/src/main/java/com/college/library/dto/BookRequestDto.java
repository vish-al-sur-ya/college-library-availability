package com.college.library.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record BookRequestDto(
        @NotBlank String title,
        @NotBlank String author,
        @NotBlank String isbn,
        @NotBlank String category,
        @NotBlank String publisher,
        @NotBlank String edition,
        @NotBlank String shelfNumber,
        @Min(1) Integer totalCopies,
        @Min(0) Integer availableCopies
) {
}
