package com.jobcareer.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

public class AuthRequest {

    @Data public static class Register {
        @NotBlank private String firstName;
        @NotBlank private String lastName;
        @Email @NotBlank private String email;
        @NotBlank @Size(min = 6) private String password;
        private String phone;
    }

    @Data public static class Login {
        @Email @NotBlank private String email;
        @NotBlank private String password;
    }
}