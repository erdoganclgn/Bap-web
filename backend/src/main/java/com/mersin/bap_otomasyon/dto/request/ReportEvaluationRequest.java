package com.mersin.bap_otomasyon.dto.request;

public class ReportEvaluationRequest {
    private String karar;
    private String notlar;

    public String getKarar() { return karar; }
    public void setKarar(String karar) { this.karar = karar; }
    public String getNotlar() { return notlar; }
    public void setNotlar(String notlar) { this.notlar = notlar; }
}