package com.example.bookshop.repository;

import com.example.bookshop.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;   

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {

    Administrator findByEmail(String email);
    Administrator findByEmailAndPassword(String email, String password);
}
