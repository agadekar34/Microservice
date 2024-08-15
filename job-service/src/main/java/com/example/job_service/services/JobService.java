package com.example.job_service.services;

import com.example.job_service.dto.JobDTO;
import com.example.job_service.model.Job;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface JobService {

     List<JobDTO> findAll();

     String createJob(Job job);

     JobDTO getJobById(Long Id);

    boolean deleteJob(Long id);

    boolean updateJob(Long id, Job updatedJobDTO);
}
