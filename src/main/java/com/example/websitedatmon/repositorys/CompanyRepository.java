package com.example.websitedatmon.repositorys;

import com.example.websitedatmon.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Integer> {
    Company findCompanyById(int id);
    List<Company> findAll();

    Company save(Company company);
    void deleteById(int id);
}
