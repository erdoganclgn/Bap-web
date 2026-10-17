package com.mersin.bap_otomasyon.entity;

import com.mersin.bap_otomasyon.entity.enums.BildirimTipi;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kullanici_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "tip", nullable = false)
    private BildirimTipi tip = BildirimTipi.Sistem;

    @Column(name = "baslik", nullable = false, length = 255)
    private String baslik;

    @Column(name = "mesaj", nullable = false, columnDefinition = "TEXT")
    private String mesaj;

    @Column(name = "okundu_mi", nullable = false)
    private Boolean okunduMu = false;

    @Column(name = "ilgili_url", length = 500)
    private String ilgiliUrl;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public BildirimTipi getTip() { return tip; }
    public void setTip(BildirimTipi tip) { this.tip = tip; }
    public String getBaslik() { return baslik; }
    public void setBaslik(String baslik) { this.baslik = baslik; }
    public String getMesaj() { return mesaj; }
    public void setMesaj(String mesaj) { this.mesaj = mesaj; }
    public Boolean getOkunduMu() { return okunduMu; }
    public void setOkunduMu(Boolean okunduMu) { this.okunduMu = okunduMu; }
    public String getIlgiliUrl() { return ilgiliUrl; }
    public void setIlgiliUrl(String ilgiliUrl) { this.ilgiliUrl = ilgiliUrl; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
