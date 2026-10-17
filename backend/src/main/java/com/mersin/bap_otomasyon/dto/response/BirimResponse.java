package com.mersin.bap_otomasyon.dto.response;

import com.mersin.bap_otomasyon.entity.Birim;
import com.mersin.bap_otomasyon.entity.enums.BirimTuru;

import java.util.UUID;

public class BirimResponse {
    private UUID id;
    private String ad;
    private String kod;
    private BirimTuru birimTuru;
    private UUID ustBirimId;
    private String ustBirimAd;
    private Boolean aktif;

    public static BirimResponse from(Birim entity) {
        BirimResponse dto = new BirimResponse();
        dto.id = entity.getId();
        dto.ad = entity.getAd();
        dto.kod = entity.getKod();
        dto.birimTuru = entity.getBirimTuru();
        dto.ustBirimId = entity.getUstBirim() != null ? entity.getUstBirim().getId() : null;
        dto.ustBirimAd = entity.getUstBirim() != null ? entity.getUstBirim().getAd() : null;
        dto.aktif = entity.getAktif();
        return dto;
    }

    public UUID getId() { return id; }
    public String getAd() { return ad; }
    public String getKod() { return kod; }
    public BirimTuru getBirimTuru() { return birimTuru; }
    public UUID getUstBirimId() { return ustBirimId; }
    public String getUstBirimAd() { return ustBirimAd; }
    public Boolean getAktif() { return aktif; }
}