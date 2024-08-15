package com.example.company_service.service.impl;

import com.example.company_service.client.ReviewClient;
import com.example.company_service.dto.ReviewMessage;
import com.example.company_service.model.Company;
import com.example.company_service.repository.CompanyRepository;
import com.example.company_service.service.CompanyService;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;
    private ReviewClient reviewClient;

    public CompanyServiceImpl(CompanyRepository companyRepository,ReviewClient reviewClient) {
        this.companyRepository = companyRepository;
        this.reviewClient=reviewClient;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public boolean updateCompany(Long id, Company company) {
        Optional<Company> companyoptional=companyRepository.findById(id);

        if(companyoptional.isPresent()){
            Company updatedCompany=companyoptional.get();

            company.setName(updatedCompany.getName());
            company.setDescription(updatedCompany.getDescription());


            return true;
        }

        return false;
    }

    @Override
    public String addCompany(Company company) {
        companyRepository.save(company);
        return "Company Added Successfully";
    }

    @Override
    public Company getCompanyById(Long Id) {
        return companyRepository.findById(Id).orElse(null);
    }

    @Override
    public boolean deleteCompany(Long id) {
        try{
            companyRepository.deleteById(id);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    @Override
    public void updateCompanyRatings(ReviewMessage reviewMessage) {
        System.out.println(reviewMessage.getDescription());
        Company company=companyRepository.findById(reviewMessage.getCompanyId()).orElseThrow(()-> new NotFoundException("company Not found : "+reviewMessage.getCompanyId()));

        double averageRating=reviewClient.getAverageRatingForCompany(company.getId());
        company.setRating(averageRating);
        companyRepository.save(company);

    }
}
