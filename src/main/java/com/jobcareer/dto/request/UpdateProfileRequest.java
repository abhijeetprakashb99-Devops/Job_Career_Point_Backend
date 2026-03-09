package com.jobcareer.dto.request;
import lombok.Data;

@Data public class UpdateProfileRequest {
    private String firstName, lastName, phone, address, city, state;
    private String qualification, experience, skills, resumeUrl, profileImageUrl;
}