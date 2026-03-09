package com.jobcareer.controller;

import com.jobcareer.dto.request.JobRequest;
import com.jobcareer.dto.response.*;
import com.jobcareer.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    // ── Public ────────────────────────────
    @GetMapping("/api/jobs/public")
    public ResponseEntity<ApiResponse<List<JobResponse.Detail>>> getActive() {
        return ResponseEntity.ok(ApiResponse.success("Jobs fetched", jobService.getActiveJobs()));
    }

    @GetMapping("/api/jobs/public/{id}")
    public ResponseEntity<ApiResponse<JobResponse.Detail>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Job fetched", jobService.getById(id)));
    }

    @GetMapping("/api/jobs/public/search")
    public ResponseEntity<ApiResponse<List<JobResponse.Detail>>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(ApiResponse.success("Search results", jobService.search(keyword)));
    }

    // ── Admin ─────────────────────────────
    @GetMapping("/api/admin/jobs")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<JobResponse.Detail>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("All jobs", jobService.getAllJobs()));
    }

    @PostMapping("/api/admin/jobs")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<JobResponse.Detail>> create(@Valid @RequestBody JobRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Job created", jobService.create(req)));
    }

    @PutMapping("/api/admin/jobs/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<JobResponse.Detail>> update(@PathVariable Long id, @RequestBody JobRequest req) {
        return ResponseEntity.ok(ApiResponse.success("Job updated", jobService.update(id, req)));
    }

    @DeleteMapping("/api/admin/jobs/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        jobService.delete(id); return ResponseEntity.ok(ApiResponse.success("Job deleted"));
    }
}