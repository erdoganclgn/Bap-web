package com.mersin.bap_otomasyon.dto.response;

import com.mersin.bap_otomasyon.entity.ProjectResearcher;
import com.mersin.bap_otomasyon.entity.enums.ArastirmaciRol;

import java.time.LocalDate;
import java.util.UUID;

public class ResearcherResponse {
    private UUID id;
    private UUID projeId;
    private String projeNo;
    private UUID kullaniciId;
    private String adSoyad;
    private String unvan;
    private ArastirmaciRol rol;
    private LocalDate baslamaTarihi;
    private LocalDate bitisTarihi;

    public static ResearcherResponse from(ProjectResearcher entity) {
        ResearcherResponse dto = new ResearcherResponse();
        dto.id = entity.getId();
        dto.projeId = entity.getProject().getId();
        dto.projeNo = entity.getProject().getProjeNo();
        dto.kullaniciId = entity.getUser().getId();
        dto.adSoyad = entity.getUser().getAdSoyad();
        dto.unvan = entity.getUser().getUnvan();
        dto.rol = entity.getRol();
        dto.baslamaTarihi = entity.getBaslamaTarihi();
        dto.bitisTarihi = entity.getBitisTarihi();
        return dto;
    }

    public UUID getId() { return id; }
    public UUID getProjeId() { return projeId; }
    public String getProjeNo() { return projeNo; }
    public UUID getKullaniciId() { return kullaniciId; }
    public String getAdSoyad() { return adSoyad; }
    public String getUnvan() { return unvan; }
    public ArastirmaciRol getRol() { return rol; }
    public LocalDate getBaslamaTarihi() { return baslamaTarihi; }
    public LocalDate getBitisTarihi() { return bitisTarihi; }
}
