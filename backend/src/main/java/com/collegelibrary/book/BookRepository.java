package com.collegelibrary.book;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :search, '%')) "
        + "OR LOWER(b.author) LIKE LOWER(CONCAT('%', :search, '%')) "
        + "OR LOWER(b.isbn) LIKE LOWER(CONCAT('%', :search, '%')) ORDER BY b.title")
    List<Book> search(@Param("search") String search);
}
