package com.mersin.bap_otomasyon.dto.response;

import com.mersin.bap_otomasyon.entity.TemelAlan;

import java.util.UUID;

public class TemelAlanResponse {
    private UUID id;
    private String kod;
    private String ad;
    private Boolean aktif;

    public static TemelAlanResponse from(TemelAlan entity) {
        TemelAlanResponse dto = new TemelAlanResponse();
        dto.id = entity.getId();
        dto.kod = entity.getKod();
        dto.ad = entity.getAd();
        dto.aktif = entity.getAktif();
        return dto;
    }

    public UUID getId() { return id; }
    public String getKod() { return kod; }
    public String getAd() { return ad; }
    public Boolean getAktif() { return aktif; }
}