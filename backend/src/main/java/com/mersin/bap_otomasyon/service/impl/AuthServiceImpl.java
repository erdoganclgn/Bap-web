package com.mersin.bap_otomasyon.service.impl;

import com.mersin.bap_otomasyon.config.JwtService;
import com.mersin.bap_otomasyon.dto.request.RegisterRequest;
import com.mersin.bap_otomasyon.dto.response.LoginResponse;
import com.mersin.bap_otomasyon.dto.response.UserResponse;
import com.mersin.bap_otomasyon.entity.PasswordResetToken;
import com.mersin.bap_otomasyon.entity.User;
import com.mersin.bap_otomasyon.entity.enums.KullaniciRol;
import com.mersin.bap_otomasyon.exception.BusinessException;
import com.mersin.bap_otomasyon.repository.PasswordResetTokenRepository;
import com.mersin.bap_otomasyon.repository.UserRepository;
import com.mersin.bap_otomasyon.service.AuthService;
import com.mersin.bap_otomasyon.service.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final MailService mailService;
    private final long accessExpirationMs;
    private final long resetTokenExpirationMinutes;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService,
                           PasswordResetTokenRepository passwordResetTokenRepository,
                           MailService mailService,
                           @Value("${bap.jwt.access-token-expiration-ms}") long accessExpirationMs,
                           @Value("${bap.password-reset.token-expiration-minutes}") long resetTokenExpirationMinutes) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.mailService = mailService;
        this.accessExpirationMs = accessExpirationMs;
        this.resetTokenExpirationMinutes = resetTokenExpirationMinutes;
    }

    @Override
    public LoginResponse login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("BAP-AUTH-002", "Email veya sifre hatali"));
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new BusinessException("BAP-AUTH-002", "Email veya sifre hatali");
        }
        if (!Boolean.TRUE.equals(user.getAktif())) {
            throw new BusinessException("BAP-AUTH-003", "Kullanici hesabi aktif degil");
        }
        if (user.getYasakBitisTarihi() != null &&
                user.getYasakBitisTarihi().isAfter(java.time.LocalDate.now())) {
            throw new BusinessException("BAP-AUTH-004", "Destek yasagi var: " + user.getYasakBitisTarihi());
        }
        return buildLoginResponse(user);
    }

    @Override
    public User register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("BAP-AUTH-005", "Bu email zaten kayitli");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setAdSoyad(request.getAdSoyad());
        user.setUnvan(request.getUnvan());
        user.setRol(request.getRol() != null ? request.getRol() : KullaniciRol.Arastirmaci);
        user.setAktif(true);
        return userRepository.save(user);
    }

    @Override
    public LoginResponse refresh(String refreshToken) {
        String email = jwtService.extractEmail(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("BAP-AUTH-006", "Gecersiz refresh token"));
        if (!jwtService.isValid(refreshToken, user) || !"refresh".equals(jwtService.extractType(refreshToken))) {
            throw new BusinessException("BAP-AUTH-006", "Gecersiz refresh token");
        }
        if (user.getRefreshToken() == null || !user.getRefreshToken().equals(refreshToken)) {
            throw new BusinessException("BAP-AUTH-006", "Gecersiz refresh token");
        }
        return buildLoginResponse(user);
    }

    @Override
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("BAP-AUTH-007", "Kullanici bulunamadi"));

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setUser(user);
        resetToken.setToken(UUID.randomUUID().toString());
        resetToken.setSonKullanmaTarihi(LocalDateTime.now().plusMinutes(resetTokenExpirationMinutes));
        resetToken.setKullanildiMi(false);
        passwordResetTokenRepository.save(resetToken);

        mailService.sendPasswordResetMail(user.getEmail(), resetToken.getToken());
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new BusinessException("BAP-AUTH-009", "Gecersiz sifre sifirlama tokeni"));
        if (Boolean.TRUE.equals(resetToken.getKullanildiMi())) {
            throw new BusinessException("BAP-AUTH-009", "Gecersiz sifre sifirlama tokeni");
        }
        if (resetToken.getSonKullanmaTarihi().isBefore(LocalDateTime.now())) {
            throw new BusinessException("BAP-AUTH-010", "Sifre sifirlama tokeninin suresi dolmus");
        }

        User user = resetToken.getUser();
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        user.setRefreshToken(null);
        userRepository.save(user);

        resetToken.setKullanildiMi(true);
        passwordResetTokenRepository.save(resetToken);
    }

    private LoginResponse buildLoginResponse(User user) {
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        user.setRefreshToken(refreshToken);
        user.setSonGirisTarihi(LocalDateTime.now());
        userRepository.save(user);
        return new LoginResponse(accessToken, refreshToken, accessExpirationMs, UserResponse.from(user));
    }
}
