package com.example.bookshop.service;

import com.example.bookshop.entity.Administrator;
import com.example.bookshop.repository.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorService {

    @Autowired
    private AdministratorRepository administratorRepository;

    public Administrator registerAdministrator(Administrator admin) {
        return administratorRepository.save(admin);
    }

    public Administrator getAdministratorByEmail(String email) {
        return administratorRepository.findByEmail(email);
    }

    public Administrator updateAdministrator(Administrator admin) {
        return administratorRepository.save(admin);
    }

    public void deleteAdministrator(Long id) {
        administratorRepository.deleteById(id);
    }

    public Administrator getAdministratorByEmailAndPassword(String email, String password) {
        return administratorRepository.findByEmailAndPassword(email, password);
    }

    public String getAdministratorNameById(Long id) {
        Administrator admin = administratorRepository.findById(id).orElse(null);
        return admin != null ? admin.getName() : null;
    }

    public Administrator getAdministratorById (Long id) {
        return administratorRepository.findById(id).orElse(null);
    }
}
