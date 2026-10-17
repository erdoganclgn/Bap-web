package com.mersin.bap_otomasyon.entity;

import com.mersin.bap_otomasyon.entity.enums.TransferDurumu;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "budget_transfers")
public class BudgetTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proje_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kaynak_kalem_id", nullable = false)
    private BudgetItem kaynakKalem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hedef_kalem_id", nullable = false)
    private BudgetItem hedefKalem;

    @Column(name = "tutar", nullable = false, precision = 12, scale = 2)
    private BigDecimal tutar;

    @Column(name = "gerekce", columnDefinition = "TEXT")
    private String gerekce;

    @Enumerated(EnumType.STRING)
    @Column(name = "durum", nullable = false)
    private TransferDurumu durum = TransferDurumu.Beklemede;

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
    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }
    public BudgetItem getKaynakKalem() { return kaynakKalem; }
    public void setKaynakKalem(BudgetItem kaynakKalem) { this.kaynakKalem = kaynakKalem; }
    public BudgetItem getHedefKalem() { return hedefKalem; }
    public void setHedefKalem(BudgetItem hedefKalem) { this.hedefKalem = hedefKalem; }
    public BigDecimal getTutar() { return tutar; }
    public void setTutar(BigDecimal tutar) { this.tutar = tutar; }
    public String getGerekce() { return gerekce; }
    public void setGerekce(String gerekce) { this.gerekce = gerekce; }
    public TransferDurumu getDurum() { return durum; }
    public void setDurum(TransferDurumu durum) { this.durum = durum; }
    public LocalDate getKomisyonKararTarihi() { return komisyonKararTarihi; }
    public void setKomisyonKararTarihi(LocalDate komisyonKararTarihi) { this.komisyonKararTarihi = komisyonKararTarihi; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
