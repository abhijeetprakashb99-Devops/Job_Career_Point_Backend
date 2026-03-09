package com.jobcareer.dto.response;
import com.jobcareer.entity.Contact;
import lombok.*;
import java.time.LocalDateTime;

public class ContactResponse {
    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class Detail {
        private Long   id;
        private String name, email, phone, subject, message, adminReply;
        private Contact.ContactStatus status;
        private LocalDateTime submittedAt;
    }
}