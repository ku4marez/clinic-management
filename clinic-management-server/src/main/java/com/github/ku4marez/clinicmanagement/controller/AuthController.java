package com.github.ku4marez.clinicmanagement.controller;

import com.github.ku4marez.clinicmanagement.dto.request.AuthRequest;
import com.github.ku4marez.clinicmanagement.dto.request.AuthResponse;
import com.github.ku4marez.clinicmanagement.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest authRequest) {
        authService.register(authRequest.email(), authRequest.password());
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        String token = authService.login(authRequest.email(), authRequest.password());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
