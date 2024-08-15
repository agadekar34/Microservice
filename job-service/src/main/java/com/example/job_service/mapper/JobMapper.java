package com.example.job_service.mapper;

import com.example.job_service.dto.JobDTO;
import com.example.job_service.external.model.Company;
import com.example.job_service.external.model.Review;
import com.example.job_service.model.Job;

import java.util.List;

public class JobMapper {

    public static JobDTO mapToJobWithCompanyDto(Job job, Company company, List<Review> reviews){
        JobDTO jobDTO =new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setCompany(company);
        jobDTO.setReviews(reviews);

        return jobDTO;
    }
}
