package com.jobcareer.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity @Table(name = "testimonials")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Testimonial {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", nullable = false) private User user;

    @Column(columnDefinition = "TEXT", nullable = false) private String message;
    private Integer rating;
    private String  designation, company;

    @Enumerated(EnumType.STRING) @Builder.Default private TestimonialStatus status = TestimonialStatus.PENDING;

    @CreationTimestamp private LocalDateTime createdAt;
    @UpdateTimestamp   private LocalDateTime updatedAt;

    public enum TestimonialStatus { PENDING, APPROVED, REJECTED }
}