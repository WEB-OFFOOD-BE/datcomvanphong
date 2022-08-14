package com.example.websitedatmon.serviceImpls;

import com.example.websitedatmon.entity.Company;
import com.example.websitedatmon.repositorys.CompanyRepository;
import com.example.websitedatmon.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    @Override
    public Company findCompanyById(int id) {
        return companyRepository.findCompanyById(id);
    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Company save(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public void deleteById(int id) {
        companyRepository.deleteById(id);
    }
}
