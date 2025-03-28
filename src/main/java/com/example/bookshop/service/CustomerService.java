package com.example.bookshop.service;

import com.example.bookshop.entity.Customer;
import com.example.bookshop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    
    public Customer registerCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
    
    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
    
    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
    
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public Customer getCustomerByEmailAndPassword(String email, String password) {
        return customerRepository.findByEmailAndPassword(email, password);
    }
}
