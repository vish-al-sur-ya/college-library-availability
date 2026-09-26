package com.collegelibrary.book;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleDataConfig {
    @Bean
    CommandLineRunner loadSampleBooks(BookRepository repository) {
        return args -> {
            if (repository.findAll().isEmpty()) {
                repository.save(new Book("Clean Code", "Robert C. Martin", "9780132350884", "Programming", "A practical guide to writing readable, maintainable code.", "A-12", 4));
                repository.save(new Book("The Alchemist", "Paulo Coelho", "9780062315007", "Fiction", "A student's journey of dreams, courage, and discovery.", "B-04", 3));
                repository.save(new Book("Introduction to Algorithms", "Thomas H. Cormen", "9780262046305", "Computer Science", "A classic reference for algorithm design and analysis.", "C-21", 2));
                repository.save(new Book("Atomic Habits", "James Clear", "9780735211292", "Self Development", "Small changes that build remarkable results over time.", "D-08", 5));
            }
        };
    }
}
