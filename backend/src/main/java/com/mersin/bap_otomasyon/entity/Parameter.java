package com.mersin.bap_otomasyon.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "parameters",
       uniqueConstraints = @UniqueConstraint(columnNames = {"grup", "anahtar"}))
public class Parameter {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "grup", nullable = false, length = 100)
    private String grup;

    @Column(name = "anahtar", nullable = false, length = 255)
    private String anahtar;

    @Column(name = "deger", columnDefinition = "jsonb", nullable = false)
    private String deger;

    @Column(name = "aciklama", columnDefinition = "TEXT")
    private String aciklama;

    @Column(name = "aktif", nullable = false)
    private Boolean aktif = true;

    @Column(name = "guncellenme_tarihi", nullable = false)
    private LocalDateTime guncellenmeTarihi = LocalDateTime.now();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getGrup() { return grup; }
    public void setGrup(String grup) { this.grup = grup; }
    public String getAnahtar() { return anahtar; }
    public void setAnahtar(String anahtar) { this.anahtar = anahtar; }
    public String getDeger() { return deger; }
    public void setDeger(String deger) { this.deger = deger; }
    public String getAciklama() { return aciklama; }
    public void setAciklama(String aciklama) { this.aciklama = aciklama; }
    public Boolean getAktif() { return aktif; }
    public void setAktif(Boolean aktif) { this.aktif = aktif; }
    public LocalDateTime getGuncellenmeTarihi() { return guncellenmeTarihi; }
    public void setGuncellenmeTarihi(LocalDateTime guncellenmeTarihi) { this.guncellenmeTarihi = guncellenmeTarihi; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
