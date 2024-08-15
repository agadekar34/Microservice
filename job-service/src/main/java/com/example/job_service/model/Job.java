package com.example.job_service.model;


import jakarta.persistence.*;

@Entity
@Table(name="job_table")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String minSalary;
    private String maxSalary;
    private String location;
    private Long CompanyId;

    public Job() {
    }

    public Job(String maxSalary, Long id, String title, String description, String minSalary, String location, Long companyId) {
        this.maxSalary = maxSalary;
        this.id = id;
        this.title = title;
        this.description = description;
        this.minSalary = minSalary;
        this.location = location;
        CompanyId = companyId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(String minSalary) {
        this.minSalary = minSalary;
    }

    public String getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(String maxSalary) {
        this.maxSalary = maxSalary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(Long companyId) {
        CompanyId = companyId;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", minSalary='" + minSalary + '\'' +
                ", maxSalary='" + maxSalary + '\'' +
                ", location='" + location + '\'' +
                ", CompanyId=" + CompanyId +
                '}';
    }
}
