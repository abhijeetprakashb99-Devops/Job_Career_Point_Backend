package com.jobcareer.controller;

import com.jobcareer.dto.request.UpdateProfileRequest;
import com.jobcareer.dto.response.*;
import com.jobcareer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api") @RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/profile")
    public ResponseEntity<ApiResponse<UserResponse.Profile>> profile(@AuthenticationPrincipal UserDetails u) {
        return ResponseEntity.ok(ApiResponse.success("Profile fetched", userService.getProfile(u.getUsername())));
    }

    @PutMapping("/user/profile")
    public ResponseEntity<ApiResponse<UserResponse.Profile>> updateProfile(
            @AuthenticationPrincipal UserDetails u, @RequestBody UpdateProfileRequest req) {
        return ResponseEntity.ok(ApiResponse.success("Profile updated", userService.updateProfile(u.getUsername(), req)));
    }

    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserResponse.Summary>>> allUsers() {
        return ResponseEntity.ok(ApiResponse.success("Users fetched", userService.getAllUsers()));
    }

    @GetMapping("/admin/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse.Profile>> userById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("User fetched", userService.getUserById(id)));
    }

    @PatchMapping("/admin/users/{id}/toggle-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse.Summary>> toggleStatus(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Status updated", userService.toggleStatus(id)));
    }
}