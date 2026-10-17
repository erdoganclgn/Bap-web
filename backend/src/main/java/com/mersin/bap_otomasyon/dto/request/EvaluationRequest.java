package com.mersin.bap_otomasyon.dto.request;

import java.util.UUID;

public class EvaluationRequest {
    private UUID projeId;
    private String degerlendirmeTuru;
    private Integer puan;
    private String gorus;
    private String karar;

    public UUID getProjeId() { return projeId; }
    public void setProjeId(UUID projeId) { this.projeId = projeId; }
    public String getDegerlendirmeTuru() { return degerlendirmeTuru; }
    public void setDegerlendirmeTuru(String degerlendirmeTuru) { this.degerlendirmeTuru = degerlendirmeTuru; }
    public Integer getPuan() { return puan; }
    public void setPuan(Integer puan) { this.puan = puan; }
    public String getGorus() { return gorus; }
    public void setGorus(String gorus) { this.gorus = gorus; }
    public String getKarar() { return karar; }
    public void setKarar(String karar) { this.karar = karar; }
}
