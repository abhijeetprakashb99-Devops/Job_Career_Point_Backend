package com.jobcareer.dto.response;
import com.jobcareer.entity.Testimonial;
import lombok.*;
import java.time.LocalDateTime;

public class TestimonialResponse {
    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class Detail {
        private Long   id, userId;
        private String userName, profileImageUrl, message, designation, company;
        private Integer rating;
        private Testimonial.TestimonialStatus status;
        private LocalDateTime createdAt;
    }
}