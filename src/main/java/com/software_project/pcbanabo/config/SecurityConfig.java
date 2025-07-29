package com.software_project.pcbanabo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.software_project.pcbanabo.jwt.JwtAuthenticationFilter;
import com.software_project.pcbanabo.jwt.JwtUtil;
import com.software_project.pcbanabo.service.UserInfoService;

@Configuration
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserInfoService userService;

    public SecurityConfig(JwtUtil jwtUtil, UserInfoService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {
        JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(jwtUtil, userService);
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for APIs
                .cors(cors -> cors.configure(http)) // Enable CORS
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/components/**",
                                "/api/benchmarks",
                                "/api/benchmarks/**",
                                "/api/shared-builds",
                                "/api/shared-builds/**",
                                "/auth/login",
                                "/api/users",
                                "/api/users/**",
                                "/auth/register",
                                "/api/chat",
                                "/api/chat/suggest-build")
                        .permitAll() // Allow public access to /api endpoints
                        .anyRequest().authenticated() // Everything else requires login
                )
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationManager(authManager)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userService)
                .passwordEncoder(passwordEncoder());
        return builder.build();
    }

    // @Bean
    // public PasswordEncoder passwordEncoder() {
    // return new BCryptPasswordEncoder();
    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
