package com.mersin.bap_otomasyon.entity;

import com.mersin.bap_otomasyon.entity.enums.SatinAlmaDurum;
import com.mersin.bap_otomasyon.entity.enums.TalepTur;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "purchase_requests")
public class PurchaseRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proje_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "butce_kalem_id", nullable = false)
    private BudgetItem butceKalem;

    @Enumerated(EnumType.STRING)
    @Column(name = "talep_turu", nullable = false)
    private TalepTur talepTur;

    @Column(name = "aciklama", length = 500)
    private String aciklama;

    @Column(name = "tutar", nullable = false, precision = 12, scale = 2)
    private BigDecimal tutar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teknik_sartname_id")
    private ProjectDocument teknikSartname;

    @Enumerated(EnumType.STRING)
    @Column(name = "durum", nullable = false)
    private SatinAlmaDurum durum = SatinAlmaDurum.Beklemede;

    @Column(name = "onay_tarihi")
    private LocalDate onayTarihi;

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
    public BudgetItem getButceKalem() { return butceKalem; }
    public void setButceKalem(BudgetItem butceKalem) { this.butceKalem = butceKalem; }
    public TalepTur getTalepTur() { return talepTur; }
    public void setTalepTur(TalepTur talepTur) { this.talepTur = talepTur; }
    public String getAciklama() { return aciklama; }
    public void setAciklama(String aciklama) { this.aciklama = aciklama; }
    public BigDecimal getTutar() { return tutar; }
    public void setTutar(BigDecimal tutar) { this.tutar = tutar; }
    public ProjectDocument getTeknikSartname() { return teknikSartname; }
    public void setTeknikSartname(ProjectDocument teknikSartname) { this.teknikSartname = teknikSartname; }
    public SatinAlmaDurum getDurum() { return durum; }
    public void setDurum(SatinAlmaDurum durum) { this.durum = durum; }
    public LocalDate getOnayTarihi() { return onayTarihi; }
    public void setOnayTarihi(LocalDate onayTarihi) { this.onayTarihi = onayTarihi; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
