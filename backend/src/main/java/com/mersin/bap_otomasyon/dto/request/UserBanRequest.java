package com.mersin.bap_otomasyon.dto.request;

import java.time.LocalDate;

public class UserBanRequest {
    private LocalDate yasakBitisTarihi;

    public LocalDate getYasakBitisTarihi() { return yasakBitisTarihi; }
    public void setYasakBitisTarihi(LocalDate yasakBitisTarihi) { this.yasakBitisTarihi = yasakBitisTarihi; }
}