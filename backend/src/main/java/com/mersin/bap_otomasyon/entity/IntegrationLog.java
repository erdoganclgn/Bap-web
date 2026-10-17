package com.mersin.bap_otomasyon.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "integration_logs")
public class IntegrationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "entegrasyon_ad", nullable = false, length = 100)
    private String entegrasyonAd;

    @Column(name = "istek_url", length = 500)
    private String istekUrl;

    @Column(name = "istek_govde", columnDefinition = "jsonb")
    private String istekGovde;

    @Column(name = "yanit_kodu")
    private Integer yanitKodu;

    @Column(name = "yanit_govde", columnDefinition = "jsonb")
    private String yanitGovde;

    @Column(name = "hata_mesaji", columnDefinition = "TEXT")
    private String hataMesaji;

    @Column(name = "basarili_mi", nullable = false)
    private Boolean basariliMi = false;

    @Column(name = "deneme_sayisi", nullable = false)
    private Integer denemeSayisi = 1;

    @Column(name = "zaman", nullable = false)
    private LocalDateTime zaman = LocalDateTime.now();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEntegrasyonAd() { return entegrasyonAd; }
    public void setEntegrasyonAd(String entegrasyonAd) { this.entegrasyonAd = entegrasyonAd; }
    public String getIstekUrl() { return istekUrl; }
    public void setIstekUrl(String istekUrl) { this.istekUrl = istekUrl; }
    public String getIstekGovde() { return istekGovde; }
    public void setIstekGovde(String istekGovde) { this.istekGovde = istekGovde; }
    public Integer getYanitKodu() { return yanitKodu; }
    public void setYanitKodu(Integer yanitKodu) { this.yanitKodu = yanitKodu; }
    public String getYanitGovde() { return yanitGovde; }
    public void setYanitGovde(String yanitGovde) { this.yanitGovde = yanitGovde; }
    public String getHataMesaji() { return hataMesaji; }
    public void setHataMesaji(String hataMesaji) { this.hataMesaji = hataMesaji; }
    public Boolean getBasariliMi() { return basariliMi; }
    public void setBasariliMi(Boolean basariliMi) { this.basariliMi = basariliMi; }
    public Integer getDenemeSayisi() { return denemeSayisi; }
    public void setDenemeSayisi(Integer denemeSayisi) { this.denemeSayisi = denemeSayisi; }
    public LocalDateTime getZaman() { return zaman; }
    public void setZaman(LocalDateTime zaman) { this.zaman = zaman; }
}
