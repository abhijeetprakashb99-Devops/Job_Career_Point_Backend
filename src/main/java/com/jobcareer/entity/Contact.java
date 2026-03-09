package com.jobcareer.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity @Table(name = "contacts")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Contact {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @Column(nullable = false) private String name;
    @Column(nullable = false) private String email;
    private String phone;
    @Column(nullable = false) private String subject;
    @Column(columnDefinition = "TEXT", nullable = false) private String message;

    @Enumerated(EnumType.STRING) @Builder.Default private ContactStatus status = ContactStatus.UNREAD;
    private String adminReply;

    @CreationTimestamp private LocalDateTime submittedAt;
    @UpdateTimestamp   private LocalDateTime updatedAt;

    public enum ContactStatus { UNREAD, READ, REPLIED }
}