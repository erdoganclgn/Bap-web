package com.mersin.bap_otomasyon.dto.request;

public class ClosureCriteriaRequest {
    private Integer olcutNo;
    private String ad;
    private String aciklama;
    private String[] gecerliAlanGruplari;
    private String[] gecerliProjeTurleri;
    private Boolean aktif;

    public Integer getOlcutNo() { return olcutNo; }
    public void setOlcutNo(Integer olcutNo) { this.olcutNo = olcutNo; }
    public String getAd() { return ad; }
    public void setAd(String ad) { this.ad = ad; }
    public String getAciklama() { return aciklama; }
    public void setAciklama(String aciklama) { this.aciklama = aciklama; }
    public String[] getGecerliAlanGruplari() { return gecerliAlanGruplari; }
    public void setGecerliAlanGruplari(String[] gecerliAlanGruplari) { this.gecerliAlanGruplari = gecerliAlanGruplari; }
    public String[] getGecerliProjeTurleri() { return gecerliProjeTurleri; }
    public void setGecerliProjeTurleri(String[] gecerliProjeTurleri) { this.gecerliProjeTurleri = gecerliProjeTurleri; }
    public Boolean getAktif() { return aktif; }
    public void setAktif(Boolean aktif) { this.aktif = aktif; }
}