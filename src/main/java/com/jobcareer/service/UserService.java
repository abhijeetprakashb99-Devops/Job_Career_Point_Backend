package com.jobcareer.service;

import com.jobcareer.dto.request.UpdateProfileRequest;
import com.jobcareer.dto.response.UserResponse;
import com.jobcareer.entity.User;
import com.jobcareer.exception.ResourceNotFoundException;
import com.jobcareer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class UserService {

    private final UserRepository repo;

    public UserResponse.Profile getProfile(String email) { return toProfile(byEmail(email)); }

    public UserResponse.Profile updateProfile(String email, UpdateProfileRequest r) {
        User u = byEmail(email);
        if (r.getFirstName()       != null) u.setFirstName(r.getFirstName());
        if (r.getLastName()        != null) u.setLastName(r.getLastName());
        if (r.getPhone()           != null) u.setPhone(r.getPhone());
        if (r.getAddress()         != null) u.setAddress(r.getAddress());
        if (r.getCity()            != null) u.setCity(r.getCity());
        if (r.getState()           != null) u.setState(r.getState());
        if (r.getQualification()   != null) u.setQualification(r.getQualification());
        if (r.getExperience()      != null) u.setExperience(r.getExperience());
        if (r.getSkills()          != null) u.setSkills(r.getSkills());
        if (r.getResumeUrl()       != null) u.setResumeUrl(r.getResumeUrl());
        if (r.getProfileImageUrl() != null) u.setProfileImageUrl(r.getProfileImageUrl());
        return toProfile(repo.save(u));
    }

    public List<UserResponse.Summary> getAllUsers() {
        return repo.findAll().stream().map(this::toSummary).collect(Collectors.toList());
    }

    public UserResponse.Profile getUserById(Long id) { return toProfile(byId(id)); }

    public UserResponse.Summary toggleStatus(Long id) {
        User u = byId(id);
        u.setStatus(u.getStatus() == User.UserStatus.ACTIVE ? User.UserStatus.BLOCKED : User.UserStatus.ACTIVE);
        return toSummary(repo.save(u));
    }

    private User byEmail(String e) { return repo.findByEmail(e).orElseThrow(() -> new ResourceNotFoundException("User not found")); }
    private User byId(Long id)     { return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found: " + id)); }

    public UserResponse.Profile toProfile(User u) {
        return UserResponse.Profile.builder().id(u.getId()).email(u.getEmail())
                .firstName(u.getFirstName()).lastName(u.getLastName()).phone(u.getPhone())
                .address(u.getAddress()).city(u.getCity()).state(u.getState())
                .qualification(u.getQualification()).experience(u.getExperience())
                .skills(u.getSkills()).resumeUrl(u.getResumeUrl()).profileImageUrl(u.getProfileImageUrl())
                .role(u.getRole()).status(u.getStatus()).createdAt(u.getCreatedAt()).build();
    }

    public UserResponse.Summary toSummary(User u) {
        return UserResponse.Summary.builder().id(u.getId()).email(u.getEmail())
                .firstName(u.getFirstName()).lastName(u.getLastName()).phone(u.getPhone())
                .role(u.getRole()).status(u.getStatus()).createdAt(u.getCreatedAt()).build();
    }
}