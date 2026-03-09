package com.jobcareer.controller;

import com.jobcareer.dto.request.TestimonialRequest;
import com.jobcareer.dto.response.*;
import com.jobcareer.service.TestimonialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api") @RequiredArgsConstructor
public class TestimonialController {

    private final TestimonialService testimonialService;

    @GetMapping("/testimonials/public")
    public ResponseEntity<ApiResponse<List<TestimonialResponse.Detail>>> approved() {
        return ResponseEntity.ok(ApiResponse.success("Approved testimonials", testimonialService.getApproved()));
    }

    @PostMapping("/user/testimonials")
    public ResponseEntity<ApiResponse<TestimonialResponse.Detail>> submit(
            @AuthenticationPrincipal UserDetails u, @Valid @RequestBody TestimonialRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Testimonial submitted", testimonialService.submit(u.getUsername(), req)));
    }

    @GetMapping("/admin/testimonials")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<TestimonialResponse.Detail>>> all() {
        return ResponseEntity.ok(ApiResponse.success("All testimonials", testimonialService.getAll()));
    }

    @PatchMapping("/admin/testimonials/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<TestimonialResponse.Detail>> updateStatus(
            @PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(ApiResponse.success("Status updated", testimonialService.updateStatus(id, status)));
    }
}