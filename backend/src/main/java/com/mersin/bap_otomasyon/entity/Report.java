package com.mersin.bap_otomasyon.entity;

import com.mersin.bap_otomasyon.entity.enums.RaporDurum;
import com.mersin.bap_otomasyon.entity.enums.RaporTuru;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proje_id", nullable = false)
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(name = "rapor_turu", nullable = false)
    private RaporTuru raporTuru;

    @Column(name = "donem_no")
    private Integer donemNo;

    @Column(name = "baslik", nullable = false, length = 500)
    private String baslik;

    @Column(name = "dosya_yolu", length = 500)
    private String dosyaYolu;

    @Column(name = "teslim_tarihi")
    private LocalDate teslimTarihi;

    @Enumerated(EnumType.STRING)
    @Column(name = "durum", nullable = false)
    private RaporDurum durum = RaporDurum.Beklemede;

    @Column(name = "degerlendirme_notu", columnDefinition = "TEXT")
    private String degerlendirmeNotu;

    @Column(name = "gecikme_gun", nullable = false)
    private Integer gecikmeGun = 0;

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
    public RaporTuru getRaporTuru() { return raporTuru; }
    public void setRaporTuru(RaporTuru raporTuru) { this.raporTuru = raporTuru; }
    public Integer getDonemNo() { return donemNo; }
    public void setDonemNo(Integer donemNo) { this.donemNo = donemNo; }
    public String getBaslik() { return baslik; }
    public void setBaslik(String baslik) { this.baslik = baslik; }
    public String getDosyaYolu() { return dosyaYolu; }
    public void setDosyaYolu(String dosyaYolu) { this.dosyaYolu = dosyaYolu; }
    public LocalDate getTeslimTarihi() { return teslimTarihi; }
    public void setTeslimTarihi(LocalDate teslimTarihi) { this.teslimTarihi = teslimTarihi; }
    public RaporDurum getDurum() { return durum; }
    public void setDurum(RaporDurum durum) { this.durum = durum; }
    public String getDegerlendirmeNotu() { return degerlendirmeNotu; }
    public void setDegerlendirmeNotu(String degerlendirmeNotu) { this.degerlendirmeNotu = degerlendirmeNotu; }
    public Integer getGecikmeGun() { return gecikmeGun; }
    public void setGecikmeGun(Integer gecikmeGun) { this.gecikmeGun = gecikmeGun; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
