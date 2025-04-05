package com.example.bookshop.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.bookshop.entity.*;
import com.example.bookshop.security.CustomUserDetails;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AdministratorService administratorService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User found = customerService.getCustomerByEmail(username);
        if (found != null && found instanceof Customer) {
            Customer customer = (Customer) found;
            return new CustomUserDetails(
                customer.getId(),
                customer.getEmail(),
                customer.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER"))
            );
        }
        found = administratorService.getAdministratorByEmail(username);
        if (found != null && found instanceof Administrator) {
            Administrator admin = (Administrator) found;
            return new CustomUserDetails(
                admin.getId(),
                admin.getEmail(),
                admin.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
        }
        throw new UsernameNotFoundException("User not found with email: " + username);
    }
}
