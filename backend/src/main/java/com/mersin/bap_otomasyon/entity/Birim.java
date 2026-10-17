package com.mersin.bap_otomasyon.entity;

import com.mersin.bap_otomasyon.entity.enums.BirimTuru;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "birimler")
public class Birim {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "ad", nullable = false, length = 255)
    private String ad;

    @Column(name = "kod", nullable = false, unique = true, length = 20)
    private String kod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ust_birim_id")
    private Birim ustBirim;

    @Enumerated(EnumType.STRING)
    @Column(name = "birim_turu")
    private BirimTuru birimTuru;

    @Column(name = "aktif", nullable = false)
    private Boolean aktif = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getAd() { return ad; }
    public void setAd(String ad) { this.ad = ad; }
    public String getKod() { return kod; }
    public void setKod(String kod) { this.kod = kod; }
    public Birim getUstBirim() { return ustBirim; }
    public void setUstBirim(Birim ustBirim) { this.ustBirim = ustBirim; }
    public BirimTuru getBirimTuru() { return birimTuru; }
    public void setBirimTuru(BirimTuru birimTuru) { this.birimTuru = birimTuru; }
    public Boolean getAktif() { return aktif; }
    public void setAktif(Boolean aktif) { this.aktif = aktif; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
