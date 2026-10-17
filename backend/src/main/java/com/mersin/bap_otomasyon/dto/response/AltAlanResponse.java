package com.mersin.bap_otomasyon.dto.response;

import com.mersin.bap_otomasyon.entity.AltAlan;

import java.util.UUID;

public class AltAlanResponse {
    private UUID id;
    private UUID temelAlanId;
    private String kod;
    private String ad;
    private Boolean aktif;

    public static AltAlanResponse from(AltAlan entity) {
        AltAlanResponse dto = new AltAlanResponse();
        dto.id = entity.getId();
        dto.temelAlanId = entity.getTemelAlan().getId();
        dto.kod = entity.getKod();
        dto.ad = entity.getAd();
        dto.aktif = entity.getAktif();
        return dto;
    }

    public UUID getId() { return id; }
    public UUID getTemelAlanId() { return temelAlanId; }
    public String getKod() { return kod; }
    public String getAd() { return ad; }
    public Boolean getAktif() { return aktif; }
}
