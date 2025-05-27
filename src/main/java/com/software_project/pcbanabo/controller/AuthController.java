package com.software_project.pcbanabo.controller;

import com.software_project.pcbanabo.dto.*;
import com.software_project.pcbanabo.jwt.JwtUtil;
import com.software_project.pcbanabo.model.UserInfo;
import com.software_project.pcbanabo.service.AuthService;
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
    private final AuthService authService;

    public AuthController(AuthenticationManager authManager,
                          JwtUtil jwtUtil,
                          UserInfoService userService,
                          AuthService authService) {
        this.authManager = authManager;
        this.jwtUtil     = jwtUtil;
        this.userService = userService;
        this.authService = authService;
    }

    // @PostMapping("/register")
    // public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest req) {
    //     authService.register(req);
    //     return ResponseEntity.ok(
    //         new RegisterResponse(
    //             "User Registered successfully."
    //                     )
    //     );
    // }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterRequest req) {
    authService.register(req);
    authManager.authenticate(
        new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
    );
    UserInfo u = userService.getByEmail(req.getEmail());
    String token = jwtUtil.generateToken(u.getEmail());

    LoginResponse.UserData ud = new LoginResponse.UserData();
    ud.setId(u.getId());
    ud.setUserName(u.getUsername());
    ud.setEmail(u.getEmail());

    LoginResponse resp = new LoginResponse();
    resp.setToken(token);
    resp.setUser(ud);
    return ResponseEntity.ok(resp);
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
