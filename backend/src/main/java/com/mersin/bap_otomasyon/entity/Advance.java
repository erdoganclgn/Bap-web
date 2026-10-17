package com.mersin.bap_otomasyon.entity;

import com.mersin.bap_otomasyon.entity.enums.AvansDurum;
import com.mersin.bap_otomasyon.entity.enums.AvansTur;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "advances")
public class Advance {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proje_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "satin_alma_talebi_id")
    private PurchaseRequest satinAlmaTalebi;

    @Enumerated(EnumType.STRING)
    @Column(name = "avans_turu", nullable = false)
    private AvansTur avansTur;

    @Column(name = "tutar", nullable = false, precision = 12, scale = 2)
    private BigDecimal tutar;

    @Column(name = "tarih", nullable = false)
    private LocalDate tarih = LocalDate.now();

    @Column(name = "mahsup_tarihi")
    private LocalDate mahsupTarihi;

    @Column(name = "mahsup_edildi_mi", nullable = false)
    private Boolean mahsupEdildiMi = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mahsup_belgesi_id")
    private ProjectDocument mahsupBelgesi;

    @Enumerated(EnumType.STRING)
    @Column(name = "durum", nullable = false)
    private AvansDurum durum = AvansDurum.Acik;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }
    public PurchaseRequest getSatinAlmaTalebi() { return satinAlmaTalebi; }
    public void setSatinAlmaTalebi(PurchaseRequest satinAlmaTalebi) { this.satinAlmaTalebi = satinAlmaTalebi; }
    public AvansTur getAvansTur() { return avansTur; }
    public void setAvansTur(AvansTur avansTur) { this.avansTur = avansTur; }
    public BigDecimal getTutar() { return tutar; }
    public void setTutar(BigDecimal tutar) { this.tutar = tutar; }
    public LocalDate getTarih() { return tarih; }
    public void setTarih(LocalDate tarih) { this.tarih = tarih; }
    public LocalDate getMahsupTarihi() { return mahsupTarihi; }
    public void setMahsupTarihi(LocalDate mahsupTarihi) { this.mahsupTarihi = mahsupTarihi; }
    public Boolean getMahsupEdildiMi() { return mahsupEdildiMi; }
    public void setMahsupEdildiMi(Boolean mahsupEdildiMi) { this.mahsupEdildiMi = mahsupEdildiMi; }
    public ProjectDocument getMahsupBelgesi() { return mahsupBelgesi; }
    public void setMahsupBelgesi(ProjectDocument mahsupBelgesi) { this.mahsupBelgesi = mahsupBelgesi; }
    public AvansDurum getDurum() { return durum; }
    public void setDurum(AvansDurum durum) { this.durum = durum; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
