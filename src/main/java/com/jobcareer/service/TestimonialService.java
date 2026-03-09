package com.jobcareer.service;

import com.jobcareer.dto.request.TestimonialRequest;
import com.jobcareer.dto.response.TestimonialResponse;
import com.jobcareer.entity.*;
import com.jobcareer.exception.ResourceNotFoundException;
import com.jobcareer.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class TestimonialService {

    private final TestimonialRepository repo;
    private final UserRepository        userRepo;

    public TestimonialResponse.Detail submit(String email, TestimonialRequest r) {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return toDetail(repo.save(Testimonial.builder()
                .user(user).message(r.getMessage()).rating(r.getRating())
                .designation(r.getDesignation()).company(r.getCompany())
                .status(Testimonial.TestimonialStatus.PENDING).build()));
    }

    public List<TestimonialResponse.Detail> getApproved() {
        return repo.findByStatus(Testimonial.TestimonialStatus.APPROVED).stream().map(this::toDetail).collect(Collectors.toList());
    }

    public List<TestimonialResponse.Detail> getAll() {
        return repo.findAll().stream().map(this::toDetail).collect(Collectors.toList());
    }

    public TestimonialResponse.Detail updateStatus(Long id, String status) {
        Testimonial t = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Testimonial not found: " + id));
        t.setStatus(Testimonial.TestimonialStatus.valueOf(status.toUpperCase()));
        return toDetail(repo.save(t));
    }

    private TestimonialResponse.Detail toDetail(Testimonial t) {
        return TestimonialResponse.Detail.builder()
                .id(t.getId()).userId(t.getUser().getId())
                .userName(t.getUser().getFirstName() + " " + t.getUser().getLastName())
                .profileImageUrl(t.getUser().getProfileImageUrl())
                .message(t.getMessage()).rating(t.getRating())
                .designation(t.getDesignation()).company(t.getCompany())
                .status(t.getStatus()).createdAt(t.getCreatedAt()).build();
    }
}