package com.jobcareer.dto.request;

import com.jobcareer.entity.JobApplication;
import jakarta.validation.constraints.*;
import lombok.Data;

public class ApplicationRequest {
    @Data public static class Apply {
        @NotNull private Long jobId;
        private String coverLetter;
        private String resumeUrl;
    }
    @Data public static class UpdateStatus {
        @NotNull private JobApplication.ApplicationStatus status;
        private String adminNote;
    }
}