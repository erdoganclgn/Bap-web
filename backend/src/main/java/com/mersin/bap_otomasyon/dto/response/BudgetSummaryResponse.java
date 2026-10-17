package com.mersin.bap_otomasyon.dto.response;

import java.math.BigDecimal;

public class BudgetSummaryResponse {
    private BigDecimal toplam;
    private BigDecimal harcanan;
    private BigDecimal kalan;

    public BudgetSummaryResponse(BigDecimal toplam, BigDecimal harcanan, BigDecimal kalan) {
        this.toplam = toplam;
        this.harcanan = harcanan;
        this.kalan = kalan;
    }

    public BigDecimal getToplam() { return toplam; }
    public BigDecimal getHarcanan() { return harcanan; }
    public BigDecimal getKalan() { return kalan; }
}