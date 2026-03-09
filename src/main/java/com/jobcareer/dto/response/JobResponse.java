package com.jobcareer.dto.response;
import com.jobcareer.entity.Job;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class JobResponse {
    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class Detail {
        private Long   id;
        private String title, company, location;
        private Job.JobType   jobType;
        private String        description, requirements, salaryRange, experience, qualification, category;
        private Integer       vacancies;
        private LocalDate     lastDate;
        private Job.JobStatus status;
        private int           applicationCount;
        private LocalDateTime createdAt;
    }
}