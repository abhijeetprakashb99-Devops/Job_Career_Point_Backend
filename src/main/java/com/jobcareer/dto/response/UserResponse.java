package com.jobcareer.dto.response;
import com.jobcareer.entity.User;
import lombok.*;
import java.time.LocalDateTime;

public class UserResponse {

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class Auth {
        private String token, tokenType;
        private Long   id;
        private String email, firstName, lastName;
        private User.Role role;
    }

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class Profile {
        private Long   id;
        private String email, firstName, lastName, phone, address, city, state;
        private String qualification, experience, skills, resumeUrl, profileImageUrl;
        private User.Role role;
        private User.UserStatus status;
        private LocalDateTime createdAt;
    }

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class Summary {
        private Long   id;
        private String email, firstName, lastName, phone;
        private User.Role       role;
        private User.UserStatus status;
        private LocalDateTime   createdAt;
    }
}