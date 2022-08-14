package com.example.websitedatmon.services;

import com.example.websitedatmon.entity.Company;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CompanyService {
    Company findCompanyById(int id);
    List<Company> findAll();

    Company save(Company company);
    void deleteById(int id);
}
