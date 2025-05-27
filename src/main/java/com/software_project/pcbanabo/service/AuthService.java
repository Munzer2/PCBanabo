// src/main/java/com/software_project/pcbanabo/service/AuthService.java
package com.software_project.pcbanabo.service;

import com.software_project.pcbanabo.dto.*;
import com.software_project.pcbanabo.model.*;
import com.software_project.pcbanabo.repository.UserInfoRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AuthService {
    private final UserInfoRepository repo;
    private final PasswordEncoder encoder;

    public AuthService(UserInfoRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Transactional
    public void register(RegisterRequest req) {
        if (repo.findByEmail(req.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
        UserInfo u = new UserInfo();
        u.setUsername(req.getUserName());
        u.setEmail(req.getEmail());
        u.setPassword(encoder.encode(req.getPassword()));

        repo.save(u);
    }

    @Transactional
    public void verify(VerifyRequest req) {
        UserInfo u = repo.findByEmail(req.getEmail())
                         .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        repo.save(u);
    }
}
