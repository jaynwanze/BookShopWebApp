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
import com.example.bookshop.factory.user.AdministratorFactory;
import com.example.bookshop.factory.user.CustomerFactory;
import com.example.bookshop.validation.LoginValidator;
import com.example.bookshop.validation.RegisterValidator;

import jakarta.servlet.http.HttpServletRequest;

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

    @Autowired
    private HttpServletRequest request;

    // Register a new Customer
    public String registerCustomer(String email, String name, String rawPassword,
            RedirectAttributes redirectAttributes) {
        // Validate the input
        RegisterValidator v = new RegisterValidator(
                email, name, rawPassword);

        if (!v.validate()) {
            redirectAttributes.addFlashAttribute("error", v.getError());
            return "redirect:/register/customer";
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
            request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
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
        // Validate the input
        LoginValidator v = new LoginValidator(email, rawPassword);
        if (!v.validate()) {
            redirectAttributes.addFlashAttribute("error", v.getError());
            return "redirect:/customer/login";
        }

        // Attempt authentication
        try {
            Authentication authResult = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, rawPassword));
            SecurityContextHolder.getContext().setAuthentication(authResult);
            request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
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
        // Validate the input
        RegisterValidator v = new RegisterValidator(
                email, name, rawPassword);

        if (!v.validate()) {
            redirectAttributes.addFlashAttribute("error", v.getError());
            return "redirect:/register/administrator";
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
            request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
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
        // Validate the input
        LoginValidator v = new LoginValidator(email, rawPassword);
        if (!v.validate()) {
            redirectAttributes.addFlashAttribute("error", v.getError());
            return "redirect:/administrator/login";
        }
        // authentication
        try {
            Authentication authResult = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, rawPassword));
            SecurityContextHolder.getContext().setAuthentication(authResult);
            request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
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