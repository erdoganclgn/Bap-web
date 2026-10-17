package com.mersin.bap_otomasyon.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mersin.bap_otomasyon.entity.enums.KullaniciRol;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "ad_soyad", nullable = false, length = 255)
    private String adSoyad;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private KullaniciRol rol;

    @Column(name = "unvan", length = 100)
    private String unvan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "birim_id")
    private Birim birim;

    @Column(name = "orcid", length = 50)
    private String orcid;

    @Column(name = "researcher_id", length = 50)
    private String researcherId;

    @Column(name = "scopus_id", length = 50)
    private String scopusId;

    @Column(name = "google_scholar", length = 255)
    private String googleScholar;

    @Column(name = "telefon", length = 20)
    private String telefon;

    @Column(name = "apbs_guncel", nullable = false)
    private Boolean apbsGuncel = false;

    @Column(name = "yoksis_guncel", nullable = false)
    private Boolean yoksisGuncel = false;

    @Column(name = "yasak_bitis_tarihi")
    private LocalDate yasakBitisTarihi;

    @Column(name = "tez_yasagi", nullable = false)
    private Boolean tezYasagi = false;

    @Column(name = "aktif", nullable = false)
    private Boolean aktif = true;

    @Column(name = "refresh_token", length = 500)
    private String refreshToken;

    @Column(name = "son_giris_tarihi")
    private LocalDateTime sonGirisTarihi;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<QuotaIncrease> quotaIncreases;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    @JsonIgnore
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getAdSoyad() { return adSoyad; }
    public void setAdSoyad(String adSoyad) { this.adSoyad = adSoyad; }
    public KullaniciRol getRol() { return rol; }
    public void setRol(KullaniciRol rol) { this.rol = rol; }
    public String getUnvan() { return unvan; }
    public void setUnvan(String unvan) { this.unvan = unvan; }
    public Birim getBirim() { return birim; }
    public void setBirim(Birim birim) { this.birim = birim; }
    public String getOrcid() { return orcid; }
    public void setOrcid(String orcid) { this.orcid = orcid; }
    public String getResearcherId() { return researcherId; }
    public void setResearcherId(String researcherId) { this.researcherId = researcherId; }
    public String getScopusId() { return scopusId; }
    public void setScopusId(String scopusId) { this.scopusId = scopusId; }
    public String getGoogleScholar() { return googleScholar; }
    public void setGoogleScholar(String googleScholar) { this.googleScholar = googleScholar; }
    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }
    public Boolean getApbsGuncel() { return apbsGuncel; }
    public void setApbsGuncel(Boolean apbsGuncel) { this.apbsGuncel = apbsGuncel; }
    public Boolean getYoksisGuncel() { return yoksisGuncel; }
    public void setYoksisGuncel(Boolean yoksisGuncel) { this.yoksisGuncel = yoksisGuncel; }
    public LocalDate getYasakBitisTarihi() { return yasakBitisTarihi; }
    public void setYasakBitisTarihi(LocalDate yasakBitisTarihi) { this.yasakBitisTarihi = yasakBitisTarihi; }
    public Boolean getTezYasagi() { return tezYasagi; }
    public void setTezYasagi(Boolean tezYasagi) { this.tezYasagi = tezYasagi; }
    public Boolean getAktif() { return aktif; }
    public void setAktif(Boolean aktif) { this.aktif = aktif; }
    @JsonIgnore
    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
    public LocalDateTime getSonGirisTarihi() { return sonGirisTarihi; }
    public void setSonGirisTarihi(LocalDateTime sonGirisTarihi) { this.sonGirisTarihi = sonGirisTarihi; }
    public List<QuotaIncrease> getQuotaIncreases() { return quotaIncreases; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
