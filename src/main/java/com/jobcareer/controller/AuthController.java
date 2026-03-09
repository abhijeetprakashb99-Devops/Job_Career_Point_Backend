package com.jobcareer.controller;

import com.jobcareer.dto.request.AuthRequest;
import com.jobcareer.dto.response.*;
import com.jobcareer.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse.Auth>> register(@Valid @RequestBody AuthRequest.Register req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Registration successful", authService.register(req)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponse.Auth>> login(@Valid @RequestBody AuthRequest.Login req) {
        return ResponseEntity.ok(ApiResponse.success("Login successful", authService.login(req)));
    }
}