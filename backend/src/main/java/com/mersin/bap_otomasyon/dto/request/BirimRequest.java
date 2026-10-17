package com.mersin.bap_otomasyon.dto.request;

import com.mersin.bap_otomasyon.entity.enums.BirimTuru;

import java.util.UUID;

public class BirimRequest {
    private String ad;
    private String kod;
    private UUID ustBirimId;
    private BirimTuru birimTuru;
    private Boolean aktif;

    public String getAd() { return ad; }
    public void setAd(String ad) { this.ad = ad; }
    public String getKod() { return kod; }
    public void setKod(String kod) { this.kod = kod; }
    public UUID getUstBirimId() { return ustBirimId; }
    public void setUstBirimId(UUID ustBirimId) { this.ustBirimId = ustBirimId; }
    public BirimTuru getBirimTuru() { return birimTuru; }
    public void setBirimTuru(BirimTuru birimTuru) { this.birimTuru = birimTuru; }
    public Boolean getAktif() { return aktif; }
    public void setAktif(Boolean aktif) { this.aktif = aktif; }
}