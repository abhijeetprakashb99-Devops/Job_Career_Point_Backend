package com.jobcareer.dto.request;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data public class TestimonialRequest {
    @NotBlank private String message;
    @Min(1) @Max(5) private Integer rating;
    private String designation, company;
}