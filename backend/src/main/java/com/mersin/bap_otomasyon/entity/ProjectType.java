package com.mersin.bap_otomasyon.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "project_types")
public class ProjectType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "kod", nullable = false, unique = true, length = 10)
    private String kod;

    @Column(name = "ad", nullable = false, length = 255)
    private String ad;

    @Column(name = "butce_tavani", nullable = false, precision = 12, scale = 2)
    private BigDecimal butceTavani;

    @Column(name = "min_sure_ay", nullable = false)
    private Integer minSureAy = 0;

    @Column(name = "max_sure_ay", nullable = false)
    private Integer maxSureAy;

    @Column(name = "ek_butce_oran", nullable = false, precision = 5, scale = 2)
    private BigDecimal ekButceOran = BigDecimal.ZERO;

    @Column(name = "ek_sure_ay", nullable = false)
    private Integer ekSureAy = 0;

    @Column(name = "performans_artisi_uygulanir", nullable = false)
    private Boolean performansArtisiUygulanir = false;

    @Column(name = "bursiyer_olabilir", nullable = false)
    private Boolean bursiyerOlabilir = false;

    @Column(name = "yayin_sarti_zorunlu", nullable = false)
    private Boolean yayinSartiZorunlu = true;

    @Column(name = "kariyer_boyu_kez", nullable = false)
    private Integer kariyerBoyuKez = 0;

    @Column(name = "yilda_kez", nullable = false)
    private Integer yildaKez = 0;

    @Column(name = "eszamanli_limit", nullable = false)
    private Integer eszamanliLimit = 1;

    @Column(name = "hakem_atanabilir", nullable = false)
    private Boolean hakemAtanabilir = false;

    @Column(name = "sozlu_sunum_istenebilir", nullable = false)
    private Boolean sozluSunumIstenebilir = false;

    @Column(name = "ara_rapor_gerekli", nullable = false)
    private Boolean araRaporGerekli = false;

    @Column(name = "kongre_destegi_var", nullable = false)
    private Boolean kongreDestegiVar = false;

    @Column(name = "protokol_gerekli", nullable = false)
    private Boolean protokolGerekli = true;

    @Column(name = "rektor_onayi_gerekli", nullable = false)
    private Boolean rektorOnayiGerekli = false;

    @Column(name = "aktif", nullable = false)
    private Boolean aktif = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getKod() { return kod; }
    public void setKod(String kod) { this.kod = kod; }
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
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
