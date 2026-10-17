package com.mersin.bap_otomasyon.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "password_reset_tokens")
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kullanici_id", nullable = false)
    private User user;

    @Column(name = "token", nullable = false, unique = true, length = 255)
    private String token;

    @Column(name = "son_kullanma_tarihi", nullable = false)
    private LocalDateTime sonKullanmaTarihi;

    @Column(name = "kullanildi_mi", nullable = false)
    private Boolean kullanildiMi = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public LocalDateTime getSonKullanmaTarihi() { return sonKullanmaTarihi; }
    public void setSonKullanmaTarihi(LocalDateTime sonKullanmaTarihi) { this.sonKullanmaTarihi = sonKullanmaTarihi; }
    public Boolean getKullanildiMi() { return kullanildiMi; }
    public void setKullanildiMi(Boolean kullanildiMi) { this.kullanildiMi = kullanildiMi; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
