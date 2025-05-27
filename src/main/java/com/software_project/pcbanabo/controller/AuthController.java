package com.software_project.pcbanabo.controller;

import com.software_project.pcbanabo.dto.*;
import com.software_project.pcbanabo.jwt.JwtUtil;
import com.software_project.pcbanabo.model.UserInfo;
import com.software_project.pcbanabo.service.UserInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserInfoService userService;

    public AuthController(AuthenticationManager authManager,
                          JwtUtil jwtUtil,
                          UserInfoService userService) {
        this.authManager = authManager;
        this.jwtUtil     = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest body) {
        // 1) Validate credentials
        authManager.authenticate(
          new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword())
        );
        // 2) Load user and issue token
        UserInfo u = userService.getByEmail(body.getEmail());
        String token = jwtUtil.generateToken(u.getEmail());
        // 3) Build response
        LoginResponse.UserData ud = new LoginResponse.UserData();
        ud.setId(u.getId());
        ud.setUserName(u.getUsername());
        ud.setEmail(u.getEmail());

        LoginResponse resp = new LoginResponse();
        resp.setToken(token);
        resp.setUser(ud);

        return ResponseEntity.ok(resp);
    }
}
