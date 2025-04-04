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

import com.example.bookshop.entity.Customer;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AdministratorService administratorService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Suppose "username" is actually the email
        com.example.bookshop.entity.User found = customerService.getCustomerByEmail(username);

        if (found == null) {
            // Maybe it’s an admin
            found = administratorService.getAdministratorByEmail(username);
        }
        if (found == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        // 1) Decide the roles. For a Customer entity, ROLE_CUSTOMER; for an Admin, ROLE_ADMIN, etc.
        List<GrantedAuthority> authorities;
        if (found instanceof Customer) {
            authorities = List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
        } else {
            authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        // 2) Return a Spring Security user, using the found user’s email & password
        return new org.springframework.security.core.userdetails.User(
            found.getEmail(),
            found.getPassword(),
            authorities
        );
    }
}
