package com.mersin.bap_otomasyon.entity;

import com.mersin.bap_otomasyon.entity.enums.BelgeTuru;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "project_documents")
public class ProjectDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proje_id", nullable = false)
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(name = "belge_turu", nullable = false)
    private BelgeTuru belgeTuru;

    @Column(name = "dosya_adi", nullable = false, length = 255)
    private String dosyaAdi;

    @Column(name = "dosya_yolu", nullable = false, length = 500)
    private String dosyaYolu;

    @Column(name = "dosya_boyutu", nullable = false)
    private Integer dosyaBoyutu;

    @Column(name = "mime_type", nullable = false, length = 100)
    private String mimeType;

    @CreationTimestamp
    @Column(name = "yukleme_tarihi", nullable = false, updatable = false)
    private LocalDateTime yuklemeTarihi;

    @Column(name = "guncel_mi", nullable = false)
    private Boolean guncelMi = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }
    public BelgeTuru getBelgeTuru() { return belgeTuru; }
    public void setBelgeTuru(BelgeTuru belgeTuru) { this.belgeTuru = belgeTuru; }
    public String getDosyaAdi() { return dosyaAdi; }
    public void setDosyaAdi(String dosyaAdi) { this.dosyaAdi = dosyaAdi; }
    public String getDosyaYolu() { return dosyaYolu; }
    public void setDosyaYolu(String dosyaYolu) { this.dosyaYolu = dosyaYolu; }
    public Integer getDosyaBoyutu() { return dosyaBoyutu; }
    public void setDosyaBoyutu(Integer dosyaBoyutu) { this.dosyaBoyutu = dosyaBoyutu; }
    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }
    public LocalDateTime getYuklemeTarihi() { return yuklemeTarihi; }
    public Boolean getGuncelMi() { return guncelMi; }
    public void setGuncelMi(Boolean guncelMi) { this.guncelMi = guncelMi; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
