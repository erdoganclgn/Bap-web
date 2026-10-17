package com.mersin.bap_otomasyon.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "alt_alanlar",
       uniqueConstraints = @UniqueConstraint(columnNames = {"temel_alan_id", "kod"}))
public class AltAlan {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "temel_alan_id", nullable = false)
    private TemelAlan temelAlan;

    @Column(name = "kod", nullable = false, length = 20)
    private String kod;

    @Column(name = "ad", nullable = false, length = 255)
    private String ad;

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
    public TemelAlan getTemelAlan() { return temelAlan; }
    public void setTemelAlan(TemelAlan temelAlan) { this.temelAlan = temelAlan; }
    public String getKod() { return kod; }
    public void setKod(String kod) { this.kod = kod; }
    public String getAd() { return ad; }
    public void setAd(String ad) { this.ad = ad; }
    public Boolean getAktif() { return aktif; }
    public void setAktif(Boolean aktif) { this.aktif = aktif; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
