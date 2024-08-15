package com.example.company_service.service;

import com.example.company_service.dto.ReviewMessage;
import com.example.company_service.model.Company;

import java.util.List;

public interface CompanyService {

    List<Company> getAllCompanies();
    boolean updateCompany(Long id,Company company);
    String addCompany(Company company);

    Company getCompanyById(Long Id);

    boolean deleteCompany(Long id);

    public void updateCompanyRatings(ReviewMessage reviewMessage);
}
