package com.mersin.bap_otomasyon.entity;

import com.mersin.bap_otomasyon.entity.enums.HakedisDurum;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "bursary_payments",
       uniqueConstraints = @UniqueConstraint(columnNames = {"bursiyer_id", "ay", "yil"}))
public class BursaryPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proje_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bursiyer_id", nullable = false)
    private User bursiyer;

    @Column(name = "ay", nullable = false)
    private Integer ay;

    @Column(name = "yil", nullable = false)
    private Integer yil;

    @Column(name = "brut_tutar", nullable = false, precision = 10, scale = 2)
    private BigDecimal brutTutar;

    @Column(name = "sgk_kesintisi", nullable = false, precision = 10, scale = 2)
    private BigDecimal sgkKesintisi = BigDecimal.ZERO;

    @Column(name = "net_tutar", nullable = false, precision = 10, scale = 2)
    private BigDecimal netTutar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hakedis_belgesi_id")
    private ProjectDocument hakedisBelgesi;

    @Column(name = "odeme_tarihi")
    private LocalDate odemeTarihi;

    @Enumerated(EnumType.STRING)
    @Column(name = "durum", nullable = false)
    private HakedisDurum durum = HakedisDurum.Beklemede;

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
    public User getBursiyer() { return bursiyer; }
    public void setBursiyer(User bursiyer) { this.bursiyer = bursiyer; }
    public Integer getAy() { return ay; }
    public void setAy(Integer ay) { this.ay = ay; }
    public Integer getYil() { return yil; }
    public void setYil(Integer yil) { this.yil = yil; }
    public BigDecimal getBrutTutar() { return brutTutar; }
    public void setBrutTutar(BigDecimal brutTutar) { this.brutTutar = brutTutar; }
    public BigDecimal getSgkKesintisi() { return sgkKesintisi; }
    public void setSgkKesintisi(BigDecimal sgkKesintisi) { this.sgkKesintisi = sgkKesintisi; }
    public BigDecimal getNetTutar() { return netTutar; }
    public void setNetTutar(BigDecimal netTutar) { this.netTutar = netTutar; }
    public ProjectDocument getHakedisBelgesi() { return hakedisBelgesi; }
    public void setHakedisBelgesi(ProjectDocument hakedisBelgesi) { this.hakedisBelgesi = hakedisBelgesi; }
    public LocalDate getOdemeTarihi() { return odemeTarihi; }
    public void setOdemeTarihi(LocalDate odemeTarihi) { this.odemeTarihi = odemeTarihi; }
    public HakedisDurum getDurum() { return durum; }
    public void setDurum(HakedisDurum durum) { this.durum = durum; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
