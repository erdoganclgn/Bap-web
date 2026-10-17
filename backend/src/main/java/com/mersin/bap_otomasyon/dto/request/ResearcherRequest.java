package com.mersin.bap_otomasyon.dto.request;

import java.time.LocalDate;
import java.util.UUID;

public class ResearcherRequest {
    private UUID userId;
    private String rol;
    private LocalDate baslamaTarihi;
    private LocalDate bitisTarihi;

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public LocalDate getBaslamaTarihi() { return baslamaTarihi; }
    public void setBaslamaTarihi(LocalDate baslamaTarihi) { this.baslamaTarihi = baslamaTarihi; }
    public LocalDate getBitisTarihi() { return bitisTarihi; }
    public void setBitisTarihi(LocalDate bitisTarihi) { this.bitisTarihi = bitisTarihi; }
}