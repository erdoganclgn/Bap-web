package com.mersin.bap_otomasyon.dto.response;

import com.mersin.bap_otomasyon.entity.User;
import com.mersin.bap_otomasyon.entity.enums.KullaniciRol;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class UserResponse {
    private UUID id;
    private String email;
    private String adSoyad;
    private String unvan;
    private KullaniciRol rol;
    private UUID birimId;
    private String telefon;
    private String orcid;
    private Boolean aktif;
    private LocalDate yasakBitisTarihi;
    private LocalDateTime createdAt;

    public static UserResponse from(User user) {
        UserResponse response = new UserResponse();
        response.id = user.getId();
        response.email = user.getEmail();
        response.adSoyad = user.getAdSoyad();
        response.unvan = user.getUnvan();
        response.rol = user.getRol();
        response.birimId = user.getBirim() != null ? user.getBirim().getId() : null;
        response.telefon = user.getTelefon();
        response.orcid = user.getOrcid();
        response.aktif = user.getAktif();
        response.yasakBitisTarihi = user.getYasakBitisTarihi();
        response.createdAt = user.getCreatedAt();
        return response;
    }

    public UUID getId() { return id; }
    public String getEmail() { return email; }
    public String getAdSoyad() { return adSoyad; }
    public String getUnvan() { return unvan; }
    public KullaniciRol getRol() { return rol; }
    public UUID getBirimId() { return birimId; }
    public String getTelefon() { return telefon; }
    public String getOrcid() { return orcid; }
    public Boolean getAktif() { return aktif; }
    public LocalDate getYasakBitisTarihi() { return yasakBitisTarihi; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
