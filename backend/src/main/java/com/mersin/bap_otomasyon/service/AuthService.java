package com.mersin.bap_otomasyon.service;

import com.mersin.bap_otomasyon.dto.request.RegisterRequest;
import com.mersin.bap_otomasyon.dto.response.LoginResponse;
import com.mersin.bap_otomasyon.entity.User;

public interface AuthService {
    LoginResponse login(String email, String password);
    User register(RegisterRequest request);
    LoginResponse refresh(String refreshToken);
    void forgotPassword(String email);
    void resetPassword(String token, String newPassword);
}
