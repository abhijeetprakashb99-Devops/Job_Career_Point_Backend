package com.jobcareer.service;

import com.jobcareer.dto.request.AuthRequest;
import com.jobcareer.dto.response.UserResponse;
import com.jobcareer.entity.User;
import com.jobcareer.exception.BadRequestException;
import com.jobcareer.repository.UserRepository;
import com.jobcareer.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class AuthService {

    private final UserRepository     userRepository;
    private final PasswordEncoder    encoder;
    private final AuthenticationManager authManager;
    private final JwtUtils           jwtUtils;
    private final UserDetailsService userDetailsService;

    public UserResponse.Auth register(AuthRequest.Register req) {
        if (userRepository.existsByEmail(req.getEmail()))
            throw new BadRequestException("Email already registered: " + req.getEmail());

        User user = User.builder()
                .firstName(req.getFirstName()).lastName(req.getLastName())
                .email(req.getEmail()).password(encoder.encode(req.getPassword()))
                .phone(req.getPhone()).role(User.Role.USER).status(User.UserStatus.ACTIVE)
                .build();

        user = userRepository.save(user);
        return buildAuth(jwtUtils.generateToken(userDetailsService.loadUserByUsername(user.getEmail())), user);
    }

    public UserResponse.Auth login(AuthRequest.Login req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid email or password"));

        if (user.getStatus() == User.UserStatus.BLOCKED)
            throw new LockedException("Your account is blocked. Contact admin.");

        authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        return buildAuth(jwtUtils.generateToken(userDetailsService.loadUserByUsername(user.getEmail())), user);
    }

    private UserResponse.Auth buildAuth(String token, User u) {
        return UserResponse.Auth.builder()
                .token(token).tokenType("Bearer")
                .id(u.getId()).email(u.getEmail())
                .firstName(u.getFirstName()).lastName(u.getLastName())
                .role(u.getRole()).build();
    }
}