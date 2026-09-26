package com.collegelibrary.order;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {
    private final List<LibraryOrder> orders = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public synchronized LibraryOrder save(Long bookId, String bookTitle, String username) {
        LibraryOrder order = new LibraryOrder(nextId.getAndIncrement(), bookId, bookTitle, username, java.time.LocalDateTime.now().toString());
        orders.add(order);
        return order;
    }

    public synchronized List<LibraryOrder> findByUsername(String username) {
        return orders.stream().filter(order -> order.getUsername().equals(username)).toList();
    }
}
