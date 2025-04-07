package com.example.bookshop.repository;

import com.example.bookshop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    public Order saveOrder(Order order);

    public Order findOrderById(Long id);

    public void deleteOrder(Long id);

}
