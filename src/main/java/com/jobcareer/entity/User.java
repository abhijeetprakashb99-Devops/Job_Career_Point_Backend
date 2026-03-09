package com.jobcareer.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity @Table(name = "users")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) private String email;
    @Column(nullable = false)               private String password;
    @Column(nullable = false)               private String firstName;
    @Column(nullable = false)               private String lastName;

    private String phone, address, city, state, qualification, experience, skills, resumeUrl, profileImageUrl;

    @Enumerated(EnumType.STRING) @Column(nullable = false)               private Role role;
    @Enumerated(EnumType.STRING) @Column(nullable = false) @Builder.Default private UserStatus status = UserStatus.ACTIVE;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) private List<JobApplication> applications;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) private List<Testimonial>    testimonials;

    @CreationTimestamp private LocalDateTime createdAt;
    @UpdateTimestamp   private LocalDateTime updatedAt;

    public enum Role        { USER, ADMIN }
    public enum UserStatus  { ACTIVE, BLOCKED }
}