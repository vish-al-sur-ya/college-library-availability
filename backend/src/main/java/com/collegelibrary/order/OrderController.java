package com.collegelibrary.order;

import com.collegelibrary.book.Book;
import com.collegelibrary.book.BookService;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderRepository orderRepository;
    private final BookService bookService;

    public OrderController(OrderRepository orderRepository, BookService bookService) {
        this.orderRepository = orderRepository;
        this.bookService = bookService;
    }

    @GetMapping
    public List<LibraryOrder> getOrders(@RequestParam String username) {
        return orderRepository.findByUsername(username);
    }

    @PostMapping("/{bookId}")
    @ResponseStatus(HttpStatus.CREATED)
    public LibraryOrder orderBook(@PathVariable Long bookId, @RequestBody Map<String, String> details) {
        Book book = bookService.issue(bookId);
        return orderRepository.save(book.getId(), book.getTitle(), details.getOrDefault("username", "student"));
    }
}
