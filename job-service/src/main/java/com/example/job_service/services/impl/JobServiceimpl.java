package com.example.job_service.services.impl;

import com.example.job_service.clients.CompanyClient;
import com.example.job_service.clients.ReviewClient;
import com.example.job_service.dto.JobDTO;
import com.example.job_service.external.model.Review;
import com.example.job_service.mapper.JobMapper;
import com.example.job_service.external.model.Company;
import com.example.job_service.model.Job;
import com.example.job_service.repository.JobRepository;
import com.example.job_service.services.JobService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobServiceimpl implements JobService {


    int attempt=0;
    //List<Job> jobs=new ArrayList<>();
     JobRepository jobRepository;
     private CompanyClient companyClient;
    private ReviewClient reviewClient;

     @Autowired
     RestTemplate restTemplate;
    private Long nextId=1L;


    public JobServiceimpl(JobRepository jobRepository, CompanyClient companyClient,ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient=reviewClient;
    }

    @Override
   /* @CircuitBreaker(name="companyBreaker", fallbackMethod = "companyBreakerFallback")
    @Retry(name="companyBreaker")*/
    @RateLimiter(name="companyBreaker", fallbackMethod = "companyBreakerFallback")
    public List<JobDTO> findAll() {
        System.out.println("Attempt "+ ++attempt);
        List<Job> jobs= jobRepository.findAll();
        List<JobDTO> jobDTOS=new ArrayList<>();
       for(Job job:jobs){
           jobDTOS.add( convertToDto(job));
       }
        return jobDTOS;
    }

    public List<String> companyBreakerFallback(Exception e) {
    List<String> errors=new ArrayList<>();
        errors.add("Company Microservice Not available");
        return errors;
    }

    @Override
    public String createJob(Job job) {
       // job.setId(nextId);
        jobRepository.save(job);
        return "Job Added Successfully";
    }

    @Override
    public JobDTO getJobById(Long Id) {

        Job job= jobRepository.findById(Id).orElse(null);
        if(null!=job){
          return  convertToDto(job);
        }
        return null;
    }

    @Override
    public boolean deleteJob(Long id) {

        try{
            jobRepository.deleteById(id);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    @Override
    public boolean updateJob(Long id, Job updatedJob) {

        Optional<Job> joboptional=jobRepository.findById(id);

            if(joboptional.isPresent()){
                Job job=joboptional.get();
               job.setDescription(updatedJob.getDescription());
               job.setLocation(updatedJob.getLocation());
               job.setTitle(updatedJob.getTitle());
               job.setMinSalary(updatedJob.getMinSalary());
               job.setMaxSalary(updatedJob.getMaxSalary());

               return true;
            }

        return false;
    }

    private JobDTO convertToDto(Job job){


        Company company=companyClient.getCompany(job.getCompanyId());


       /* ResponseEntity<List<Review>> responseEntity=restTemplate.exchange("http://REVIEW-SERVICE:8083/reviews?companyId=" + job.getCompanyId(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Review>>() {
        });*/

        List<Review> reviews=reviewClient.getReviewsByCompanyId(job.getCompanyId());
        return JobMapper.mapToJobWithCompanyDto(job,company,reviews);

    }
}
