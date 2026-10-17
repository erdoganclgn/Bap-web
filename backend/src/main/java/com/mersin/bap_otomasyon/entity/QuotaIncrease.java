package com.mersin.bap_otomasyon.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "quota_increases")
public class QuotaIncrease {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kullanici_id", nullable = false)
    private User user;

    @Column(name = "proje_turu", nullable = false, length = 10)
    private String projeTuru;

    @Column(name = "artis_miktari", nullable = false)
    private Integer artisMiktari = 1;

    @Column(name = "gerekce", length = 500)
    private String gerekce;

    @Column(name = "baslangic_tarihi", nullable = false)
    private LocalDate baslangicTarihi;

    @Column(name = "bitis_tarihi", nullable = false)
    private LocalDate bitisTarihi;

    @Column(name = "komisyon_karar_tarihi")
    private LocalDate komisyonKararTarihi;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getProjeTuru() { return projeTuru; }
    public void setProjeTuru(String projeTuru) { this.projeTuru = projeTuru; }
    public Integer getArtisMiktari() { return artisMiktari; }
    public void setArtisMiktari(Integer artisMiktari) { this.artisMiktari = artisMiktari; }
    public String getGerekce() { return gerekce; }
    public void setGerekce(String gerekce) { this.gerekce = gerekce; }
    public LocalDate getBaslangicTarihi() { return baslangicTarihi; }
    public void setBaslangicTarihi(LocalDate baslangicTarihi) { this.baslangicTarihi = baslangicTarihi; }
    public LocalDate getBitisTarihi() { return bitisTarihi; }
    public void setBitisTarihi(LocalDate bitisTarihi) { this.bitisTarihi = bitisTarihi; }
    public LocalDate getKomisyonKararTarihi() { return komisyonKararTarihi; }
    public void setKomisyonKararTarihi(LocalDate komisyonKararTarihi) { this.komisyonKararTarihi = komisyonKararTarihi; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
