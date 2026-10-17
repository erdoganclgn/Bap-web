package com.mersin.bap_otomasyon.dto.request;

import java.math.BigDecimal;

public class ProjectTypeUpdateRequest {
    private String ad;
    private BigDecimal butceTavani;
    private Integer minSureAy;
    private Integer maxSureAy;
    private BigDecimal ekButceOran;
    private Integer ekSureAy;
    private Boolean performansArtisiUygulanir;
    private Boolean bursiyerOlabilir;
    private Boolean yayinSartiZorunlu;
    private Integer kariyerBoyuKez;
    private Integer yildaKez;
    private Integer eszamanliLimit;
    private Boolean hakemAtanabilir;
    private Boolean sozluSunumIstenebilir;
    private Boolean araRaporGerekli;
    private Boolean kongreDestegiVar;
    private Boolean protokolGerekli;
    private Boolean rektorOnayiGerekli;
    private Boolean aktif;

    public String getAd() { return ad; }
    public void setAd(String ad) { this.ad = ad; }
    public BigDecimal getButceTavani() { return butceTavani; }
    public void setButceTavani(BigDecimal butceTavani) { this.butceTavani = butceTavani; }
    public Integer getMinSureAy() { return minSureAy; }
    public void setMinSureAy(Integer minSureAy) { this.minSureAy = minSureAy; }
    public Integer getMaxSureAy() { return maxSureAy; }
    public void setMaxSureAy(Integer maxSureAy) { this.maxSureAy = maxSureAy; }
    public BigDecimal getEkButceOran() { return ekButceOran; }
    public void setEkButceOran(BigDecimal ekButceOran) { this.ekButceOran = ekButceOran; }
    public Integer getEkSureAy() { return ekSureAy; }
    public void setEkSureAy(Integer ekSureAy) { this.ekSureAy = ekSureAy; }
    public Boolean getPerformansArtisiUygulanir() { return performansArtisiUygulanir; }
    public void setPerformansArtisiUygulanir(Boolean performansArtisiUygulanir) { this.performansArtisiUygulanir = performansArtisiUygulanir; }
    public Boolean getBursiyerOlabilir() { return bursiyerOlabilir; }
    public void setBursiyerOlabilir(Boolean bursiyerOlabilir) { this.bursiyerOlabilir = bursiyerOlabilir; }
    public Boolean getYayinSartiZorunlu() { return yayinSartiZorunlu; }
    public void setYayinSartiZorunlu(Boolean yayinSartiZorunlu) { this.yayinSartiZorunlu = yayinSartiZorunlu; }
    public Integer getKariyerBoyuKez() { return kariyerBoyuKez; }
    public void setKariyerBoyuKez(Integer kariyerBoyuKez) { this.kariyerBoyuKez = kariyerBoyuKez; }
    public Integer getYildaKez() { return yildaKez; }
    public void setYildaKez(Integer yildaKez) { this.yildaKez = yildaKez; }
    public Integer getEszamanliLimit() { return eszamanliLimit; }
    public void setEszamanliLimit(Integer eszamanliLimit) { this.eszamanliLimit = eszamanliLimit; }
    public Boolean getHakemAtanabilir() { return hakemAtanabilir; }
    public void setHakemAtanabilir(Boolean hakemAtanabilir) { this.hakemAtanabilir = hakemAtanabilir; }
    public Boolean getSozluSunumIstenebilir() { return sozluSunumIstenebilir; }
    public void setSozluSunumIstenebilir(Boolean sozluSunumIstenebilir) { this.sozluSunumIstenebilir = sozluSunumIstenebilir; }
    public Boolean getAraRaporGerekli() { return araRaporGerekli; }
    public void setAraRaporGerekli(Boolean araRaporGerekli) { this.araRaporGerekli = araRaporGerekli; }
    public Boolean getKongreDestegiVar() { return kongreDestegiVar; }
    public void setKongreDestegiVar(Boolean kongreDestegiVar) { this.kongreDestegiVar = kongreDestegiVar; }
    public Boolean getProtokolGerekli() { return protokolGerekli; }
    public void setProtokolGerekli(Boolean protokolGerekli) { this.protokolGerekli = protokolGerekli; }
    public Boolean getRektorOnayiGerekli() { return rektorOnayiGerekli; }
    public void setRektorOnayiGerekli(Boolean rektorOnayiGerekli) { this.rektorOnayiGerekli = rektorOnayiGerekli; }
    public Boolean getAktif() { return aktif; }
    public void setAktif(Boolean aktif) { this.aktif = aktif; }
}
