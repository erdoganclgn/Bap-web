package com.mersin.bap_otomasyon.entity;

import com.mersin.bap_otomasyon.entity.enums.KalemKodu;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "budget_items")
public class BudgetItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proje_id", nullable = false)
    private Project project;

    @Column(name = "kalem_kodu", nullable = false)
    private KalemKodu kalemKodu;

    @Column(name = "aciklama", length = 500)
    private String aciklama;

    @Column(name = "birim_fiyat", nullable = false, precision = 12, scale = 2)
    private BigDecimal birimFiyat;

    @Column(name = "miktar", nullable = false, precision = 10, scale = 2)
    private BigDecimal miktar = BigDecimal.ONE;

    @Column(name = "toplam_tutar", nullable = false, precision = 12, scale = 2)
    private BigDecimal toplamTutar;

    @Column(name = "kdv_orani", nullable = false, precision = 5, scale = 2)
    private BigDecimal kdvOrani = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proforma_fatura_id")
    private ProjectDocument proformaFatura;

    @Column(name = "onayli_mi", nullable = false)
    private Boolean onayliMi = false;

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
    public KalemKodu getKalemKodu() { return kalemKodu; }
    public void setKalemKodu(KalemKodu kalemKodu) { this.kalemKodu = kalemKodu; }
    public String getAciklama() { return aciklama; }
    public void setAciklama(String aciklama) { this.aciklama = aciklama; }
    public BigDecimal getBirimFiyat() { return birimFiyat; }
    public void setBirimFiyat(BigDecimal birimFiyat) { this.birimFiyat = birimFiyat; }
    public BigDecimal getMiktar() { return miktar; }
    public void setMiktar(BigDecimal miktar) { this.miktar = miktar; }
    public BigDecimal getToplamTutar() { return toplamTutar; }
    public void setToplamTutar(BigDecimal toplamTutar) { this.toplamTutar = toplamTutar; }
    public BigDecimal getKdvOrani() { return kdvOrani; }
    public void setKdvOrani(BigDecimal kdvOrani) { this.kdvOrani = kdvOrani; }
    public ProjectDocument getProformaFatura() { return proformaFatura; }
    public void setProformaFatura(ProjectDocument proformaFatura) { this.proformaFatura = proformaFatura; }
    public Boolean getOnayliMi() { return onayliMi; }
    public void setOnayliMi(Boolean onayliMi) { this.onayliMi = onayliMi; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
