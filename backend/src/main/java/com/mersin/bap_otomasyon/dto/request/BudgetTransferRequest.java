package com.mersin.bap_otomasyon.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

public class BudgetTransferRequest {
    private UUID kaynakKalemId;
    private UUID hedefKalemId;
    private BigDecimal tutar;
    private String gerekce;

    public UUID getKaynakKalemId() { return kaynakKalemId; }
    public void setKaynakKalemId(UUID kaynakKalemId) { this.kaynakKalemId = kaynakKalemId; }
    public UUID getHedefKalemId() { return hedefKalemId; }
    public void setHedefKalemId(UUID hedefKalemId) { this.hedefKalemId = hedefKalemId; }
    public BigDecimal getTutar() { return tutar; }
    public void setTutar(BigDecimal tutar) { this.tutar = tutar; }
    public String getGerekce() { return gerekce; }
    public void setGerekce(String gerekce) { this.gerekce = gerekce; }
}
