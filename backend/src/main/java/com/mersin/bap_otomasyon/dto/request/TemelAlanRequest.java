package com.mersin.bap_otomasyon.dto.request;

public class TemelAlanRequest {
    private String kod;
    private String ad;
    private Boolean aktif;

    public String getKod() { return kod; }
    public void setKod(String kod) { this.kod = kod; }
    public String getAd() { return ad; }
    public void setAd(String ad) { this.ad = ad; }
    public Boolean getAktif() { return aktif; }
    public void setAktif(Boolean aktif) { this.aktif = aktif; }
}