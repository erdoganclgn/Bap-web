package com.mersin.bap_otomasyon.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "closure_criteria")
public class ClosureCriteria {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "olcut_no", nullable = false)
    private Integer olcutNo;

    @Column(name = "ad", nullable = false, length = 255)
    private String ad;

    @Column(name = "aciklama", columnDefinition = "TEXT")
    private String aciklama;

    @Column(name = "gecerli_alan_gruplari", columnDefinition = "TEXT[]", nullable = false)
    private String[] gecerliAlanGruplari;

    @Column(name = "gecerli_proje_turleri", columnDefinition = "VARCHAR(10)[]", nullable = false)
    private String[] gecerliProjeTurleri;

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
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
