package com.mersin.bap_otomasyon.dto.request;

import java.math.BigDecimal;

public class BudgetItemRequest {
    private String kalemKodu;
    private String aciklama;
    private BigDecimal birimFiyat;
    private BigDecimal miktar;
    private BigDecimal kdvOrani;

    public String getKalemKodu() { return kalemKodu; }
    public void setKalemKodu(String kalemKodu) { this.kalemKodu = kalemKodu; }
    public String getAciklama() { return aciklama; }
    public void setAciklama(String aciklama) { this.aciklama = aciklama; }
    public BigDecimal getBirimFiyat() { return birimFiyat; }
    public void setBirimFiyat(BigDecimal birimFiyat) { this.birimFiyat = birimFiyat; }
    public BigDecimal getMiktar() { return miktar; }
    public void setMiktar(BigDecimal miktar) { this.miktar = miktar; }
    public BigDecimal getKdvOrani() { return kdvOrani; }
    public void setKdvOrani(BigDecimal kdvOrani) { this.kdvOrani = kdvOrani; }
}