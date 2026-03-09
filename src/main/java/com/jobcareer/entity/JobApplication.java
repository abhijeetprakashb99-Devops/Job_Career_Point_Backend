package com.jobcareer.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity @Table(name = "job_applications",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","job_id"}))
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class JobApplication {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", nullable = false) private User user;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "job_id",  nullable = false) private Job  job;

    @Column(columnDefinition = "TEXT") private String coverLetter;
    private String resumeUrl;

    @Enumerated(EnumType.STRING) @Builder.Default private ApplicationStatus status = ApplicationStatus.PENDING;
    private String adminNote;

    @CreationTimestamp private LocalDateTime appliedAt;
    @UpdateTimestamp   private LocalDateTime updatedAt;

    public enum ApplicationStatus { PENDING, APPROVED, REJECTED }
}