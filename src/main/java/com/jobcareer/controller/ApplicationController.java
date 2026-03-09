package com.jobcareer.controller;

import com.jobcareer.dto.request.ApplicationRequest;
import com.jobcareer.dto.response.*;
import com.jobcareer.service.JobApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api") @RequiredArgsConstructor
public class ApplicationController {

    private final JobApplicationService appService;

    @PostMapping("/user/applications")
    public ResponseEntity<ApiResponse<ApplicationResponse.Detail>> apply(
            @AuthenticationPrincipal UserDetails u, @Valid @RequestBody ApplicationRequest.Apply req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Applied successfully", appService.apply(u.getUsername(), req)));
    }

    @GetMapping("/user/applications")
    public ResponseEntity<ApiResponse<List<ApplicationResponse.Detail>>> mine(@AuthenticationPrincipal UserDetails u) {
        return ResponseEntity.ok(ApiResponse.success("Applications fetched", appService.getMyApplications(u.getUsername())));
    }

    @GetMapping("/admin/applications")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<ApplicationResponse.Detail>>> all() {
        return ResponseEntity.ok(ApiResponse.success("All applications", appService.getAll()));
    }

    @GetMapping("/admin/applications/job/{jobId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<ApplicationResponse.Detail>>> byJob(@PathVariable Long jobId) {
        return ResponseEntity.ok(ApiResponse.success("Applications", appService.getByJob(jobId)));
    }

    @PatchMapping("/admin/applications/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ApplicationResponse.Detail>> updateStatus(
            @PathVariable Long id, @RequestBody ApplicationRequest.UpdateStatus req) {
        return ResponseEntity.ok(ApiResponse.success("Status updated", appService.updateStatus(id, req)));
    }
}