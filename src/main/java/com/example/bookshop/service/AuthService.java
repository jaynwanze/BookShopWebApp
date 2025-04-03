package com.example.bookshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.bookshop.entity.Administrator;
import com.example.bookshop.entity.Customer;
import com.example.bookshop.entity.User;
import com.example.bookshop.factory.AdministratorFactory;
import com.example.bookshop.factory.CustomerFactory;

@Service
public class AuthService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register a new Customer
    public String registerCustomer(String email, String name, String rawPassword,
            RedirectAttributes redirectAttributes) {
        // Check if user with this email already exists
        Customer existingUser = customerService.getCustomerByEmail(email);
        if (existingUser != null) {
            redirectAttributes.addFlashAttribute("error", "User already exists.");
            return "redirect:/login";
        }

        // Create & save the new Customer with a hashed password
        CustomerFactory customerFactory = new CustomerFactory(null, null);
        Customer customer = (Customer) customerFactory.createUser(name, email, rawPassword);
        String hashed = passwordEncoder.encode(rawPassword);
        customer.setPassword(hashed);

        customerService.registerCustomer(customer);

        // Authenticate the new user
        try {
            Authentication authResult = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, rawPassword));
            SecurityContextHolder.getContext().setAuthentication(authResult);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Could not auto-login: " + ex.getMessage());
            return "redirect:/login";
        }

        // Redirect to the customer dashboard
        return "redirect:/customer/dashboard";
    }

    // Login an existing Customer
    public String loginCustomer(String email, String rawPassword,
            RedirectAttributes redirectAttributes) {
        // 1) Check DB just to confirm user exists
        User found = customerService.getCustomerByEmail(email);
        if (found == null) {
            redirectAttributes.addFlashAttribute("error", "Invalid credentials.");
            return "redirect:/login";
        }

        // Attempt authentication
        try {
            Authentication authResult = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, rawPassword));
            SecurityContextHolder.getContext().setAuthentication(authResult);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Invalid credentials.");
            return "redirect:/login";
        }

        // Redirect to customer dashboard
        return "redirect:/customer/dashboard";
    }

    // Register a new Administrator
    public String registerAdministrator(String email, String name, String rawPassword,
            String jobTitle, String department,
            RedirectAttributes redirectAttributes) {
        // Check if user exists
        Administrator existingUser = administratorService.getAdministratorByEmail(email);
        if (existingUser != null) {
            redirectAttributes.addFlashAttribute("error", "User already exists.");
            return "redirect:/login";
        }

        // Create & save Administrator with hashed password
        AdministratorFactory factory = new AdministratorFactory(jobTitle, department);
        Administrator admin = (Administrator) factory.createUser(name, email, rawPassword);
        admin.setPassword(passwordEncoder.encode(rawPassword));
        administratorService.registerAdministrator(admin);

        // 3) Authenticate the new user
        try {
            Authentication authResult = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, rawPassword));
            SecurityContextHolder.getContext().setAuthentication(authResult);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Could not auto-login: " + ex.getMessage());
            return "redirect:/login";
        }

        // Redirect to the admin dashboard (define your route)
        return "redirect:/administrator/dashboard";
    }

    // Login an existing Administrator
    public String loginAdministrator(String email, String rawPassword,
            RedirectAttributes redirectAttributes) {
        // Check DB just to confirm user exists (optional)
        com.example.bookshop.entity.User found = administratorService.getAdministratorByEmail(email);
        if (found == null) {
            redirectAttributes.addFlashAttribute("error", "Invalid credentials.");
            return "redirect:/login";
        }

        // authentication
        try {
            Authentication authResult = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, rawPassword));
            SecurityContextHolder.getContext().setAuthentication(authResult);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Invalid credentials.");
            return "redirect:/login";
        }

        // Redirect to admin dashboard
        return "redirect:/administrator/dashboard";
    }

    public void logout() {
        SecurityContextHolder.clearContext();
    }
}