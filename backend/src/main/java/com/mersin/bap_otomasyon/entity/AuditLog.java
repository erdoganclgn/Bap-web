package com.mersin.bap_otomasyon.entity;

import com.mersin.bap_otomasyon.entity.enums.IslemTur;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kullanici_id")
    private UUID kullaniciId;

    @Enumerated(EnumType.STRING)
    @Column(name = "islem_turu", nullable = false)
    private IslemTur islemTur;

    @Column(name = "varlik_turu", nullable = false, length = 50)
    private String varlikTuru;

    @Column(name = "varlik_id", nullable = false)
    private UUID varlikId;

    @Column(name = "eski_deger", columnDefinition = "jsonb")
    private String eskiDeger;

    @Column(name = "yeni_deger", columnDefinition = "jsonb")
    private String yeniDeger;

    @Column(name = "ip_adresi", length = 45)
    private String ipAdresi;

    @Column(name = "zaman", nullable = false)
    private LocalDateTime zaman = LocalDateTime.now();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public UUID getKullaniciId() { return kullaniciId; }
    public void setKullaniciId(UUID kullaniciId) { this.kullaniciId = kullaniciId; }
    public IslemTur getIslemTur() { return islemTur; }
    public void setIslemTur(IslemTur islemTur) { this.islemTur = islemTur; }
    public String getVarlikTuru() { return varlikTuru; }
    public void setVarlikTuru(String varlikTuru) { this.varlikTuru = varlikTuru; }
    public UUID getVarlikId() { return varlikId; }
    public void setVarlikId(UUID varlikId) { this.varlikId = varlikId; }
    public String getEskiDeger() { return eskiDeger; }
    public void setEskiDeger(String eskiDeger) { this.eskiDeger = eskiDeger; }
    public String getYeniDeger() { return yeniDeger; }
    public void setYeniDeger(String yeniDeger) { this.yeniDeger = yeniDeger; }
    public String getIpAdresi() { return ipAdresi; }
    public void setIpAdresi(String ipAdresi) { this.ipAdresi = ipAdresi; }
    public LocalDateTime getZaman() { return zaman; }
    public void setZaman(LocalDateTime zaman) { this.zaman = zaman; }
}
