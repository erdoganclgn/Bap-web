package com.mersin.bap_otomasyon.controller;

import com.mersin.bap_otomasyon.common.SecurityUtils;
import com.mersin.bap_otomasyon.dto.request.LoginRequest;
import com.mersin.bap_otomasyon.dto.request.RefreshTokenRequest;
import com.mersin.bap_otomasyon.dto.request.RegisterRequest;
import com.mersin.bap_otomasyon.dto.response.ApiResponse;
import com.mersin.bap_otomasyon.dto.response.LoginResponse;
import com.mersin.bap_otomasyon.dto.response.UserResponse;
import com.mersin.bap_otomasyon.entity.User;
import com.mersin.bap_otomasyon.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(authService.login(request.getEmail(), request.getPassword())));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody RegisterRequest request) {
        User user = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(UserResponse.from(user)));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginResponse>> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(authService.refresh(request.getRefreshToken())));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> me() {
        User user = SecurityUtils.getCurrentUser();
        return ResponseEntity.ok(ApiResponse.ok(UserResponse.from(user)));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<Void>> forgotPassword(@RequestParam String email) {
        authService.forgotPassword(email);
        return ResponseEntity.ok(ApiResponse.ok(null, "Sifre sifirlama maili gonderildi."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<Void>> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        authService.resetPassword(token, newPassword);
        return ResponseEntity.ok(ApiResponse.ok(null, "Sifre basariyla sifirlandi."));
    }
}
