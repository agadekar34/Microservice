package com.example.company_service.controller;

import com.example.company_service.model.Company;
import com.example.company_service.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private CompanyService companyService;


    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies(){
        return new ResponseEntity<>(companyService.getAllCompanies(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addCompany(@RequestBody Company company){

        return new ResponseEntity<>(companyService.addCompany(company),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id){
        Company company=companyService.getCompanyById(id);
        if(null!=company)
            return new ResponseEntity<>(company,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletecompany(@PathVariable Long id){
        boolean deleted=companyService.deleteCompany(id);
        if(deleted){
            return new ResponseEntity<>("Company Removed Successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> UpdateCompany(@PathVariable Long id,@RequestBody Company updatedCompany){
        boolean updated=companyService.updateCompany(id,updatedCompany);
        if(updated){
            return new ResponseEntity<>("Company Updated Successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
