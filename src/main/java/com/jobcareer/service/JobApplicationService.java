package com.jobcareer.service;

import com.jobcareer.dto.request.ApplicationRequest;
import com.jobcareer.dto.response.ApplicationResponse;
import com.jobcareer.entity.*;
import com.jobcareer.exception.*;
import com.jobcareer.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class JobApplicationService {

    private final JobApplicationRepository appRepo;
    private final JobRepository            jobRepo;
    private final UserRepository           userRepo;

    public ApplicationResponse.Detail apply(String email, ApplicationRequest.Apply r) {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Job  job  = jobRepo.findById(r.getJobId()).orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        if (appRepo.existsByUserIdAndJobId(user.getId(), job.getId()))
            throw new BadRequestException("You have already applied for this job");
        if (job.getStatus() != Job.JobStatus.ACTIVE)
            throw new BadRequestException("This job is no longer accepting applications");

        return toDetail(appRepo.save(JobApplication.builder()
                .user(user).job(job).coverLetter(r.getCoverLetter())
                .resumeUrl(r.getResumeUrl() != null ? r.getResumeUrl() : user.getResumeUrl())
                .status(JobApplication.ApplicationStatus.PENDING).build()));
    }

    public List<ApplicationResponse.Detail> getMyApplications(String email) {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return appRepo.findByUserId(user.getId()).stream().map(this::toDetail).collect(Collectors.toList());
    }

    public List<ApplicationResponse.Detail> getAll()          { return appRepo.findAll().stream().map(this::toDetail).collect(Collectors.toList()); }
    public List<ApplicationResponse.Detail> getByJob(Long id) { return appRepo.findByJobId(id).stream().map(this::toDetail).collect(Collectors.toList()); }

    public ApplicationResponse.Detail updateStatus(Long id, ApplicationRequest.UpdateStatus r) {
        JobApplication app = appRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Application not found: " + id));
        app.setStatus(r.getStatus());
        if (r.getAdminNote() != null) app.setAdminNote(r.getAdminNote());
        return toDetail(appRepo.save(app));
    }

    private ApplicationResponse.Detail toDetail(JobApplication a) {
        return ApplicationResponse.Detail.builder()
                .id(a.getId()).jobId(a.getJob().getId()).jobTitle(a.getJob().getTitle())
                .company(a.getJob().getCompany()).location(a.getJob().getLocation())
                .userId(a.getUser().getId())
                .applicantName(a.getUser().getFirstName() + " " + a.getUser().getLastName())
                .applicantEmail(a.getUser().getEmail())
                .coverLetter(a.getCoverLetter()).resumeUrl(a.getResumeUrl())
                .status(a.getStatus()).adminNote(a.getAdminNote())
                .appliedAt(a.getAppliedAt()).updatedAt(a.getUpdatedAt()).build();
    }
}