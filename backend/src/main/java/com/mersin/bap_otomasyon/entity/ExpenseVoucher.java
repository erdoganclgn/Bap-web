package com.mersin.bap_otomasyon.entity;

import com.mersin.bap_otomasyon.entity.enums.HarcamaPusulaDurum;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "expense_vouchers")
public class ExpenseVoucher {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proje_id", nullable = false)
    private Project project;

    @Column(name = "alici_adi", nullable = false, length = 255)
    private String aliciAdi;

    @Column(name = "tutar", nullable = false, precision = 12, scale = 2)
    private BigDecimal tutar;

    @Column(name = "vergi_kesintisi", precision = 12, scale = 2)
    private BigDecimal vergiKesintisi = BigDecimal.ZERO;

    @Column(name = "net_tutar", nullable = false, precision = 12, scale = 2)
    private BigDecimal netTutar;

    @Column(name = "gerekce", columnDefinition = "TEXT")
    private String gerekce;

    @Column(name = "tarih")
    private LocalDate tarih;

    @Enumerated(EnumType.STRING)
    @Column(name = "durum")
    private HarcamaPusulaDurum durum = HarcamaPusulaDurum.Hazir;

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
    public String getAliciAdi() { return aliciAdi; }
    public void setAliciAdi(String aliciAdi) { this.aliciAdi = aliciAdi; }
    public BigDecimal getTutar() { return tutar; }
    public void setTutar(BigDecimal tutar) { this.tutar = tutar; }
    public BigDecimal getVergiKesintisi() { return vergiKesintisi; }
    public void setVergiKesintisi(BigDecimal vergiKesintisi) { this.vergiKesintisi = vergiKesintisi; }
    public BigDecimal getNetTutar() { return netTutar; }
    public void setNetTutar(BigDecimal netTutar) { this.netTutar = netTutar; }
    public String getGerekce() { return gerekce; }
    public void setGerekce(String gerekce) { this.gerekce = gerekce; }
    public LocalDate getTarih() { return tarih; }
    public void setTarih(LocalDate tarih) { this.tarih = tarih; }
    public HarcamaPusulaDurum getDurum() { return durum; }
    public void setDurum(HarcamaPusulaDurum durum) { this.durum = durum; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
