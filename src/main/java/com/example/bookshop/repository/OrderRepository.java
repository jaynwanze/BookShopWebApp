package com.example.bookshop.repository;

import com.example.bookshop.entity.Order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    public Order findOrderById(Long id);
    public Order findOrderByCustomerId(Long customerId);
    public List<Order> findOrdersByCustomerId(Long customerId);
}
