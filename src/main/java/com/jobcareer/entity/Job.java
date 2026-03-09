package com.jobcareer.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity @Table(name = "jobs")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Job {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @Column(nullable = false) private String title;
    @Column(nullable = false) private String company;
    @Column(nullable = false) private String location;

    @Enumerated(EnumType.STRING) private JobType jobType;

    @Column(columnDefinition = "TEXT", nullable = false) private String description;
    @Column(columnDefinition = "TEXT")                   private String requirements;

    private String  salaryRange, experience, qualification, category;
    private Integer vacancies;
    private LocalDate lastDate;

    @Enumerated(EnumType.STRING) @Builder.Default private JobStatus status = JobStatus.ACTIVE;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL) private List<JobApplication> applications;

    @CreationTimestamp private LocalDateTime createdAt;
    @UpdateTimestamp   private LocalDateTime updatedAt;

    public enum JobType   { FULL_TIME, PART_TIME, CONTRACT, INTERNSHIP, REMOTE }
    public enum JobStatus { ACTIVE, INACTIVE, CLOSED }
}