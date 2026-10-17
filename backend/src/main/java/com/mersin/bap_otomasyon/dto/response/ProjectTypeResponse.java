package com.mersin.bap_otomasyon.dto.response;

import com.mersin.bap_otomasyon.entity.ProjectType;

import java.math.BigDecimal;

public class ProjectTypeResponse {
    private String kod;
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

    public static ProjectTypeResponse from(ProjectType entity) {
        ProjectTypeResponse dto = new ProjectTypeResponse();
        dto.kod = entity.getKod();
        dto.ad = entity.getAd();
        dto.butceTavani = entity.getButceTavani();
        dto.minSureAy = entity.getMinSureAy();
        dto.maxSureAy = entity.getMaxSureAy();
        dto.ekButceOran = entity.getEkButceOran();
        dto.ekSureAy = entity.getEkSureAy();
        dto.performansArtisiUygulanir = entity.getPerformansArtisiUygulanir();
        dto.bursiyerOlabilir = entity.getBursiyerOlabilir();
        dto.yayinSartiZorunlu = entity.getYayinSartiZorunlu();
        dto.kariyerBoyuKez = entity.getKariyerBoyuKez();
        dto.yildaKez = entity.getYildaKez();
        dto.eszamanliLimit = entity.getEszamanliLimit();
        dto.hakemAtanabilir = entity.getHakemAtanabilir();
        dto.sozluSunumIstenebilir = entity.getSozluSunumIstenebilir();
        dto.araRaporGerekli = entity.getAraRaporGerekli();
        dto.kongreDestegiVar = entity.getKongreDestegiVar();
        dto.protokolGerekli = entity.getProtokolGerekli();
        dto.rektorOnayiGerekli = entity.getRektorOnayiGerekli();
        return dto;
    }

    public String getKod() { return kod; }
    public String getAd() { return ad; }
    public BigDecimal getButceTavani() { return butceTavani; }
    public Integer getMinSureAy() { return minSureAy; }
    public Integer getMaxSureAy() { return maxSureAy; }
    public BigDecimal getEkButceOran() { return ekButceOran; }
    public Integer getEkSureAy() { return ekSureAy; }
    public Boolean getPerformansArtisiUygulanir() { return performansArtisiUygulanir; }
    public Boolean getBursiyerOlabilir() { return bursiyerOlabilir; }
    public Boolean getYayinSartiZorunlu() { return yayinSartiZorunlu; }
    public Integer getKariyerBoyuKez() { return kariyerBoyuKez; }
    public Integer getYildaKez() { return yildaKez; }
    public Integer getEszamanliLimit() { return eszamanliLimit; }
    public Boolean getHakemAtanabilir() { return hakemAtanabilir; }
    public Boolean getSozluSunumIstenebilir() { return sozluSunumIstenebilir; }
    public Boolean getAraRaporGerekli() { return araRaporGerekli; }
    public Boolean getKongreDestegiVar() { return kongreDestegiVar; }
    public Boolean getProtokolGerekli() { return protokolGerekli; }
    public Boolean getRektorOnayiGerekli() { return rektorOnayiGerekli; }
}
