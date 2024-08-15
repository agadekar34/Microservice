package com.example.job_service.Controller;

import com.example.job_service.dto.JobDTO;
import com.example.job_service.model.Job;
import com.example.job_service.services.JobService;
import jakarta.ws.rs.DELETE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping
    public ResponseEntity<List<JobDTO>> findall(){

        return ResponseEntity.ok(jobService.findAll());
    }

    @PostMapping
    public ResponseEntity<String> createJob(@RequestBody Job job){

        return new ResponseEntity<>(jobService.createJob(job),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long id){
        JobDTO jobDTO=jobService.getJobById(id);
        if(null!=jobDTO)
        return new ResponseEntity<>(jobDTO,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id){
        boolean deleted=jobService.deleteJob(id);
        if(deleted){
            return new ResponseEntity<>("Job Deleted Successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> UpdateJob(@PathVariable Long id,@RequestBody Job updatedJob){
        boolean updated=jobService.updateJob(id,updatedJob);
        if(updated){
            return new ResponseEntity<>("Job Updated Successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
