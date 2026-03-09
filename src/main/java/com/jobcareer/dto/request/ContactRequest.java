package com.jobcareer.dto.request;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data public class ContactRequest {
    @NotBlank private String name;
    @Email @NotBlank private String email;
    private String phone;
    @NotBlank private String subject;
    @NotBlank private String message;
}