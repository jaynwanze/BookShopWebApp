package com.example.bookshop.service;

import com.example.bookshop.entity.Customer;
import com.example.bookshop.repository.CustomerRepository;

import java.util.List;

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

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }
    
    public Customer getCustomerByEmailAndPassword(String email, String password) {
        return customerRepository.findByEmailAndPassword(email, password);
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }
    
    public List<Customer> searchCustomersByNameOrEmail(String search) {
        return customerRepository.findCustomersByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(search, search);
    }

    public String getCustomerNameById(Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        return customer != null ? customer.getName() : null;
    }
}
