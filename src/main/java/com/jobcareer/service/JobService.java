package com.jobcareer.service;

import com.jobcareer.dto.request.JobRequest;
import com.jobcareer.dto.response.JobResponse;
import com.jobcareer.entity.Job;
import com.jobcareer.exception.ResourceNotFoundException;
import com.jobcareer.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class JobService {

    private final JobRepository            jobRepo;
    private final JobApplicationRepository appRepo;

    public List<JobResponse.Detail> getActiveJobs()       { return jobRepo.findByStatus(Job.JobStatus.ACTIVE).stream().map(this::toDetail).collect(Collectors.toList()); }
    public List<JobResponse.Detail> getAllJobs()           { return jobRepo.findAll().stream().map(this::toDetail).collect(Collectors.toList()); }
    public JobResponse.Detail       getById(Long id)       { return toDetail(find(id)); }
    public List<JobResponse.Detail> search(String keyword) { return jobRepo.searchJobs(keyword).stream().map(this::toDetail).collect(Collectors.toList()); }

    public JobResponse.Detail create(JobRequest r) {
        Job j = Job.builder()
                .title(r.getTitle()).company(r.getCompany()).location(r.getLocation())
                .jobType(r.getJobType()).description(r.getDescription()).requirements(r.getRequirements())
                .salaryRange(r.getSalaryRange()).experience(r.getExperience()).qualification(r.getQualification())
                .category(r.getCategory()).vacancies(r.getVacancies()).lastDate(r.getLastDate())
                .status(r.getStatus() != null ? r.getStatus() : Job.JobStatus.ACTIVE).build();
        return toDetail(jobRepo.save(j));
    }

    public JobResponse.Detail update(Long id, JobRequest r) {
        Job j = find(id);
        if (r.getTitle()         != null) j.setTitle(r.getTitle());
        if (r.getCompany()       != null) j.setCompany(r.getCompany());
        if (r.getLocation()      != null) j.setLocation(r.getLocation());
        if (r.getJobType()       != null) j.setJobType(r.getJobType());
        if (r.getDescription()   != null) j.setDescription(r.getDescription());
        if (r.getRequirements()  != null) j.setRequirements(r.getRequirements());
        if (r.getSalaryRange()   != null) j.setSalaryRange(r.getSalaryRange());
        if (r.getExperience()    != null) j.setExperience(r.getExperience());
        if (r.getQualification() != null) j.setQualification(r.getQualification());
        if (r.getCategory()      != null) j.setCategory(r.getCategory());
        if (r.getVacancies()     != null) j.setVacancies(r.getVacancies());
        if (r.getLastDate()      != null) j.setLastDate(r.getLastDate());
        if (r.getStatus()        != null) j.setStatus(r.getStatus());
        return toDetail(jobRepo.save(j));
    }

    public void delete(Long id) { find(id); jobRepo.deleteById(id); }

    private Job find(Long id) { return jobRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Job not found: " + id)); }

    private JobResponse.Detail toDetail(Job j) {
        return JobResponse.Detail.builder()
                .id(j.getId()).title(j.getTitle()).company(j.getCompany()).location(j.getLocation())
                .jobType(j.getJobType()).description(j.getDescription()).requirements(j.getRequirements())
                .salaryRange(j.getSalaryRange()).experience(j.getExperience()).qualification(j.getQualification())
                .category(j.getCategory()).vacancies(j.getVacancies()).lastDate(j.getLastDate())
                .status(j.getStatus()).applicationCount(appRepo.findByJobId(j.getId()).size())
                .createdAt(j.getCreatedAt()).build();
    }
}