package com.jobcareer.dto.request;

import com.jobcareer.entity.Job;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDate;

@Data
public class JobRequest {
    @NotBlank private String title;
    @NotBlank private String company;
    @NotBlank private String location;
    private Job.JobType   jobType;
    @NotBlank private String description;
    private String        requirements, salaryRange, experience, qualification, category;
    private Integer       vacancies;
    private LocalDate     lastDate;
    private Job.JobStatus status;
}