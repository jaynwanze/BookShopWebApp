package com.example.bookshop.service;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.bookshop.entity.Administrator;
import com.example.bookshop.entity.Customer;
import com.example.bookshop.entity.User;

@Service
public class AuthService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AdministratorService administratorService;

    public String registerCustomer(String email, String name, String password, HttpSession session,
            RedirectAttributes redirectAttributes) {
        Customer existingUser = customerService.getCustomerByEmail(email);
        if (existingUser != null) {
            redirectAttributes.addFlashAttribute("error", "User already exists.");
            return "redirect:/login";
        }
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setName(name);
        customer.setPassword(password);
        Customer authenticatedUser = customerService.registerCustomer(customer);
        session.setAttribute("authenticatedUser", authenticatedUser);
        return "redirect:/dashboard";
    }

    public String loginCustomer(String email, String password, HttpSession session,
            RedirectAttributes redirectAttributes) {
        User authenticatedUser = customerService.getCustomerByEmailAndPassword(email, password);
        if (authenticatedUser == null) {
            redirectAttributes.addFlashAttribute("error", "Invalid credentials.");
            return "redirect:/login";
        }
        session.setAttribute("authenticatedUser", authenticatedUser);
        return "redirect:/dashboard";
    }

    public String registerAdministrator(String email, String name, String password, HttpSession session,
            RedirectAttributes redirectAttributes) {
        Administrator existingUser = administratorService.getAdministratorByEmail(email);
        if (existingUser != null) {
            redirectAttributes.addFlashAttribute("error", "User already exists.");
            return "redirect:/login";
        }

        Administrator administrator = new Administrator();
        administrator.setEmail(email);
        administrator.setName(name);
        administrator.setPassword(password);
        Administrator authenticatedUser = administratorService.registerAdministrator(administrator);
        session.setAttribute("authenticatedUser", authenticatedUser);
        return "redirect:/dashboard";
    }

    public String loginAdministrator(String email, String password, HttpSession session,
            RedirectAttributes redirectAttributes) {
        User authenticatedUser = administratorService.getAdministratorByEmailAndPassword(email, password);
        if (authenticatedUser == null) {
            redirectAttributes.addFlashAttribute("error", "Invalid credentials.");
            return "redirect:/login";
        }
        session.setAttribute("authenticatedUser", authenticatedUser);
        return "redirect:/dashboard";
    }
    public void logout(HttpSession session) {
        session.invalidate();
    }
}