package com.jobcareer.dto.response;
import com.jobcareer.entity.JobApplication;
import lombok.*;
import java.time.LocalDateTime;

public class ApplicationResponse {
    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class Detail {
        private Long   id, jobId, userId;
        private String jobTitle, company, location;
        private String applicantName, applicantEmail;
        private String coverLetter, resumeUrl, adminNote;
        private JobApplication.ApplicationStatus status;
        private LocalDateTime appliedAt, updatedAt;
    }
}