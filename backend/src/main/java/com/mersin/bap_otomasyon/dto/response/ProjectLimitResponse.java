package com.mersin.bap_otomasyon.dto.response;

import java.util.UUID;

public class ProjectLimitResponse {
    private UUID projeId;
    private long aktifProjeSayisi;
    private Integer limit;
    private Boolean izinVarMi;

    public ProjectLimitResponse(UUID projeId, long aktifProjeSayisi, Integer limit, Boolean izinVarMi) {
        this.projeId = projeId;
        this.aktifProjeSayisi = aktifProjeSayisi;
        this.limit = limit;
        this.izinVarMi = izinVarMi;
    }

    public UUID getProjeId() { return projeId; }
    public long getAktifProjeSayisi() { return aktifProjeSayisi; }
    public Integer getLimit() { return limit; }
    public Boolean getIzinVarMi() { return izinVarMi; }
}