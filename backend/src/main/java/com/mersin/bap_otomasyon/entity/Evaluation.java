package com.mersin.bap_otomasyon.entity;

import com.mersin.bap_otomasyon.entity.enums.DegerlendiriciTip;
import com.mersin.bap_otomasyon.entity.enums.DegerlendirmeKarar;
import com.mersin.bap_otomasyon.entity.enums.DegerlendirmeTuru;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "evaluations")
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proje_id", nullable = false)
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(name = "degerlendirici_tip", nullable = false)
    private DegerlendiriciTip degerlendiriciTip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "degerlendirici_id", nullable = false)
    private User degerlendirici;

    @Enumerated(EnumType.STRING)
    @Column(name = "degerlendirme_turu", nullable = false)
    private DegerlendirmeTuru degerlendirmeTuru;

    @Column(name = "puan")
    private Integer puan;

    @Column(name = "gorus", columnDefinition = "TEXT")
    private String gorus;

    @Enumerated(EnumType.STRING)
    @Column(name = "karar")
    private DegerlendirmeKarar karar;

    @Column(name = "davet_tarihi")
    private LocalDateTime davetTarihi;

    @Column(name = "davet_cevap_tarihi")
    private LocalDateTime davetCevapTarihi;

    @Column(name = "davet_cevap_durumu")
    private Boolean davetCevapDurumu;

    @Column(name = "teslim_tarihi")
    private LocalDateTime teslimTarihi;

    @Column(name = "odeme_yapildi_mi", nullable = false)
    private Boolean odemeYapildiMi = false;

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
    public DegerlendiriciTip getDegerlendiriciTip() { return degerlendiriciTip; }
    public void setDegerlendiriciTip(DegerlendiriciTip degerlendiriciTip) { this.degerlendiriciTip = degerlendiriciTip; }
    public User getDegerlendirici() { return degerlendirici; }
    public void setDegerlendirici(User degerlendirici) { this.degerlendirici = degerlendirici; }
    public DegerlendirmeTuru getDegerlendirmeTuru() { return degerlendirmeTuru; }
    public void setDegerlendirmeTuru(DegerlendirmeTuru degerlendirmeTuru) { this.degerlendirmeTuru = degerlendirmeTuru; }
    public Integer getPuan() { return puan; }
    public void setPuan(Integer puan) { this.puan = puan; }
    public String getGorus() { return gorus; }
    public void setGorus(String gorus) { this.gorus = gorus; }
    public DegerlendirmeKarar getKarar() { return karar; }
    public void setKarar(DegerlendirmeKarar karar) { this.karar = karar; }
    public LocalDateTime getDavetTarihi() { return davetTarihi; }
    public void setDavetTarihi(LocalDateTime davetTarihi) { this.davetTarihi = davetTarihi; }
    public LocalDateTime getDavetCevapTarihi() { return davetCevapTarihi; }
    public void setDavetCevapTarihi(LocalDateTime davetCevapTarihi) { this.davetCevapTarihi = davetCevapTarihi; }
    public Boolean getDavetCevapDurumu() { return davetCevapDurumu; }
    public void setDavetCevapDurumu(Boolean davetCevapDurumu) { this.davetCevapDurumu = davetCevapDurumu; }
    public LocalDateTime getTeslimTarihi() { return teslimTarihi; }
    public void setTeslimTarihi(LocalDateTime teslimTarihi) { this.teslimTarihi = teslimTarihi; }
    public Boolean getOdemeYapildiMi() { return odemeYapildiMi; }
    public void setOdemeYapildiMi(Boolean odemeYapildiMi) { this.odemeYapildiMi = odemeYapildiMi; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
