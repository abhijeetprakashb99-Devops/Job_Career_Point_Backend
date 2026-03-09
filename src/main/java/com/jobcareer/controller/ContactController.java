package com.jobcareer.controller;

import com.jobcareer.dto.request.ContactRequest;
import com.jobcareer.dto.response.*;
import com.jobcareer.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController @RequestMapping("/api") @RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping("/contact")
    public ResponseEntity<ApiResponse<ContactResponse.Detail>> submit(@Valid @RequestBody ContactRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Enquiry submitted", contactService.submit(req)));
    }

    @GetMapping("/admin/contacts")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<ContactResponse.Detail>>> all() {
        return ResponseEntity.ok(ApiResponse.success("All enquiries", contactService.getAll()));
    }

    @GetMapping("/admin/contacts/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ContactResponse.Detail>> byId(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Enquiry fetched", contactService.getById(id)));
    }

    @PatchMapping("/admin/contacts/{id}/reply")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ContactResponse.Detail>> reply(
            @PathVariable Long id, @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(ApiResponse.success("Replied", contactService.reply(id, body.get("reply"))));
    }
}