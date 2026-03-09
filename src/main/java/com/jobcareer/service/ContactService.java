package com.jobcareer.service;

import com.jobcareer.dto.request.ContactRequest;
import com.jobcareer.dto.response.ContactResponse;
import com.jobcareer.entity.Contact;
import com.jobcareer.exception.ResourceNotFoundException;
import com.jobcareer.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class ContactService {

    private final ContactRepository repo;

    public ContactResponse.Detail submit(ContactRequest r) {
        return toDetail(repo.save(Contact.builder()
                .name(r.getName()).email(r.getEmail()).phone(r.getPhone())
                .subject(r.getSubject()).message(r.getMessage())
                .status(Contact.ContactStatus.UNREAD).build()));
    }

    public List<ContactResponse.Detail> getAll() { return repo.findAll().stream().map(this::toDetail).collect(Collectors.toList()); }

    public ContactResponse.Detail getById(Long id) {
        Contact c = find(id);
        if (c.getStatus() == Contact.ContactStatus.UNREAD) { c.setStatus(Contact.ContactStatus.READ); c = repo.save(c); }
        return toDetail(c);
    }

    public ContactResponse.Detail reply(Long id, String reply) {
        Contact c = find(id);
        c.setAdminReply(reply);
        c.setStatus(Contact.ContactStatus.REPLIED);
        return toDetail(repo.save(c));
    }

    private Contact find(Long id) { return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contact not found: " + id)); }

    private ContactResponse.Detail toDetail(Contact c) {
        return ContactResponse.Detail.builder()
                .id(c.getId()).name(c.getName()).email(c.getEmail()).phone(c.getPhone())
                .subject(c.getSubject()).message(c.getMessage())
                .status(c.getStatus()).adminReply(c.getAdminReply())
                .submittedAt(c.getSubmittedAt()).build();
    }
}