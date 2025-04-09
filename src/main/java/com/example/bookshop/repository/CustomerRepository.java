package com.example.bookshop.repository;

import com.example.bookshop.entity.Customer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);

    Customer findByEmailAndPassword(String email, String password);

    List<Customer> findCustomersByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);
}