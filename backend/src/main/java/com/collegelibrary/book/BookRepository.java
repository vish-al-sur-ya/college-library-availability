package com.collegelibrary.book;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {
    private final ConcurrentHashMap<Long, Book> books = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public List<Book> findAll() {
        return books.values().stream().sorted(Comparator.comparing(Book::getTitle)).collect(Collectors.toList());
    }

    public List<Book> search(String search) {
        String term = search.toLowerCase();
        return books.values().stream()
            .filter(book -> book.getTitle().toLowerCase().contains(term)
                || book.getAuthor().toLowerCase().contains(term)
                || book.getIsbn().toLowerCase().contains(term))
            .sorted(Comparator.comparing(Book::getTitle))
            .collect(Collectors.toList());
    }

    public Book findById(Long id) {
        return books.get(id);
    }

    public Book save(Book book) {
        if (book.getId() == null) {
            book.setId(nextId.getAndIncrement());
        }
        books.put(book.getId(), book);
        return book;
    }

    public void deleteById(Long id) {
        books.remove(id);
    }

    public boolean existsById(Long id) {
        return books.containsKey(id);
    }
}
